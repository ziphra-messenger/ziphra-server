package ar.ziphra.appserver.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.appserver.component.grupo.GrupoProcessService;
import ar.ziphra.appserver.component.message.MessageProcessService;
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
