package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.server.component.common.service.RequestHelperService;
import com.privacity.server.tasks.HealthChecker;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Component
@NoArgsConstructor
@Accessors(fluent = true, chain = false)
@Getter
public class FacadeComponent {
	@Autowired @Lazy
	private FacadeCommonComponent common;
	@Autowired @Lazy
	private FacadeRepositoryComponent repo;
	@Autowired @Lazy
	private FacadeUtilComponent util;
	@Autowired @Lazy
	private FacadeServiceComponent service;
	@Autowired @Lazy
	private FacadeWebSocketComponent webSocket;
	@Autowired @Lazy
	private FacadeProcessServiceComponent process;
	@Autowired @Lazy
	private FacadeValidationService validation;
	
	@Autowired @Lazy
	private FacadeFactoryService factory;
	
	@Autowired @Lazy
	private RequestHelperService requestHelper;
	
	@Autowired @Lazy
	
	private HealthChecker healthChecker;
}
