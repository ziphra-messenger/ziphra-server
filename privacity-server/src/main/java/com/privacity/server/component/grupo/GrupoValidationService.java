package com.privacity.server.component.grupo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.GrupoGralConfDTO;
import com.privacity.common.dto.GrupoUserConfDTO;
import com.privacity.common.dto.MembersQuantityDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.UserForGrupoDTO;
import com.privacity.common.dto.WrittingDTO;
import com.privacity.common.dto.request.GrupoAddUserRequestDTO;
import com.privacity.common.dto.request.GrupoChangeUserRoleDTO;
import com.privacity.common.dto.request.GrupoInfoNicknameRequestDTO;
import com.privacity.common.dto.request.GrupoInvitationAcceptRequestDTO;
import com.privacity.common.dto.request.GrupoNewRequestDTO;
import com.privacity.common.dto.request.GrupoRemoveUserDTO;
import com.privacity.common.dto.response.SaveGrupoGralConfLockResponseDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.GrupoRolesEnum;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.common.exceptions.ValidationException;
import com.privacity.core.model.AES;
import com.privacity.core.model.Grupo;
import com.privacity.core.model.GrupoInvitation;
import com.privacity.core.model.GrupoInvitationId;
import com.privacity.core.model.GrupoUserConf;
import com.privacity.core.model.Message;
import com.privacity.core.model.UserForGrupo;
import com.privacity.core.model.UserForGrupoId;
import com.privacity.core.model.Usuario;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.encryptkeys.EncryptKeysValidation;
import com.privacity.server.security.JwtUtils;

import lombok.extern.slf4j.Slf4j;

@Service

@Slf4j
public class GrupoValidationService {

	@Autowired
	@Lazy
	private FacadeComponent comps;
	@Autowired
	@Lazy
	private EncryptKeysValidation encryptKeysValidation;
	@Autowired @Lazy
	private JwtUtils jwtUtils;
	
	public boolean loginGrupo(GrupoDTO r) throws ValidationException {
		Usuario u = comps.requestHelper().getUsuarioLogged();
		Grupo g = comps.util().grupo().getGrupoByIdValidation(r.getIdGrupo());
		comps.util().userForGrupo().getValidation(u, g.getIdGrupo());
		String pw = r.getPassword().getPassword();

		return comps.process().grupo().loginGrupo(g, pw);

	}
	
	public void changeUserRole(GrupoChangeUserRoleDTO request) throws Exception {
		Grupo g = comps.requestHelper().setGrupoInUse(request);
		
		if ( (!comps.requestHelper().getUserForGrupoInUse().getRole().equals(GrupoRolesEnum.ADMIN) )) {
			
			log.error(ExceptionReturnCode.GRUPO_ROLE_NOT_ALLOW_THIS_ACTION.toShow("changeUserRole"));
			throw new ValidationException(ExceptionReturnCode.GRUPO_ROLE_NOT_ALLOW_THIS_ACTION);
			
		}
		
		Usuario usuarioToChange = comps.repo().user().findById(Long.parseLong( request.getIdUsuarioToChange())).get();
		UserForGrupo entity = comps.repo().userForGrupo().findByIdPrimitive(g.getIdGrupo(), usuarioToChange.getIdUser());
		
		entity.setRole(request.getRole());
		comps.repo().userForGrupo().save(entity);
		
		MessageDTO mensajeD = comps.webSocket().sender().buildSystemMessage(
				g, 
				"system__message__grupo__change__user_role__" + request.getRole().name());
		
		UserForGrupo usersForGrupo = comps.repo().userForGrupo().findByIdPrimitive(g.getIdGrupo(), usuarioToChange.getIdUser());
		List<UserForGrupo> lg = new ArrayList();
		lg.add(usersForGrupo);
		
		Message mensaje = comps.common().mapper().doit(mensajeD, comps.util().usuario().getUsuarioSystem(), g, lg, false);
		
		comps.process().message().sendNormal(comps.util().usuario().getUsuarioSystem().getIdUser(), mensaje, g.getIdGrupo());
		
	ProtocoloDTO p;
		p = comps.webSocket().sender().buildProtocoloDTO(
				ProtocoloComponentsEnum.GRUPO,
				ProtocoloActionsEnum.GRUPO_CHANGE_USER_ROLE_OUTPUT,request);
	
			
		comps.webSocket().sender().senderToUser(p, usuarioToChange);
		
		System.out.println(request.toString());
		//comps.repo().grupo().save(grupo);
	}

//	
//	@com.privacity.common.RolesAllowed({GrupoRolesEnum.ADMIN,GrupoRolesEnum.MODERATOR} )
	public void blockGrupoRemoto (GrupoDTO grupoBlockRemotoRequestLocalDTO) throws Exception {

			Grupo g  = comps.requestHelper().setGrupoInUse(grupoBlockRemotoRequestLocalDTO);
			
			if ( (!comps.requestHelper().getUserForGrupoInUse().getRole().equals(GrupoRolesEnum.ADMIN) &&
					(!comps.requestHelper().getUserForGrupoInUse().getRole().equals(GrupoRolesEnum.MODERATOR)))) {
				
				log.error(ExceptionReturnCode.GRUPO_ROLE_NOT_ALLOW_THIS_ACTION.toShow("blockGrupoRemoto"));
				throw new ValidationException(ExceptionReturnCode.GRUPO_ROLE_NOT_ALLOW_THIS_ACTION);
				
			}
					
			MessageDTO mensajeD = comps.webSocket().sender().buildSystemMessage(
					g, 
					"system__message__grupo__bloque_de_emergencia");
			Message mensaje = comps.common().mapper().doit(mensajeD, comps.util().usuario().getUsuarioSystem(), g);
			
			comps.process().message().sendNormal(comps.util().usuario().getUsuarioSystem().getIdUser(), mensaje, g.getIdGrupo());
			
		ProtocoloDTO p;
			p = comps.webSocket().sender().buildProtocoloDTO(
					ProtocoloComponentsEnum.GRUPO,
					ProtocoloActionsEnum.GRUPO_BLOCK_REMOTO,grupoBlockRemotoRequestLocalDTO);
		
				
			comps.webSocket().sender().senderToGrupo(p, g.getIdGrupo());

	}

	public void saveGrupoGralConfLock(GrupoDTO request) throws ValidationException {
		if (request.getLock().isEnabled()) {
			if (request.getLock().getSeconds() != 0
					&& request.getLock().getSeconds()< comps.common().serverConf().getSystemGralConf()
							.getMyAccountConf().getLock().getMinSecondsValidation()) {
				
				throw new ValidationException(ExceptionReturnCode.GRUPO_GENERAL_CONF_LOCK_MIN_SECONDS_VALIDATION
						.getToShow("entrada: " + request.getLock().getSeconds() + " validacion: < " + comps.common().serverConf().getSystemGralConf()
							.getMyAccountConf().getLock().getMinSecondsValidation()));
			}
		}

		Usuario u = comps.requestHelper().getUsuarioLogged();
		Grupo g = comps.util().grupo().getGrupoByIdValidation(request.getIdGrupo());

		comps.util().grupo().validateRoleAdmin(u, g);

		boolean changes = false;

		if (g.getPassword().isEnabled() != request.getPassword().isEnabled()) {
			g.getPassword().setEnabled(request.getPassword().isEnabled());
			changes = true;

		}
		if (g.getLock().isEnabled() != request.getLock().isEnabled()) {
			g.getLock().setEnabled(request.getLock().isEnabled());
			changes = true;
		}

		int secondDB = 0;
		if (g.getLock().getSeconds() != null) {
			secondDB = g.getLock().getSeconds().intValue();
		}

		int secondNew = 0;
		if (request.getLock().getSeconds() != 0) {
			secondNew = request.getLock().getSeconds();
		}

		if (secondNew != secondDB) {
			g.getLock().setSeconds(request.getLock().getSeconds());
			changes = true;
		}

//		if (g.getPassword().isDeleteExtraEncryptEnabled() != request.getPassword().isDeleteExtraEncryptEnabled()) {
//			g.getPassword().setDeleteExtraEncryptEnabled(request.getPassword().isDeleteExtraEncryptEnabled());
//			changes = true;
//			
//		}
//
//		if (g.getPassword().isExtraEncryptDefaultEnabled() != request.getPassword().isExtraEncryptDefaultEnabled()) {
//			g.getPassword().setExtraEncryptDefaultEnabled(request.getPassword().isExtraEncryptDefaultEnabled());
//			changes = true;
//			
//		}

		if (changes) {
			try {
				comps.repo().grupo().save(g);

				SaveGrupoGralConfLockResponseDTO c = comps.common().mapper().doit(g);

				MessageDTO mensajeD = comps.webSocket().sender().buildSystemMessage(g,
						"system__message__grupo__lock_config__modified"
						);
				Message mensaje = comps.common().mapper().doit(mensajeD, comps.util().usuario().getUsuarioSystem(), g);
				comps.process().message().sendNormal(comps.util().usuario().getUsuarioSystem().getIdUser(), mensaje,
						g.getIdGrupo());

				ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(ProtocoloComponentsEnum.GRUPO,
						ProtocoloActionsEnum.GRUPO_SAVE_GENERAL_CONFIGURATION_LOCK, c);
				comps.webSocket().sender().senderToGrupo(p, g.getIdGrupo(), u.getUsername());

			} catch (Exception e) {
				e.printStackTrace();
				throw new ValidationException(ExceptionReturnCode.GRUPO_GRUPOID_BADFORMAT);
			}
		}

	}

	public MembersQuantityDTO getMembersOnline(GrupoDTO request) throws PrivacityException {

			return comps.webSocket().sender().getMembersOnline(request);

	}


	public UserForGrupoDTO[] getMembers(GrupoDTO request) throws PrivacityException {

		Grupo g =  comps.requestHelper().setGrupoInUse(request);
		
		
		List<UserForGrupo> users;
		
		if ( comps.requestHelper().isUsuarioLoggedAdminOnGrupoInUse() ||
				!comps.requestHelper().getGrupoInUse().getGralConf().isHideMemberList()) {
		
			users = comps.repo().userForGrupo().findByForGrupo(g.getIdGrupo());
			Collections.shuffle(users, new Random((new Random()).nextLong(990)));
		}else {
			users=new ArrayList<>();
			users.add(	comps.requestHelper().getUserForGrupoInUse());
		}
		
		UserForGrupoDTO[] retorno = new UserForGrupoDTO[users.size()];
		int count = 0;
		
		for (UserForGrupo u : users) {
			retorno[count] = comps.common().mapper().getUserForGrupoDTO(u);
			count++;
		}

		return retorno;

	}

	public void saveGrupoGralConfPassword(GrupoDTO request) throws PrivacityException {
		Usuario u = comps.requestHelper().getUsuarioLogged();
		Grupo g = comps.util().grupo().getGrupoByIdValidation(request.getIdGrupo());
		comps.util().grupo().validateRoleAdmin(u, g);

		g.getPassword().setPassword(comps.util().passwordEncoder().encode(request.getPassword().getPassword()));

		comps.repo().grupo().save(g);
	}

//	@com.privacity.common.RolesAllowed({ GrupoRolesEnum.ADMIN, GrupoRolesEnum.MODERATOR, GrupoRolesEnum.MEMBER,
	//		GrupoRolesEnum.READONLY })
	public void removeMe(GrupoRemoveUserDTO request) throws PrivacityException {

		Usuario usuarioSystem = comps.util().usuario().getUsuarioSystem();
		Grupo g = comps.requestHelper().setGrupoInUse(request);
		
		if (!((comps.requestHelper().getUsuarioId()+"").equals(request.getIdUsuario()))) {
			throw new ValidationException(ExceptionReturnCode.GENERAL_INVALID_SENT_DATA);
		}
		try {
			comps.process().grupo().removeMe(comps.requestHelper().getUsuarioLogged(), usuarioSystem, g,
					comps.repo().userForGrupo().findByIdPrimitive(g.getIdGrupo(),
							comps.requestHelper().getUsuarioLogged().getIdUser()));
		} catch (Exception e) {
			throw new PrivacityException(e.getMessage());
		}

	}
	public void removeOther(GrupoRemoveUserDTO request) throws PrivacityException {

		Usuario usuarioSystem = comps.util().usuario().getUsuarioSystem();
		Grupo g = comps.requestHelper().setGrupoInUse(request);
		UserForGrupo ufg = comps.util().userForGrupo().getValidation(comps.requestHelper().getUsuarioLogged(), g.getIdGrupo());
		Usuario uToRemove = comps.util().usuario().getUsuarioById(request.getIdUsuario());
		try {
			comps.process().grupo().removeMe(uToRemove, usuarioSystem, g,
					comps.repo().userForGrupo().findByIdPrimitive(g.getIdGrupo(),
							uToRemove.getIdUser()));
		} catch (Exception e) {
			throw new PrivacityException(e.getMessage());
		}

	}
	public void delete(GrupoDTO request) throws PrivacityException {
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();

		// Grupo grupo = comps.util().grupo().getGrupoByIdValidation(request);
		long idGrupo = Long.parseLong(request.getIdGrupo());
		UserForGrupo ufg = comps.util().userForGrupo().getValidation(usuarioLogged, idGrupo);
		comps.util().grupo().validateRoleAdmin(usuarioLogged, ufg.getUserForGrupoId().getGrupo());

		Usuario usuarioSystem = comps.util().usuario().getUsuarioSystem();

		try {
			comps.process().grupo().delete(usuarioLogged, usuarioSystem, ufg.getUserForGrupoId().getGrupo());
		} catch (Exception e) {
			throw new PrivacityException(e.getMessage());
		}

	}

	public void sentInvitation(GrupoAddUserRequestDTO request) throws PrivacityException, IOException {

		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();

		Grupo g = comps.requestHelper().setGrupoInUse(request.getIdGrupo());

		comps.util().grupo().validateRoleAdmin(usuarioLogged, g);

		Usuario userInvitationCode = comps.repo().user().findByUserInvitationCode(request.getInvitationCode());

		if (userInvitationCode == null) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_NOT_EXISTS_INVITATION_CODE);
		}

		comps.util().grupo().isSameUser(usuarioLogged, userInvitationCode);

		// valido que el usuario no este en el grupo
		{

			Optional<UserForGrupo> ufg = comps.repo().userForGrupo()
					.findById(new UserForGrupoId(userInvitationCode, g));

			if (ufg.isPresent() && !ufg.get().isDeleted()) {
				throw new ValidationException(ExceptionReturnCode.GRUPO_USER_IS_IN_THE_GRUPO);
			}
		}

		// GrupoRolesEnum role =
		// comps.util().grupo().getGrupoRoleEnum(request.getRole());

		// valido que no haya ya sido invitado
		Optional<GrupoInvitation> gi = comps.repo().grupoInvitation()
				.findById(new GrupoInvitationId(userInvitationCode, usuarioLogged, g));
		if (gi.isPresent()) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_INVITATION_EXIST_INVITATION);
		}
		//
		encryptKeysValidation.aesValitadation(request.getAesDTO());
		AES aes = comps.common().mapper().doit(request.getAesDTO());
		aes.setIdAES(comps.factory().idsGenerator().getNextAESId());
		comps.process().grupo().sentInvitation(g, request.getRole(), request.getInvitationMessage(), usuarioLogged, userInvitationCode, aes);

	}

	public GrupoDTO newGrupo(GrupoNewRequestDTO request) throws Exception {
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();

		Grupo g = new Grupo();
		g.setIdGrupo(comps.factory().idsGenerator().getNextGrupoId());
		g.setName(request.getGrupoDTO().getName());
		comps.util().grupo().getDefaultGrupoGeneralConfiguration(g);
		g.getGralConf().setGrupo(g);
		
		//comps.repo().grupo().save(g);
		
		encryptKeysValidation.aesValitadation(request.getAesDTO());

		
		AES aes = comps.common().mapper().doit(request.getAesDTO());
		aes.setIdAES(comps.factory().idsGenerator().getNextAESId());

		GrupoDTO grupoCreado = comps.process().grupo().newGrupo(usuarioLogged, g, aes);
		grupoCreado.setMembersQuantityDTO(new MembersQuantityDTO());
		grupoCreado.getMembersQuantityDTO().setTotalQuantity(1);
		return grupoCreado;
	}

	public GrupoDTO[] getIdsMisGrupos() throws Exception {
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		return comps.process().grupo().getIdsMisGrupos(usuarioLogged);
	}

	public GrupoDTO[] getGrupoByIds(GrupoDTO[] idDTO) throws Exception {
		GrupoDTO[] r = new GrupoDTO[idDTO.length];

		for (int i = 0; i < idDTO.length; i++) {
			GrupoDTO gg = new GrupoDTO();
			gg.setIdGrupo(idDTO[i].getIdGrupo());
			r[i] = getGrupoById(gg);
		}

		return r;
	}

	public void startWritting(WrittingDTO request) throws Exception {
		generalWritting(ProtocoloActionsEnum.GRUPO_WRITTING, request);
	}

	public void stopWritting(WrittingDTO request) throws Exception {
		generalWritting(ProtocoloActionsEnum.GRUPO_STOP_WRITTING, request);
	}

	private void generalWritting(ProtocoloActionsEnum protocoloAction, WrittingDTO request) throws Exception {

		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(ProtocoloComponentsEnum.GRUPO, protocoloAction,
				request);
		comps.webSocket().sender().senderToGrupo(p, comps.requestHelper().getGrupoInUse().getIdGrupo(), comps.requestHelper().getUsuarioUsername());
	}

	// hacer q reciba un array
	public GrupoDTO getGrupoById(GrupoDTO idDTO) throws PrivacityException {

		GrupoDTO r = null;
		log.trace("Entrada getGrupoById: " + idDTO.getIdGrupo());

		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		long idGrupo = idDTO.convertIdGrupoToLong();
		GrupoInvitation inv = comps.util().grupoInvitation().getGrupoInvitation(usuarioLogged.getIdUser(), idGrupo);


		if (inv == null) {
		
			UserForGrupo v = comps.util().userForGrupo().getValidation(usuarioLogged, idGrupo);
			r = comps.process().grupo().getGrupoDTO(usuarioLogged, v);
			Grupo g = comps.repo().grupo().findById(idGrupo).get();
			r.setUserConfDTO(comps.process().grupo().getGrupoUserConf(usuarioLogged, g));
			
			
			
			
			

			try {
				r.setMembersQuantityDTO(getMembersOnline(r));
			} catch (PrivacityException e) {
				log.error(ExceptionReturnCode.MESSAGING_GET_ONLINE_MEMBERS_FAIL.getToShow(idDTO.getIdGrupo(), e));  
				r.setMembersQuantityDTO(new MembersQuantityDTO());
			}

		}else {
			Grupo grupo = comps.util().grupo().getGrupoByIdValidation(idDTO.getIdGrupo());
			r = comps.common().mapper().getGrupoDTOPropio( grupo);
			r.setUserConfDTO(comps.process().grupo().getGrupoUserConf(usuarioLogged, grupo));
			
			r.setGrupoInvitationDTO(comps.common().mapper().getGrupoInvitationDTOPropio(inv));
			try {
				r.setMembersQuantityDTO(getMembersOnline(r));
			} catch (PrivacityException e) {
				r.setMembersQuantityDTO(new MembersQuantityDTO());
			}
		}
		
		
//        new Thread(new Runnable() {
//			//FacadeComponent val = GrupoValidationService.this.comps;
//			@Override
//			public void run() {
//				comps.process().grupo().refreshOnline(idDTO.convertIdGrupoToLong());
//				
//			}
//		}).start();

		return r;
	}

	
	public void saveGrupoGeneralConfiguration(GrupoGralConfDTO r) throws PrivacityException {
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		Grupo g = comps.requestHelper().setGrupoInUse(r);
		comps.util().grupo().validateRoleAdmin(usuarioLogged, g);

		comps.common().mapper().doit(g, r);

		comps.process().grupo().saveGrupoGeneralConfiguration(g);
		
		try {
			comps.repo().grupo().save(g);

			GrupoGralConfDTO c = comps.common().mapper().getGrupoDTOPropio(g).getGralConfDTO();
			c.setIdGrupo(g.getIdGrupo()+"");
			MessageDTO mensajeD = comps.webSocket().sender().buildSystemMessage(g,
					"system__message__grupo__general_config__modified");
			Message mensaje = comps.common().mapper().doit(mensajeD, comps.util().usuario().getUsuarioSystem(), g);
			comps.process().message().sendNormal(comps.util().usuario().getUsuarioSystem().getIdUser(), mensaje,
					g.getIdGrupo());

			ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(ProtocoloComponentsEnum.GRUPO,
					ProtocoloActionsEnum.GRUPO_SAVE_GENERAL_CONFIGURATION, c);
			comps.webSocket().sender().senderToGrupo(p, g.getIdGrupo(), usuarioLogged.getUsername());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ValidationException(ExceptionReturnCode.GRUPO_GRUPOID_BADFORMAT);
		}
	}

	public GrupoDTO acceptInvitation(GrupoInvitationAcceptRequestDTO request) throws PrivacityException {
		Usuario usuarioInvitado = comps.requestHelper().getUsuarioLogged();

		long idGrupo = Long.parseLong(request.getIdGrupo());

		GrupoInvitation gi = comps.repo().grupoInvitation()
				.findByGrupoInvitationUsuarioGrupo(usuarioInvitado.getIdUser(), idGrupo);
		if (gi == null) {
			log.debug(ExceptionReturnCode.GRUPO_USER_NOT_EXISTS_INVITATION_CODE.getToShow());
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_NOT_EXISTS_INVITATION_CODE);
		}

		Usuario usuarioInvitante = gi.getGrupoInvitationId().getUsuarioInvitante();

		// comps.util().grupo().validateRoleAdmin(usuarioInvitante, g);
		comps.util().grupo().isSameUser(usuarioInvitado, usuarioInvitante);
		encryptKeysValidation.aesValitadation(request.getAesDTO());
		AES aes = comps.common().mapper().doit(request.getAesDTO());

		try {
			return comps.process().grupo().acceptInvitation(gi, aes);
		} catch (Exception e) {
			log.error(ExceptionReturnCode.GRUPO_USER_NOT_EXISTS_INVITATION_CODE.getToShow(e));
			e.printStackTrace();
			throw new PrivacityException(e.getMessage());
		}
	}
//	/*
//	public GrupoUserConfDTO getGrupoUserConf(GrupoDTO r) throws PrivacityException {
//		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
//
//		Grupo grupo = comps.repo().grupo().findById(Long.parseLong(r.getIdGrupo())).get();
//		
//		Optional<UserForGrupo> ufg = comps.repo().userForGrupo().findById(new UserForGrupoId(usuarioLogged, grupo));
//		
//		if (!ufg.isPresent() ) {
//			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO);				
//		}
//		
//		return comps.process().grupo().getGrupoUserConf(usuarioLogged, grupo);
//	}
//	*/

	public void saveNickname(GrupoInfoNicknameRequestDTO r) throws PrivacityException {
		comps.requestHelper().setGrupoInUse(r);
		comps.requestHelper().validationUserInTheGrupo();

		UserForGrupo ufg = comps.requestHelper().getUserForGrupoInUse();
		
		ufg.setNickname(r.getNickname());
		comps.repo().userForGrupo().save(ufg);

	}
	
	public void saveGrupoUserConf(GrupoUserConfDTO request) throws PrivacityException {

		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();

		Grupo grupo = comps.repo().grupo().findById(Long.parseLong(request.getIdGrupo())).get();

		UserForGrupo ufg = comps.util().userForGrupo().getValidation(usuarioLogged, grupo.getIdGrupo());

		GrupoUserConf conf = comps.common().mapper().doit(request);
		conf.getGrupoUserConfId().setGrupo(grupo);
		conf.getGrupoUserConfId().setUser(usuarioLogged);

//		String nicknameForGrupoAnterior =  comps.util().userForGrupo().getNicknameForGrupo(grupo, usuarioLogged);
//		
//		if (nicknameForGrupoAnterior == null) {
//			nicknameForGrupoAnterior = usuarioLogged.getNickname();
//		}
//		
//		if (request.getNicknameForGrupo() != null) {
		// mandar mail avisando cambio de nombre

//			MessageDTO mensajeD = comps.webSocket().sender().buildSystemMessage(gi.getGrupoInvitationId().getGrupo(), 
//					"El usuario ");
//			Message mensaje = comps.common().mapper().doit(mensajeD, comps.util().usuario().getUsuarioSystem());
//			 comps.process().message().sendNormal(mensaje);

		comps.repo().userForGrupo().save(ufg);

		// String nicknameForGrupoNuevo =
		// comps.util().userForGrupo().getNicknameForGrupo(grupo, usuarioLogged);

//			if (nicknameForGrupoNuevo == null) {
//				nicknameForGrupoNuevo = usuarioLogged.getNickname();
//			}

//			if (!nicknameForGrupoNuevo.equals(nicknameForGrupoAnterior)) {

//				MessageDTO mensaje = comps.webSocket().sender().buildSystemMessage(grupo, "EL USUARIO " + nicknameForGrupoAnterior + " HA CAMBIADO SU NICKNAME. (No se revela el nuevo nick por seguridad)");
//				Usuario usuarioSystem = comps.util().usuario().getUsuarioSystem();
//				 try {
//					comps.process().message().sendNormal(comps.common().mapper().doit(mensaje, usuarioSystem,grupo));
//				} catch (ValidationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					throw new PrivacityException(e.getMessage());
//				} catch (ProcessException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					throw new PrivacityException(e.getMessage());
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					
//					e.printStackTrace();
//					throw new PrivacityException(e.getMessage());
//				}
//			}
//		}
		comps.process().grupo().saveGrupoUserConf(conf);
	}
//	
//

//	

//
//	

//	

//	

//	
//	public InitGrupoResponse initGrupo(String request) throws Exception {
//		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
//
//		Grupo g = comps.util().grupo().getGrupoById(request);
//
//		Optional<UserForGrupo> ufg = comps.repo().userForGrupo().findById(new UserForGrupoId(usuarioLogged, g));
//		
//		if (!ufg.isPresent() ) {
//			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO);				
//		}
//		
//		return comps.process().grupo().initGrupo(usuarioLogged, g);
//	}
}
