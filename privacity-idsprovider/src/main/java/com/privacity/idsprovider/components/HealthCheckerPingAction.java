package com.privacity.idsprovider.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.interfaces.HealthCheckerPingActionInterface;
import com.privacity.core.idsprovider.AESIdGeneratorService;
import com.privacity.core.idsprovider.EncryptKeysIdGeneratorService;
import com.privacity.core.idsprovider.GrupoIdGeneratorService;
import com.privacity.core.idsprovider.MessageIdGeneratorService;
import com.privacity.core.idsprovider.UsuarioIdGeneratorService;

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
	public void actionBeforePing() throws PrivacityException {
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

