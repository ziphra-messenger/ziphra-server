package ar.ziphra.idsprovider.components;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ar.ziphra.common.util.RandomNicknameUtil;
import ar.ziphra.core.model.UserForGrupo;
import ar.ziphra.core.repository.UserForGrupoRepository;


@Component
public class RandomNicknameComponent {
	
	@Autowired
	@Lazy
	private UserForGrupoRepository repo;

	private final Map<Long,Map<Long,String>> nicknamesByGrupo= new HashMap<Long, Map<Long,String>>();
	
	private List<String> l;
	
	public RandomNicknameComponent() {
		super();
		l =RandomNicknameUtil.getList();
	}

	public Map<Long,String>  getByGrupo(Long idGrupo){
		
		if ( !nicknamesByGrupo.containsKey(idGrupo)) {
			nicknamesByGrupo.put(idGrupo, new HashMap<Long, String>());
		
			
		
		
		
		int i = 0;
		
		List<UserForGrupo> ufgs = repo.findByForGrupo(idGrupo);
		
		Collections.shuffle(ufgs, new Random((new Random()).nextLong(90)));
		Collections.shuffle(ufgs, new Random((new Random()).nextLong(94830)));
		for (UserForGrupo ufg :  ufgs) {
			nicknamesByGrupo.get(idGrupo).put( ufg.getUserForGrupoId().getUser().getIdUser(), l.get(i));
			
			i++;
			
		}
		}
		return nicknamesByGrupo.get(idGrupo);
		
	}
}
