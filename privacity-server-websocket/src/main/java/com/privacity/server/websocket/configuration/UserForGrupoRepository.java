package com.privacity.server.websocket.configuration;

import java.util.List;

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
			+ " and u.userForGrupoId.grupo.deleted = false "
	      + " and u.deleted = false ")
	List<Long> findIdGrupoByUserForGrupoIdUser(Long usuarioId);

	// olds 

	@Query("SELECT u.userForGrupoId.grupo FROM UserForGrupo u "
			+ "WHERE u.userForGrupoId.user.idUser = ?1"
			+ " and u.userForGrupoId.grupo.deleted = false "
			+ " and u.deleted = false ")
	List<UserForGrupo> findByUserForGrupoIdUser(Long usuarioId);

	@Query("SELECT u.userForGrupoId.grupo FROM UserForGrupo u "
			+ "WHERE u.userForGrupoId.user.username = ?1"
			+ " and u.userForGrupoId.grupo.deleted = false "
			+ " and u.deleted = false ")

	List<Grupo> findByGrupoByUsername(String username);
	
	@Query("SELECT u.userForGrupoId.user FROM UserForGrupo u "
			+ " WHERE u.userForGrupoId.grupo.idGrupo = ?1 "
			+ " and u.userForGrupoId.grupo.deleted = false "
			+ " and u.deleted = false ")
	List<Usuario> findByUsuariosForGrupoDeletedFalse(Long idGrupo);
	
	/*
	 * 	@Query("SELECT u FROM UserForGrupo u WHERE u.userForGrupoId.grupo.idGrupo = ?1"
			+ " and u.userForGrupoId.grupo.delete == false ") 
	 */
	@Query("SELECT u FROM UserForGrupo u WHERE u.userForGrupoId.grupo.idGrupo = ?1 "
			+ " and u.deleted = false and u.userForGrupoId.grupo.deleted = false")
	List<UserForGrupo> findByForGrupo(Long idGrupo);
	
	@Query(" SELECT u.username FROM Usuario u "
			+ " where u.idUser in ( "
			+ " 	Select ufg.userForGrupoId.user.idUser From "
			+ " 	UserForGrupo ufg "
			+ " 	WHERE ufg.userForGrupoId.grupo.idGrupo = ?1 and ufg.deleted=false "
			+ " 	and ufg.userForGrupoId.grupo.deleted = false   "
			+ " )")
	List<String> findByForGrupoAll(Long idGrupo);
	
	
	@Query(" SELECT u.username FROM Usuario u "
			+ " where u.idUser in ( "
			+ " 	Select ufg.userForGrupoId.user.idUser From "
			+ " 	UserForGrupo ufg "
			+ " 	WHERE ufg.userForGrupoId.grupo.idGrupo = ?1  and ufg.deleted=false "
			+ " 	and ufg.userForGrupoId.user.idUser != ?2 "
			+ " 	and ufg.userForGrupoId.grupo.deleted = false   "
			+ ")")
	List<String> findByForGrupoMinusCreator(Long idGrupo, Long idUsuario);
	

	@Query(
			 " 	Select ufg From "
			+ " 	UserForGrupo ufg "
			+ " 	WHERE ufg.userForGrupoId.grupo.deleted = false "
			+ " 	and ufg.deleted = false "
			+ " 	and ufg.userForGrupoId.user = ?1 ")
	List<UserForGrupo> findByUserForGrupoIdUser(Usuario u);

	List<UserForGrupo> findByUserForGrupoIdGrupo(Grupo grupo);

	@Query(
			 " 	Select ufg From "
			+ " 	UserForGrupo ufg "
			+ " 	WHERE ufg.userForGrupoId.grupo.idGrupo = ?1 "
			+ " 	and ufg.userForGrupoId.grupo.deleted = false "
			+ " 	and ufg.deleted = false "
			+ " 	and ufg.userForGrupoId.user.idUser = ?2 ")
			
	UserForGrupo findByIdPrimitive(Long idGrupo, Long idUsuario);


	@Query(
			 " 	Select ufg From "
			+ " 	UserForGrupo ufg "
			+ " 	WHERE ufg.userForGrupoId.grupo.deleted = false "
			+ " 	and ufg.deleted = false ")
	void deleteLogicDelete();
}
