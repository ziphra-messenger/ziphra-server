package com.privacity.server.main;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.request.RequestEncryptDTO;
import com.privacity.commonback.common.enumeration.ServerUrls;
import com.privacity.commonback.common.interfaces.HealthCheckerInterface;
import com.privacity.server.component.common.ControllerBase;
import com.privacity.server.component.common.service.facade.FacadeComponent;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/private")
@Slf4j
public class PrivateController extends ControllerBase{

	@Autowired @Lazy
	private FacadeComponent comps;
	@Autowired
	@Lazy
	private HealthCheckerInterface healthChecker;
	@PostMapping("/entry")
	public ResponseEntity<String> inMain(@RequestBody String request, @RequestHeader Map<String, String> headers) throws Exception {
        
		if ( checkServersSessionManager()!= null) return checkServersSessionManager();
		if ( checkServersRequestId()!= null) return checkServersRequestId();
				
		RequestEncryptDTO requestDTO = comps.util().gson().fromJson(request, RequestEncryptDTO.class);
		request = requestDTO.getRequest();
		ProtocoloDTO p = comps.service().usuarioSessionInfo().decryptProtocolo(comps.requestHelper().getUsuarioUsername(), request, getUrl().name());

		 log.debug( " ================================================================================");
		
		ProtocoloDTO retornoFuncion = super.in(p);
		//log.debug( " retornoFuncion >>  " + comps.util().string().cutStringToGson(retornoFuncion));
		//System.out.println("ret completp " + comps.util().string().gsonPretty().toJson(retornoFuncion));
			String objetoRetorno = comps.service().usuarioSessionInfo().encryptProtocolo(
					comps.requestHelper().getUsuarioUsername()
					, retornoFuncion, getUrl().name());
		
//		if ("null".equals(retornoFuncion.getObjectDTO() ) && retornoFuncion.getCodigoRespuesta() != null ){
//			retornoFuncion.setObjectDTO(null);
//			return ResponseEntity.ok().body(UtilsString.gsonToSend(retornoFuncion));
//		}
//		
//		String retornoFuncionEncriptado = comps.service().usuarioSessionInfo().encryptSessionAESServerOut(comps.requestHelper().getUsuarioUsername(),comps.util().gson().toJson(retornoFuncion)   );
//
//			if ( retornoFuncion.getMessageDTO() != null)
//			{
//				log.debug ( " Salida >>  " + comps.util().string().cutString(retornoFuncion.toString()));
//			}
//			else {
//				log.debug( " Salida >>  " + comps.util().string().cutString(retornoFuncionEncriptado));
//				log.debug( " ================================================================================");
//			}
//		
			
			log.debug( " Salida >>  " + comps.util().string().gsonPretty().toJson(objetoRetorno));
			System.out.println(comps.util().string().gsonPretty().toJson(objetoRetorno));
		return ResponseEntity.ok().body(objetoRetorno);

	}





	@Override
	public boolean isSecure() {
		return true;
	}

	@Override
	public boolean isRequestId() {
		return true;
	}
	
	@Override
	public ServerUrls getUrl() {
		return ServerUrls.CONSTANT_URL_PATH_PRIVATE;
	}

}
