package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class FacadeUtilComponent{

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
	
	public GrupoInvitationUtil grupoInvitation() {
		return grupoInvitation;
	}

	
	public PasswordEncoder passwordEncoder() {
		return passwordEncoder;
	}

	public AuthUtil auth() {
		return auth;
	}
	
	public MediaUtilService media() {
		return media;
	}

	public UserForGrupoUtil userForGrupo() {
		return userForGrupo;
	}

	public MessageUtilService message() {
		return message;
	}

	public MessageDetailUtil messageDetail() {
		return messageDetail;
	}

	public GrupoUtilService grupo() {
		return grupo;
	}

	public MyAccountConfUtilService myAccountConf() {
		return myAccountConf;
	}

	public GrupoUserConfUtil grupoUserConf() {
		return grupoUserConf;
	}

	public UserUtilService usuario() {
		return usuario;
	}

	public RequestIdUtilService requestId() {
		return requestId;
	}




	
//	@Autowired
//	@Lazy
//	private GrupoUserConfUtilService grupoUserConf;
}
