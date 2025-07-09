package ar.ziphra.sessionmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ziphra.common.dto.MessageDTO;
import ar.ziphra.common.dto.ProtocoloDTO;
import ar.ziphra.commonback.constants.SessionManagerRestConstants;
import ar.ziphra.sessionmanager.enumeration.Urls;
import ar.ziphra.sessionmanager.services.UtilFacade;
import ar.ziphra.sessionmanager.util.protocolomap.ProtocoloValue;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = SessionManagerRestConstants.PROTOCOLO_DECRYPT)
@Slf4j
public class ProtocoloDecryptController {

	@Autowired
	@Lazy
	private UtilFacade uf;

	@PostMapping(SessionManagerRestConstants.PROTOCOLO_DECRYPT_SERVER_IN)
	public String decryptProtocolo(@RequestParam String username, @RequestParam String obj, @RequestParam String url) throws Throwable, Exception {
		log.debug("entrada - decryptProtocolo");
		log.debug("username" + username);
		log.debug("url " + Urls.valueOf(url));
		log.debug("obj: " + uf.utilsString().cutString(obj));

		ProtocoloDTO p = uf.utilsString().protocoloToSendDecrypt(uf.usuarioSessionInfo().get(username).getSessionAESServerIn(), obj);

		log.trace("Protocolo: " + uf.utilsString().cutStringToGson(p));

		ProtocoloValue value = uf.map().get(Urls.valueOf(url),  p.getComponent(),  p.getAction());

		if ( p.getObjectDTO() != null ) {
			 
			log.debug("p.getObjectDTO(): " + p.getObjectDTO());
			Object o = uf.gson().fromJson(p.getObjectDTO(),value.getParametersType());
			o = uf.usuarioSessionInfo().get(username).getZiphraIdEncoder().decryptIds(o);
			if ( p.getObjectDTO().equals("[]")) {
				o=null;
			}
			p.setObjectDTO( uf.utilsString().gsonToSend(o)) ;
		}

		if (p.getMessage()!= null) {
			MessageDTO m = (MessageDTO) uf.usuarioSessionInfo().get(username).getZiphraIdEncoder().decryptIds(p.getMessage());
			p.setMessage(m);
		}

		log.debug("salida decryptProtocolo : " + uf.utilsString().gsonToSend(p));
		return  uf.utilsString().gsonToSend(p);
	}



}
