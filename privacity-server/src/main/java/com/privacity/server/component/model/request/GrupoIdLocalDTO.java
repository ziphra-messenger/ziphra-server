package com.privacity.server.component.model.request;

import com.google.gson.annotations.SerializedName;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.interfaces.GrupoRoleInterface;
import com.privacity.common.interfaces.UserForGrupoRoleInterface;
import com.privacity.common.interfaces.UsuarioRoleInterface;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.UserForGrupo;
import com.privacity.server.security.Usuario;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GrupoIdLocalDTO  implements GrupoRoleInterface, UsuarioRoleInterface, UserForGrupoRoleInterface {

	@PrivacityId
	@SerializedName("id")
	public String idGrupo;
	
	public Usuario usuarioLoggued;
	public Grupo grupo;

	public UserForGrupo userForGrupo;

	
	public GrupoIdLocalDTO(String idGrupo) {
		super();
		this.idGrupo = idGrupo;
	}


	
	


}
