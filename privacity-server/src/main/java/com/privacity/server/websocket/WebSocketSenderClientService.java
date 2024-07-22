package com.privacity.server.websocket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.MembersQuantityDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.common.exceptions.ProcessException;
import com.privacity.commonback.common.enumeration.HealthCheckerServerType;
import com.privacity.commonback.constants.MessagingRestConstants;
import com.privacity.commonback.pojo.HealthCheckerPojo;
import com.privacity.core.model.Grupo;
import com.privacity.core.model.Usuario;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.usuario.UserDetailsImpl;
import com.privacity.server.security.JwtUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WebSocketSenderClientService {

	private static final String WEBSOCKET_CHANNEL = "/topic/reply";

	@Autowired
	@Lazy
	private FacadeComponent comps;
	
	@Autowired @Lazy
	private JwtUtils jwtUtils;



	public void senderToUser(ProtocoloDTO p, Usuario usuario) throws PrivacityException  {
		log.debug("senderToUser(ProtocoloDTO p, Usuario usuario");
		
		String urlTemplate = buildSenderUrl(MessagingRestConstants.SENDER_TO_USER);

		MultiValueMap<String, String> params = new  LinkedMultiValueMap<String, String>();
		params.add("obj", comps.util().string().gsonToSend(p));
		params.add("username", usuario.getUsername());

		send(urlTemplate, params);

	}


	
	public void senderToGrupo(ProtocoloDTO p, Long idGrupo, String username) throws PrivacityException {
		log.debug("senderToGrupo(ProtocoloDTO p, Long idGrupo, String username)");
		
		
		String url = UriComponentsBuilder.fromHttpUrl(getServerWS() + MessagingRestConstants.SENDER + MessagingRestConstants.SENDER_TO_GRUPO_EXCLUDE_CREATOR )
         .toUriString();

		MultiValueMap<String, String> params = new  LinkedMultiValueMap<String, String>();
		params.add("idGrupo", idGrupo+"");
		params.add("username", username);
		params.add("obj" ,comps.util().string().replacing( comps.util().gson().toJson(p)));
		send(url, params);
	}

	public void senderToGrupo(ProtocoloDTO p, Long idGrupo) throws PrivacityException {
		log.debug("senderToGrupo(ProtocoloDTO p, Long idGrupo, String username)");
		
		String urlTemplate = buildSenderUrl(MessagingRestConstants.SENDER_TO_GRUPO_ALL);

		MultiValueMap<String, String> params = new  LinkedMultiValueMap<String, String>();
		params.add("obj", comps.util().string().gsonToSend(p));
		params.add("idGrupo", idGrupo+"");
		
		send(urlTemplate, params);

	}


	public ProtocoloDTO buildProtocoloDTO(ProtocoloComponentsEnum comp, ProtocoloActionsEnum action,
			Object obj) throws PrivacityException  {

		ProtocoloDTO p = new ProtocoloDTO(comp,action);
		p.setObjectDTO(comps.util().string().gsonToSend(obj));
		return p;
	}
	public ProtocoloDTO buildProtocoloDTO(ProtocoloComponentsEnum comp, ProtocoloActionsEnum action,
			MessageDTO m) {
		ProtocoloDTO p = new ProtocoloDTO(comp,action);
		p.setMessageDTO(m);
		return p;
	}

	public MembersQuantityDTO getMembersOnline(GrupoDTO grupo) throws PrivacityException {
	
		log.debug("getMembersOnline(GrupoDTO grupo))");
		
		String urlTemplate = buildOnlineMemberUrl(MessagingRestConstants.ONLINE_GET_GRUPO_MEMBERSQUANTITY);

		MultiValueMap<String, String> params = new  LinkedMultiValueMap<String, String>();
		params.add("idGrupo", grupo.getIdGrupo()+"");
		
		MembersQuantityDTO r=null;
		r = new MembersQuantityDTO();
		r.setQuantityOnline(0);
		r.setTotalQuantity(0);
	
		try {
			RestTemplate rest = new RestTemplate();

			return rest.postForObject( urlTemplate, params, MembersQuantityDTO.class);
		} catch (Exception e) {
			log.error(ExceptionReturnCode.MESSAGING_GET_ONLINE_MEMBERS_FAIL.getToShow(grupo.getIdGrupo(), e));
			comps.healthChecker().alertOffLine(HealthCheckerServerType.MESSAGING);
		}

		return r;
		

	}
	public void getMembersOnlineRefresh(String username) throws PrivacityException  {
		log.debug("getMembersOnlineRefresh(String username)");
		
		String urlTemplate = buildOnlineMemberUrl(MessagingRestConstants.ONLINE_REFRESH_FOR_USERNAME);

		MultiValueMap<String, String> params = new  LinkedMultiValueMap<String, String>();
		params.add("username", username);

		send(urlTemplate, params);
		
	}
	

	private String getServerWS() throws PrivacityException {
		return comps.healthChecker().getServerValidate(HealthCheckerServerType.MESSAGING);
	}
	
	public MessageDTO buildSystemMessage(Grupo grupo, String text) throws ProcessException {

		MessageDTO mensaje = new MessageDTO();
		mensaje.setIdGrupo(grupo.getIdGrupo()+"");
		mensaje.setBlackMessage(false);
		mensaje.setTimeMessage(0);
		mensaje.setAnonimo(false);
		mensaje.setSystemMessage(true);
		mensaje.setText(text);
		mensaje.setSystemMessage(true);
		mensaje.setUsuarioCreacion(comps.util().usuario().getUsuarioSystemDTO());

		return mensaje;

	}
	
	private HttpHeaders buildHeaders() throws PrivacityException {
		Authentication auth = SecurityContextHolder
				.getContext()
				.getAuthentication();
		String token =jwtUtils.generateJwtToken(((UserDetailsImpl) auth.getPrincipal()).getUsername());

		

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+token);
		//headers.set("Content-Type", "application/json");
		return headers;
	}
	
	private String buildSenderUrl(String service) throws PrivacityException {
		return UriComponentsBuilder.fromHttpUrl(getServerWS() + MessagingRestConstants.SENDER + service ).toUriString();
	}
	
	private String buildOnlineMemberUrl(String service) throws PrivacityException {
		return UriComponentsBuilder.fromHttpUrl(getServerWS() + MessagingRestConstants.ONLINE + service ).toUriString();
	}
	
	private List<HttpMessageConverter<?>> getJsonMessageConverters() {
	    List<HttpMessageConverter<?>> converters = new ArrayList<>();
	    converters.add(new MappingJackson2HttpMessageConverter());
	    return converters;
	}
	
	private void send(String urlTemplate, MultiValueMap<String, String> params) throws PrivacityException {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					RestTemplate rest = new RestTemplate();
					//rest.setMessageConverters(getJsonMessageConverters());
					
//					rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		//
		//

					
					rest.postForObject( urlTemplate, params, String.class);


		//
//					
//					rest.post
//					
//					(urlTemplate, entity);
//					        
//					String r = rest.postForObject(urlTemplate, params, String.class);

				
				} catch (RestClientException e) {
					e.printStackTrace();
					log.error(e.getMessage());
					comps.healthChecker().alertOffLine(HealthCheckerServerType.MESSAGING);
				} 
				
			}
		}).start();;

	}
}
