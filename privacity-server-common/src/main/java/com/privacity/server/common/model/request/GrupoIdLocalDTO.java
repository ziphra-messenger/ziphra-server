package com.privacity.server.common.model.request;

import com.google.gson.annotations.SerializedName;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.server.common.interfaces.GrupoRoleInterface;
import com.privacity.server.common.interfaces.UserForGrupoRoleInterface;
import com.privacity.server.common.interfaces.UsuarioRoleInterface;
import com.privacity.server.common.model.Grupo;
import com.privacity.server.common.model.UserForGrupo;
import com.privacity.server.common.model.Usuario;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GrupoIdLocalDTO  implements GrupoRoleInterface, UsuarioRoleInterface, UserForGrupoRoleInterface {

	@PrivacityId
	@PrivacityIdOrder
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
