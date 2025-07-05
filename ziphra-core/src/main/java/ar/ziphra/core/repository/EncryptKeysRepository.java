package ar.ziphra.core.repository;

import org.springframework.data.jpa.repository.Query;

import ar.ziphra.core.interfaces.GetMaxIdInterface;
import ar.ziphra.core.model.EncryptKeys;

public interface EncryptKeysRepository extends GetMaxIdInterface<EncryptKeys, Long> {
	@Query(value = "SELECT COALESCE(MAX(m.id), 0) FROM EncryptKeys m")
	Long getMaxId();
}
