package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.server.component.grupo.GrupoProcessService;
import com.privacity.server.component.message.MessageProcessService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Service
@NoArgsConstructor
@Accessors(fluent = true, chain = false)
@Getter
public class FacadeProcessServiceComponent {


	@Autowired @Lazy
	private GrupoProcessService grupo;

	@Autowired @Lazy
	private MessageProcessService message;

}
