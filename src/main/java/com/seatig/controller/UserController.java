package com.seatig.controller;

import com.plaid.client.PlaidClient;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.quanaxy.schema.Grants.GrantsStateSchemaV1;
import com.quanaxy.state.GrantsState;
import com.quanaxy.utils.CordaUtils;
import com.seatig.common.BusinessException;
import com.seatig.common.Result;
import com.seatig.common.ResultGenerator;
import com.seatig.domain.Company;
import com.seatig.domain.PageDTO;
import com.seatig.domain.User;
import com.seatig.service.UserService;
import com.seatig.socket.SpringWebSocketHandler;
import com.seatig.utils.CordaUtil;
import com.seatig.utils.JSONUtils;
import com.seatig.utils.Utils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.corda.client.rpc.CordaRPCConnection;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.node.services.Vault;
import net.corda.core.node.services.vault.QueryCriteria;
import net.corda.core.schemas.PersistentState;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import net.corda.core.schemas.PersistentState;


@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    SpringWebSocketHandler socketHandler;

    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String login(HttpServletRequest request, HttpServletResponse response) {

//        Result result=new Result();
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        int userByUserName = userService.findUserByUserName(username);
//        if (userByUserName==0){
//            result.setResCode(-1);
//            result.setResMsg("用户不存在");
//        }else if(userByUserName==1){
//            User user = userService.loadUserByUsername(username);
//            if (user.getPassword().equals(password)){
//                result.setResCode(0);
//                result.setResMsg("成功");
//            }
//        }

        return JSONUtils.beanToJson("s");
    }


    @RequestMapping(value = "login", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout,
                              @RequestParam(value = "timeout", required = false) String timeout) {
        ModelAndView mv = new ModelAndView("login");

        if (error != null) {
            mv.addObject("error", "用户名或密码错误");
        }
        if (logout != null) {
            mv.addObject("logout", "用户退出成功");
        }
        if (timeout != null) {
            System.out.println("检测到超时，timeout!=null");
            mv.addObject("info", "登录超时，请重新登录.");
        }
        return mv;
    }


    @RequestMapping(value = "testhh", method = RequestMethod.GET)
    public @ResponseBody
    Result test(HttpServletRequest request, HttpServletResponse response) {

        try {
            CordaRPCConnection connection = CordaUtil.rpcConnection("localhost", "user1", "test", 10006);
            CordaRPCOps proxy = connection.getProxy();
            Vault.Page<GrantsState> grantsStatePage = proxy.vaultQuery(GrantsState.class);

            List<StateAndRef<GrantsState>> states = grantsStatePage.getStates();
            for (StateAndRef<GrantsState> s:states
                 ) {
                System.out.println(s.getState().getData().getLinearId().toString());

            }
        } catch (BusinessException e) {
            e.printStackTrace();
            System.out.println("发送异常");
        }
        return ResultGenerator.getSuccessResult();
    }


    @RequestMapping(value = "register", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String registerUser(MultipartHttpServletRequest request, HttpServletResponse response) {
//        Result result = new Result();
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String email = request.getParameter("email");
//        if (username.trim() == null || username == "") {
//            result.setResCode(-1);
//            result.setResMsg("用户名不能为空(Server)");
//            return JSONUtils.beanToJson(result);
//        }
//        if (password.trim() == null || password.trim() == "") {
//            result.setResCode(-1);
//            result.setResMsg("密码不能为空(Server)");
//            return JSONUtils.beanToJson(result);
//        }
//        if (email.trim() == null || email.trim() == "") {
//            result.setResMsg("邮箱不能为空(Server)");
//            result.setResCode(-1);
//            return JSONUtils.beanToJson(result);
//        }

        User user = new User();
//        user.setPassword(password);
//        user.setUsername(username);

//        if (userService.findUserByUserName(user.getUsername()) > 0) {
//            //如果用户名找到的用户>0
////            result.setResCode(-1);
////            result.setResMsg("注册失败,用户名已存在.");
////            return JSONUtils.beanToJson(result);
//        }
//        userService.registerUser(user);
//
//        Iterator<String> fileNames = request.getFileNames();
//        while (fileNames.hasNext()) {
//            //用户上传了头像
//            MultipartFile file = request.getFile(fileNames.next());
//            String avatarFileName = user.getUsername() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//            try {
//                File file_path = new File(Utils.get("avatar_path", Thread.currentThread().getContextClassLoader().getResource("/")
//                        .getPath() + "properties/user.properties") + avatarFileName);
//                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file_path));
//                Utils.fileToDisk(bos, file.getInputStream());
//                String avatar_url = Utils.get("avatar_url", Thread.currentThread().getContextClassLoader().getResource("/").getPath() + "properties/user.properties");
//                avatar_url += avatarFileName;
//                userService.updateAvatar(user.getUsername(), avatar_url);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
        return JSONUtils.beanToJson("");
    }


    @RequestMapping(value = "test", method = RequestMethod.GET)
    public @ResponseBody
    Result test(String id, String type, String num, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("服务器收到数据库发送来到请求,userId=" + id + " type=" + type + "  num=" + num);

        String str = "我收到了请求并将这串字符发送给你";

        return ResultGenerator.getSuccessResult(str);
    }

    @RequestMapping(value = "sendMessage", method = RequestMethod.GET)
    public @ResponseBody
    Result getElementList(HttpServletRequest request, HttpServletResponse response) {

        User user = (User) request.getSession().getAttribute("loginUser");

        socketHandler.sendMessageToUser(user.getId(), new TextMessage("你好啊hi"));

        return null;
    }


    @RequestMapping(value = "forgot", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public ModelAndView forGot(HttpServletRequest request, HttpServletResponse response) {


        return new ModelAndView("forgot");
    }


}
