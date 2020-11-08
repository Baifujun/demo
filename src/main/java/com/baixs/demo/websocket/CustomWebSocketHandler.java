package com.baixs.demo.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomWebSocketHandler implements WebSocketHandler {

    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<String, WebSocketSession>();

    public static int getOnlineCount() {
        return sessions.size();
    }

    public static void sendMessage(String id, String msg) throws IOException {
        sessions.get(id).sendMessage(new TextMessage(msg));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        sendMessage(session.getId(), "session id:" + session.getId() + " websocket 已连接");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession wsSession, WebSocketMessage<?> message) throws Exception {
        System.out.println(message.getPayload().toString());
        sendMessage(wsSession.getId(), "session id:" + wsSession.getId() + " 你发送的消息是：" + message.getPayload().toString());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        sessions.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}
