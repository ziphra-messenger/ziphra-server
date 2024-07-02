package com.privacity.server.common.interfaces;

import com.privacity.server.common.model.UserForGrupo;

public interface UserForGrupoRoleInterface extends GrupoRoleInterface, UsuarioRoleInterface {

	
	public UserForGrupo getUserForGrupo();
	public void setUserForGrupo(UserForGrupo userForGrupo);
}
