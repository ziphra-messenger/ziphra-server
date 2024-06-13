package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
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

	public FacadeValidationService validation() {
		return validation;
	}
	
	public FacadeCommonComponent common() {
		return common;
	}
	public FacadeRepositoryComponent repo() {
		return repo;
	}
	public FacadeUtilComponent util() {
		return util;
	}
	public FacadeServiceComponent service() {
		return service;
	}
	public FacadeWebSocketComponent webSocket() {
		return webSocket;
	}
	public FacadeProcessServiceComponent process() {
		return process;
	}



}
