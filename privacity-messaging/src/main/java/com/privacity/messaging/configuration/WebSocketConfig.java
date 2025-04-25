package com.privacity.messaging.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.privacity.messaging.handlers.HttpHandshakeInterceptor;
import com.privacity.messaging.listeners.STOMPConnectEventListener;
import com.privacity.messaging.listeners.STOMPDisconnectEventListener;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
		
        //config.enableSimpleBroker("/user/queue/specific-user");
        config.setUserDestinationPrefix("/user");		
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/gs-guide-websocket").addInterceptors(new HttpHandshakeInterceptor()).withSockJS();
	}

    @Bean
    public SocketSessionRegistry SocketSessionRegistry(){
        return new SocketSessionRegistry();
    }
    @Bean
    public STOMPConnectEventListener STOMPConnectEventListener(){
        return new STOMPConnectEventListener();
    }
    @Bean
    public STOMPDisconnectEventListener STOMPDisconnectEventListener(){
        return new STOMPDisconnectEventListener();
    }
//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//      registration.setMessageSizeLimit(1073741824); //128 * 1024);
//      registration.setSendTimeLimit(5 * 60 * 1000);
//      registration.setSendBufferSizeLimit(1024 * 1024);
//      registration.setTimeToFirstMessage(30 * 1000);
//    }
}
