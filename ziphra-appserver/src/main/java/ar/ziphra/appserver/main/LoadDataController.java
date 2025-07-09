package ar.ziphra.appserver.main;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.ziphra.commonback.common.enumeration.RolesSecurityAccessToServerEnum;
import ar.ziphra.core.model.Role;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.core.repository.RoleRepository;
import ar.ziphra.core.repository.UsuarioRepository;
import ar.ziphra.appserver.util.MapperService;


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
			role.setName(RolesSecurityAccessToServerEnum.ROLE_ADMIN);
			roleRepository.save(role);

			Role role2 = new Role();
			role2.setId(2);
			role2.setName(RolesSecurityAccessToServerEnum.ROLE_USER);

			roleRepository.save(role2);
			
			u = new Usuario();
			u.setIdUser(1L);
			u.setNickname("INFO");
			u.setUsername("SYSTEM");
	usuarioRepository.save(u);
	
	Usuario u2 = new Usuario();
	u2.setIdUser(2L);
	u2.setNickname("Anonimo");
	u2.setUsername("Anonimo");
usuarioRepository.save(u2);

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

}