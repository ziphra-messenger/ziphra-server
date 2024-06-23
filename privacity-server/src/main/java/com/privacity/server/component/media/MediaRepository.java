package com.privacity.server.component.media;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.privacity.server.model.Media;
import com.privacity.server.model.MediaId;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete


public interface MediaRepository extends CrudRepository<Media, MediaId> {


	@Transactional
	@Modifying
	@Query("DELETE FROM  Media u where "
			+ " u.mediaId.message in (SELECT e FROM Message e	WHERE e.messageId.grupo.idGrupo = ?1 and e.userCreation.idUser = ?2 )")
	void deleteAllMyMediasByGrupo (Long grupo, Long usuarioLogged);
	@Transactional
	@Modifying
	@Query("DELETE FROM  Media u where "
			+ " u.mediaId.message in (SELECT e FROM Message e	WHERE e.deleted=true)")
	void deleteLogicDelete();

}
