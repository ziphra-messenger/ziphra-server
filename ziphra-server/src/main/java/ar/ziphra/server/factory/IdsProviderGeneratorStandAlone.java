package ar.ziphra.server.factory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.core.idsprovider.AESIdGeneratorService;
import ar.ziphra.core.idsprovider.EncryptKeysIdGeneratorService;
import ar.ziphra.core.idsprovider.GrupoIdGeneratorService;
import ar.ziphra.core.idsprovider.MessageIdGeneratorAbstractService;
import ar.ziphra.core.idsprovider.UsuarioIdGeneratorService;
import ar.ziphra.core.interfaces.IdsGeneratorInterface;

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
	public Long getNextMessageId(Long idGrupo) throws ZiphraException {
		return messageIdGenerator.getNextMessageIdFromRepository(idGrupo);
	}

	@Override
	public Long getNextGrupoId() throws ZiphraException {
		return grupoIdGenerator.getNextIdFromRepository();
	}

	@Override
	public Long getNextUsuarioId() throws ZiphraException {
		return usuarioIdGenerator.getNextIdFromRepository();
	}

	@Override
	public Long getNextAESId() throws ZiphraException {
		return aesIdGenerator.getNextIdFromRepository();
	}

	@Override
	public Long getNextEncryptKeysId() throws ZiphraException {
		return encryptKeysIdGenerator.getNextIdFromRepository();
	}

	@Override
	public void refresh() throws ZiphraException {
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
	public Map<Long, String> getRandomNicknameByGrupo(String idGrupo) throws ZiphraException {
		// TODO Auto-generated method stub
		return null;
	}

}

