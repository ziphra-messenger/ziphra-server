package com.privacity.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.privacity.core.model.Grupo;
import com.privacity.core.model.Message;
import com.privacity.core.model.MessageId;
import com.privacity.core.model.Usuario;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface MessageRepository extends CrudRepository<Message, MessageId> {

	
//	@Transactional
//	@Modifying
//	@Query("insert into media (data, media_type, id_grupo, id_message) values (?1, ?2, ?3, ?4) ")
//	void insertMed(byte[] data, int media_type, Long id_grupo, Long id_message);
	
	List<Message> findByMessageIdGrupoIdGrupo(Long idGrupo);

	@Transactional
	@Modifying
	@Query("DELETE FROM Message e	WHERE e.messageId.grupo.idGrupo = ?1 and e.userCreation.idUser = ?2  ")
	void deleteAllMyMessageForEverybodyByGrupo(long parseLong, Long idUser);

	Message findByMessageIdIdMessage(long parseLong);

	@Transactional
	@Modifying
	@Query("update Message u set u.deleted=true where "
			+ " u.userCreation = ?2  "
			+ " and u.messageId.grupo = ?1 ")
	void deleteLogicAllMyMessagesByGrupo(Grupo grupo, Usuario usuario);

	@Query("SELECT u FROM Message u where "
			+ " u.userCreation = ?2  "
			+ " and u.anonimo = 1  "
			+ " and u.messageId.grupo = ?1 ")

	List<Message> findByMessageIdGrupoUserAnonimo(Grupo grupo, Usuario usuarioLogged);
	
	@Query("SELECT u FROM Message u where "
			+ " u.messageId.idMessage = ?2  "
			+ " and u.messageId.grupo.idGrupo = ?1 ")

	Message findByIdPrimitive(long idGrupo, long idMessage);
	
	@Transactional
	@Modifying
	@Query("update Message u set u.deleted=true where "
			+ " u.messageId.idMessage = ?2  "
			+ " and u.messageId.grupo.idGrupo = ?1 ")
	void deleteLogic(Long idGrupo, Long idMessage);

	@Transactional
	@Modifying
	@Query("DELETE FROM  Message u where "
			+ "  u.deleted=true and u.parentReply is not null ")
	void deleteLogicDeleteParentReply();
	
	@Transactional
	@Modifying
	@Query("DELETE FROM  Message u where "
			+ "  u.deleted=true ")
	void deleteLogicDelete();
	
	@Query(value = "SELECT COALESCE(MAX(m.messageId.idMessage), 0) FROM Message m"
			+ " where m.messageId.grupo.idGrupo= ?1 "
			)
	Long getMaxId(Long idGrupo);

}
