package com.privacity.server.factory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.core.idsprovider.AESIdGeneratorService;
import com.privacity.core.idsprovider.EncryptKeysIdGeneratorService;
import com.privacity.core.idsprovider.GrupoIdGeneratorService;
import com.privacity.core.idsprovider.MessageIdGeneratorRepositoryService;
import com.privacity.core.idsprovider.UsuarioIdGeneratorService;
import com.privacity.core.interfaces.IdsGeneratorInterface;

import lombok.extern.slf4j.Slf4j;

@Component("IdsProviderGeneratorRepository")
@Slf4j
public class IdsProviderGeneratorRepository implements IdsGeneratorInterface {

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
	private MessageIdGeneratorRepositoryService messageIdGenerator;
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
		log.error(ExceptionReturnCode.IDS_PROVIDER_REFRESH_MUST_NOT_BE_CALLED_IN_THIS_CASE.toShow("Obtiene el id desde la base de datos, no es necesario refrescar"));
		throw new PrivacityException(ExceptionReturnCode.IDS_PROVIDER_REFRESH_MUST_NOT_BE_CALLED_IN_THIS_CASE);
	}

	@Override
	public Map<Long, String> getRandomNicknameByGrupo(String idGrupo) throws PrivacityException {
		// TODO Auto-generated method stub
		return null;
	}

}
