package com.privacity.common.interfaces;

import com.privacity.server.model.UserForGrupo;

public interface UserForGrupoRoleInterface extends GrupoRoleInterface, UsuarioRoleInterface {

	
	public UserForGrupo getUserForGrupo();
	public void setUserForGrupo(UserForGrupo userForGrupo);
}
