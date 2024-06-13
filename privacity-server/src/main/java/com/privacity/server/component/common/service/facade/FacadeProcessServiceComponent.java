package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.server.component.grupo.GrupoProcessService;
import com.privacity.server.component.message.MessageProcessService;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class FacadeProcessServiceComponent {


	@Autowired @Lazy
	private GrupoProcessService grupo;

	@Autowired @Lazy
	private MessageProcessService message;

	public GrupoProcessService grupo() {
		return grupo;
	}

	public MessageProcessService message() {
		return message;
	}


	
}
