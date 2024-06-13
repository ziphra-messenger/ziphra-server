package com.privacity.server.component.userforgrupo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.privacity.server.model.Grupo;
import com.privacity.server.model.UserForGrupo;
import com.privacity.server.model.UserForGrupoId;
import com.privacity.server.security.Usuario;




// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserForGrupoRepository extends CrudRepository<UserForGrupo, UserForGrupoId> {



	@Query("SELECT u.userForGrupoId.grupo.idGrupo FROM UserForGrupo u "
			+ "WHERE u.userForGrupoId.user.idUser = ?1"
			+ " and u.userForGrupoId.grupo.deleted = false")
	List<Long> findIdGrupoByUserForGrupoIdUser(Long usuarioId);

	// olds 
	@Query("SELECT u FROM UserForGrupo u WHERE u.userForGrupoId.user.idUser = ?1")
	List<UserForGrupo> findByUserForGrupoIdUser(Long usuarioId);

	@Query("SELECT u.userForGrupoId.grupo FROM UserForGrupo u WHERE u.userForGrupoId.user.username = ?1")
	List<Grupo> findByGrupoByUsername(String username);
	
	@Query("SELECT u.userForGrupoId.user FROM UserForGrupo u WHERE u.userForGrupoId.grupo.idGrupo = ?1")
	List<Usuario> findByUsuariosForGrupo(Long idGrupo);
	
	/*
	 * 	@Query("SELECT u FROM UserForGrupo u WHERE u.userForGrupoId.grupo.idGrupo = ?1"
			+ " and u.userForGrupoId.grupo.delete == false ") 
	 */
	@Query("SELECT u FROM UserForGrupo u WHERE u.userForGrupoId.grupo.idGrupo = ?1")
	List<UserForGrupo> findByForGrupo(Long idGrupo);
	
	@Query(" SELECT u.username FROM Usuario u "
			+ " where u.idUser in ( "
			+ " 	Select ufg.userForGrupoId.user.idUser From "
			+ " 	UserForGrupo ufg "
			+ " 	WHERE ufg.userForGrupoId.grupo.idGrupo = ?1 "
			+ " 	and ufg.userForGrupoId.user.idUser != ?2 "
			+ ")")
	List<String> findByForGrupoMinusCreator(Long idGrupo, Long idUsuario);
	
	List<UserForGrupo> findByUserForGrupoIdUser(Usuario u);

	List<UserForGrupo> findByUserForGrupoIdGrupo(Grupo grupo);

	@Query(
			 " 	Select ufg From "
			+ " 	UserForGrupo ufg "
			+ " 	WHERE ufg.userForGrupoId.grupo.idGrupo = ?1 "
			+ " 	and ufg.userForGrupoId.user.idUser = ?2 ")
			
	UserForGrupo findByIdPrimitive(Long idGrupo, Long idUsuario);
}
