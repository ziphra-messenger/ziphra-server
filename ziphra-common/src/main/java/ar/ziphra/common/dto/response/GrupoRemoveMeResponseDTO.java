package ar.ziphra.common.dto.response;

import ar.ziphra.common.dto.GrupoDTO;
import ar.ziphra.common.dto.UsuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoRemoveMeResponseDTO {

	private GrupoDTO grupoDTO;
	private UsuarioDTO usuariosDTO;

}