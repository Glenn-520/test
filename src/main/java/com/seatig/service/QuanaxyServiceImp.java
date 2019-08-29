package com.seatig.service;

import com.google.common.collect.ImmutableList;
import com.quanaxy.state.GrantsState;
import com.quanaxy.state.QXState;
import com.seatig.common.NodeRPCConnection;
import com.seatig.dao.QuanaxyDao;
import com.seatig.domain.*;
import com.seatig.utils.Utils;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.Party;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.node.NodeInfo;
import net.corda.core.node.services.Vault;
import net.corda.core.node.services.vault.ColumnPredicate;
import net.corda.core.node.services.vault.PageSpecification;
import net.corda.core.node.services.vault.QueryCriteria;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class QuanaxyServiceImp implements QuanaxyService {

    @Autowired
    QuanaxyDao quanaxyDao;

    CordaRPCOps rpcOps;


    public QuanaxyServiceImp(NodeRPCConnection nodeRPCConnection) {
    }


    @Override
    public int findClientTotal() {
        return quanaxyDao.findClientTotal();
    }

    @Override
    public int findSupplierTotal() {
        return quanaxyDao.findSupplierTotal();
    }

    @Override
    public int findContractTotal() {
        return quanaxyDao.findContractTotal();
    }

    @Override
    public int findNodeTotal() {
        rpcOps.nodeInfo();
        return 1;
    }

    @Override
    public List<Party> findNotary() {
        return rpcOps.notaryIdentities();
    }

    @Override
    public List<String> findFlow() {
        return rpcOps.registeredFlows();
    }

    @Override
    public List<NodeInfo> findNode() {
        return rpcOps.networkMapSnapshot();
    }

    @Override
    public List<State> findState() {
        return quanaxyDao.findState();
    }

    @Override
    public List<GrantState> findTransaction() {
        List<GrantState> list = new ArrayList<>();

        Vault.Page<GrantsState> grantsStatePage = rpcOps.vaultQuery(GrantsState.class);
        if (grantsStatePage != null) {
            List<StateAndRef<GrantsState>> states = grantsStatePage.getStates();
            list = Utils.toListGrantState(states);
        }

        return list;
    }

    @Override
    public List<GrantState> findTransactionByid(String linrarId) {


        List<GrantState> list = new ArrayList<>();

        UniqueIdentifier uniqueIdentifier = UniqueIdentifier.Companion.fromString(linrarId);

        QueryCriteria queryCriteria = new QueryCriteria.LinearStateQueryCriteria(
                null,
                ImmutableList.of(uniqueIdentifier),
                Vault.StateStatus.ALL,
                null);
        Vault.Page<GrantsState> grantsStatePage = rpcOps.vaultQueryByCriteria(queryCriteria, GrantsState.class);
        if (grantsStatePage != null) {
            List<StateAndRef<GrantsState>> states = grantsStatePage.getStates();
            list=Utils.toListGrantState(states);
        }
        return list;
    }

    @Override
    public List<Monitor> findMonitor() {

        List<Monitor> list = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Monitor monitor = new Monitor(1 + 1, "Asian Infrastructure Investment Bank", "O=PartyD,L=Abobo,C=CH", new Random().nextInt(10), new Random().nextInt(100) * 123093 + "", "2019-01-01");
            list.add(monitor);
        }
        return list;
    }

    @Override
    public List<State> findContract() {

        List<State> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            State state = new State(0, UUID.randomUUID().toString(), "China State Construction", "Chang Hong", "Real estate development contract", "Chang Hong", "Chang Hong", "ICBC", "2020-09-08", "5,000,000.00", "150,000.00", "China State Construction", "9,828,793.00", "550e8400-e29b-41d4-a716-446655440000", "CONSUMED", true);
            list.add(state);
        }
        return list;
    }

    @Override
    public Map<String, List<String>> findMarketIndicator(int timeCode) {
        List<String> autlist = new ArrayList<>();
        List<String> ttvlist = new ArrayList<>();
        List<String> cleardlist = new ArrayList<>();
        List<String> times = new ArrayList<>();
        Random random = new Random();
        Date date = new Date();


        switch (timeCode) {
            case 1:
//                val start = TODAY
//                val end = TODAY.plus(30, ChronoUnit.DAYS)
//                val recordedBetweenExpression = TimeCondition(
//                        QueryCriteria.TimeInstantType.RECORDED,
//                        ColumnPredicate.Between(start, end))
//                val criteria = VaultQueryCriteria(timeCondition = recordedBetweenExpression)
//                val results = vaultService.queryBy<ContractState>(criteria)
//
                Calendar now = Calendar.getInstance();
                now.get(Calendar.DAY_OF_MONTH);
                Calendar before7 = Calendar.getInstance();
                before7.add(Calendar.DAY_OF_MONTH, -7);
                QueryCriteria.TimeCondition timeCondition = new QueryCriteria
                        .TimeCondition(QueryCriteria.TimeInstantType.RECORDED, new ColumnPredicate.Between(now, before7));
                QueryCriteria queryCriteria = new QueryCriteria.VaultQueryCriteria(Vault.StateStatus.UNCONSUMED, null, null, null, null, timeCondition);

                List<StateAndRef<QXState>> states = rpcOps.vaultQueryByCriteria(queryCriteria, QXState.class).getStates();
                for (StateAndRef<QXState> state : states
                ) {
                    state.getState().getData().getTotalTokenizationAmount();
                }

                break;
        }

        switch (timeCode) {

            case 1:
                for (int i = 0; i < 7; i++) {
                    autlist.add(((i + 10) * 139) * random.nextInt(200) + 1 + "");
                    ttvlist.add(((i + 10) * 139) * random.nextInt(300 - 200 + 1) + 200 + "");
                    cleardlist.add(((i + 10) * 139) * random.nextInt(200) + 1 + "");
                    times.add(Utils.getWeekOfDate(date, i + 1));
                }
                break;
            case 2:
                for (int i = 0; i < 4; i++) {
                    autlist.add(((i + 10) * 139) * random.nextInt(300) + 1 + "");
                    ttvlist.add(((i + 10) * 139) * random.nextInt(400 - 300 + 1) + 300 + "");
                    cleardlist.add(((i + 10) * 139) * random.nextInt(300) + 1 + "");
                    if (i < 4)
                        times.add("the " + (i + 1) + " week");

                }
                break;
            case 3:
                for (int i = 0; i < 3; i++) {
                    autlist.add(((i + 10) * 139) * random.nextInt(400) + 1 + "");
                    ttvlist.add(((i + 10) * 139) * random.nextInt(500 - 400 + 1) + 400 + "");
                    cleardlist.add(((i + 10) * 139) * random.nextInt(400) + 1 + "");
                    if (i < 3)
                        times.add(Utils.getMonth(date, -i));
                }
                break;
            case 4:
                for (int i = 0; i < 12; i++) {
                    autlist.add(((i + 10) * 139) * random.nextInt(500) + 1 + "");
                    ttvlist.add(((i + 10) * 139) * random.nextInt(600 - 500 + 1) + 500 + "");
                    cleardlist.add(((i + 10) * 139) * random.nextInt(500) + 1 + "");
                    times.add(Utils.getMonth(date, -i));

                }
                break;
            case 5:
                for (int i = 0; i < 5; i++) {
                    autlist.add(((i + 10) * 139) * random.nextInt(600) + 1 + "");
                    ttvlist.add(((i + 10) * 139) * random.nextInt(700 - 600 + 1) + 600 + "");
                    cleardlist.add(((i + 10) * 139) * random.nextInt(600) + 1 + "");
                    if (i < 5)
                        times.add(Utils.getYear(date, i) + "");
                }
                break;
            default:
                System.out.println("timeCode错误");
        }

        Map<String, List<String>> map = new HashedMap();
        Collections.reverse(times);
        map.put("AUT", autlist);
        map.put("TTV", ttvlist);
        map.put("Cleared", cleardlist);
        map.put("Times", times);
        return map;
    }

    @Override
    public ClientTrend findClientMarketIndicator(int timeCode) {

        ClientTrend clientTrend = new ClientTrend();
        switch (timeCode) {
            case 0:
                clientTrend.setId(1);
                clientTrend.setAutClient(324122);
                clientTrend.setTtvClient(2928993);
                clientTrend.setTtvClientHistorical(82199);
                break;
            case 1:
                clientTrend.setId(2);
                clientTrend.setAutClient(924122);
                clientTrend.setTtvClient(9928993);
                clientTrend.setTtvClientHistorical(1282199);

                break;
            case 2:
                clientTrend.setId(3);
                clientTrend.setAutClient(1324122);
                clientTrend.setTtvClient(12928993);
                clientTrend.setTtvClientHistorical(1082199);
                break;
            case 3:
                clientTrend.setId(4);
                clientTrend.setAutClient(2924122);
                clientTrend.setTtvClient(4128993);
                clientTrend.setTtvClientHistorical(1182199);
                break;
            case 4:
                clientTrend.setId(5);
                clientTrend.setAutClient(5324122);
                clientTrend.setTtvClient(12928993);
                clientTrend.setTtvClientHistorical(4482199);
                break;
            case 5:
                clientTrend.setId(6);
                clientTrend.setAutClient(8324122);
                clientTrend.setTtvClient(72928993);
                clientTrend.setTtvClientHistorical(6182199);
                break;
            default:
                System.out.println("timeCode=" + timeCode);
        }

        return clientTrend;
    }

    @Override
    public Map<String, List<String>> findMarket(int timeCode) {
        List<String> times = new ArrayList<>();
        List<String> aut = new ArrayList<>();
        List<String> ttv = new ArrayList<>();
        List<String> autClient = new ArrayList<>();
        List<String> ttvClient = new ArrayList<>();
        List<String> ttvClientHistorical = new ArrayList<>();
        Random random = new Random();
        Map<String, List<String>> map = new HashedMap();
        Date date = new Date();
        switch (timeCode) {
            case 1:
                for (int i = 0; i < 7; i++) {
                    times.add(Utils.getWeekOfDate(date, i + 1));
                    aut.add(((i + 10) * 139 * random.nextInt(100) + 1 + ""));
                    ttv.add((((i + 10) * 159) * random.nextInt(100) + 1 + ""));
                    autClient.add(((i + 10) * 109 * random.nextInt(100) + 1 + ""));
                    ttvClient.add(((i + 10) * 109 * random.nextInt(100) + 1 + ""));
                    ttvClientHistorical.add((((i + 10) * 179) * random.nextInt(100) + 1 + ""));
                }

                break;
            case 2:
                for (int i = 0; i < 4; i++) {
                    times.add("the " + (i + 1) + " week");
                    aut.add(((i + 10) * 159 * random.nextInt(300) + 1 + ""));
                    ttv.add(((i + 10) * 179 * random.nextInt(300) + 1 + ""));
                    autClient.add(((i + 10) * 129) * random.nextInt(300) + 1 + "");
                    ttvClient.add(((i + 10) * 129 * random.nextInt(300) + 1 + ""));
                    ttvClientHistorical.add((((i + 10) * 169) * random.nextInt(300) + 1 + ""));
                }
                break;
            case 3:
                for (int i = 0; i < 3; i++) {
                    times.add(Utils.getMonth(date, -i));

                    aut.add(((i + 10) * 179) * random.nextInt(500) + 1 + "");
                    ttv.add(((i + 10) * 199) * random.nextInt(500) + 1 + "");
                    autClient.add(((i + 10) * 149) * random.nextInt(500) + 1 + "");
                    ttvClient.add(((i + 10) * 149) * random.nextInt(500) + 1 + "");
                    ttvClientHistorical.add(((i + 10) * 189) * random.nextInt(500) + 1 + "");
                }
                break;
            case 4:
                for (int i = 0; i < 12; i++) {
                    times.add(Utils.getMonth(date, -i));
                    aut.add(((i + 10) * 199) * random.nextInt(500) + 1 + "");
                    ttv.add(((i + 10) * 219) * random.nextInt(500) + 1 + "");
                    autClient.add(((i + 10) * 169) * random.nextInt(500) + 1 + "");
                    ttvClient.add(((i + 10) * 169) * random.nextInt(500) + 1 + "");
                    ttvClientHistorical.add(((i + 10) * 209) * random.nextInt(500) + 1 + "");
                }
                break;
            case 5:
                for (int i = 0; i < 5; i++) {
                    times.add(Utils.getYear(date, i) + "");
                    aut.add(((i + 10) * 219) * random.nextInt(700) + 1 + "");
                    ttv.add(((i + 10) * 239) * random.nextInt(700) + 1 + "");
                    autClient.add(((i + 10) * 189) * random.nextInt(700) + 1 + "");
                    ttvClient.add(((i + 10) * 189) * random.nextInt(700) + 1 + "");
                    ttvClientHistorical.add(((i + 10) * 229) * random.nextInt(700) + 1 + "");
                }
                break;
            default:
                System.out.println("timeCode=" + timeCode);


        }

        Collections.reverse(times);
        map.put("times", times);
        map.put("aut", aut);
        map.put("ttv", ttv);
        map.put("autClient", autClient);
        map.put("ttvClient", ttvClient);
        map.put("ttvClientHistorical", ttvClientHistorical);
        return map;
    }

    @Override
    public Map<String, String> indexMarket() {

        Map<String, QXState> map = new HashedMap();
        Map<String, String> market = new HashedMap();
        QueryCriteria queryCriteria = new QueryCriteria.VaultQueryCriteria(Vault.StateStatus.UNCONSUMED);
        PageSpecification pageSpecification = new PageSpecification();
        Vault.Page<QXState> contractStatePage =
                rpcOps.vaultQueryBy(queryCriteria, pageSpecification, null, QXState.class);
        BigDecimal autTotal = new BigDecimal("0.00");
        autTotal.setScale(2);


        List<StateAndRef<QXState>> states = contractStatePage.getStates();
        for (int i = 0; i < states.size(); i++) {
            QXState state = (QXState) states.get(i).getState().getData();
            map.put(state.getLinearId().toString(), state);
        }

        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            QXState state = map.get(iterator.next());
            autTotal.add(state.getTotalTokenizationAmount());
        }

        market.put("aut", autTotal.toString());


        QueryCriteria queryCriteria2 = new QueryCriteria.VaultQueryCriteria(Vault.StateStatus.ALL);

        PageSpecification pageSpecification2 = new PageSpecification();
        Vault.Page<QXState> contractStatePage2 =
                rpcOps.vaultQueryBy(queryCriteria2, pageSpecification2, null, QXState.class);
        BigDecimal ttvTotal = new BigDecimal("0.00");
        BigDecimal cleraTotal = new BigDecimal("0.00");
        ttvTotal.setScale(2);
        List<StateAndRef<QXState>> states2 = contractStatePage2.getStates();
        map.clear();
        for (int i = 0; i < states2.size(); i++) {
            QXState state = states2.get(i).getState().getData();
            map.put(state.getLinearId().toString(), state);
            ttvTotal.add(state.getTradingAmount());
        }
        market.put("ttv", ttvTotal.toString());

        Iterator<String> iterator1 = map.keySet().iterator();
        while (iterator1.hasNext()) {
            QXState state = map.get(iterator1.next());
            if (state.isSettled()) {
                cleraTotal.add(state.getTotalTokenizationAmount());
            }
        }
        market.put("clear", cleraTotal.toString());
        return market;
    }
}
