package ar.ziphra.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ziphra.commonback.common.enumeration.RolesSecurityAccessToServerEnum;
import ar.ziphra.core.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RolesSecurityAccessToServerEnum name);
}
