package ar.ziphra.idsprovider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ziphra.commonback.common.utils.IdsProviderRestConstants;
import ar.ziphra.core.idsprovider.AESIdGeneratorService;
import ar.ziphra.core.idsprovider.EncryptKeysIdGeneratorService;
import ar.ziphra.core.idsprovider.GrupoIdGeneratorService;
import ar.ziphra.core.idsprovider.MessageIdGeneratorService;
import ar.ziphra.core.idsprovider.UsuarioIdGeneratorService;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping(path = IdsProviderRestConstants.GENERATOR)
@Slf4j

public class GeneratorController {

	
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
	
	public GeneratorController() {
		super();

	}
	@PostMapping(IdsProviderRestConstants.GENERATOR_GET_NEXT_MESSAGE_ID + "/{grupoid}")
	public long getIdMessageByGrupo(@PathVariable long grupoid) throws Exception {
		log.debug("entrada getIdMessageByGrupo: " + grupoid);
		return messageIdGenerator.getNextMessageId(grupoid);

	}

	@PostMapping(IdsProviderRestConstants.GENERATOR_GET_NEXT_GRUPO_ID)
	public long getNextGrupoId() throws Exception {
		log.debug("entrada getNextGrupoId");
		
		return grupoIdGenerator.getNextId();

	}
	@PostMapping(IdsProviderRestConstants.GENERATOR_GET_NEXT_ENCRYPT_KEYS_ID)
	public long getNextEncryptKeysId() throws Exception {
		log.debug("entrada getNextEncryptKeysId");
		
		return encryptKeysIdGenerator.getNextId();

	}

	@PostMapping(IdsProviderRestConstants.GENERATOR_GET_NEXT_USUARIO_ID)
	public long getNextUsuarioId() throws Exception {
		log.debug("entrada getNextUsuarioId");
		
		return usuarioIdGenerator.getNextId();

	}
	
	@PostMapping(IdsProviderRestConstants.GENERATOR_GET_NEXT_AES_ID)
	public long getNextAESId() throws Exception {
		log.debug("entrada getNextAESId");
		
		return aesIdGenerator.getNextId();

	}	

}
