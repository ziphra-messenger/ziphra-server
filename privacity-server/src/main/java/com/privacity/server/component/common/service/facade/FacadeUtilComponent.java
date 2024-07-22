package com.privacity.server.component.common.service.facade;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.adapters.LocalDateAdapter;
import com.privacity.common.util.ZipUtilService;
import com.privacity.server.component.auth.AuthUtil;
import com.privacity.server.component.grupo.GrupoUtilService;
import com.privacity.server.component.grupoinvitation.GrupoInvitationUtil;
import com.privacity.server.component.grupouserconf.GrupoUserConfUtil;
import com.privacity.server.component.media.MediaUtilService;
import com.privacity.server.component.message.MessageUtilService;
import com.privacity.server.component.messagedetail.MessageDetailUtil;
import com.privacity.server.component.myaccount.MyAccountConfUtilService;
import com.privacity.server.component.requestid.RequestIdUtilService;
import com.privacity.server.component.userforgrupo.UserForGrupoUtil;
import com.privacity.server.component.usuario.UserUtilService;
import com.privacity.server.services.UtilsStringService;
import com.privacity.server.util.UtilService;

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
