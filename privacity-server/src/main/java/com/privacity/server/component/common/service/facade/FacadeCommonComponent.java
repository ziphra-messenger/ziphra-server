package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.server.component.common.service.RandomGeneratorService;
import com.privacity.server.component.common.service.ServerConfService;
import com.privacity.server.component.common.service.ZipUtilService;
import com.privacity.server.component.encryptkeys.PrivacityRSAValidation;
import com.privacity.server.encrypt.RSA;
import com.privacity.server.util.MapperService;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class FacadeCommonComponent {

	@Autowired @Lazy
	private ServerConfService serverConf;
	
	@Autowired @Lazy
	private RandomGeneratorService randomGenerator;
	
	@Autowired @Lazy
	private MapperService mapper;
	
	@Autowired @Lazy
	private ZipUtilService zip;
	
	

	@Autowired @Lazy
	private PrivacityRSAValidation privacityRSA;

	@Autowired @Lazy
	private RSA RSA;


	public ServerConfService serverConf() {
		return serverConf;
	}

	
	public RSA RSA() {
		return RSA;
	}
	
	public PrivacityRSAValidation privacityRSA() {
		return privacityRSA;
	}
	
	public RandomGeneratorService randomGenerator() {
		return randomGenerator;
	}

	public MapperService mapper() {
		return mapper;
	}

	public ZipUtilService zip() {
		return zip;
	}


}
