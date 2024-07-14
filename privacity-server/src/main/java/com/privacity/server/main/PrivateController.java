package com.privacity.server.main;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.request.RequestEncryptDTO;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.server.common.adapters.LocalDateAdapter;
import com.privacity.server.common.enumeration.Urls;
import com.privacity.server.common.utils.UtilsString;
import com.privacity.server.component.common.ControllerBase;
import com.privacity.server.component.common.service.facade.FacadeComponent;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/private")
@Slf4j
public class PrivateController extends ControllerBase{

	@Autowired @Lazy
	private FacadeComponent comps;
	
	@PostMapping("/entry")
	public ResponseEntity<String> inMain(@RequestBody String request, @RequestHeader Map<String, String> headers) throws Exception {
        
		RequestEncryptDTO requestDTO = UtilsString.gson().fromJson(request, RequestEncryptDTO.class);
		request = requestDTO.getRequest();
		ProtocoloDTO p = comps.service().usuarioSessionInfo().decryptProtocolo(comps.requestHelper().getUsuarioUsername(), request, getUrl().name());

		 log.debug( " ================================================================================");
		
		ProtocoloDTO retornoFuncion = super.in(p);
		
		if ("null".equals(retornoFuncion.getObjectDTO() ) && retornoFuncion.getCodigoRespuesta() != null ){
			retornoFuncion.setObjectDTO(null);
			return ResponseEntity.ok().body(UtilsString.gsonToSend(retornoFuncion));
		}
		String retornoFuncionJson = UtilsString.gsonToSend(retornoFuncion);
		String retornoFuncionEncriptado = comps.service().usuarioSessionInfo().encryptSessionAESServerOut(comps.requestHelper().getUsuarioUsername(),retornoFuncionJson);

			if ( retornoFuncion.getMessageDTO() != null)
			{
				log.debug ( " Salida >>  " + UtilsString.shrinkString(retornoFuncion.toString()));
			}
			else {
				log.debug( " Salida >>  " + UtilsString.shrinkString(retornoFuncionJson));
				log.debug( " ================================================================================");
			}
		
		return ResponseEntity.ok().body(UtilsString.gsonToSend(retornoFuncionEncriptado));

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
	public Urls getUrl() {
		return Urls.CONSTANT_URL_PATH_PRIVATE;
	}

}
