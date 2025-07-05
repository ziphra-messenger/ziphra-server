package ar.ziphra.common.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import ar.ziphra.common.dto.AESDTO;
import ar.ziphra.common.dto.GrupoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GrupoNewRequestDTO {

	public GrupoDTO grupoDTO;
	
	// aca viene el par nuevo, el private encriptado por el publico del creador
	private AESDTO aesDTO;
}
