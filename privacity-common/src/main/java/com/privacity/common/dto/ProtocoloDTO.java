package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.dto.response.SaveGrupoGralConfLockResponseDTO;

import lombok.Data;

@Data
public class ProtocoloDTO {

	public ProtocoloDTO() {
		super();
	}

	public ProtocoloDTO(String component, String action) {
		super();
		this.component = component;
		this.action = action;
	}

    private String component;
    private String action;
    @JsonInclude(Include.NON_NULL)
    private String asyncId;
    @JsonInclude(Include.NON_NULL)
    private String mensajeRespuesta;
    @JsonInclude(Include.NON_NULL)
    private String codigoRespuesta;
    @JsonInclude(Include.NON_NULL)
    private RequestIdDTO requestIdDTO;
    @JsonInclude(Include.NON_NULL)
    private GrupoDTO grupoDTO;
    @JsonInclude(Include.NON_NULL)
    private MessageDTO messageDTO;
    @JsonInclude(Include.NON_NULL)
    private String objectDTO; 
    
    @JsonInclude(Include.NON_NULL)
    public SaveGrupoGralConfLockResponseDTO saveGrupoGralConfLockResponseDTO;

}
