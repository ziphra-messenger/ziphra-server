package com.privacity.server.component.grupoinvitation;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.privacity.server.model.GrupoInvitation;
import com.privacity.server.model.GrupoInvitationId;
import com.privacity.server.security.Usuario;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface GrupoInvitationRepository extends CrudRepository<GrupoInvitation, GrupoInvitationId> {

	@Query("SELECT u.grupoInvitationId.grupo.idGrupo FROM GrupoInvitation u where "
			+ " u.grupoInvitationId.usuarioInvitado = ?1  "
			+ " and u.grupoInvitationId.grupo.deleted=false "
			)		
	List<Long> findIdGrupoByGrupoInvitationUsuarioGrupo(Usuario u);
	
	// olds
	List<GrupoInvitation> findByGrupoInvitationIdUsuarioInvitado(Usuario u);
	
	@Query("SELECT u FROM GrupoInvitation u where "
			+ " u.grupoInvitationId.usuarioInvitado.idUser = ?1  "
			+ " and u.grupoInvitationId.grupo.idGrupo = ?2")	
	GrupoInvitation findByGrupoInvitationUsuarioGrupo(Long u, Long g);


}
