package com.privacity.server.factory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.enumeration.HealthCheckerServerType;
import com.privacity.commonback.common.interfaces.HealthCheckerInterface;
import com.privacity.core.interfaces.IdsGeneratorMessageIdInterface;
import com.privacity.core.services.MessageIdGeneratorService;
import com.privacity.server.component.common.service.RequestHelperService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageIdGeneratorFactory {
	
	@Autowired
	@Lazy
	private MessageIdGeneratorService messageIdGenerator;
	@Lazy
	@Autowired
	private RequestHelperService requestHelperService;
	@Lazy
	@Autowired
	private IdsGeneratorMessageIdRest messageIdRest;
	
	@Lazy
	@Autowired	
	private HealthCheckerInterface healthChecker;
	
	
	private IdsGeneratorMessageIdInterface interf;
	
	public Long get() throws PrivacityException {
		return get(requestHelperService.getGrupoInUse().getIdGrupo());
	}
	
	public Long get(Long idGrupo) throws PrivacityException {

		return interf.getNextMessageId(idGrupo);	

	}

	public Long get(String idGrupo) throws PrivacityException {
		return get(Long.parseLong(idGrupo));
	}
	
	@PostConstruct
	public void pc() {
		if (healthChecker.thereIsA(HealthCheckerServerType.IDS_PROVIDER)) {
			log.info("Inicializando Rest IdsGeneratorMessageIdRest");
			interf = messageIdRest;	
		}else {
			log.info("Inicializando Local MessageIdGeneratorService");
			interf = messageIdGenerator;
		}
	}
}
