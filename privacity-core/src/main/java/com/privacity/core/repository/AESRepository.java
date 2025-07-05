package com.privacity.core.repository;

import org.springframework.data.jpa.repository.Query;

import com.privacity.core.interfaces.GetMaxIdInterface;
import com.privacity.core.model.AES;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AESRepository extends GetMaxIdInterface<AES, Long> {


	@Query(value = "SELECT COALESCE(MAX(m.id), 0) FROM AES m")
	Long getMaxId();
}
