package com.seatig.controller;


import com.plaid.client.PlaidClient;
import com.plaid.client.request.AccountsBalanceGetRequest;
import com.plaid.client.request.IdentityGetRequest;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.response.*;
import com.quanaxy.state.GrantsState;
import com.seatig.common.NodeRPCConnection;
import com.seatig.common.Result;
import com.seatig.common.ResultGenerator;
import com.seatig.domain.*;
import com.seatig.service.QuanaxyService;
import com.seatig.utils.JSONUtils;

import net.corda.core.messaging.CordaRPCOps;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import retrofit2.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Controller
public class QuanaxyController {

    @Autowired
    QuanaxyService quanaxyService;


    private static final String PUBLIC_KEY = "c42640182cd941551056877f484537";
    private static final String CLIENT_ID = "5d5649c59a2f6e0013d58787";
    private static final String SECRET = "dccde2988145aab9e9cf2bbb5a4ca2";


    @RequestMapping(value = "testdd", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    Result testDD(HttpServletRequest request, HttpServletResponse response) throws ParseException {

        String UTCTime = "2017-11-09T23:16:03.562Z";
        String localTimePatten = "yyyy-MM-dd'T'HH:mm:ss.SSS";
        SimpleDateFormat sdf = new SimpleDateFormat(localTimePatten);

        System.out.println("UTC=" + UTCTime + "LOCAL_TIME=" + sdf.parse(UTCTime));

        return ResultGenerator.getSuccessResult();
    }

    /**
     * @Description:
     * @Param: 根据时间范围返回范围内的交易信息 分页
     * @return: com.seatig.common.Result
     * @Author: glenn
     * @Date: 2019/8/23
     */
    @RequestMapping(value = "get-transactions", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Result getTransactions(String startDateStr, String endDateStr, String pageNum, String pageSize, String accessToken, HttpServletRequest request, HttpServletResponse response) {

        PlaidClient plaidClient;

        SimpleDateFormat simpleDateFormat = new
                SimpleDateFormat("yyyy-MM-dd");

        try {
            //交易的开始和结束时间
            Date startDate = simpleDateFormat.parse(startDateStr);
            Date endDate = simpleDateFormat.parse(endDateStr);

            // 通过bulid方法生成PlaidClient
            plaidClient = PlaidClient.newBuilder()
                    .clientIdAndSecret(CLIENT_ID, SECRET)
                    .publicKey(PUBLIC_KEY)
                    .sandboxBaseUrl() // sandbox Plaid environment
                    .logLevel(HttpLoggingInterceptor.Level.BODY)
                    .build();
            //操作count和offset参数以进行分页
            //并检索所有事务可用数据
            Response<TransactionsGetResponse> transactionsResponse =
                    plaidClient.service().transactionsGet(
                            new TransactionsGetRequest(
                                    accessToken,
                                    startDate,
                                    endDate)
                                    .withAccountIds(Arrays.asList("account_id"))
                                    .withCount(Integer.parseInt(pageSize))
                                    .withOffset(Integer.parseInt(pageNum))).execute();

            for (TransactionsGetResponse.Transaction txn :
                    transactionsResponse.body().getTransactions()) {


            }


        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ResultGenerator.getSuccessResult();
    }


    /**
     * @Description: 交换public_token接口
     * @Param: [request, response]
     * @return: com.seatig.common.Result
     * @Author: glenn
     * @Date: 2019/8/23
     */
    @RequestMapping(value = "get_access_token", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    Result getAccessToken(@RequestBody Map map) throws IOException {

        PlaidClient plaidClient;
        String publicToken;
        String accessToken;
        String itemId;


        //通过public token交换私有密钥

        publicToken = (String) map.get("public_token");

        // 通过bulid方法生成PlaidClient
        plaidClient = PlaidClient.newBuilder()
                .clientIdAndSecret(CLIENT_ID, SECRET)
                .publicKey(PUBLIC_KEY)
                .sandboxBaseUrl() // sandbox Plaid environment
                .logLevel(HttpLoggingInterceptor.Level.BODY)
                .build();

        // 交换 public_token 和 access_token

        Response<ItemPublicTokenExchangeResponse> execute = plaidClient
                .service()
                .itemPublicTokenExchange(
                        new ItemPublicTokenExchangeRequest(publicToken))
                .execute();


        accessToken = execute.body().getAccessToken();
        itemId = execute.body().getItemId();

        System.out.println("服务器收到请求，并将public_token替换 accessToken=" + accessToken + "   itemId=" + itemId);

        return ResultGenerator.getSuccessResult();
    }


    /**
     * @Description: 检索余额请求
     * @Param: [request, response]
     * @return: com.seatig.common.Result
     * @Author: glenn
     * @Date: 2019/8/26
     */
    @RequestMapping(value = "accounts_balance_get", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Result getAccountsBalance(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PlaidClient plaidClient;

        // 通过bulid方法生成PlaidClient
        plaidClient = PlaidClient.newBuilder()
                .clientIdAndSecret(CLIENT_ID, SECRET)
                .publicKey(PUBLIC_KEY)
                .sandboxBaseUrl() // sandbox Plaid environment
                .logLevel(HttpLoggingInterceptor.Level.BODY)
                .build();
        //为每个相关帐户提取实时余额信息
        Response<AccountsBalanceGetResponse> plaidResponse =
                plaidClient.service().accountsBalanceGet(
                        new AccountsBalanceGetRequest("ACCESS_TOKEN"))
                        .execute();
        List<Account> accounts = plaidResponse.body().getAccounts();

        return ResultGenerator.getSuccessResult(accounts);
    }


    /**
     * @Description: 检索与金融机构，包括姓名，电子邮件地址，电话号码和地址的文件的各种账户持有人的信息。
     * @Param: [request, response]
     * @return: com.seatig.common.Result
     * @Author: glenn
     * @Date: 2019/8/26
     */
    @RequestMapping(value = "identity_get", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Result getIdentity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PlaidClient plaidClient;


        // 通过bulid方法生成PlaidClient
        plaidClient = PlaidClient.newBuilder()
                .clientIdAndSecret(CLIENT_ID, SECRET)
                .publicKey(PUBLIC_KEY)
                .sandboxBaseUrl() // sandbox Plaid environment
                .logLevel(HttpLoggingInterceptor.Level.BODY)
                .build();
        //拉取Item中的标识数据
        Response<IdentityGetResponse> plaidResponse =
                plaidClient.service().identityGet(
                        new IdentityGetRequest("ACCESS_TOKEN")
                ).execute();
        List<IdentityGetResponse.AccountWithOwners> accounts =
                plaidResponse.body().getAccounts();
        for (IdentityGetResponse.AccountWithOwners account : accounts) {
            List<IdentityGetResponse.Identity> identities =
                    account.getOwners();

        }

        return ResultGenerator.getSuccessResult();
    }

    /*  */

    /**
     * @Description: 返回当前节点信息
     * @Param: []
     * @return: com.seatig.quanaxy.common.Result
     * @Author: glenn
     * @Date: 2019/3/12
     *//*
    @RequestMapping(value = "me", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String me(HttpServletRequest request, HttpServletResponse response) {
        Party party = rpcOps.nodeInfo().getLegalIdentities().get(0);

        return JSONUtils.beanToJson(party.toString());
    }


    @RequestMapping(value = "issuers", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String issuers(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        String client = request.getParameter("client");
        String issuer = request.getParameter("issuer");
        String monitorparty = request.getParameter("monitorparty");
        String contractName = request.getParameter("contractName");
        String time = request.getParameter("instant");
        String value = request.getParameter("value");
        String hash = request.getParameter("hash");
        BigDecimal amount = new BigDecimal(value);
        Instant instant = Instant.parse(time);


        Set<Party> clients = rpcOps.partiesFromName(client, false);
        Set<Party> issuers = rpcOps.partiesFromName(issuer, false);
        Set<Party> monitorpartys = rpcOps.partiesFromName(monitorparty, false);
        Party clientParty = clients.iterator().next();
        Party issuerParty = issuers.iterator().next();
        Party monitorParty = monitorpartys.iterator().next();
        System.out.println("检查节点是否存在：client:" + clientParty.getName() + "   issuer:" + issuerParty.getName() + "    monitor:" + monitorParty.getName());


        try {
            rpcOps.startFlowDynamic(
                    IssuranceFlowGlenn.class,
                    clientParty, issuerParty, monitorParty, contractName, instant, amount, hash
            );
        } catch (Exception e) {
            System.out.println("异常！！！");
            result.setResMsg("异常，失败");
            System.out.println(e.getMessage());
        }
        result.setResMsg("成功");
        return JSONUtils.beanToJson(result);
    }

    @RequestMapping(value = "contract-info", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String contractInfo(HttpServletRequest request, HttpServletResponse response) {

        Result result = new Result();
        int clientNum = quanaxyService.findClientTotal();
        int supplierNum = quanaxyService.findSupplierTotal();
        int contractNum = quanaxyService.findContractTotal();
        //int nodeNum = quanaxyService.findNodeTotal();
        Map<String, Integer> map = new HashMap<>();
        map.put("clientNum", clientNum);
        map.put("supplierNum", supplierNum);
        map.put("contractNum", contractNum);
        result.setResCode(0);
        result.setResMsg("成功");
        result.setData(map);

        return JSONUtils.beanToJson(result);
    }

    @RequestMapping(value = "test", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String test(HttpServletRequest request, HttpServletResponse response) {

        String row = request.getParameter("row");
        String json;
        if (row.equals("prant")) {
            json = "{\n" +
                    "\t\"total\": 3,\n" +
                    "\t\"rows\": [{\n" +
                    "\t\t\t\"id\": \"FI-SW-01\",\n" +
                    "\t\t\t\"companyName\": \"Koi\",\n" +
                    "\t\t\t\"contractName\": 10.00,\n" +
                    "\t\t\t\"client\": \"P\",\n" +
                    "\t\t\t\"supplier\": 36.50,\n" +
                    "\t\t\t\"contractAmount\": \"Large\",\n" +
                    "\t\t\t\"duedate\": \"EST-1\",\n" +
                    "\t\t\t\"attachment\": \"EST-1\",\n" +
                    "\t\t\t\"buyer\": \"EST-1\",\n" +
                    "\t\t\t\"seller\": \"EST-1\",\n" +
                    "\t\t\t\"owner\": \"EST-1\",\n" +
                    "\t\t\t\"transactionAmount\": 7262839\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"id\": \"FI-SW-01\",\n" +
                    "\t\t\t\"companyName\": \"Koi\",\n" +
                    "\t\t\t\"contractName\": 10.00,\n" +
                    "\t\t\t\"client\": \"P\",\n" +
                    "\t\t\t\"supplier\": 36.50,\n" +
                    "\t\t\t\"contractAmount\": \"Large\",\n" +
                    "\t\t\t\"duedate\": \"EST-1\",\n" +
                    "\t\t\t\"attachment\": \"EST-1\",\n" +
                    "\t\t\t\"buyer\": \"EST-1\",\n" +
                    "\t\t\t\"seller\": \"EST-1\",\n" +
                    "\t\t\t\"owner\": \"EST-1\",\n" +
                    "\t\t\t\"transactionAmount\": 7262839\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"id\": \"FI-SW-01\",\n" +
                    "\t\t\t\"companyName\": \"Koi\",\n" +
                    "\t\t\t\"contractName\": 10.00,\n" +
                    "\t\t\t\"client\": \"P\",\n" +
                    "\t\t\t\"supplier\": 36.50,\n" +
                    "\t\t\t\"contractAmount\": \"Large\",\n" +
                    "\t\t\t\"duedate\": \"EST-1\",\n" +
                    "\t\t\t\"attachment\": \"EST-1\",\n" +
                    "\t\t\t\"buyer\": \"EST-1\",\n" +
                    "\t\t\t\"seller\": \"EST-1\",\n" +
                    "\t\t\t\"owner\": \"EST-1\",\n" +
                    "\t\t\t\"transactionAmount\": 7262839\n" +
                    "\t\t}\n" +
                    "\t]\n" +
                    "}";
        } else {
            json = "{\n" +
                    "\t\"total\": 2,\n" +
                    "\t\"rows\": [{\n" +
                    "\t\t\t\"id\": \"linear\",\n" +
                    "\t\t\t\"companyName\": \"CRCC\",\n" +
                    "\t\t\t\"contractName\": \"Railway construction loan contract\",\n" +
                    "\t\t\t\"client\": \"CRCC\",\n" +
                    "\t\t\t\"supplier\": \"Agricultural Bank of China\",\n" +
                    "\t\t\t\"contractAmount\": 12,398,123,\n" +
                    "\t\t\t\"duedate\": \"2020-09-09\",\n" +
                    "\t\t\t\"attachment\": \"Download\",\n" +
                    "\t\t\t\"buyer\": \"Agricultural Bank of China\",\n" +
                    "\t\t\t\"seller\": \"CRCC\",\n" +
                    "\t\t\t\"owner\": \"CRCC\",\n" +
                    "\t\t\t\"transactionAmount\": 7,262,839,\n" +
                    "\t\t\t\"status\": \"CONSUMED\"\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"id\": \"linear\",\n" +
                    "\t\t\t\"companyName\": \"公司名称\",\n" +
                    "\t\t\t\"contractName\": \"合同名称\",\n" +
                    "\t\t\t\"client\": \"客户\",\n" +
                    "\t\t\t\"supplier\": \"供应商\",\n" +
                    "\t\t\t\"contractAmount\": 12398123,\n" +
                    "\t\t\t\"duedate\": \"2020-09-09\",\n" +
                    "\t\t\t\"attachment\": \"EST-1\",\n" +
                    "\t\t\t\"buyer\": \"买方\",\n" +
                    "\t\t\t\"seller\": \"卖方\",\n" +
                    "\t\t\t\"owner\": \"拥有者\",\n" +
                    "\t\t\t\"transactionAmount\": 7262839,\n" +
                    "\t\t\t\"status\": \"已消耗\"\n" +
                    "\n" +
                    "\t\t}\n" +
                    "\t]\n" +
                    "}";
        }

        return json;
    }


    @RequestMapping(value = "trading-info", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String tradingInfo(HttpServletRequest request, HttpServletResponse response) {

        Result result = new Result();
        List<State> state = quanaxyService.findState();
        Map<String, Object> map = new HashedMap();


        return JSONUtils.beanToJson(result);
    }*/
    @RequestMapping(value = "transaction-info", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String transactionInfo(HttpServletRequest request, HttpServletResponse response) {

        String linrarId = request.getParameter("linearId");
        ContractInfo contractInfo = null;
        if (linrarId == null) {
            List<GrantState> states = quanaxyService.findTransaction();
            contractInfo = new ContractInfo();
            contractInfo.setTotal(states.size());
            contractInfo.setRows(states);
        } else {
            List<GrantState> states = quanaxyService.findTransactionByid(linrarId);
            contractInfo = new ContractInfo();
            contractInfo.setRows(states);
            contractInfo.setTotal(states.size());
        }


        System.out.println(JSONUtils.beanToJson(contractInfo));

        return JSONUtils.beanToJson(contractInfo);
    }

    @RequestMapping(value = "hehe", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String getElementList(HttpServletRequest request, HttpServletResponse response) {

        Result result = new Result();
        List<List<String>> list = new ArrayList<>();
        int i = 0;
        while (i < 10) {
            List<String> list1 = new ArrayList<>();
            list1.add("2019-01-0" + 1 + i);
            list1.add(new Random().nextInt(100) + "");
            list1.add(new Random().nextInt(100) + "");
            list.add(list1);
            i++;
        }
        List<String> list1 = new ArrayList<>();
        list1.add("product");
        list1.add("融资收益");
        list1.add("数字化收益");
        list.add(0, list1);
        result.setData(list);

        return JSONUtils.beanToJson(result);
    }
   /* @RequestMapping(value = "monitor", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String monitor(HttpServletRequest request, HttpServletResponse response) {

        String row = request.getParameter("row");
        if (row == null) {
            List<Monitor> monitors = quanaxyService.findMonitor();
            MonitorInfo monitorInfo = new MonitorInfo();
            monitorInfo.setRows(monitors);
            monitorInfo.setTotal(monitors.size());
            return JSONUtils.beanToJson(monitorInfo);
        } else {
            List<State> list = quanaxyService.findContract();
            ContractInfo contractInfo = new ContractInfo();
            contractInfo.setTotal(list.size());
            contractInfo.setRows(list);
            return JSONUtils.beanToJson(contractInfo);
        }

    }


    @RequestMapping(value = "market", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String market(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        int timeCode = Integer.parseInt(request.getParameter("timeCode"));
        Map<String, List<String>> map = quanaxyService.findMarketIndicator(timeCode);
        result.setResCode(0);
        result.setResMsg("成功");
        result.setData(map);


        return JSONUtils.beanToJson(result);
    }


    @RequestMapping(value = "client-market", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String clientMarket(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        int timeCode = Integer.parseInt(request.getParameter("timeCode"));
        ClientTrend clientTrend = quanaxyService.findClientMarketIndicator(timeCode);
        result.setResCode(0);
        result.setResMsg("成功");
        result.setData(clientTrend);


        return JSONUtils.beanToJson(result);
    }

    @RequestMapping(value = "index-market", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String getElementList(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Map<String, String> map = quanaxyService.indexMarket();
        result.setData(map);
        return JSONUtils.beanToJson(result);
    }


    @RequestMapping(value = "platform-market", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String platformMarket(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        int timeCode = Integer.parseInt(request.getParameter("timeCode"));
        Map<String, List<String>> map = quanaxyService.findMarket(timeCode);
        result.setResCode(0);
        result.setResMsg("成功");
        result.setData(map);
        return JSONUtils.beanToJson(result);
    }


    @RequestMapping(value = "corda-info", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String cordaInfo(HttpServletRequest request, HttpServletResponse response) {

        Result result = new Result();
        List<Party> notarys = quanaxyService.findNotary();
        List<String> flows = quanaxyService.findFlow();
        List<NodeInfo> nodes = quanaxyService.findNode();
        List<QXState> qxStates = new ArrayList<>();

        Map<String, Map<String, Object>> map = new HashedMap();
        Map<String, Object> notaryMap = new HashedMap();
        Map<String, Object> nodeMap = new HashedMap();
        Map<String, Object> stateMap = new HashedMap();
        for (Party p : notarys) {
            notaryMap.put(p.getOwningKey().toString(), p.getName().toString());
        }


        for (NodeInfo nodeInfo : nodes) {
            String nodeName = nodeInfo.getLegalIdentities().get(0).getName().toString();
            String nodeKey = nodeInfo.getLegalIdentities().get(0).getOwningKey().toString();
            nodeMap.put(nodeKey, nodeName);
        }

        map.put("notarys", notaryMap);
        map.put("nodes", nodeMap);
        map.put("states", stateMap);
        result.setResCode(0);
        result.setData(map);
        result.setResMsg("成功");
        return JSONUtils.beanToJson(result);
    }


    @RequestMapping(value = "client-trend", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String clientTrend(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        String clientId = request.getParameter("clientId");
        ClientTrend clientTrend = null;
        switch (clientId) {
            case "O=PartyA,L=London,C=GB":
                clientTrend = new ClientTrend(1, 102002, 288900, 100020);
                break;
            case "O=PartyB,L=New York,C=US":
                clientTrend = new ClientTrend(2, 202002, 388900, 400020);
                break;
            case "O=PartyC,L=Shang Hai,C=CH":
                clientTrend = new ClientTrend(3, 120023, 12981239, 1782389);
                break;
            case "O=PartyD,L=Shang Hai,C=CH":
                clientTrend = new ClientTrend(4, 1298321, 7823874, 9023948);
                break;
            case "O=PartyE,L=Miany Yang,C=CH":
                clientTrend = new ClientTrend(5, 9812389, 7218939, 89128399);
                break;
            case "O=PartyF,L=Miany Yang,C=CH":
                clientTrend = new ClientTrend(6, 192822, 2393033, 3929999);
                break;
            case "O=PartyF,L=Chen Du,C=CH":
                clientTrend = new ClientTrend(7, 87218, 89000, 929038);
                break;

            default:
                System.out.println(clientId);
        }

        result.setResMsg("成功");
        result.setResCode(0);
        result.setData(clientTrend);
        return JSONUtils.beanToJson(result);

    }

    @RequestMapping(value = "getdata", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String getData(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();

        File file = new File("/Users/zxx/Desktop/Quanaxy_JavaWeb/quanaxy_web/data/data.json");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }*/


}
