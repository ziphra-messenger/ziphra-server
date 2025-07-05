package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.server.component.common.service.RandomGeneratorService;
import com.privacity.server.component.common.service.ServerConfService;
import com.privacity.server.component.encryptkeys.PrivacityRSAValidation;
import com.privacity.server.component.encryptkeys.RSAComponent;
import com.privacity.server.util.MapperService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = true, chain = false)
@Component
@NoArgsConstructor
@Getter
public class FacadeCommonComponent {

	@Autowired @Lazy
	private ServerConfService serverConf;
	
	@Autowired @Lazy
	private RandomGeneratorService randomGenerator;
	
	@Autowired @Lazy
	private MapperService mapper;


	@Autowired @Lazy
	private PrivacityRSAValidation privacityRSA;

	@Autowired @Lazy
	private RSAComponent RSA;

}
