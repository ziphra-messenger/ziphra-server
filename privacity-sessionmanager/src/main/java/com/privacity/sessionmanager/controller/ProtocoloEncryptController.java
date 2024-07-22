package com.privacity.sessionmanager.controller;

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

import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.constants.SessionManagerRestConstants;
import com.privacity.sessionmanager.enumeration.Urls;
import com.privacity.sessionmanager.model.AESToUse;
import com.privacity.sessionmanager.services.UtilFacade;
import com.privacity.sessionmanager.util.protocolomap.ProtocoloValue;

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
		log.debug("username" + username);
		log.debug("url " + Urls.valueOf(url));
		log.debug("obj: " + uf.utilsString().cutString(obj));

		ProtocoloDTO p = uf.gson().fromJson(obj, ProtocoloDTO.class);

		ProtocoloValue value = uf.map().get(Urls.valueOf(url),  p.getComponent(),  p.getAction());

		if (p.getObjectDTO() != null) {

			Object o;
			if (value.getParametersTypeOut() != null) {
				o = uf.gson().fromJson(p.getObjectDTO(),value.getParametersTypeOut());
			}else {
				o = uf.gson().fromJson(p.getObjectDTO(),value.getParametersType());	
			}

			o = uf.usuarioSessionInfo().get(username).getPrivacityIdEncoder().encryptIds(o);

			p.setObjectDTO( uf.gson().toJson(o));
		};

		if (p.getMessageDTO()!= null) {
			MessageDTO m = (MessageDTO) uf.usuarioSessionInfo().get(username).getPrivacityIdEncoder().encryptIds(p.getMessageDTO());
			p.setMessageDTO(m);
		}

		log.debug("pre salida " + metodo +" : " + uf.gson().toJson(p));

		String r = uf.utilsString().gsonToSendCompress( aes.getAES(uf.gson().toJson(p)));
		log.debug("salida " + metodo +" : " + uf.utilsString().cutString(r));
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
			} catch (PrivacityException e) {
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
