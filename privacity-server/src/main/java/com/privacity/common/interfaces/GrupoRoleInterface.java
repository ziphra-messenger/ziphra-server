package com.privacity.common.interfaces;

import com.privacity.server.model.Grupo;

public interface GrupoRoleInterface extends IdGrupoInterface{
	
	public Grupo getGrupo();
	public void setGrupo(Grupo grupo);
}
