package ar.ziphra.sessionmanager.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ar.ziphra.common.dto.MessageDTO;
import ar.ziphra.common.dto.ProtocoloDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.utils.AESToUse;
import ar.ziphra.commonback.constants.SessionManagerRestConstants;
import ar.ziphra.sessionmanager.enumeration.Urls;
import ar.ziphra.sessionmanager.services.UtilFacade;
import ar.ziphra.sessionmanager.util.protocolomap.ProtocoloValue;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = SessionManagerRestConstants.PROTOCOLO_ENCRYPT)
@Slf4j
public class ProtocoloEncryptController {

	@Autowired
	@Lazy
	private UtilFacade uf;

	private String encryptProtocolo(String metodo, AESToUse aes, String username, String obj, String url) throws Throwable, Exception {

		log.debug("entrada - " + metodo );
		log.debug("username: " + username);
		log.debug("url: " + Urls.valueOf(url));
		log.debug("obj: " + uf.utilsString().cutString(obj));

		
		ObjectMapper mapper = new ObjectMapper();
		ProtocoloDTO p;
		try {
			p = mapper.readValue(obj, ProtocoloDTO.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			log.error(ExceptionReturnCode.DECRYPT_PROCESS.toShow(e));
			throw new ZiphraException(ExceptionReturnCode.DECRYPT_PROCESS);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.error(ExceptionReturnCode.DECRYPT_PROCESS.toShow(e));
			throw new ZiphraException(ExceptionReturnCode.DECRYPT_PROCESS);
		}
		ProtocoloValue value = uf.map().get(Urls.valueOf(url),  p.getComponent(),  p.getAction());
		Object o=null;
		if (p.getObjectDTO() != null) {

			
			if (value.getParametersTypeOut() != null) {
				o = uf.gson().fromJson(p.getObjectDTO(),value.getParametersTypeOut());
			}else {
				o = uf.gson().fromJson(p.getObjectDTO(),value.getParametersType());	
			}

			o = uf.usuarioSessionInfo().get(username).getZiphraIdEncoder().encryptIds(o);

			//p.setObjectDTO( uf.utilsString().gsonToSend(o));
		};

		if (p.getMessage()!= null) {
			MessageDTO m = (MessageDTO) uf.usuarioSessionInfo().get(username).getZiphraIdEncoder().encryptIds(p.getMessage());
			p.setMessage(m);
		}

		String preSalida = uf.utilsString().gsonToSend(p);
		log.debug("pre salida " + metodo +" : " + preSalida);

		String r = uf.utilsString().protocoloToSendEncrypt( aes, p, o);
		log.debug("salida " + metodo +" : " + r);
		return r;
	}


	@PostMapping(SessionManagerRestConstants.PROTOCOLO_ENCRYPT_SERVER_OUT)
	public String encryptProtocoloServerOut( String username,String p, String url) throws Throwable, Exception {


		return encryptProtocolo("encryptProtocoloServerOut",
				uf.usuarioSessionInfo().get(username).getSessionAESServerOut(),
				username,p,url);
	}

	@PostMapping(SessionManagerRestConstants.PROTOCOLO_ENCRYPT_WS)
	public String encryptProtocoloWS(@RequestParam String username,  String p, String url) throws Throwable, Exception {
		return encryptProtocolo("encryptProtocoloWS",
				uf.usuarioSessionInfo().get(username).getSessionAESWS(),
				username,p,url);
	}

	@PostMapping(SessionManagerRestConstants.PROTOCOLO_ENCRYPT_WS_USERLIST)
	public Map<String, String> encryptProtocoloWsUsersList(@RequestParam String usersnamelist,  String p, String url) throws Throwable, Exception {



		log.debug("entrada - " + "encryptProtocoloWsUsersList" );
		log.debug("userslist" + usersnamelist);
		log.debug("url " + Urls.valueOf(url));
		log.debug("p" + uf.utilsString().cutString(p));

		String[] userslist = uf.gson().fromJson(usersnamelist, String[].class);
		List<String> wordList = Arrays.asList(userslist);  
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();



		wordList.stream().forEach( username -> {
			try {
				map.put(username, encryptProtocolo("encryptProtocoloWsUsersList",
						uf.usuarioSessionInfo().get(username).getSessionAESWS(),
						username,p,url));
			} catch (ZiphraException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return map;
	}
}
