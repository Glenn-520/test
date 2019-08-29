package com.seatig.socket;

import com.seatig.domain.User;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @version V1.0
 * @className: SpringWebSocketHandlerInterceptor
 * @description: websocket拦截器
 * @author: glenn
 * @create: 2019-07-22 17:11
 **/
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {


    /**
     * 在连接之前调用方法
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        //在连接之前记录用户登陆Session 以便通过Session找到用户进行通信
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            System.out.println("指定到这里");
            if (session != null) {
                //使用userId区分WebSocketHandler，以便定向发送消息
                System.out.println("执行到这里2");
                User user = (User) session.getAttribute("loginUser");  //一般直接保存user实体
                if (user!=null) {
                    attributes.put("WEBSOCKET_USERID",user.getId());
                }

            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    /**
     * 在连接之后调用方法
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
