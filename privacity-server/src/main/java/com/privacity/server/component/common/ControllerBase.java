package com.privacity.server.component.common;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestBody;

import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.exceptions.ValidationException;
import com.privacity.common.interfaces.IdGrupoInterface;
import com.privacity.commonback.common.enumeration.ServerUrls;
import com.privacity.server.services.protocolomap.ProtocoloValue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ControllerBase extends ControllerBaseUtil {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object getDTOObject(ProtocoloDTO request, String objectDTO, Class clazz) throws IOException {
		log.debug("getDTOObject Base :" +comps.util().string().cutString ( objectDTO) + "clazz:" + clazz.getName());
		return comps.util().gson().fromJson(objectDTO, clazz);
	}


	public ProtocoloDTO in(@RequestBody ProtocoloDTO request)  {

		log.debug(">> " + request.toString());

		
		ProtocoloDTO p = new ProtocoloDTO();

		// tomo el dto a ejecutar
		Object objetoRetorno=null;
		Object dtoObject=null;


		try {
			ProtocoloValue value = comps.service().protocoloMap().get(getUrl(),  request.getComponent(),  request.getAction());
			
			if (value == null) {
				throw new ValidationException(ExceptionReturnCode.GENERAL_INVALID_ACCESS_PROTOCOL);
			}
			if ( this.isSecure() && (this.isRequestId()) && !request.getAction().equals(ProtocoloActionsEnum.REQUEST_ID_PRIVATE_GET)) {
				comps.util().requestId().existsRequestId(request.getRequestIdDTO(), true) ;
			}else if ( !this.isSecure() && (this.isRequestId()) && !request.getAction().equals(ProtocoloActionsEnum.REQUEST_ID_PUBLIC_GET)) {
				comps.util().requestId().existsRequestId(request.getRequestIdDTO(),false) ;
			}

			log.debug("URL Base = " + request.getComponent());
			log.debug("COMPONENT Base = " + request.getComponent());
			log.debug("ACTION Base = " + request.getAction());

			if (   value.getParametersType() == null) {

				log.debug("Invoke 1 sin parametros = " + value.getMethod().getName() + " " + value.getClazz().toString());
				objetoRetorno = value.getMethod().invoke(value.getClazz());
			}else {
				dtoObject =  getDTOObject(request, request.getObjectDTO(), value.getParametersType());


				log.debug("Invoke 1 parametro = " + value.getMethod().getName() + " " + value.getClazz().toString());

				log.debug("Invoke 2 parameter = " + ((dtoObject==null) ? "null" : comps.util().string().cutString(dtoObject.toString())));

				if (dtoObject!= null && dtoObject instanceof IdGrupoInterface) {
					comps.requestHelper().setGrupoInUse((IdGrupoInterface)dtoObject);
				}
				objetoRetorno =value.getMethod().invoke(value.getClazz(),  dtoObject);
			}



		} catch (Exception e) {
			e.printStackTrace();
			p.setObjectDTO(null);
			if ( e.getCause() != null) {
				p.setCodigoRespuesta(e.getCause().getMessage());
			}else if( e.getMessage() != null && !e.getMessage().equals("")) {
				if (e instanceof ValidationException) {
					p.setMensajeRespuesta(((ValidationException)e).getDescription());

				}
				p.setCodigoRespuesta(e.getMessage());
				//p.setMensajeRespuesta(e.getCause());
			}else {
				p.setMensajeRespuesta("UNCLASSIFIED_ERROR");
				p.setCodigoRespuesta("G_000");
			}

		} 


		//		if ( this.isSecure()){
		//
		//			objetoRetorno= comps.service().usuarioSessionInfo().privacityIdServiceEncrypt2(
		//					comps.requestHelper().getUsuarioUsername()
		//					, objetoRetorno
		//					,value.getReturnType().getName());
		//		}

		p.setComponent(request.getComponent());
		p.setAction(request.getAction());

		if (objetoRetorno instanceof MessageDTO ) {
			p.setMessage((MessageDTO)objetoRetorno);
		}else {

			String retornoJson=null;
			if (objetoRetorno != null) {
				//				if ( !this.isSecure()){
				//				retornoJson = UtilsString.gsonToSend(objetoRetorno);
				//				}else {
				retornoJson=comps.util().gson().toJson(objetoRetorno);
				//}

			}

			log.debug("ProtocoloDTO Retorno >> " + comps.util().string().cutString(retornoJson));

			p.setObjectDTO(retornoJson);	
		}

		p.setAsyncId(request.getAsyncId());
		return p;

	}	

	public abstract boolean isSecure();

	public abstract boolean isRequestId();

	public abstract ServerUrls getUrl();
	

}