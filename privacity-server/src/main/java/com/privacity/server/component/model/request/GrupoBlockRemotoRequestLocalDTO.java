package com.privacity.server.component.model.request;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.interfaces.GrupoRoleInterface;
import com.privacity.common.interfaces.UserForGrupoRoleInterface;
import com.privacity.common.interfaces.UsuarioRoleInterface;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.UserForGrupo;
import com.privacity.server.security.Usuario;

import lombok.Data;

@Data
public class GrupoBlockRemotoRequestLocalDTO  implements GrupoRoleInterface, UsuarioRoleInterface, UserForGrupoRoleInterface {

	@PrivacityId
	public String idGrupo;
	
	public Usuario usuarioLoggued;
	public Grupo grupo;

	public UserForGrupo userForGrupo;


	
	


}
