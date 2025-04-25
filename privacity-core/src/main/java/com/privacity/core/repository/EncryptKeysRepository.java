package com.privacity.core.repository;

import org.springframework.data.jpa.repository.Query;

import com.privacity.core.interfaces.GetMaxIdInterface;
import com.privacity.core.model.EncryptKeys;

public interface EncryptKeysRepository extends GetMaxIdInterface<EncryptKeys, Long> {
	@Query(value = "SELECT COALESCE(MAX(m.id), 0) FROM EncryptKeys m")
	Long getMaxId();
}
