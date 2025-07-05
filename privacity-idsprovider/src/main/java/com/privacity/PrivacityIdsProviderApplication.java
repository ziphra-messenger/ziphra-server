package com.privacity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.privacity.commonback.annotations.SpringIgnoreThisComponent;


@SpringBootApplication

@ComponentScan(basePackages = "com.privacity.idsprovider.components")
@ComponentScan(basePackages = "com.privacity.idsprovider.controller")
@ComponentScan(basePackages = "com.privacity.core.model")
@ComponentScan(basePackages = "com.privacity.core.util")
@ComponentScan(basePackages = "com.privacity.commonback.controller")
@ComponentScan(basePackages = "com.privacity.idsprovider")
@ComponentScan(basePackages = "com.privacity.core.idsprovider")
@ComponentScan(basePackages = "com.privacity.idsprovider.security")
@EnableJpaRepositories(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {
        		SpringIgnoreThisComponent.class})
        
})
//@ComponentScan(excludeFilters = @Filter(SpringIgnoreThisComponent.class))
//@EnableJpaRepositories(basePackages = "com.privacity.core.repository")
//@ComponentScan(basePackages = "com.privacity.server.security")
//@ComponentScan(basePackages = "com.privacity.server")
@SpringIgnoreThisComponent
public class PrivacityIdsProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrivacityIdsProviderApplication.class, args);
		
	}
	


//	  @Bean
//	  EntityManagerFactory entityManagerFactory() {
//	    // …
//	  }
	  
//	 @Bean 
//	 MessageRepositoryImpl getMyService(MessageRepository myRepository) {
//	  return new MessageRepositoryImpl(myRepository); 
//	 }
	 
	/*
	@Bean
	public HttpFirewall getHttpFirewall() {
	    StrictHttpFirewall strictHttpFirewall = new StrictHttpFirewall();
	    strictHttpFirewall.setAllowSemicolon(true);
	    return strictHttpFirewall;
	}

	@Bean
	public ServerWebSocketContainer serverWebSocketContainer() {
	    return new ServerWebSocketContainer("/messages")
	          .withSockJs(new ServerWebSocketContainer.SockJsServiceOptions()
	                                  .setHeartbeatTime(60_000));
	}
	
	@Bean
	ServerWebSocketContainer serverWebSocketContainer() {
	    return new ServerWebSocketContainer("/messages").withSockJs();
	}

	@Bean
	MessageHandler webSocketOutboundAdapter() {
	    return new WebSocketOutboundMessageHandler(serverWebSocketContainer());
	}

	@Bean(name = "webSocketFlow.input")
	MessageChannel requestChannel() {
	    return new DirectChannel();
	}

	@Bean
	IntegrationFlow webSocketFlow() {
	    return f -> {
	        Function<Message , Object> splitter = m -> serverWebSocketContainer()
	                .getSessions()
	                .keySet()
	                .stream()
	                .map(s -> MessageBuilder.fromMessage(m)
	                        .setHeader(SimpMessageHeaderAccessor.SESSION_ID_HEADER, s)
	                        .build())
	                .collect(Collectors.toList());
	        f.split( Message.class, splitter)
	                .channel(c -> c.executor(Executors.newCachedThreadPool()))
	                .handle(webSocketOutboundAdapter());
	    };
	}

	
	// Set maxPostSize of embedded tomcat server to 10 megabytes (default is 2 MB, not large enough to support file uploads > 1.5 MB)
	/*
	@Bean
	EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {
	    return (ConfigurableEmbeddedServletContainer container) -> {
	        if (container instanceof TomcatEmbeddedServletContainerFactory) {
	            TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
	            tomcat.addConnectorCustomizers(
	                (connector) -> {
	                    connector.setMaxPostSize(10000000); // 10 MB
	                }
	            );
	        }
	    };
	}	
	*/  

}
