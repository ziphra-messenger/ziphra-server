package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.enumeration.GrupoRolesEnum;
import com.privacity.common.interfaces.IdGrupoInterface;

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
	@PrivacityId
	@PrivacityIdOrder
	private String idGrupo; 
	
	private UsuarioDTO usuario;
	@PrivacityIdExclude	
	private GrupoRolesEnum role;
	
	private AESDTO aesDTO;

	@PrivacityIdExclude	
    private String nickname;
	@PrivacityIdExclude	
    private String alias;
    
	@Override
	public String toString() {
		return "UserForGrupoDTO [idGrupo=" + idGrupo + ", usuario=" + usuario + ", role=" + role + ", nickname="
				+ nickname + ", alias=" + alias + "]";
	}	
	
}
