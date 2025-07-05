package ar.ziphra.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.enumeration.GrupoRolesEnum;
import ar.ziphra.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserForGrupoDTO implements IdGrupoInterface{
	@ZiphraId
	@ZiphraIdOrder
	private String idGrupo; 
	
	private UsuarioDTO usuario;
	@ZiphraIdExclude	
	private GrupoRolesEnum role;
	
	private AESDTO aesDTO;

	@ZiphraIdExclude	
    private String nickname;
	@ZiphraIdExclude	
    private String alias;
    
	@Override
	public String toString() {
		return "UserForGrupoDTO [idGrupo=" + idGrupo + ", usuario=" + usuario + ", role=" + role + ", nickname="
				+ nickname + ", alias=" + alias + "]";
	}	
	
}
