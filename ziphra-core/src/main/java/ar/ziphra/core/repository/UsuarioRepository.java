package ar.ziphra.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.ziphra.core.interfaces.GetMaxIdInterface;
import ar.ziphra.core.model.Usuario;



@Repository
public interface UsuarioRepository extends GetMaxIdInterface<Usuario, Long> {
	Optional<Usuario> findByUsername(String username);

	Boolean existsByUsername(String username);

	@Query("SELECT u FROM Usuario u where "
			+ " u.idUser = (select a.usuario.idUser from UserInvitationCode a where   "
			+ " a.invitationCode = ?1 )  " )
	Usuario findByUserInvitationCode(String invitationCode);

	
	@Query(value = "SELECT COALESCE(MAX(m.idUser), 0) FROM Usuario m")
	Long getMaxId();
	
	//Usuario findByUserInvitationCode(UserInvitationCode invitationCodeToAdd);
	
	

}
