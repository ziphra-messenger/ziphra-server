package com.privacity.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.privacity.core.model.Usuario;



@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByUsername(String username);

	Boolean existsByUsername(String username);

	@Query("SELECT u FROM Usuario u where "
			+ " u.idUser = (select a.usuario.idUser from UserInvitationCode a where   "
			+ " a.invitationCode = ?1 )  " )
	Usuario findByUserInvitationCode(String invitationCode);

	//Usuario findByUserInvitationCode(UserInvitationCode invitationCodeToAdd);
	
	

}
