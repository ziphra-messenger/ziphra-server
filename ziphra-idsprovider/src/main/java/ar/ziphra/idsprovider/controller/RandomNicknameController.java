package ar.ziphra.idsprovider.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.utils.IdsProviderRestConstants;
import ar.ziphra.commonback.constants.SessionManagerRestConstants;
import ar.ziphra.core.util.UtilsStringComponent;
import ar.ziphra.idsprovider.components.RandomNicknameComponent;

import antlr.StringUtils;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = IdsProviderRestConstants.RANDOM)
@Slf4j
public class RandomNicknameController {

	@Autowired
	@Lazy
	private RandomNicknameComponent c;
	@Autowired
	@Lazy

	private UtilsStringComponent us;
	
	@PostMapping(IdsProviderRestConstants.RANDOM_NICKNAME_GET_BY_GRUPO+ "/{idGrupo}")
	public Map<Long,String> getByGrupo(@PathVariable String idGrupo) throws ZiphraException, Exception  {
		
		Map<Long, String> r = c.getByGrupo(Long.parseLong(idGrupo));
		
		log.debug(us.gsonPretty().toJson(r));
		
		return c.getByGrupo(Long.parseLong(idGrupo));
	}
	
	public Map<String,String> refreshByGrupo(@RequestParam String idGrupo) throws ZiphraException, Exception  {
		return null;
		
	}
}
