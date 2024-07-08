package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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

    public ProtocoloComponentsEnum component;
    public ProtocoloActionsEnum action;
    
    @JsonInclude(Include.NON_NULL)
    public String asyncId;
    @JsonInclude(Include.NON_NULL)
    public String mensajeRespuesta;
    
    @JsonInclude(Include.NON_NULL)
    public String codigoRespuesta;
    
    @JsonInclude(Include.NON_NULL)
    public RequestIdDTO requestIdDTO;
    
    @JsonInclude(Include.NON_NULL)
    public MessageDTO messageDTO;
    
    @JsonInclude(Include.NON_NULL)
    public String objectDTO; 
    
}
