package com.privacity.server.messageidgenerator.repositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.privacity.server.messageidgenerator.model.Message;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MessageIdSequenceRepository extends CrudRepository<Message, Long> {
	@Query(value = "SELECT COALESCE(MAX(m.idMessage), 0) FROM Message m"
			+ " where m.idGrupo= ?1 "
			)
	Long getMaxIdGrupo(Long idGrupo);
}
