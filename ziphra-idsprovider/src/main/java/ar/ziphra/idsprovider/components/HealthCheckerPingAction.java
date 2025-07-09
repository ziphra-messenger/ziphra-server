package ar.ziphra.idsprovider.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.interfaces.HealthCheckerPingActionInterface;
import ar.ziphra.core.idsprovider.AESIdGeneratorService;
import ar.ziphra.core.idsprovider.EncryptKeysIdGeneratorService;
import ar.ziphra.core.idsprovider.GrupoIdGeneratorService;
import ar.ziphra.core.idsprovider.MessageIdGeneratorService;
import ar.ziphra.core.idsprovider.UsuarioIdGeneratorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HealthCheckerPingAction implements HealthCheckerPingActionInterface{
	
	@Autowired
	@Lazy
	private AESIdGeneratorService aesIdGenerator;

	@Autowired
	@Lazy
	private UsuarioIdGeneratorService usuarioIdGenerator;
	
	@Autowired
	@Lazy
	private GrupoIdGeneratorService grupoIdGenerator;
	
	@Autowired
	@Lazy
	private EncryptKeysIdGeneratorService encryptKeysIdGenerator;

	
	@Autowired
	@Lazy
	private MessageIdGeneratorService messageIdGenerator;

	@Override
	public void actionBeforePing() throws ZiphraException {
		log.warn("Ejecutando actionBeforePing");
		log.warn("Ejecutando refresh message id");
		messageIdGenerator.refesh();
		log.warn("Ejecutando refresh encrypt keys id");
		encryptKeysIdGenerator.getNextId(true);
		log.warn("Ejecutando refresh usuario id");
		usuarioIdGenerator.getNextId(true);
		log.warn("Ejecutando refresh grupo id");
		grupoIdGenerator.getNextId(true);
		log.warn("Ejecutando refresh aes id");
		aesIdGenerator.getNextId(true);		
		
	}
	

}

