package ar.ziphra.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProtocoloWrapperDTO {

	private AESDTO aesEncripted;
	@ZiphraIdExclude	
	private String protocoloDTO;

	

}
