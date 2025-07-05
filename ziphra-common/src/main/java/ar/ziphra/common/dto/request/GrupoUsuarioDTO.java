package ar.ziphra.common.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.interfaces.IdGrupoInterface;
import ar.ziphra.common.interfaces.IdUsuarioInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoUsuarioDTO implements IdGrupoInterface, IdUsuarioInterface{
	
	@ZiphraId
	@ZiphraIdOrder
	@JsonInclude(Include.NON_NULL)
	private String idGrupo;
	
	@ZiphraId
	@ZiphraIdOrder
	@JsonInclude(Include.NON_NULL)
	private String idUsuario;

}
