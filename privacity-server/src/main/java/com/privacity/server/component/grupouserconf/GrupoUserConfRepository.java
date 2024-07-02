package com.privacity.server.component.grupouserconf;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.privacity.server.common.model.Grupo;
import com.privacity.server.common.model.GrupoUserConf;
import com.privacity.server.common.model.GrupoUserConfId;
import com.privacity.server.common.model.Usuario;




// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface GrupoUserConfRepository extends CrudRepository<GrupoUserConf, GrupoUserConfId> {
	@Transactional
	@Modifying
	@Query("delete from GrupoUserConf e WHERE  "
			+ "  (e.grupoUserConfId.grupo, e.grupoUserConfId.user)  in (  "
			+ " select u.userForGrupoId.grupo, u.userForGrupoId.user FROM UserForGrupo u  "
			+ " where u.deleted=true "
			+ " )")
	
	void deleteLogicDelete();

}
