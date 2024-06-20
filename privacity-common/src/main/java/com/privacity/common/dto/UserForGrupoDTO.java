package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.enumeration.GrupoRolesEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForGrupoDTO {
	@PrivacityId
	@JsonInclude(Include.NON_NULL)
	public String idGrupo; 
	
	public UsuarioDTO usuario;
	public GrupoRolesEnum role;
	public AESDTO aesDTO;
	@JsonInclude(Include.NON_NULL)
    private String nickname;
    @JsonInclude(Include.NON_NULL)
    private String alias;
	@Override
	public String toString() {
		return "UserForGrupoDTO [idGrupo=" + idGrupo + ", usuario=" + usuario + ", role=" + role + ", nickname="
				+ nickname + ", alias=" + alias + "]";
	}	
	
}
