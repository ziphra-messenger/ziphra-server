package ar.ziphra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ar.ziphra.commonback.annotations.SpringIgnoreThisComponent;


@SpringBootApplication

@ComponentScan(basePackages = "ar.ziphra.idsprovider.components")
@ComponentScan(basePackages = "ar.ziphra.idsprovider.controller")
@ComponentScan(basePackages = "ar.ziphra.core.model")
@ComponentScan(basePackages = "ar.ziphra.core.util")
@ComponentScan(basePackages = "ar.ziphra.commonback.controller")
@ComponentScan(basePackages = "ar.ziphra.idsprovider")
@ComponentScan(basePackages = "ar.ziphra.core.idsprovider")
@ComponentScan(basePackages = "ar.ziphra.idsprovider.security")
@EnableJpaRepositories(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {
        		SpringIgnoreThisComponent.class})
        
})
//@ComponentScan(excludeFilters = @Filter(SpringIgnoreThisComponent.class))
//@EnableJpaRepositories(basePackages = "ar.ziphra.core.repository")
//@ComponentScan(basePackages = "ar.ziphra.server.security")
//@ComponentScan(basePackages = "ar.ziphra.server")
@SpringIgnoreThisComponent
public class ZiphraIdsProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZiphraIdsProviderApplication.class, args);
		
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
