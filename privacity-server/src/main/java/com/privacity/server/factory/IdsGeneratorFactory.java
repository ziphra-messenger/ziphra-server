package com.privacity.server.factory;

import java.util.Map;

import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.enumeration.HealthCheckerServerType;
import com.privacity.commonback.common.interfaces.HealthCheckerInterface;
import com.privacity.core.interfaces.IdsGeneratorInterface;
import com.privacity.server.component.common.service.RequestHelperService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IdsGeneratorFactory {
	
	

	@Autowired
	@Lazy
	private IdsGeneratorInterface idGenerator;
	@Lazy
	@Autowired
	private RequestHelperService requestHelperService;
	@Lazy
	@Autowired
	@Qualifier("IdsProviderGeneratorRepository")
	private IdsProviderGeneratorRepository repository;
	
	@Lazy
	@Autowired
	@Qualifier("IdsProviderGeneratorStandAlone")
	private IdsProviderGeneratorStandAlone standAlone;
	
	@Lazy
	@Autowired
	@Qualifier("IdsProviderGeneratorRest")
	private IdsProviderGeneratorRest rest;

	@Lazy
	@Autowired	
	private HealthCheckerInterface healthChecker;
	
	public Long getNextMessageId() throws PrivacityException {
		return getNextMessageId(requestHelperService.getGrupoInUse().getIdGrupo());	
	}
	
	public Long getNextMessageId(Long idGrupo) throws PrivacityException {
		IdsGeneratorInterface server = getServer();
		try {
			return server.getNextMessageId(requestHelperService.getGrupoInUse().getIdGrupo());
		} catch (PrivacityException e) {
			log.error(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL.toShow(e));
			if (server instanceof IdsProviderGeneratorRest) {
				return getServer().getNextMessageId(requestHelperService.getGrupoInUse().getIdGrupo());				
			}
		}	
		throw new PrivacityException(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL);
			

	}

	public Long getNextMessageId(String idGrupo) throws PrivacityException {
		return getNextMessageId(Long.parseLong(idGrupo));
	}
	
	public Map<Long,String> getRandomNicknameByGrupo(String idGrupo) throws PrivacityException{
		return getServer().getRandomNicknameByGrupo(idGrupo);
	}
	public Long getNextGrupoId() throws PrivacityException{
		IdsGeneratorInterface server = getServer();
		try {
			return server.getNextGrupoId();
		} catch (PrivacityException e) {
			log.error(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL.toShow(e));
			if (server instanceof IdsProviderGeneratorRest) {
				return getServer().getNextGrupoId();				
			}
		}	
		throw new PrivacityException(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL);
	}
	public Long getNextUsuarioId() throws PrivacityException{
		IdsGeneratorInterface server = getServer();
		try {
			return server.getNextUsuarioId();
		} catch (PrivacityException e) {
			log.error(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL.toShow(e));
			if (server instanceof IdsProviderGeneratorRest) {
				return getServer().getNextUsuarioId();				
			}
		}	
		throw new PrivacityException(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL);		
	}
	public Long getNextAESId() throws PrivacityException{
		IdsGeneratorInterface server = getServer();
		try {
			return server.getNextAESId();
		} catch (PrivacityException e) {
			log.error(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL.toShow(e));
			if (server instanceof IdsProviderGeneratorRest) {
				return getServer().getNextAESId();				
			}
		}	
		throw new PrivacityException(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL);		
	}
	public Long getNextEncryptKeysId() throws PrivacityException{
		
		
		IdsGeneratorInterface server = getServer();
		try {
			return server.getNextEncryptKeysId();
		} catch (PrivacityException e) {
			log.error(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL.toShow(e));
			if (server instanceof IdsProviderGeneratorRest) {
				return getServer().getNextEncryptKeysId();				
			}
		}	
		throw new PrivacityException(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL);		
	}
	

	private IdsGeneratorInterface getServer() {
		if (healthChecker.thereIsA(HealthCheckerServerType.IDS_PROVIDER)) {
			log.info("Ejecutando Rest Generator");
			if(healthChecker.isOnline(HealthCheckerServerType.IDS_PROVIDER)) {
				return this.rest;	
			}else {
				log.warn("Ejecutando Repository Generator");
				return this.repository;
			}
			
		}else {
			log.info("Ejecutando StandAlone Generator");
			return this.standAlone;
		}
	}
}
