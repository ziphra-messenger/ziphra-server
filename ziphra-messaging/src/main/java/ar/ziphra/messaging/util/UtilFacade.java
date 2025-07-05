package ar.ziphra.messaging.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import ar.ziphra.core.repository.UserForGrupoRepository;
import ar.ziphra.core.util.UtilsStringComponent;
import ar.ziphra.messaging.configuration.SocketSessionRegistry;
import ar.ziphra.messaging.configuration.UsuarioSessionInfoService;
import ar.ziphra.messaging.services.WebSocketSenderService;

import lombok.Getter;
import lombok.experimental.Accessors;

@Service
@Accessors(fluent = true, chain = false)
@Getter
public class UtilFacade {
	
	@Autowired
	@Lazy
	private UsuarioSessionInfoService usuarioSessionInfo;

	@Autowired
	@Lazy
	private WebSocketSenderService webSocketSender;

	@Autowired
	@Lazy
	private UserForGrupoRepository userForGrupoRepository;
	
	@Autowired
	@Lazy
	private UtilsStringComponent utilsString;
	
	@Autowired
	@Lazy
	private SocketSessionRegistry socketSessionRegistry;

	public Gson gson() {
		 		return utilsString.gson();
	}

}
