package com.privacity.server.main;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.GsonBuilder;
import com.privacity.common.dto.GrupoDTO;
import com.privacity.server.common.enumeration.ERole;
import com.privacity.server.common.model.Grupo2;
import com.privacity.server.common.model.Grupobase;
import com.privacity.server.common.model.Role;
import com.privacity.server.common.model.Usuario;
import com.privacity.server.common.repositories.UsuarioRepository;
import com.privacity.server.component.grupo.Grupo2Repository;
import com.privacity.server.component.grupo.GrupobaseRepository;
import com.privacity.server.security.RoleRepository;
import com.privacity.server.util.LocalDateAdapter;
import com.privacity.server.util.MapperService;


@Component
public class LoadDataController {

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
//	@Autowired
//	Grupo2Repository grupoRepository2;
//	@Autowired
//	GtupobaseRepository grupoRepository;
//	
	@Autowired
	
	MapperService mapperService;
	@PostConstruct
	public void load() throws Exception {
		
		Usuario u;
		try {
			Role role = new Role();
			role.setId(1);
			role.setName(ERole.ROLE_ADMIN);
			roleRepository.save(role);

			Role role2 = new Role();
			role2.setId(2);
			role2.setName(ERole.ROLE_USER);

			roleRepository.save(role2);
			
			u = new Usuario();
			u.setIdUser(1L);
			u.setNickname("INFO");
			u.setUsername("SYSTEM");
			usuarioRepository.save(u);
		} catch (Exception e) {

		}

//	{	
//		Grupobase ggg = new Grupobase();
//		ggg.setIdGrupo(1L);
//		ggg.setName("111");
//		ggg.setDeleted(false);
//		grupoRepository.save(ggg);
//	}
//	{	
//		Grupobase ggg = new Grupobase();
//		ggg.setIdGrupo(3L);
//		ggg.setName("3333");
//		ggg.setDeleted(false);
//		grupoRepository.save(ggg);
//	}
//	{
//		Grupobase ggg = new Grupobase();
//		ggg.setIdGrupo(2L);
//		ggg.setName("222");
//		ggg.setDeleted(true);
//		grupoRepository.save(ggg);
//	}	
//	
//	{	
//		Grupobase ggg = new Grupobase();
//		ggg.setIdGrupo(4L);
//		ggg.setName("4444");
//		ggg.setDeleted(false);
//		grupoRepository.save(ggg);
//	}
//		for (Grupo2 g : grupoRepository2.findAll()) {
//			
//		
//		System.out.println(
//			       new GsonBuilder()
//	                .setPrettyPrinting()
//	                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
//	                .create().toJson(doit(g))
//
//		);
//		}
//
////		

	}
	static public GrupoDTO doit(Grupo2 grupo) {
		
		GrupoDTO g = new GrupoDTO();
		g.setIdGrupo(grupo.getIdGrupo().toString());
		g.setName(grupo.getName() + "");
		g.setAlias(grupo.isDeleted() + "");
		return g;
	
}
}