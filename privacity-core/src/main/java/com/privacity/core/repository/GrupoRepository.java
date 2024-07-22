package com.privacity.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.privacity.core.model.Grupo;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface GrupoRepository extends CrudRepository<Grupo, Long> {

	Grupo findByIdGrupoAndDeleted(Long id, boolean deleted);
	
	@Query(value = "SELECT COALESCE(MAX(m.idGrupo), 0) FROM Grupo m")
	Long getMaxId();
}
