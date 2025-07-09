package ar.ziphra.appserver.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ar.ziphra.appserver.component.common.service.RandomGeneratorService;
import ar.ziphra.appserver.component.common.service.ServerConfService;
import ar.ziphra.appserver.component.encryptkeys.RSAComponent;
import ar.ziphra.appserver.component.encryptkeys.ZiphraRSAValidation;
import ar.ziphra.appserver.util.MapperService;
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
	private ZiphraRSAValidation ziphraRSA;

	@Autowired @Lazy
	private RSAComponent RSA;

}
