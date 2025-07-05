package com.privacity.server.factory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.common.exceptions.PrivacityException;
import com.privacity.core.idsprovider.AESIdGeneratorService;
import com.privacity.core.idsprovider.EncryptKeysIdGeneratorService;
import com.privacity.core.idsprovider.GrupoIdGeneratorService;
import com.privacity.core.idsprovider.MessageIdGeneratorAbstractService;
import com.privacity.core.idsprovider.UsuarioIdGeneratorService;
import com.privacity.core.interfaces.IdsGeneratorInterface;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("IdsProviderGeneratorStandAlone")
public class IdsProviderGeneratorStandAlone implements IdsGeneratorInterface {

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
	private MessageIdGeneratorAbstractService messageIdGenerator;
	@Override
	public Long getNextMessageId(Long idGrupo) throws PrivacityException {
		return messageIdGenerator.getNextMessageIdFromRepository(idGrupo);
	}

	@Override
	public Long getNextGrupoId() throws PrivacityException {
		return grupoIdGenerator.getNextIdFromRepository();
	}

	@Override
	public Long getNextUsuarioId() throws PrivacityException {
		return usuarioIdGenerator.getNextIdFromRepository();
	}

	@Override
	public Long getNextAESId() throws PrivacityException {
		return aesIdGenerator.getNextIdFromRepository();
	}

	@Override
	public Long getNextEncryptKeysId() throws PrivacityException {
		return encryptKeysIdGenerator.getNextIdFromRepository();
	}

	@Override
	public void refresh() throws PrivacityException {
		log.warn("Ejecutando IdsGeneratorInterface refresh");
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

	@Override
	public Map<Long, String> getRandomNicknameByGrupo(String idGrupo) throws PrivacityException {
		// TODO Auto-generated method stub
		return null;
	}

}

