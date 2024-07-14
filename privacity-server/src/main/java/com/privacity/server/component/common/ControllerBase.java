package com.privacity.server.component.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;

import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.interfaces.IdGrupoInterface;
import com.privacity.server.common.enumeration.Urls;
import com.privacity.server.common.utils.UtilsString;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.requestid.RequestIdUtilService;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.services.protocolomap.ProtocoloValue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ControllerBase {

	@Autowired
	@Lazy
	private RequestIdUtilService requestIdUtil;

	@Autowired
	@Lazy
	private FacadeComponent comps;


	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object getDTOObject(ProtocoloDTO request, String objectDTO, Class clazz) {

		log.debug("getDTOObject Base :" +UtilsString.shrinkString ( objectDTO) + "clazz:" + clazz.getName());

		return UtilsString.gson().fromJson(objectDTO, clazz);
	}


	public ProtocoloDTO in(@RequestBody ProtocoloDTO request) throws Exception {

		log.debug(">> " + request.toString());

		ProtocoloValue value = comps.service().protocoloMap().get(getUrl(),  request.getComponent(),  request.getAction());
		ProtocoloDTO p = new ProtocoloDTO();

		// tomo el dto a ejecutar
		Object objetoRetorno=null;
		Object dtoObject=null;


		try {
			if (value == null) {
				throw new ValidationException(ExceptionReturnCode.GENERAL_INVALID_ACCESS_PROTOCOL);
			}
			if ( this.isSecure() && (this.isRequestId()) && !request.getAction().equals(ProtocoloActionsEnum.REQUEST_ID_PRIVATE_GET)) {
				requestIdUtil.existsRequestId(request.getRequestIdDTO(), true) ;
			}else if ( !this.isSecure() && (this.isRequestId()) && !request.getAction().equals(ProtocoloActionsEnum.REQUEST_ID_PUBLIC_GET)) {
				requestIdUtil.existsRequestId(request.getRequestIdDTO(),false) ;
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

				log.debug("Invoke 2 parameter = " + ((dtoObject==null) ? "null" : UtilsString.shrinkString(dtoObject.toString())));

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


		if ( this.isSecure()){

			objetoRetorno= comps.service().usuarioSessionInfo().privacityIdServiceEncrypt2(
					comps.requestHelper().getUsuarioUsername()
					, objetoRetorno
					,value.getReturnType().getName());
		}

		p.setComponent(request.getComponent());
		p.setAction(request.getAction());

		if (objetoRetorno instanceof MessageDTO ) {
			p.setMessageDTO((MessageDTO)objetoRetorno);
		}else {

			String retornoJson=null;
			if (objetoRetorno != null) {
				retornoJson = UtilsString.gsonToSend(objetoRetorno);	
			}

			log.debug("ProtocoloDTO Retorno >> " + UtilsString.shrinkString(retornoJson));

			p.setObjectDTO(retornoJson);	
		}

		p.setAsyncId(request.getAsyncId());
		return p;

	}	

	public abstract boolean isSecure();

	public abstract boolean isRequestId();

	public abstract Urls getUrl();
}