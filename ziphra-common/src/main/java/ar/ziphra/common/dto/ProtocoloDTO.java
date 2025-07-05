package ar.ziphra.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ar.ziphra.common.enumeration.ProtocoloActionsEnum;
import ar.ziphra.common.enumeration.ProtocoloComponentsEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProtocoloDTO implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1468646917559489843L;

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
	private MessageDTO message;

	@JsonInclude(Include.ALWAYS)
	private String objectDTO;

}
