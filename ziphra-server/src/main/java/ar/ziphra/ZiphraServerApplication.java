package ar.ziphra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import ar.ziphra.commonback.annotations.SpringIgnoreThisComponent;
import ar.ziphra.security.util.CustomAccessDeniedHandler;
import ar.ziphra.security.util.CustomAuthenticationFailureHandler;


@SpringBootApplication
@ComponentScan(basePackages = "ar.ziphra.server.component")
@ComponentScan(basePackages = "ar.ziphra.server.security")
@ComponentScan(basePackages = "ar.ziphra.core.idsprovider")
//@EnableConfigurationProperties(value = MessageIdSequenceFactoryPropertiesEnumConfiguration.class)
@EnableJpaRepositories(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {
        		SpringIgnoreThisComponent.class})
})  
public class ZiphraServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZiphraServerApplication.class, args);
	}

	
	@Bean
	AccessDeniedHandler accessDeniedHandler() {
	   return new CustomAccessDeniedHandler();
	}
	
	@Bean
	AuthenticationFailureHandler authenticationFailureHandler() {
	    return new CustomAuthenticationFailureHandler();
	} 
	
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
