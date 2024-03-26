package ktcloud.mvp.kmart.push;

import ktcloud.mvp.kmart.KMartApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler{

    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();

    private static WebSocketSession SESSION = null;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        CLIENTS.put(session.getId(), session);
        KMartApplication.log(">>>>> websocket connection established <<<");
        SESSION = session;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        CLIENTS.remove(session.getId());
        KMartApplication.log(">>>>> websocket connection closed <<<");
    }

    public static void pushProductDelivery(String message){
        try {
            SESSION.sendMessage(new TextMessage(message));
            KMartApplication.log(">>>>> websocket message send <<<"+message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}