package com.privacity.server.component.grupo;

import org.springframework.data.repository.CrudRepository;

import com.privacity.server.model.Grupobase;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface GrupobaseRepository{

	Grupobase findByIdGrupoAndDeleted(Long id, boolean deleted);
}
