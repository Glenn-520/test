package com.seatig.socket;

import com.seatig.domain.User;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @className: SpringWebSocketHandler
 * @description: WebSocket监听器
 * @author: glenn
 * @create: 2019-07-22 18:57
 **/
public class SpringWebSocketHandler extends TextWebSocketHandler {
    ///Map来存储WebSocketSession，key用USER_ID 即在线用户列表
    private static final Map<String, WebSocketSession> users = new HashMap<>();

    //标识key 对应监听器里的key
    private static final String KEY = "WEBSOCKET_USERID";

    /**
     * 在建立连接时候调用 该方法 连接成功时候，会触发页面上onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //连接成功时 需要把用户标识存入map
        String id = (String) session.getAttributes().get(KEY);
        System.out.println("用户存储成功");
        users.put(id, session);
    }

    /**
     * 处理前端页面发来的文本信息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    /**
     * 当发生错误的时候触发该方法
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        //传输发生错误 需要从map中移除对应无效的Session
        if (session.isOpen()) {
            session.close();
        }
        User user = (User) session.getAttributes().get(KEY);
        users.remove(user.getId());
    }

    /**
     * 在连接关闭之前触发该方法
     **/
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        //关闭Socket时需要移除对应的Session
        User user = (User) session.getAttributes().get(KEY);

        users.remove(user.getId());
    }

    /**
     * 是否支持处理部分消息
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param userId
     * @param message
     */
    public void sendMessageToUser(String userId, TextMessage message) {
        for (String id : users.keySet()) {
            if (id.equals(userId)) {
                try {
                    if (users.get(id).isOpen()) {
                        users.get(id).sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
