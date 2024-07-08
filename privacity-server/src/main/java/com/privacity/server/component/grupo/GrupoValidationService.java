package com.privacity.server.component.grupo;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.GrupoGralConfDTO;
import com.privacity.common.dto.GrupoUserConfDTO;
import com.privacity.common.dto.IdDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.UserForGrupoDTO;
import com.privacity.common.dto.WrittingDTO;
import com.privacity.common.dto.request.GrupoAddUserRequestDTO;
import com.privacity.common.dto.request.GrupoInfoNicknameRequestDTO;
import com.privacity.common.dto.request.GrupoInvitationAcceptRequestDTO;
import com.privacity.common.dto.request.GrupoNewRequestDTO;
import com.privacity.common.dto.response.SaveGrupoGralConfLockResponseDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.GrupoRolesEnum;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.encryptkeys.EncryptKeysValidation;
import com.privacity.server.exceptions.PrivacityException;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.model.AES;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.GrupoInvitation;
import com.privacity.server.model.GrupoInvitationId;
import com.privacity.server.model.GrupoUserConf;
import com.privacity.server.model.Message;
import com.privacity.server.model.UserForGrupo;
import com.privacity.server.model.UserForGrupoId;
import com.privacity.server.security.Usuario;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Service
@AllArgsConstructor
@Log
public class GrupoValidationService {
	private static final Logger log = Logger.getLogger(GrupoValidationService.class.getCanonicalName());
	@Autowired
	@Lazy
	private FacadeComponent comps;
	@Autowired
	@Lazy
	private EncryptKeysValidation encryptKeysValidation;

	public boolean loginGrupo(GrupoDTO r) throws ValidationException {
		Usuario u = comps.requestHelper().getUsuarioLogged();
		Grupo g = comps.util().grupo().getGrupoByIdValidation(r.getIdGrupo());
		comps.util().userForGrupo().getValidation(u, g.getIdGrupo());
		String pw = r.getPassword().getPassword();

		return comps.process().grupo().loginGrupo(g, pw);

	}

//	
//	@com.privacity.common.RolesAllowed({GrupoRolesEnum.ADMIN,GrupoRolesEnum.MODERATOR} )
//	public void blockGrupoRemoto (GrupoDTO grupoBlockRemotoRequestLocalDTO) throws Exception {
//
//
//
//			MessageDTO mensajeD = comps.webSocket().sender().buildSystemMessage(
//					grupoBlockRemotoRequestLocalDTO.getGrupo(), 
//					"BLOQUEO DE EMERGENCIA");
//			Message mensaje = comps.common().mapper().doit(mensajeD, comps.util().usuario().getUsuarioSystem(), grupoBlockRemotoRequestLocalDTO.getGrupo());
//			comps.process().message().sendNormal(comps.util().usuario().getUsuarioSystem().getIdUser(), mensaje, grupoBlockRemotoRequestLocalDTO.getGrupo().getIdGrupo());
//			
//			GrupoDTO grupoDTO = comps.process().grupo().getGrupoDTO(grupoBlockRemotoRequestLocalDTO.getUsuarioLoggued(), grupoBlockRemotoRequestLocalDTO.getUserForGrupo());
//			
//		ProtocoloDTO p;
//			p = comps.webSocket().sender().buildProtocoloDTO(
//					ProtocoloComponentsEnum.GRUPO,
//					ProtocoloActionsEnum.GRUPO_BLOCK_REMOTO);
//		
//				
//				comps.webSocket().sender().senderToGrupoMinusCreator( comps.util().usuario().getUsuarioSystem().getIdUser(), grupoBlockRemotoRequestLocalDTO.getGrupo().getIdGrupo(), p);
//
//	}

	public void saveGrupoGralConfLock(GrupoDTO request) throws ValidationException {
		if (request.getLock().isEnabled()) {
			if (request.getLock().getSeconds() == null
					|| request.getLock().getSeconds().intValue() < comps.common().serverConf().getSystemGralConf()
							.getMyAccountConf().getLock().getMinSecondsValidation().intValue()) {
				throw new ValidationException(ExceptionReturnCode.MYACCOUNT_LOCK_MIN_SECONDS_VALIDATION);
			}
		}

		Usuario u = comps.requestHelper().getUsuarioLogged();
		Grupo g = comps.util().grupo().getGrupoByIdValidation(request.getIdGrupo());

		UserForGrupo v = comps.util().userForGrupo().getValidation(u, g.getIdGrupo());

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
		if (request.getLock().getSeconds() != null) {
			secondNew = request.getLock().getSeconds().intValue();
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
						"UN ADMINISTRADOR HA MODIFICADO LA CONFIGURACION DE BLOQUEO DEL GRUPO");
				Message mensaje = comps.common().mapper().doit(mensajeD, comps.util().usuario().getUsuarioSystem(), g);
				comps.process().message().sendNormal(comps.util().usuario().getUsuarioSystem().getIdUser(), mensaje,
						g.getIdGrupo());

				ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(ProtocoloComponentsEnum.GRUPO,
						ProtocoloActionsEnum.GRUPO_SAVE_GENERAL_CONFIGURATION_LOCK, c);
				comps.webSocket().sender().senderToGrupo(p, g.getIdGrupo(), u.getUsername() , true);

			} catch (Exception e) {
				e.printStackTrace();
				throw new ValidationException(ExceptionReturnCode.GRUPO_GRUPOID_BADFORMAT);
			}
		}

	}

	public int getMembersOnline(GrupoDTO request) throws PrivacityException {

		Long idGrupo = comps.util().grupo().convertIdGrupoStringToLong(request.getIdGrupo());

		List<Usuario> users = comps.repo().userForGrupo().findByUsuariosForGrupoDeletedFalse(idGrupo);

		int count = 0;
		for (Usuario u : users) {

			Set<String> online = comps.webSocket().socketSessionRegistry().getSessionIds(u.getUsername());

			if (online.size() > 0) {
				count++;
			}
		}

		return count;

	}

	public UserForGrupoDTO[] getMembers(GrupoDTO request) throws PrivacityException {

		Long idGrupo = comps.util().grupo().convertIdGrupoStringToLong(request.getIdGrupo());

		List<UserForGrupo> users = comps.repo().userForGrupo().findByForGrupo(idGrupo);

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

	@com.privacity.common.RolesAllowed({ GrupoRolesEnum.ADMIN, GrupoRolesEnum.MODERATOR, GrupoRolesEnum.MEMBER,
			GrupoRolesEnum.READONLY })
	public void removeMe(GrupoDTO request) throws PrivacityException {

		Usuario usuarioSystem = comps.util().usuario().getUsuarioSystem();
		Grupo g = comps.repo().grupo().findById(new Long(Long.parseLong(request.getIdGrupo()))).get();
		try {
			comps.process().grupo().removeMe(comps.requestHelper().getUsuarioLogged(), usuarioSystem, g,
					comps.repo().userForGrupo().findByIdPrimitive(g.getIdGrupo(),
							comps.requestHelper().getUsuarioLogged().getIdUser()));
		} catch (Exception e) {
			throw new PrivacityException(e.getMessage());
		}

	}

	public void delete(IdDTO request) throws PrivacityException {
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();

		// Grupo grupo = comps.util().grupo().getGrupoByIdValidation(request);
		long idGrupo = Long.parseLong(request.getId());
		UserForGrupo ufg = comps.util().userForGrupo().getValidation(usuarioLogged, idGrupo);
		comps.util().grupo().validateRoleAdmin(usuarioLogged, ufg.getUserForGrupoId().getGrupo());

		Usuario usuarioSystem = comps.util().usuario().getUsuarioSystem();

		try {
			comps.process().grupo().delete(usuarioLogged, usuarioSystem, ufg.getUserForGrupoId().getGrupo());
		} catch (Exception e) {
			throw new PrivacityException(e.getMessage());
		}

	}

	public void sentInvitation(GrupoAddUserRequestDTO request) throws PrivacityException {

		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();

		Grupo g = comps.requestHelper().setGrupoInUse(request.getIdGrupo());

		comps.util().grupo().validateRoleAdmin(usuarioLogged, g);

		Usuario UserInvitationCode = comps.repo().user().findByUserInvitationCode(request.getInvitationCode());

		if (UserInvitationCode == null) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_NOT_EXISTS_INVITATION_CODE);
		}

		comps.util().grupo().isSameUser(usuarioLogged, UserInvitationCode);

		// valido que el usuario no este en el grupo
		{

			Optional<UserForGrupo> ufg = comps.repo().userForGrupo()
					.findById(new UserForGrupoId(UserInvitationCode, g));

			if (ufg.isPresent() && !ufg.get().isDeleted()) {
				throw new ValidationException(ExceptionReturnCode.GRUPO_USER_IS_IN_THE_GRUPO);
			}
		}

		// GrupoRolesEnum role =
		// comps.util().grupo().getGrupoRoleEnum(request.getRole());

		// valido que no haya ya sido invitado
		Optional<GrupoInvitation> gi = comps.repo().grupoInvitation()
				.findById(new GrupoInvitationId(UserInvitationCode, usuarioLogged, g));
		if (gi.isPresent()) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_INVITATION_EXIST_INVITATION);
		}
		//
		encryptKeysValidation.aesValitadation(request.getAesDTO());
		AES aes = comps.common().mapper().doit(request.getAesDTO());

		comps.process().grupo().sentInvitation(g, request.getRole(), usuarioLogged, UserInvitationCode, aes);

	}

	public GrupoDTO newGrupo(GrupoNewRequestDTO request) throws Exception {
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();

		Grupo g = new Grupo();
		// g.setIdGrupo(comps.util().grupo().generateIdGrupo());
		g.setName(request.getGrupoDTO().getName());

		encryptKeysValidation.aesValitadation(request.getAesDTO());

		comps.util().grupo().getDefaultGrupoGeneralConfiguration(g);
		AES aes = comps.common().mapper().doit(request.getAesDTO());

		GrupoDTO grupoCreado = comps.process().grupo().newGrupo(usuarioLogged, g, aes);

		return this.getGrupoById(new IdDTO(grupoCreado.getIdGrupo()));
	}

	public IdDTO[] getIdsMisGrupos() throws Exception {
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		return comps.process().grupo().getIdsMisGrupos(usuarioLogged);
	}

	public GrupoDTO[] getGrupoByIds(IdDTO[] idDTO) throws Exception {
		GrupoDTO[] r = new GrupoDTO[idDTO.length];

		for (int i = 0; i < idDTO.length; i++) {
			r[i] = getGrupoById(idDTO[i]);
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
		comps.webSocket().sender().senderToGrupo(p, comps.requestHelper().getGrupoInUse().getIdGrupo(), comps.requestHelper().getUsuarioUsername(), true);
	}

	// hacer q reciba un array
	public GrupoDTO getGrupoById(IdDTO idDTO) throws Exception {
		Date inicio = new Date();
		GrupoDTO r = null;
		log.info("Entrada getGrupoById: " + idDTO.toString());

		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		// log.info("2");

		// log.info("3");

		long idGrupo = idDTO.convertIdToLong();

		GrupoInvitation inv = comps.util().grupoInvitation().getGrupoInvitation(usuarioLogged.getIdUser(), idGrupo);

		// log.info("4");
		if (inv == null) {
			// log.info("5");
			UserForGrupo v = comps.util().userForGrupo().getValidation(usuarioLogged, idGrupo);
			// log.info("////log.info");
			r = comps.process().grupo().getGrupoDTO(usuarioLogged, v);
			Grupo g = comps.repo().grupo().findById(idGrupo).get();
			// log.info("7");
			r.setUserConfDTO(comps.process().grupo().getGrupoUserConf(usuarioLogged, g));

			r.setMembersOnLine(this.getMembersOnline(r));

		} else {
			Grupo grupo = comps.util().grupo().getGrupoByIdValidation(idDTO.getId());
			// log.info("8");
			r = comps.process().grupo().getGrupoDTOInvitation(usuarioLogged, inv, grupo);
			// log.info("9");

		}
		Date fin = new Date();

		double total = fin.getTime() - inicio.getTime();

		// log.info("total:>> " + (total/1000));
		return r;
	}

	public void saveGrupoGeneralConfiguration(GrupoGralConfDTO r) throws PrivacityException {
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		Grupo grupo = comps.requestHelper().setGrupoInUse(r);
		comps.util().grupo().validateRoleAdmin(usuarioLogged, grupo);

		comps.common().mapper().doit(grupo, r);

		comps.process().grupo().saveGrupoGeneralConfiguration(grupo);
	}

	public GrupoDTO acceptInvitation(GrupoInvitationAcceptRequestDTO request) throws PrivacityException {
		Usuario usuarioInvitado = comps.requestHelper().getUsuarioLogged();

		long idGrupo = Long.parseLong(request.getIdGrupo());

		GrupoInvitation gi = comps.repo().grupoInvitation()
				.findByGrupoInvitationUsuarioGrupo(usuarioInvitado.getIdUser(), idGrupo);
		if (gi == null) {
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
			// TODO Auto-generated catch block
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
//		r.getUserForGrupo().setNickname(r.getNickname());
//		comps.repo().userForGrupo().save(r.getUserForGrupo());

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
