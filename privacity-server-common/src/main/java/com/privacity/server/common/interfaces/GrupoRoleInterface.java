package com.privacity.server.common.interfaces;

import com.privacity.common.interfaces.IdGrupoInterface;
import com.privacity.server.common.model.Grupo;

public interface GrupoRoleInterface extends IdGrupoInterface{
	
	public Grupo getGrupo();
	public void setGrupo(Grupo grupo);
}
