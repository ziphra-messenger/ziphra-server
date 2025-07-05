package ar.ziphra.sessionmanager.controller;

import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.util.Random;

import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ar.ziphra.common.adapters.LocalDateAdapter;
import ar.ziphra.common.dto.AESDTO;
import ar.ziphra.common.dto.MessageDTO;
import ar.ziphra.common.dto.MessageDetailDTO;
import ar.ziphra.common.dto.ProtocoloDTO;
import ar.ziphra.common.dto.UsuarioDTO;
import ar.ziphra.common.enumeration.ProtocoloActionsEnum;
import ar.ziphra.common.enumeration.ProtocoloComponentsEnum;
import ar.ziphra.commonback.common.utils.AESToUse;
import ar.ziphra.commonback.common.utils.ZiphraIdEncoder;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
public class ProtocoloEncryptControllerTest {

//	@Autowired
//	private ProtocoloEncryptController encr;
//	@Autowired
//	private ProtocoloDecryptController decr;

	@Test
	void registerUser() throws Throwable {
		ProtocoloDTO p = new ProtocoloDTO();
		p.setComponent(ProtocoloComponentsEnum.MESSAGE);
		p.setAction(ProtocoloActionsEnum.MESSAGE_SEND);
		log.debug("test");
		p.setMessage(new MessageDTO().setAnonimo(true));
		
		String pStr = gsonToSend(p);
		
		System.out.println("1- l: " +pStr.length() +" - " + pStr);
		getMessageDTO();
		AESToUse aes= new AESToUse(getAESDTO());
		ZiphraIdEncoder ids = new ZiphraIdEncoder(aes);
		p= (ProtocoloDTO) ids.encryptIds(p);
		pStr = aes.getAES(pStr);
		System.out.println("2- l: " +pStr.length() +" - " + pStr);
		
		//byte[] comprimido = new ZipUtil().compress(pStr.getBytes());
		
		//String.by.toByteArray(comprimido);
		
		//String sss = new String(java.util.Base64.getEncoder().encode(pStr));
		
		System.out.println("3- l: " +pStr.length() +" - " + pStr);
		
		//byte[] comprimido2 = java.util.Base64.getDecoder().decode(comprimido);
		//byte[] descomprimido = new ZipUtil().decompress(comprimido2);
		
		
		//System.out.println("4- l: " +descomprimido.length +" - " + descomprimido);
		
		//String descomprimidoStr= new String(descomprimido);
		//System.out.println("5- l: " +descomprimidoStr.length() +" - " + descomprimidoStr);
		
		String desencriptado = aes.getAESDecrypt(pStr);
		
		System.out.println("6- l: " +desencriptado.length() +" - " + desencriptado);
		
		ProtocoloDTO p2 = gson().fromJson(desencriptado, ProtocoloDTO.class);
		
		System.out.println( (p.hashCode() == (p2.hashCode())));

	}
	public final Gson gson() {
		return new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
				//.excludeFieldsWithoutExposeAnnotation()
				.excludeFieldsWithModifiers(Modifier.STATIC)
				.serializeNulls()
				.create();

		
	}
	public final String replacing(String s) {
		if (s == null) return s;

		String r = s.toString().replace("\n", "").replace("\t", "").replace("\r", "").replace("\b", "").replace("\f", "")
				.replace("  ", " ").replace("  ", " ").replace("  ", " ").replace(", " , ",");

		return r;
	}
	
	public String gsonToSend(Object s) {
		if (s==null)return null;
		
		ObjectMapper mapper = new ObjectMapper();
	  	  String dtoAsString=null;
		try {
			dtoAsString = mapper.writeValueAsString(s);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//throw new ZiphraException(ExceptionReturnCode.GENERAL_INVALID_SENT_DATA);
		}
	//  	dtoAsString= replacing(dtoAsString);
		return dtoAsString;
	}
	
	private AESDTO getAESDTO() {
		
		return Instancio.of(AESDTO.class)
				 .set(Select.field(AESDTO::getBitsEncrypt), (128+""))
				 .set(Select.field(AESDTO::getIteration), (new Random()).nextInt(100)+"")
				  .create();
		
	}
	
	private MessageDTO getMessageDTO() {
		 MessageDTO m = Instancio.of(MessageDTO.class)
				  .set(Select.field(MessageDTO::getIdMessage), (new Random()).nextLong()+"")
				  .set(Select.field(MessageDTO::getIdGrupo), (new Random()).nextLong()+"")
				  .set(Select.field(MessageDTO::getMessagesDetail), (getMessageDetailDTO(5)))
				  .set(Select.field(MessageDTO::getUsuarioCreacion), (getUsuarioDTO()))
				  .ignore(Select.field(MessageDTO::getParentReply))
				  .ignore(Select.field(MessageDTO::getParentResend))
				  .ignore(Select.field(MessageDTO::getMedia))
				  .create();
		 return m;
	}
	private UsuarioDTO getUsuarioDTO() {
		
		return Instancio.of(UsuarioDTO.class)
				 .set(Select.field(UsuarioDTO::getIdUsuario), (new Random()).nextInt()+"")
				 .ignore(Select.field(UsuarioDTO::getEncryptKeysDTO))
				 
				  .create();
		
	}
	private MessageDetailDTO[] getMessageDetailDTO(int cantidad) {
		
		MessageDetailDTO[] r = new MessageDetailDTO[cantidad];
		
		for ( int i = 0; i < cantidad ;i++) {
			r[i] = 		 Instancio.of(MessageDetailDTO.class)
					  .set(Select.field(MessageDetailDTO::getIdMessage), (new Random()).nextLong()+"")
					  .set(Select.field(MessageDetailDTO::getIdGrupo), (new Random()).nextLong()+"")
					  .set(Select.field(MessageDetailDTO::getUsuarioDestino), (getUsuarioDTO()))
					  .create();
		}
		
		return r;
		

	}
}

