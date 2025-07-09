package ar.ziphra.appserver.factory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.enumeration.HealthCheckerServerType;
import ar.ziphra.commonback.common.interfaces.HealthCheckerInterface;
import ar.ziphra.core.interfaces.IdsGeneratorInterface;
import ar.ziphra.appserver.component.common.service.RequestHelperService;
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
	
	public Long getNextMessageId() throws ZiphraException {
		return getNextMessageId(requestHelperService.getGrupoInUse().getIdGrupo());	
	}
	
	public Long getNextMessageId(Long idGrupo) throws ZiphraException {
		IdsGeneratorInterface server = getServer();
		try {
			return server.getNextMessageId(requestHelperService.getGrupoInUse().getIdGrupo());
		} catch (ZiphraException e) {
			log.error(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL.toShow(e));
			if (server instanceof IdsProviderGeneratorRest) {
				return getServer().getNextMessageId(requestHelperService.getGrupoInUse().getIdGrupo());				
			}
		}	
		throw new ZiphraException(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL);
			

	}

	public Long getNextMessageId(String idGrupo) throws ZiphraException {
		return getNextMessageId(Long.parseLong(idGrupo));
	}
	
	public Map<Long,String> getRandomNicknameByGrupo(String idGrupo) throws ZiphraException{
		return getServer().getRandomNicknameByGrupo(idGrupo);
	}
	public Long getNextGrupoId() throws ZiphraException{
		IdsGeneratorInterface server = getServer();
		try {
			return server.getNextGrupoId();
		} catch (ZiphraException e) {
			log.error(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL.toShow(e));
			if (server instanceof IdsProviderGeneratorRest) {
				return getServer().getNextGrupoId();				
			}
		}	
		throw new ZiphraException(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL);
	}
	public Long getNextUsuarioId() throws ZiphraException{
		IdsGeneratorInterface server = getServer();
		try {
			return server.getNextUsuarioId();
		} catch (ZiphraException e) {
			log.error(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL.toShow(e));
			if (server instanceof IdsProviderGeneratorRest) {
				return getServer().getNextUsuarioId();				
			}
		}	
		throw new ZiphraException(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL);		
	}
	public Long getNextAESId() throws ZiphraException{
		IdsGeneratorInterface server = getServer();
		try {
			return server.getNextAESId();
		} catch (ZiphraException e) {
			log.error(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL.toShow(e));
			if (server instanceof IdsProviderGeneratorRest) {
				return getServer().getNextAESId();				
			}
		}	
		throw new ZiphraException(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL);		
	}
	public Long getNextEncryptKeysId() throws ZiphraException{
		
		
		IdsGeneratorInterface server = getServer();
		try {
			return server.getNextEncryptKeysId();
		} catch (ZiphraException e) {
			log.error(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL.toShow(e));
			if (server instanceof IdsProviderGeneratorRest) {
				return getServer().getNextEncryptKeysId();				
			}
		}	
		throw new ZiphraException(ExceptionReturnCode.IDS_PROVIDER_GENERAL_FAIL);		
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
