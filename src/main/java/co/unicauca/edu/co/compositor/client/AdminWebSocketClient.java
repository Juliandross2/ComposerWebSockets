package co.unicauca.edu.co.compositor.client;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import java.lang.reflect.Type;

@Component
public class AdminWebSocketClient {

    private static final String URL = "ws://localhost:5000/ws";

    private StompSession stompSession;

    public void conectar() {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.afterPropertiesSet();
        stompClient.setTaskScheduler(scheduler);

        stompClient.connect(URL, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                System.out.println("Conectado al WebSocket");
                stompSession = session;  // Guardamos la sesión activa

                session.subscribe("/notificaciones/generales", new StompFrameHandler() {
                    public Type getPayloadType(StompHeaders headers) {
                        return String.class;
                    }

                    public void handleFrame(StompHeaders headers, Object payload) {
                        System.out.println("Notificación general: " + payload);
                    }
                });

                session.subscribe("/notificaciones/financiera", new StompFrameHandler() {
                    public Type getPayloadType(StompHeaders headers) {
                        return String.class;
                    }

                    public void handleFrame(StompHeaders headers, Object payload) {
                        System.out.println("Notificación financiera: " + payload);
                    }
                });
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                System.err.println("Error en transporte STOMP: " + exception.getMessage());
            }
        });
    }

    public void notificar(String destino, Object mensaje) {
        if (stompSession != null && stompSession.isConnected()) {
            stompSession.send(destino, mensaje);
            System.out.println("Mensaje enviado a " + destino + ": " + mensaje);
        } else {
            System.err.println("No se pudo enviar el mensaje. STOMP no conectado.");
        }
    }
}
