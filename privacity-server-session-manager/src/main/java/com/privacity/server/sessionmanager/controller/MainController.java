package com.privacity.server.sessionmanager.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.dto.AESAllDTO;
import com.privacity.common.dto.AESDTO;
import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.RequestIdDTO;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.server.sessionmanager.enumeration.Urls;
import com.privacity.server.sessionmanager.services.PrivacityIdServices;
import com.privacity.server.sessionmanager.services.UsuarioSessionInfoService;
import com.privacity.server.sessionmanager.util.LocalDateAdapter;
import com.privacity.server.sessionmanager.util.protocolomap.ProtocoloMapService;
import com.privacity.server.sessionmanager.util.protocolomap.ProtocoloValue;

@RestController
@RequestMapping(path = "/entry")
public class MainController {
	private static final int MAX_LOG = 2000;
	private static final Logger log = Logger.getLogger(MainController.class.getCanonicalName());
	@Autowired
	UsuarioSessionInfoService usuarioSessionInfoService;
	Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
			.create();
	@Autowired
	private ProtocoloMapService map;
	
//	@PostMapping("/encryption/encrypt/protocolo/Server")
//	public String encryptProtocolo(@RequestParam String username, @RequestParam String obj, @RequestParam String url) throws Throwable, Exception {
//		
//		//String protS = usuarioSessionInfoService.get(username).getSessionAESServerOut().getAES(obj);
//		ProtocoloDTO p =  gson.fromJson(obj, ProtocoloDTO.class);
//		
//		ProtocoloValue value = map.get(Urls.valueOf(url),  p.getComponent(),  p.getAction());
//
//		
//		Object o = gson.fromJson(p.getObjectDTO(),value.getParametersType());
//		
//		o = usuarioSessionInfoService.get(username).getPrivacityIdServices().encryptIds(o);
//		
//		p.setObjectDTO( gson.toJson(o)) ;
//		
//		
//		return usuarioSessionInfoService.get(username).getSessionAESServerOut().getAES(gson.toJson(p));
//	}
	
	@PostMapping("/encryption/encrypt/protocolo")
	public String encryptProtocoloServerOut(@RequestParam String username, @RequestParam String obj, @RequestParam String url) throws Throwable, Exception {
		
		//String protS = usuarioSessionInfoService.get(username).getSessionAESServerOut().getAES(obj);
		ProtocoloDTO p =  gson.fromJson(obj, ProtocoloDTO.class);
		
		ProtocoloValue value = map.get(Urls.valueOf(url),  p.getComponent(),  p.getAction());

		if (p.getObjectDTO() != null) {
			Object o = gson.fromJson(p.getObjectDTO(),value.getParametersType());
			
			o = usuarioSessionInfoService.get(username).getPrivacityIdServices().encryptIds(o);
			
			p.setObjectDTO( gson.toJson(o));
		};
		
		if (p.getMessageDTO()!= null) {
			
			MessageDTO m = (MessageDTO) usuarioSessionInfoService.get(username).getPrivacityIdServices().encryptIds(p.getMessageDTO());
			
			p.setMessageDTO(m);

			
		}
		return usuarioSessionInfoService.get(username).getSessionAESServerOut().getAES(gson.toJson(p));
	}
	
	@PostMapping("/encryption/encrypt/protocoloWS")
	public String encryptProtocoloWS(@RequestParam String username, @RequestParam String obj, @RequestParam String url) throws Throwable, Exception {
		log.info("Session manager Entrada WS");
		log.info("username: " + username);
		log.info("url: " + url);
		log.info("Object: " + shrinkString(obj));
		//String protS = usuarioSessionInfoService.get(username).getSessionAESServerOut().getAES(obj);
		ProtocoloDTO p =  gson.fromJson(obj, ProtocoloDTO.class);
		
		ProtocoloValue value = map.get(Urls.valueOf(url),  p.getComponent(),  p.getAction());

		if (p.getObjectDTO() != null) {
			Object o = gson.fromJson(p.getObjectDTO(),value.getParametersType());
			
			o = usuarioSessionInfoService.get(username).getPrivacityIdServices().encryptIds(o);
			
			p.setObjectDTO( gson.toJson(o));
		};
		
		if (p.getMessageDTO()!= null) {
			
			MessageDTO m = (MessageDTO) usuarioSessionInfoService.get(username).getPrivacityIdServices().encryptIds(p.getMessageDTO());
			
			p.setMessageDTO(m);

			
		}
		
		String r = usuarioSessionInfoService.get(username).getSessionAESWS().getAES(gson.toJson(p));
		
		log.info(shrinkString(r.toString()));
		
		return r;
	}
	
	@PostMapping("/encryption/decrypt/protocoloServerOutTest")
	public ProtocoloDTO decryptProtocoloServerOutTest(@RequestParam String username, @RequestParam String obj, @RequestParam String url) throws Throwable, Exception {
		log.fine("entrada");
		log.fine(username);
		log.fine(obj);
	
		
		String pS = usuarioSessionInfoService.get(username).getSessionAESServerOut().getAESDecrypt(obj);
		//String protS = usuarioSessionInfoService.get(username).getSessionAESServerOut().getAES(obj);
		ProtocoloDTO p =  gson.fromJson(pS, ProtocoloDTO.class);
		
		ProtocoloValue value = map.get(Urls.valueOf(url),  p.getComponent(),  p.getAction());

		
		if ( p.getObjectDTO() != null ) {
			log.fine("entrada 1");
			Object o = gson.fromJson(p.getObjectDTO(),value.getParametersType());
			
			o = usuarioSessionInfoService.get(username).getPrivacityIdServices().decryptIds(o);
			
			p.setObjectDTO( gson.toJson(o)) ;
		}
		
		if (p.getMessageDTO()!= null) {
			
			log.fine("entrada 2");
		
			
			MessageDTO m = (MessageDTO) usuarioSessionInfoService.get(username).getPrivacityIdServices().decryptIds(p.getMessageDTO());
			
			p.setMessageDTO(m);

			
		}
		if (p.getMessageDTO() != null && p.getMessageDTO().getMediaDTO() != null) {
			log.fine("entrada 3");
			byte[] dataDescr = usuarioSessionInfoService.get(username).getSessionAESServerIn().getAESDecryptData(p.getMessageDTO().getMediaDTO().getData());
			p.getMessageDTO().getMediaDTO().setData(dataDescr);
			
		}
		log.fine("salida " + gson.toJson(p));
		return p;
	}
	
	@PostMapping("/encryption/decrypt/protocolo")
	public ProtocoloDTO decryptProtocolo(@RequestParam String username, @RequestParam String obj, @RequestParam String url) throws Throwable, Exception {
		log.fine("entrada");
		log.fine(username);
		log.fine(obj);
	
		
		String pS = usuarioSessionInfoService.get(username).getSessionAESServerIn().getAESDecrypt(obj);
		//String protS = usuarioSessionInfoService.get(username).getSessionAESServerOut().getAES(obj);
		ProtocoloDTO p =  gson.fromJson(pS, ProtocoloDTO.class);
		log.fine("protocolo");
		log.fine(shrinkString(p.toString()));
		ProtocoloValue value = map.get(Urls.valueOf(url),  p.getComponent(),  p.getAction());

		
		if ( p.getObjectDTO() != null ) {
			log.fine("entrada 1");
			Object o = gson.fromJson(p.getObjectDTO(),value.getParametersType());
			
			o = usuarioSessionInfoService.get(username).getPrivacityIdServices().decryptIds(o);
			
			p.setObjectDTO( gson.toJson(o)) ;
		}
		
		if (p.getMessageDTO()!= null) {
			
			log.fine("entrada 2");
		
			
			MessageDTO m = (MessageDTO) usuarioSessionInfoService.get(username).getPrivacityIdServices().decryptIds(p.getMessageDTO());
			
			p.setMessageDTO(m);

			if (p.getMessageDTO().getMediaDTO() != null) {
//				byte[] dataDescr = c.getAESDecryptData(data.getBytes());
//				p.getMessageDTO().getMediaDTO().setData(dataDescr);
				
			}
			
			
		}
		if (p.getMessageDTO() != null && p.getMessageDTO().getMediaDTO() != null) {
			log.fine("entrada 3");
			byte[] dataDescr = usuarioSessionInfoService.get(username).getSessionAESServerIn().getAESDecryptData(p.getMessageDTO().getMediaDTO().getData());
			p.getMessageDTO().getMediaDTO().setData(dataDescr);
			
		}
		log.fine("salida " + gson.toJson(p));
		return p;
	}


	@PostMapping("/encryption/encrypt/ids")
	public Object encrypt(@RequestParam String username, @RequestParam String obj, @RequestParam String className) throws Throwable, Exception {
		
		if ("void".equals(className)) return null;
		
		Object o = gson.fromJson(obj, Class.forName(className));
		return  usuarioSessionInfoService.get(username).getPrivacityIdServices().encryptIds(o);
	}
	
	@PostMapping("/encryption/decrypt/sessionAESServerIn")
	public String decryptSessionAESServerIn(@RequestParam String username, @RequestParam String obj, @RequestParam String className) throws Throwable, Exception {
		log.fine("entrada");
		log.fine(username);
		log.fine(obj);
		log.fine(className);
		
		String r = usuarioSessionInfoService.get(username).getSessionAESServerIn().getAESDecrypt(obj);
		log.fine(r);
		//Object objDR = gson.fromJson(objD, Class.forName(className));
		
		log.fine("retorno");
		log.fine(r);
		return r;
		
	}
	
	@PostMapping("/encryption/encrypt/sessionAESServerWS")
	public String encryptSessionAESServerWS(@RequestParam String username, @RequestParam String obj) throws Throwable, Exception {
		String objD = usuarioSessionInfoService.get(username).getSessionAESWS().getAES(obj);
		return objD;
		
	}

	@PostMapping("/encryption/encrypt/sessionAESServerOut")
	public String encryptSessionAESServerOut(@RequestParam String username, @RequestParam String obj) throws Throwable, Exception {
		String objD = usuarioSessionInfoService.get(username).getSessionAESServerOut().getAES(obj);
		return objD;
		
	}
	
	@PostMapping("/encryption/decrypt/ids")
	public Object decrypt(@RequestParam String username, @RequestParam String obj, @RequestParam String className) throws Throwable, Exception {
		log.info("Entrada");
		log.info("username: " + username);
		log.info("className: " + className);
		log.info("Object: " + shrinkString(obj));

		Object o = gson.fromJson(obj, Class.forName(className));
		
		
		Object r = usuarioSessionInfoService.get(username).getPrivacityIdServices().decryptIds(o);
		log.info("Salida");
		
		log.info(shrinkString(r.toString()));
		return r;

		
	}

	private String shrinkString(String s) {
		String r = s.toString().replace("\n", "").replace("\t", "").replace("\r", "").replace("\b", "").replace("\f", "")
				.replace("  ", " ").replace("  ", " ").replace("  ", " ").replace(", -" , "").replace(", " , "");
						
		return r.substring(0, (r.length()> MAX_LOG)? MAX_LOG : r.length()-1);
	}
	
	@PostMapping("/get/sessionAESServerIn")
	public AESDTO getAesDtoSessionIn(String username) throws Exception {
		return usuarioSessionInfoService.get(username).getSessionAESServerIn().getAESDTO();

	}

	@PostMapping("/get/sessionAESServerOut")
	public AESDTO getSessionAESServerOut(String username) throws Exception {
		return usuarioSessionInfoService.get(username).getSessionAESServerOut().getAESDTO();

	}

	@PostMapping("/get/sessionAESWS")
	public AESDTO getAesDtoSessionWs(String username) throws Exception {
		return usuarioSessionInfoService.get(username).getSessionAESWS().getAESDTO();

	}

	@PostMapping("/get/AesDtoAll")
	public AESAllDTO getAesDtoAll(String username) throws Exception {
		return usuarioSessionInfoService.get(username).getAESAllDTO();

	}
	
	@PostMapping("/remove/session")
	public void removeSession(String username) throws Exception {
		usuarioSessionInfoService.remove(username);	
	}
	
	@PostMapping("/requestId/add")
	public void requestIdAdd(String username, String requestIdClientSide, String serverRequestIdDTO) throws Exception {
		usuarioSessionInfoService.get(username).getRequestIds().put(requestIdClientSide,
				gson.fromJson(requestIdClientSide, RequestIdDTO.class));	
	}
	@PostMapping("/requestId/getAll")
	public Map requestIdGetAll(String username) throws Exception {
		return usuarioSessionInfoService.get(username).getRequestIds();	
	}
	
	public static void main(String[] args) {
		
		ProtocoloDTO p = new ProtocoloDTO();
		p.setComponent(ProtocoloComponentsEnum.GRUPO);
		p.setAction(ProtocoloActionsEnum.GRUPO_GRAL_CONF_SAVE_LOCK);
		GrupoDTO g = new GrupoDTO();
		g.setIdGrupo("123");

		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				
				.create();
		String j = gson.toJson(g);
		p.setObjectDTO(j);
		
		log.fine(gson.toJson(p));
	}
}
