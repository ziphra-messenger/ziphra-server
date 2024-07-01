package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.dto.response.SaveGrupoGralConfLockResponseDTO;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ProtocoloDTO {

	public ProtocoloDTO() {
		super();
	}

	public ProtocoloDTO(ProtocoloComponentsEnum component, ProtocoloActionsEnum action) {
		super();
		this.component = component;
		this.action = action;
	}

    private ProtocoloComponentsEnum component;
    private ProtocoloActionsEnum action;
    
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
