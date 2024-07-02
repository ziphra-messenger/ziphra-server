package com.privacity.server.common.model.request;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.server.common.interfaces.GrupoRoleInterface;
import com.privacity.server.common.interfaces.UserForGrupoRoleInterface;
import com.privacity.server.common.interfaces.UsuarioRoleInterface;
import com.privacity.server.common.model.Grupo;
import com.privacity.server.common.model.UserForGrupo;
import com.privacity.server.common.model.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoBlockRemotoRequestLocalDTO  implements GrupoRoleInterface, UsuarioRoleInterface, UserForGrupoRoleInterface {

	@PrivacityId
	@PrivacityIdOrder
	public String idGrupo;
	
	public Usuario usuarioLoggued;
	public Grupo grupo;

	public UserForGrupo userForGrupo;

	@Override
	public String toString() {
		return "GrupoBlockRemotoRequestLocalDTO [idGrupo=" + idGrupo + "]";
	}



	
	


}
