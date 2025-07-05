package ar.ziphra.server.factory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.core.idsprovider.AESIdGeneratorService;
import ar.ziphra.core.idsprovider.EncryptKeysIdGeneratorService;
import ar.ziphra.core.idsprovider.GrupoIdGeneratorService;
import ar.ziphra.core.idsprovider.MessageIdGeneratorRepositoryService;
import ar.ziphra.core.idsprovider.UsuarioIdGeneratorService;
import ar.ziphra.core.interfaces.IdsGeneratorInterface;

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
		log.error(ExceptionReturnCode.IDS_PROVIDER_REFRESH_MUST_NOT_BE_CALLED_IN_THIS_CASE.toShow("Obtiene el id desde la base de datos, no es necesario refrescar"));
		throw new ZiphraException(ExceptionReturnCode.IDS_PROVIDER_REFRESH_MUST_NOT_BE_CALLED_IN_THIS_CASE);
	}

	@Override
	public Map<Long, String> getRandomNicknameByGrupo(String idGrupo) throws ZiphraException {
		// TODO Auto-generated method stub
		return null;
	}

}
