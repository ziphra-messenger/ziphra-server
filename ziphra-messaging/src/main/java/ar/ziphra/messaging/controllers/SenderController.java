package ar.ziphra.messaging.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonSyntaxException;
import ar.ziphra.common.dto.ProtocoloDTO;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.constants.MessagingRestConstants;
import ar.ziphra.messaging.util.UtilFacade;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = MessagingRestConstants.SENDER)
@Slf4j
public class SenderController {

	@Autowired
	@Lazy
	private UtilFacade uf;

	@PostMapping(MessagingRestConstants.SENDER_TO_USER)
	public void senderToUser( String username,  String obj)  {
		try {
			log.debug("entrada - senderToUser");
			log.debug("username" + username);
			log.debug("obj" + uf.utilsString().cutString(obj));
			
			ProtocoloDTO p = uf.gson().fromJson(obj, ProtocoloDTO.class);
			
			log.debug("p: " + uf.utilsString().cutStringToGson(p));
			
			uf.webSocketSender().senderToUser(p,username);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZiphraException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@PostMapping( MessagingRestConstants.SENDER_TO_GRUPO_EXCLUDE_CREATOR)
	public void senderToGrupoExcludeCreator( String username, String obj, String idGrupo
			) throws ZiphraException {

		log.debug("entrada - senderToGrupoExcludeCreator");
		log.debug("idGrupo" + idGrupo);
		log.debug("username" + username);
		log.debug("obj: " + uf.utilsString().cutString(obj));

		ProtocoloDTO p = uf.gson().fromJson(obj, ProtocoloDTO.class);
		
		log.debug("p: " + uf.utilsString().cutStringToGson(p));
		
		uf.webSocketSender().senderToGrupo(p,Long.parseLong( idGrupo), username, true);
	}

	@PostMapping(MessagingRestConstants.SENDER_TO_GRUPO_ALL)
	public void senderToGrupoAll( String idGrupo,  String obj) throws ZiphraException {
		log.debug("entrada - senderToGrupoAll");
		log.debug("idGrupo" + idGrupo);
		log.debug("obj: " + uf.utilsString().cutStringToGson(obj));
		
		ProtocoloDTO p = uf.gson().fromJson(obj, ProtocoloDTO.class);
		
		log.debug("p: " + uf.utilsString().cutStringToGson(p));
		
		uf.webSocketSender().senderToGrupo(p,  Long.parseLong( idGrupo), null, false);
	}
	
	@PostMapping(MessagingRestConstants.SENDER_TO_EVERYONE)
	public void senderToEveryOne( String obj) throws ZiphraException {
		log.debug("entrada - senderToEveryOne");
		log.debug("obj: " + uf.utilsString().cutStringToGson(obj));
		
		ProtocoloDTO p = uf.gson().fromJson(obj, ProtocoloDTO.class);
		
		log.debug("p: " + uf.utilsString().cutStringToGson(p));
	}
	
//	public static void main(String[] args) {
//		UtilsStringService s = new UtilsStringService();
//		
//		String f=
//"{ \"component\": \"MESSAGE\"\"action\": \"MESSAGE_RECIVIED\"\"messageDTO\": { \"idMessage\": \"13040\"\"idGrupo\": \"27072\"\"usuarioCreacion\": { \"idUsuario\": \"163972\"\"nickname\": \"2222\" }\"text\": \"20mET2y094bAAfx2XKzTlIuM21+harozY2sqOir6CY0\\u003d\"\"blackMessage\": false\"timeMessage\": 0\"anonimo\": false\"systemMessage\": false\"secretKeyPersonal\": false\"permitirReenvio\": true\"MessagesDetailDTO\": [ { \"idMessage\": \"13040\"\"idGrupo\": \"27072\"\"usuarioDestino\": {  \"idUsuario\": \"163972\" \"nickname\": \"2222\" }\"estado\": \"MY_MESSAGE_SENT\"\"deleted\": false }{ \"idMessage\": \"13040\"\"idGrupo\": \"27072\"\"usuarioDestino\": {  \"idUsuario\": \"164142\" \"nickname\": \"1\" }\"estado\": \"DESTINY_SERVER\"\"deleted\": false } ]\"changeNicknameToRandom\": false\"hideMessageDetails\": false\"hideMessageState\": false }}\r\n"
//				+ "";
//		
//		s.gson().fromJson(f, ProtocoloDTO.class);
//	}
}
