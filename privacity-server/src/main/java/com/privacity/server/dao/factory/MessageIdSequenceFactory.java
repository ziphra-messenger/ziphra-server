package com.privacity.server.dao.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.server.component.common.service.RequestHelperService;
import com.privacity.server.dao.impl.MessageIdLocal;
import com.privacity.server.dao.impl.MessageIdRest;

@Service
public class MessageIdSequenceFactory {
	

	@Autowired
	@Lazy
	private MessageIdLocal messageIdLocal;
	@Lazy
	@Autowired
	private RequestHelperService requestHelperService;
	@Lazy
	@Autowired
	private MessageIdRest messageIdRest;
	
	
	public Long get() {
		return get(requestHelperService.getGrupoInUse().getIdGrupo());
	}
	
	public Long get(Long idGrupo) {
		return messageIdRest.getNextMessageId(idGrupo);
	}

	public Long get(String idGrupo) {
		return get(Long.parseLong(idGrupo));
	}
}
