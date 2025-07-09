package ar.ziphra.appserver.component.common.service.facade;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ar.ziphra.common.adapters.LocalDateAdapter;
import ar.ziphra.common.util.ZipUtilService;
import ar.ziphra.appserver.component.auth.AuthUtil;
import ar.ziphra.appserver.component.grupo.GrupoUtilService;
import ar.ziphra.appserver.component.grupoinvitation.GrupoInvitationUtil;
import ar.ziphra.appserver.component.grupouserconf.GrupoUserConfUtil;
import ar.ziphra.appserver.component.media.MediaUtilService;
import ar.ziphra.appserver.component.message.MessageUtilService;
import ar.ziphra.appserver.component.messagedetail.MessageDetailUtil;
import ar.ziphra.appserver.component.myaccount.MyAccountConfUtilService;
import ar.ziphra.appserver.component.requestid.RequestIdUtilService;
import ar.ziphra.appserver.component.userforgrupo.UserForGrupoUtil;
import ar.ziphra.appserver.component.usuario.UserUtilService;
import ar.ziphra.appserver.services.UtilsStringService;
import ar.ziphra.appserver.util.UtilService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = true, chain = false)
@Component
@NoArgsConstructor
@Getter
public class FacadeUtilComponent{

	@Autowired @Lazy
	private ZipUtilService zip;
	
	@Autowired
	@Lazy	
	private MediaUtilService media;
	
	@Autowired
	@Lazy
	private UserForGrupoUtil userForGrupo;
	
	@Autowired
	@Lazy
	private MessageUtilService message;
	
	@Autowired
	@Lazy
	private MessageDetailUtil messageDetail;
	
	@Autowired
	@Lazy
	private GrupoUtilService grupo;
	
	@Autowired
	@Lazy
	private MyAccountConfUtilService myAccountConf;
	
	@Autowired
	@Lazy
	private GrupoUserConfUtil grupoUserConf;
	
	@Autowired
	@Lazy
	private UserUtilService usuario;
	
	@Autowired
	@Lazy
	private RequestIdUtilService requestId;

	@Autowired
	@Lazy
	private AuthUtil auth;
	
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	@Lazy
	private GrupoInvitationUtil grupoInvitation;
	
	@Autowired
	@Lazy
	private UtilService util;

	@Autowired
	@Lazy
	private UtilsStringService string;
	
	public final Gson gson() {
		return new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
				.create();

	}
}
