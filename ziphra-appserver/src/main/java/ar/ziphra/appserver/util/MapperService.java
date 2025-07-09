package ar.ziphra.appserver.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.AESDTO;
import ar.ziphra.common.dto.EncryptKeysDTO;
import ar.ziphra.common.dto.GrupoDTO;
import ar.ziphra.common.dto.GrupoGralConfDTO;
import ar.ziphra.common.dto.GrupoGralConfPasswordDTO;
import ar.ziphra.common.dto.GrupoInvitationDTO;
import ar.ziphra.common.dto.GrupoUserConfDTO;
import ar.ziphra.common.dto.IdMessageDTO;
import ar.ziphra.common.dto.LockDTO;
import ar.ziphra.common.dto.MediaDTO;
import ar.ziphra.common.dto.MessageDTO;
import ar.ziphra.common.dto.MessageDetailDTO;
import ar.ziphra.common.dto.MyAccountConfDTO;
import ar.ziphra.common.dto.RequestIdDTO;
import ar.ziphra.common.dto.UserForGrupoDTO;
import ar.ziphra.common.dto.UsuarioDTO;
import ar.ziphra.common.dto.response.SaveGrupoGralConfLockResponseDTO;
import ar.ziphra.common.enumeration.MessageState;
import ar.ziphra.common.exceptions.ProcessException;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.core.model.AES;
import ar.ziphra.core.model.EncryptKeys;
import ar.ziphra.core.model.Grupo;
import ar.ziphra.core.model.GrupoInvitation;
import ar.ziphra.core.model.GrupoUserConf;
import ar.ziphra.core.model.GrupoUserConfId;
import ar.ziphra.core.model.Media;
import ar.ziphra.core.model.MediaId;
import ar.ziphra.core.model.Message;
import ar.ziphra.core.model.MessageDetail;
import ar.ziphra.core.model.MessageId;
import ar.ziphra.core.model.MyAccountConf;
import ar.ziphra.core.model.UserForGrupo;
import ar.ziphra.core.model.UserForGrupoId;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.appserver.component.common.service.facade.FacadeComponent;
import lombok.extern.java.Log;

@Service
@Log
public class MapperService {
	private static final Logger log = Logger.getLogger(MapperService.class.getCanonicalName());
	@Autowired
	@Lazy
	private FacadeComponent comps;
	@Autowired @Lazy
	private ar.ziphra.appserver.factory.IdsGeneratorFactory idGeneratorFactory;

	public UserForGrupoDTO getUserForGrupoDTOPropio(UserForGrupo userForGrupo) {
		UserForGrupoDTO ufgDTO = new UserForGrupoDTO();
//		ufgDTO.setIdGrupo(userForGrupo.getUserForGrupoId().getGrupo().getIdGrupo()+"");
//		ufgDTO.setUsuario(doitForGrupo(userForGrupo.getUserForGrupoId().getGrupo(),userForGrupo.getUserForGrupoId().getUser()));
		ufgDTO.setRole(userForGrupo.getRole());
		ufgDTO.setAesDTO( doit(userForGrupo.getAes()));
		
		ufgDTO.setAlias(userForGrupo.getAlias());
		ufgDTO.setNickname(userForGrupo.getNickname());
		
		return ufgDTO;
	}	
	public UserForGrupoDTO getUserForGrupoDTO(UserForGrupo userForGrupo) throws ZiphraException {
		UserForGrupoDTO ufgDTO = new UserForGrupoDTO();
		ufgDTO.setIdGrupo(userForGrupo.getUserForGrupoId().getGrupo().getIdGrupo()+"");
		ufgDTO.setUsuario(doitForGrupo(userForGrupo.getUserForGrupoId().getGrupo(),userForGrupo.getUserForGrupoId().getUser(),false));
 		ufgDTO.setRole(userForGrupo.getRole());
		//ufgDTO.setAesDTO( doit(userForGrupo.getAes()));
		
		ufgDTO.setAlias(userForGrupo.getAlias());
		ufgDTO.setNickname(userForGrupo.getNickname());
		
		return ufgDTO;
	}	
	public GrupoDTO getGrupoDTOInvitation(Grupo grupo) {
		GrupoDTO g = new GrupoDTO();
		g.setIdGrupo(grupo.getIdGrupo().toString());
		g.setName(grupo.getName());
	
		return g;
	}	
	
	public GrupoDTO getGrupoDTOPropio(Grupo grupo) {
		GrupoDTO g = new GrupoDTO();
		g.setIdGrupo(grupo.getIdGrupo().toString());
		g.setName(grupo.getName());
		
		g.setGralConfDTO(new GrupoGralConfDTO());
		g.getGralConfDTO().setAnonimo(grupo.getGralConf().getAnonimo());
		g.getGralConfDTO().setBlockAudioMessages(grupo.getGralConf().isBlockAudioMessages());
		g.getGralConfDTO().setAudiochatMaxTime(grupo.getGralConf().getAudiochatMaxTime());
		g.getGralConfDTO().setBlackMessageAttachMandatory(grupo.getGralConf().isBlackMessageAttachMandatory());
		g.getGralConfDTO().setRandomNickname(grupo.getGralConf().isRandomNickname());
		
		g.getGralConfDTO().setBlockMediaDownload(grupo.getGralConf().isBlockMediaDownload());

		g.getGralConfDTO().setExtraEncrypt(grupo.getGralConf().getExtraEncrypt());
		g.getGralConfDTO().setHideMessageDetails(grupo.getGralConf().isHideMessageDetails());
		g.getGralConfDTO().setHideMessageReadState(grupo.getGralConf().isHideMessageReadState());
		g.getGralConfDTO().setHideMemberList(grupo.getGralConf().isHideMemberList());
		g.getGralConfDTO().setBlockResend(grupo.getGralConf().isBlockResend());
		g.getGralConfDTO().setTimeMessageMandatory(grupo.getGralConf().isTimeMessageMandatory());
		g.getGralConfDTO().setTimeMessageMaxTimeAllow(grupo.getGralConf().getTimeMessageMaxTimeAllow());
	

		g.setPassword(new GrupoGralConfPasswordDTO());
		g.getPassword().setEnabled(grupo.getPassword().isEnabled());
		g.getPassword().setExtraEncryptDefaultEnabled(grupo.getPassword().isExtraEncryptDefaultEnabled());
		g.getPassword().setDeleteExtraEncryptEnabled(grupo.getPassword().isDeleteExtraEncryptEnabled());		
		g.getPassword().setPassword(grupo.getPassword().getPassword());
		g.getPassword().setPasswordExtraEncrypt(grupo.getPassword().getPasswordExtraEncrypt());

		g.setLock(new LockDTO());
		g.getLock().setEnabled(grupo.getLock().isEnabled());
		g.getLock().setSeconds(grupo.getLock().getSeconds());
		return g;
	}		
	
	public GrupoInvitationDTO getGrupoInvitationDTOPropio(GrupoInvitation gi) {
	 return new GrupoInvitationDTO(
				comps.common().mapper().getUsuarioDTOOtro(gi.getGrupoInvitationId().getUsuarioInvitante()), 
				gi.getRole(),gi.getInvitationMessage(),
				comps.common().mapper().doit(gi.getAes()),
				gi.getPrivateKey()
				);
	}
	public GrupoUserConfDTO doitGrupoUserConf(GrupoUserConf d) {
		
		GrupoUserConfDTO r = new GrupoUserConfDTO();
		
		r.setAnonimoAlways(d.getAnonimoAlways());
		r.setAnonimoRecived(d.isAnonimoRecived());
		r.setAnonimoRecivedMyMessage(d.isAnonimoRecivedMyMessage());
		r.setBlackMessageAttachMandatory(d.getBlackMessageAttachMandatory());
		r.setBlackMessageAttachMandatoryReceived(d.getBlackMessageAttachMandatoryReceived());
		r.setBlockResend(d.getBlockResend());
		r.setExtraAesAlways(d.getSecretKeyPersonalAlways());
		r.setTimeMessageAlways(d.getTimeMessageAlways());
		r.setTimeMessageSeconds(d.getTimeMessageSeconds());

		r.setBlockMediaDownload(d.getBlockMediaDownload());
		
		//r.setIdGrupo(d.getGrupoUserConfId().getGrupo().getIdGrupo()+"");
		//r.setChangeNicknameToNumber(d.getChangeNicknameToNumber());
		return r;
	}
	
	public AESDTO doit(AES e) {
		AESDTO r = new AESDTO();
		r.setSecretKeyAES(e.getSecretKeyAES());
		r.setSaltAES(e.getSaltAES());
		r.setIteration(e.getIteration().toString());
		return r;
	}
	
	public UsuarioDTO getUsuarioDTOOtro(Usuario u) {
		// configurar el nickname
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		//usuarioDTO.setIdUsuario(u.getIdUser()+"");
		usuarioDTO.setNickname(u.getNickname());
//		usuarioDTO.setAlias(u.getAlias());
		return usuarioDTO;
	}
	
	//old
	public RequestIdDTO doitClientRequestIdDTO(RequestIdDTO serverRequestIdDTO) {
		RequestIdDTO r = new RequestIdDTO();
		r.setDate(serverRequestIdDTO.getDate());
		r.setRequestIdServerSide(serverRequestIdDTO.getRequestIdServerSide());
		
		return r;
		
	}
	

	
	public GrupoDTO doit(Grupo grupo, UserForGrupo ufg) {
		
		GrupoDTO g = new GrupoDTO();
		g.setIdGrupo(grupo.getIdGrupo().toString());
		g.setName(grupo.getName());
		

		g.setAlias(ufg.getAlias());	
		g.setGralConfDTO(new GrupoGralConfDTO());
		
	


		g.getGralConfDTO().setBlockAudioMessages(grupo.getGralConf().isBlockAudioMessages());
		g.getGralConfDTO().setAudiochatMaxTime(grupo.getGralConf().getAudiochatMaxTime());
		g.getGralConfDTO().setBlackMessageAttachMandatory(grupo.getGralConf().isBlackMessageAttachMandatory());
		g.getGralConfDTO().setRandomNickname(grupo.getGralConf().isRandomNickname());

		g.getGralConfDTO().setBlockMediaDownload(grupo.getGralConf().isBlockMediaDownload());

		g.getGralConfDTO().setExtraEncrypt(grupo.getGralConf().getExtraEncrypt());
		g.getGralConfDTO().setHideMessageDetails(grupo.getGralConf().isHideMessageDetails());
		g.getGralConfDTO().setHideMessageReadState(grupo.getGralConf().isHideMessageReadState());
		g.getGralConfDTO().setHideMemberList(grupo.getGralConf().isHideMemberList());
		g.getGralConfDTO().setBlockResend(grupo.getGralConf().isBlockResend());
		g.getGralConfDTO().setTimeMessageMandatory(grupo.getGralConf().isTimeMessageMandatory());
		g.getGralConfDTO().setTimeMessageMaxTimeAllow(grupo.getGralConf().getTimeMessageMaxTimeAllow());
	
		
		return g;
	}
	public MediaDTO doit(Media m) throws ProcessException {
		return doit(m, false);
	}
	
	public MediaDTO doit(Media m, boolean fillMediaData) throws ProcessException {
		if (m == null) return null;
		
		MediaDTO mediaDTO = new MediaDTO();

		if (fillMediaData && m.getData() != null) {
			//mediaDTO.setData(services.getZipUtilService().decompress(m.getData()));
			mediaDTO.setData(m.getData());

		}
		
		mediaDTO.setMiniatura(m.getMiniatura());
		mediaDTO.setDownloadable(m.isDownloadable());
		mediaDTO.setIdGrupo(m.getMediaId().getMessage().getMessageId().getGrupo().getIdGrupo()+"");
		mediaDTO.setIdMessage(m.getMediaId().getMessage().getMessageId().getIdMessage()+"");
		mediaDTO.setMediaType(m.getMediaType());

		return mediaDTO;
	}


	public UsuarioDTO doitForGrupo(Grupo grupo, Usuario u, boolean randomNickname) throws ZiphraException {

		
		if (u.getUsername().equals("SYSTEM") || u.getUsername().equals("Anonimo") ){
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setIdUsuario(u.getIdUser()+"");
			usuarioDTO.setNickname(u.getNickname()+"");
			
			return usuarioDTO;
		}
		
		
		UserForGrupo ugr = comps.repo().userForGrupo().findById(new UserForGrupoId(u, grupo)).get();
		String nicknameForGrupo = ""; 
		
		if (ugr.getNickname() != null && !ugr.getNickname().equals("")) {
			nicknameForGrupo=ugr.getNickname();
		}else {
			nicknameForGrupo= u.getNickname();
		}
		if (grupo.getGralConf().isRandomNickname() || randomNickname) {
			nicknameForGrupo=comps.requestHelper().getRandomNicknameByGrupo(grupo.getIdGrupo(),u.getIdUser());
		}
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(u.getIdUser()+"");
		usuarioDTO.setNickname(
				
				nicknameForGrupo);


		return usuarioDTO;
	}
	public static List<String> getList(){
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<String> emojis = new ArrayList<String>();
		words.add("And");
		words.add("Are");
		words.add("Ape");
		words.add("Ace");
		words.add("Act");
		words.add("Ask");
		words.add("Arm");
		words.add("Age");
		words.add("Ago");
		words.add("Air");
		words.add("Ate");
		words.add("All");
		words.add("But");
		words.add("Bye");
		words.add("Bad");
		words.add("Big");
		words.add("Bed");
		words.add("Bat");
		words.add("Boy");
		words.add("Bus");
		words.add("Bag");
		words.add("Box");
		words.add("Bit");
		words.add("Bee");
		words.add("Buy");
		words.add("Bun");
		words.add("Cub");
		words.add("Cat");
		words.add("Car");
		words.add("Cut");
		words.add("Cow");
		words.add("Cry");
		words.add("Cab");
		words.add("Can");
		words.add("Dad");
		words.add("Dab");
		words.add("Dam");
		words.add("Did");
		words.add("Dug");
		words.add("Den");
		words.add("Dot");
		words.add("Dip");
		words.add("Day");
		words.add("Ear");
		words.add("Eye");
		words.add("Eat");
		words.add("End");
		words.add("Elf");
		words.add("Egg");
		words.add("Far");
		words.add("Fat");
		words.add("Few");
		words.add("Fan");
		words.add("Fun");
		words.add("Fit");
		words.add("Fin");
		words.add("Fox");
		words.add("Own");
		words.add("Odd");
		words.add("Our");
		words.add("Pet");
		words.add("Pat");
		words.add("Peg");
		words.add("Paw");
		words.add("Pup");
		words.add("Pit");
		words.add("Put");
		words.add("Pot");
		words.add("Pop");
		words.add("Pin");
		words.add("Rat");
		words.add("Rag");
		words.add("Rub");
		words.add("Row");
		words.add("Rug");
		words.add("Run");
		words.add("Rap");
		words.add("Ram");
		words.add("Sow");
		words.add("See");
		words.add("Saw");
		/*words.add("Set");
		words.add("Sit");
		words.add("Sir");
		words.add("Sat");
		words.add("Sob");
		words.add("Tap");
		words.add("Tip");
		words.add("Top");
		words.add("Tug");
		words.add("Tow");
		words.add("Toe");
		words.add("Tan");
		words.add("Ten");
		words.add("Two");
		words.add("Use");
		words.add("Van");
		words.add("Vet");
		words.add("Was");
		words.add("Wet");
		words.add("Win");
		words.add("Won");
		words.add("Wig");
		words.add("War");
		words.add("Why");
		words.add("Who");
		words.add("Way");
		words.add("Wow");
		words.add("You");
		words.add("Yes");
		words.add("Yak");
		words.add("Yet");
		words.add("Zip");
		words.add("Zap");
		words.add("Fix");
		words.add("Fly");
		words.add("Fry");
		words.add("For");
		words.add("Got");
		words.add("Get");
		words.add("God");
		words.add("Gel");
		words.add("Gas");
		words.add("Hat");
		words.add("Hit");
		words.add("Has");
		words.add("Had");
		words.add("How");
		words.add("Her");
		words.add("His");
		words.add("Hen");
		words.add("Ink");
		words.add("Ice");
		words.add("Ill");
		words.add("Jab");
		words.add("Jug");
		words.add("Jet");
		words.add("Jam");
		words.add("Jar");
		words.add("Job");
		words.add("Jog");
		words.add("Kit");
		words.add("Key");
		words.add("Lot");
		words.add("Lit");
		words.add("Let");
		words.add("Lay");
		words.add("Mat");
		words.add("Man");
		words.add("Mad");
		words.add("Mug");
		words.add("Mix");
		words.add("Map");
		words.add("Mum");
		words.add("Mud");
		words.add("Mom");
		words.add("May");
		words.add("Met");
		words.add("Net");
		words.add("New");
		words.add("Nap");
		words.add("Now");
		words.add("Nod");
		words.add("Net");
		words.add("Not");
		words.add("Nut");
		words.add("Oar");
		words.add("One");
		words.add("Out");
		words.add("Owl");
		words.add("Old");*/
		
		emojis.add("😀");
		emojis.add("😆");
		emojis.add("🤣");
		emojis.add("😇");
		emojis.add("🙃");
		emojis.add("😌");
		emojis.add("🤪");
		emojis.add("🧐");
		emojis.add("😎");
		emojis.add("🥸");
		emojis.add("😖");
		emojis.add("🥵");
		emojis.add("🥶");
		emojis.add("😨");
		emojis.add("😰");
		emojis.add("🤐");
		emojis.add("🤠");
		emojis.add("🤡");
		emojis.add("💩");
		emojis.add("👻");
		emojis.add("☠️");
		emojis.add("👽");
		emojis.add("👾");
		emojis.add("🤖");
		emojis.add("🎃");
		emojis.add("🐶");
		emojis.add("🐱");
		emojis.add("🐭");
		emojis.add("🐹");
		emojis.add("🐰");
		emojis.add("🦊");
		emojis.add("🐻");
		emojis.add("🐼");
		emojis.add("🐻‍");
		emojis.add("❄️");
		emojis.add("🐨");
		emojis.add("🐯");
		emojis.add("🦁");
		emojis.add("🐮");
		emojis.add("🐷");
		emojis.add("🐽");
		emojis.add("🐸");
		emojis.add("🐵");
		emojis.add("🍐");
		emojis.add("🍊");
		emojis.add("🍌");
		emojis.add("🍉");
		emojis.add("🍇");
		emojis.add("🍓");
		emojis.add("🫐");
		emojis.add("🍈");
		emojis.add("🍒");
		emojis.add("🍑");
		emojis.add("🥭");
		emojis.add("🍍");
		emojis.add("🥝");
		emojis.add("🍅");
		emojis.add("🍆");
		emojis.add("🥑");
		emojis.add("🫛");
		emojis.add("🌶");
		emojis.add("💪");
		emojis.add("🐌");
		emojis.add("🐜");
		emojis.add("🪰");
		emojis.add("🪲");
		emojis.add("🪳");
		emojis.add("🦟");
		emojis.add("🦗");
		emojis.add("🕷");
		emojis.add("🦂");
		emojis.add("🦝");
		emojis.add("🍀");
		emojis.add("🍫");
		emojis.add("🌭");
		emojis.add("🍔");
		emojis.add("🍟");
		emojis.add("🍕");
		emojis.add("🥕");
		emojis.add("🍩");
		emojis.add("🍪");
		Collections.shuffle(words, new Random((new Random()).nextLong(990)));
		Collections.shuffle(words, new Random((new Random()).nextLong(9903)));
		Collections.shuffle(emojis, new Random((new Random()).nextLong(90)));
		Collections.shuffle(emojis, new Random((new Random()).nextLong(94830)));
		int i = 0;
		List<String> r = new ArrayList<>();
		for (String w : words) {
			
			r.add(w.toUpperCase() + "__" + emojis.get(i));
			i++;
		}
	
		return r;

	}
	public UsuarioDTO doitForGrupo(Usuario u) {
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(u.getIdUser()+"");
		usuarioDTO.setNickname(u.getNickname());

		return usuarioDTO;
	}
	
//	public UsuarioDTO doitForGrupo(Long idGrupo, Usuario u) throws ZiphraException {
//		
//		UsuarioDTO usuarioDTO = new UsuarioDTO();
//		usuarioDTO.setIdUsuario(u.getIdUser()+"");
//		usuarioDTO.setNickname(comps.requestHelper().getRandomNicknameByGrupo(idGrupo, u.getIdUser()));
//
//		return usuarioDTO;
//	}
	

	public Message doit(MessageDTO dto, Usuario usuarioCreacion, Grupo g) throws ZiphraException {
		List<UserForGrupo> usersForGrupo = comps.repo().userForGrupo().findByForGrupo(g.getIdGrupo());
		return doit(dto, usuarioCreacion, g, usersForGrupo, false);
	}
	public Message doit(MessageDTO dto, Usuario usuarioCreacion, Grupo g, List<UserForGrupo> usersForGrupo,  boolean newId) throws ZiphraException {
		
		
		
	
		if (comps.util().usuario().getUsuarioSystem().equals(usuarioCreacion)) {
			dto.setSystemMessage(true);
		}
		
		Message m = new Message();
		//m.setDateCreation(new Date());
		
		if (dto.isSystemMessage()) {
			m.setUserCreation(comps.util().usuario().getUsuarioSystem());
		}else {
			m.setUserCreation(usuarioCreacion);	
		}
		m.setText(dto.getText());
		m.setBlackMessage(dto.isBlackMessage());
		m.setAnonimo(dto.isAnonimo());
		m.setTimeMessage(dto.getTimeMessage());
		m.setSystemMessage(dto.isSystemMessage());
		m.setSecretKeyPersonal(dto.isSecretKeyPersonal());
		m.setBlockResend(dto.isBlockResend());
		m.setHideMessageState(dto.isHideMessageReadState());
		
		//Grupo g = comps.util().grupo().getGrupoByIdValidation(dto.getIdGrupo());
		
		MessageId idm = new MessageId();
		idm.setGrupo(g);
		
		if (dto.getIdMessage() == null || newId) {
			idm.setIdMessage(comps.factory().idsGenerator().getNextMessageId(g.getIdGrupo()));
		}
		
		m.setMessageId(idm);
		
		m.setMedia(doit(dto.getMedia(), m,true));
		m.setMessagesDetail( comps.util().messageDetail().generateMessagesDetail(g.getIdGrupo(),m,usersForGrupo));
		
		if ( dto.getParentReply() != null) {
			log.fine("Procesando Reply idParentReply: grupo -> "  + dto.getParentReply().getIdGrupo() + " message: -> " + dto.getParentReply().getIdMessage() );
			Message mr = comps.repo().message().findById( new MessageId (g, Long.parseLong(dto.getParentReply().getIdMessage()))).get();
			m.setParentReply(mr);
		}
		
		if ( dto.getParentResend() != null) {
			Grupo grupoResend = comps.repo().grupo().findById((Long.parseLong(dto.getParentResend().getIdGrupo()))).get();
			log.fine("Procesando Resend idParentResend: grupo -> "  + dto.getParentResend().getIdGrupo() + " message: -> " + dto.getParentResend().getIdMessage() );
			Message mr = comps.repo().message().findById( new MessageId( grupoResend, Long.parseLong(dto.getParentResend().getIdMessage()))).get();
			m.setParentResend(mr);
		}
		return m;
	}

	public Media doit(MediaDTO dto, Message message) throws ValidationException, ProcessException {
		return doit(dto,message, false);
	}
	public Media doit(MediaDTO dto, Message message, boolean fillMediaData) throws ValidationException, ProcessException {
		if (dto == null) return null;
		Media media = new Media();
		

		if (fillMediaData && dto.getData() != null) {
			byte[] compress = dto.getData(); //services.getZipUtilService().compress(dto.getData());
			//byte[] compress = zipUtilService.compress("123");
			media.setData(compress);
		}
		media.setMiniatura(dto.getMiniatura());
		media.setMediaType(dto.getMediaType());
		media.setDownloadable(dto.isDownloadable());
		media.setMediaId(new MediaId());
		media.getMediaId().setMessage(message);

		
		return media;
	}
	
	public MessageDTO doitWithOutMediaData(Message m, String mediaReplace) throws ZiphraException {
		MessageDTO r = new MessageDTO();
		r.setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
		r.setText(m.getText());
		r.setUsuarioCreacion(doitForGrupo(m.getMessageId().getGrupo(), m.getUserCreation(),m.isChangeNicknameToRandom()));
		r.setIdMessage(m.getMessageId().getIdMessage()+"");
		r.setMessagesDetail(new MessageDetailDTO[1]);
		r.setSecretKeyPersonal(m.isSecretKeyPersonal());
		Set<MessageDetail> details = m.getMessagesDetail();
		r.setBlackMessage(m.isBlackMessage());
		r.setAnonimo(m.isAnonimo());
		r.setTimeMessage(m.getTimeMessage());
		r.setHideMessageReadState(m.isHideMessageState());
		r.setSystemMessage(m.isSystemMessage());
		r.setBlockResend(m.isBlockResend());
		r.setMessagesDetail(new MessageDetailDTO[details.size()]);
		
		if (m.getParentResend() != null) {
			r.setParentResend(new IdMessageDTO());
			r.getParentResend().setIdGrupo(m.getParentResend().getMessageId().getGrupo().getIdGrupo()+"");
			r.getParentResend().setIdMessage(m.getParentResend().getMessageId().getIdMessage()+"");
		}
		
		Media media = m.getMedia();
		MediaDTO mediaDTO = doit(media);
		r.setMedia(mediaDTO);
		
		

		int i=0;
		for ( MessageDetail d : details) {
			
			r.getMessagesDetail()[i] = new MessageDetailDTO();
			
			r.getMessagesDetail()[i].setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
			r.getMessagesDetail()[i].setIdMessage(m.getMessageId().getIdMessage()+"");
			//			r.getMessagesDetailDTO()[i].setIdMessageDetail(d.getMessageDetailId().getIdMessageDetail()+"");
			
			
			if (d.getMessageDetailId().getUserDestino().getIdUser() == m.getUserCreation().getIdUser()) {
				r.getMessagesDetail()[i].setUsuarioDestino(r.getUsuarioCreacion());
			}else {
				r.getMessagesDetail()[i].setUsuarioDestino( doitForGrupo(m.getMessageId().getGrupo(),d.getMessageDetailId().getUserDestino(),m.isChangeNicknameToRandom()));
			}
			
			//response.getMessagesDetailDTO()[i].setUserDestino(d.getMessageDetailId().getUserDestino().getUsername());
			
			r.getMessagesDetail()[i].setEstado(d.getState());

			i++;
		}
		return r;
	}
	
	public MessageDTO doitWithOutTextAndMedia(MessageDTO m) throws ProcessException {
		MessageDTO r = new MessageDTO();
		if (m==null) return r;
		r.setIdGrupo(m.getIdGrupo());
		
		r.setUsuarioCreacion(m.getUsuarioCreacion());
		r.setIdMessage(m.getIdMessage());
		
		r.setSecretKeyPersonal(m.isSecretKeyPersonal());
		
		r.setBlackMessage(m.isBlackMessage());
		r.setAnonimo(m.isAnonimo());
		r.setTimeMessage(m.getTimeMessage());
		r.setHideMessageReadState(m.isHideMessageReadState());
		r.setSystemMessage(m.isSystemMessage());
		r.setBlockResend(m.isBlockResend());
		r.setChangeNicknameToRandom(m.isChangeNicknameToRandom());
		r.setMessagesDetail(m.getMessagesDetail());
		
		if (m.getParentResend() != null) {
			r.setParentResend(new IdMessageDTO());
			r.getParentResend().setIdGrupo(m.getParentResend().getIdGrupo());
			r.getParentResend().setIdMessage(m.getParentResend().getIdMessage());
		}
		if (m.getMedia() != null) {
			MediaDTO media = new MediaDTO();
			media.setDownloadable(m.getMedia().isDownloadable());
			media.setIdGrupo(m.getIdGrupo());
			media.setIdMessage(m.getMedia().getIdMessage());
			media.setMediaType(m.getMedia().getMediaType());
			
			r.setMedia(media);
		}

		return r;
	}
	
	public MessageDTO doitWithoutMediaData(Message m) throws ZiphraException {
		MessageDTO r = new MessageDTO();
		r.setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
		r.setText(m.getText());
		r.setUsuarioCreacion(doitForGrupo(m.getMessageId().getGrupo(), m.getUserCreation(),m.isChangeNicknameToRandom()));
		r.setIdMessage(m.getMessageId().getIdMessage()+"");
		r.setMessagesDetail(new MessageDetailDTO[1]);
		r.setSecretKeyPersonal(m.isSecretKeyPersonal());
		Set<MessageDetail> details = m.getMessagesDetail();
		r.setBlackMessage(m.isBlackMessage());
		r.setAnonimo(m.isAnonimo());
		r.setTimeMessage(m.getTimeMessage());
		r.setHideMessageReadState(m.isHideMessageState());
		r.setSystemMessage(m.isSystemMessage());
		r.setBlockResend(m.isBlockResend());
		r.setMessagesDetail(new MessageDetailDTO[details.size()]);
		if (m.getParentResend() != null) {
			r.setParentResend(new IdMessageDTO());
			r.getParentResend().setIdGrupo(m.getParentResend().getMessageId().getGrupo().getIdGrupo()+"");
			r.getParentResend().setIdMessage(m.getParentResend().getMessageId().getIdMessage()+"");
		}
		Media media = m.getMedia();
		MediaDTO mediaDTO = doit(media,false);
		r.setMedia(mediaDTO);
		

		int i=0;
		for ( MessageDetail d : details) {
			
			r.getMessagesDetail()[i] = new MessageDetailDTO();
			
			r.getMessagesDetail()[i].setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
			r.getMessagesDetail()[i].setIdMessage(m.getMessageId().getIdMessage()+"");
			//			r.getMessagesDetailDTO()[i].setIdMessageDetail(d.getMessageDetailId().getIdMessageDetail()+"");
			
			
			if (d.getMessageDetailId().getUserDestino().getIdUser() == m.getUserCreation().getIdUser()) {
				r.getMessagesDetail()[i].setUsuarioDestino(r.getUsuarioCreacion());
			}else {
				r.getMessagesDetail()[i].setUsuarioDestino( doitForGrupo(m.getMessageId().getGrupo(),d.getMessageDetailId().getUserDestino(),m.isChangeNicknameToRandom()));
			}
			
			//response.getMessagesDetailDTO()[i].setUserDestino(d.getMessageDetailId().getUserDestino().getUsername());
			
			r.getMessagesDetail()[i].setEstado(d.getState());

			i++;
		}
		return r;
	}
	
	
	
	public MessageDTO doit(Message m) throws ZiphraException {
		String idGrupo = m.getMessageId().getGrupo().getIdGrupo()+"";
		String idMessage = m.getMessageId().getIdMessage() +"";
		
		MessageDTO r = new MessageDTO();
		r.setIdGrupo(idGrupo+"");
		r.setText(m.getText()+"");
		r.setUsuarioCreacion(doitForGrupo(m.getMessageId().getGrupo(), m.getUserCreation(),m.isChangeNicknameToRandom()));
		r.setIdMessage(idMessage+"");
		r.setMessagesDetail(new MessageDetailDTO[1]);
		r.setSecretKeyPersonal(m.isSecretKeyPersonal());
		Set<MessageDetail> details = m.getMessagesDetail();
		r.setBlackMessage(m.isBlackMessage());
		r.setAnonimo(m.isAnonimo());
		r.setTimeMessage(m.getTimeMessage());
		r.setHideMessageReadState(m.isHideMessageState());
		r.setSystemMessage(m.isSystemMessage());
		r.setBlockResend(m.isBlockResend());
		r.setChangeNicknameToRandom(m.isChangeNicknameToRandom());
		
		if (m.getParentReply() != null) {
			r.setParentReply(new IdMessageDTO( 
					idGrupo+"",
					m.getParentReply().getMessageId().getIdMessage() +""
					));
			
		}

		if (m.getParentResend() != null) {
			r.setParentReply(new IdMessageDTO( 
					m.getParentResend().getMessageId().getGrupo().getIdGrupo() +"",
					m.getParentResend().getMessageId().getIdMessage() +""
					));
			
		}

		
		r.setMessagesDetail(new MessageDetailDTO[details.size()]);
		
		Media media = m.getMedia();
		MediaDTO mediaDTO = doit(media,false);
		r.setMedia(mediaDTO);
		

		int i=0;
		for ( MessageDetail d : details) {
			
			r.getMessagesDetail()[i] = new MessageDetailDTO();
			
			r.getMessagesDetail()[i].setIdGrupo(idGrupo+"");
			r.getMessagesDetail()[i].setIdMessage(idMessage);
			//			r.getMessagesDetailDTO()[i].setIdMessageDetail(d.getMessageDetailId().getIdMessageDetail()+"");
			
//			if (d.getMessageDetailId().getUserDestino().getIdUser() == m.getUserCreation().getIdUser()) {
//				r.getMessagesDetailDTO()[i].setUsuarioDestino(r.getUsuarioCreacion());
//			}else {
				r.getMessagesDetail()[i].setUsuarioDestino( doitForGrupo(m.getMessageId().getGrupo(),d.getMessageDetailId().getUserDestino(),m.isChangeNicknameToRandom()));
//			}
			
			//response.getMessagesDetailDTO()[i].setUserDestino(d.getMessageDetailId().getUserDestino().getUsername());
			
			r.getMessagesDetail()[i].setEstado(d.getState());

			i++;
		}
		return r;
	}

	

	public MessageDTO doitAnonimoToSend(Message m, Long idUser) throws ProcessException {
		MessageDTO r = new MessageDTO();
		r.setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
		r.setText(m.getText());
		//r.setUserDTOCreation();
		r.setIdMessage(m.getMessageId().getIdMessage()+"");
		r.setMessagesDetail(new MessageDetailDTO[1]);
		Set<MessageDetail> details = m.getMessagesDetail();
		r.setBlackMessage(m.isBlackMessage());
		r.setAnonimo(m.isAnonimo());
		r.setTimeMessage(m.getTimeMessage());
		r.setHideMessageReadState(m.isHideMessageState());
		r.setSystemMessage(m.isSystemMessage());
		r.setSecretKeyPersonal(m.isSecretKeyPersonal());
		r.setBlockResend(m.isBlockResend());
		r.setMessagesDetail(new MessageDetailDTO[1]);
		
		Media media = m.getMedia();
		MediaDTO mediaDTO = doit(media,true);
		r.setMedia(mediaDTO);
		

		int i=0;
		for ( MessageDetail d : details) {
			
			if (d.getMessageDetailId().getUserDestino().getIdUser().equals(idUser)){
				r.getMessagesDetail()[i] = new MessageDetailDTO();
				
				r.getMessagesDetail()[i].setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
				r.getMessagesDetail()[i].setIdMessage(m.getMessageId().getIdMessage()+"");
				//r.getMessagesDetailDTO()[i].setIdMessageDetail(d.getMessageDetailId().getIdMessageDetail()+"");
				

				r.getMessagesDetail()[i].setUsuarioDestino( doitForGrupo(d.getMessageDetailId().getUserDestino()));
				
				//response.getMessagesDetailDTO()[i].setUserDestino(d.getMessageDetailId().getUserDestino().getUsername());
				
				r.getMessagesDetail()[i].setEstado(d.getState());
			}
			
		}
		return r;
	}
	public EncryptKeysDTO doit(EncryptKeys e) {
		EncryptKeysDTO r = new EncryptKeysDTO();
		r.setPrivateKey(e.getPrivateKey());
		r.setPublicKey(e.getPublicKey());
		return r;
	}
	
	public EncryptKeysDTO doitPublicKeyNoEncrypt(EncryptKeys e) {
		EncryptKeysDTO r = new EncryptKeysDTO();
		
		r.setPublicKeyNoEncrypt(e.getPublicKeyNoEncrypt());
		return r;
	}
	
	public EncryptKeys doit(EncryptKeysDTO e) {
		EncryptKeys r = new EncryptKeys();
		r.setPrivateKey(e.getPrivateKey());
		r.setPublicKey(e.getPublicKey());
		r.setPublicKeyNoEncrypt(e.getPublicKeyNoEncrypt());
		return r;
	}
	public AES doit(AESDTO e) {
		AES r = new AES();
		r.setSecretKeyAES(e.getSecretKeyAES().toString());
		r.setSaltAES(e.getSaltAES().toString());
		r.setIteration(e.getIteration().toString());

		return r;
	}


	public MessageDetailDTO doit(MessageDetail md) throws ZiphraException {
		MessageDetailDTO r = new MessageDetailDTO();
		
		r.setIdGrupo(md.getMessageDetailId().getMessage().getMessageId().getGrupo().getIdGrupo()+"");
		r.setIdMessage(md.getMessageDetailId().getMessage().getMessageId().getIdMessage()+"");
		r.setUsuarioDestino( doitForGrupo(md.getMessageDetailId().getMessage().getMessageId().getGrupo(), md.getMessageDetailId().getUserDestino(),md.getMessageDetailId().getMessage().isChangeNicknameToRandom()));
		
		
		
		if (md.isHideRead() && md.getState().equals(MessageState.DESTINY_READ) && md.getMessageDetailId().getUserDestino().getIdUser().equals(comps.requestHelper().getUsuarioId())) {
			r.setEstado( MessageState.DESTINY_DELIVERED);
			r.setHideRead(false);
		}else {
			r.setEstado(md.getState());
			r.setHideRead(md.isHideRead());
		}
		
		return r;
	}
	
	public MessageDetailDTO doitChangeState(MessageState state, boolean hideRead, String idGrupo, String idMessage, UsuarioDTO destino) throws NumberFormatException, ValidationException {
		MessageDetailDTO r = new MessageDetailDTO();
		
		r.setIdGrupo(idGrupo);
		r.setIdMessage(idMessage);
		r.setUsuarioDestino(destino);
		
		
		if (hideRead && state.equals(MessageState.DESTINY_READ) && Long.parseLong( destino.getIdUsuario()) != comps.requestHelper().getUsuarioId().longValue()) {
			r.setEstado( MessageState.DESTINY_DELIVERED);
			r.setHideRead(false);
		}else {
			r.setEstado(state);
			r.setHideRead(hideRead);
		}
		return r;
	}	
	public GrupoUserConf doit(GrupoUserConfDTO d) {

		GrupoUserConf r = new GrupoUserConf();
		r.setAnonimoAlways(d.getAnonimoAlways());
		r.setAnonimoRecived(d.isAnonimoRecived());
		r.setAnonimoRecivedMyMessage(d.isAnonimoRecivedMyMessage());
		r.setBlackMessageAttachMandatory(d.getBlackMessageAttachMandatory());
		r.setBlackMessageAttachMandatoryReceived(d.getBlackMessageAttachMandatoryReceived());
		r.setBlockResend(d.getBlockResend());
		r.setSecretKeyPersonalAlways(d.getExtraAesAlways());
		r.setTimeMessageAlways(d.getTimeMessageAlways());
		r.setTimeMessageSeconds(d.getTimeMessageSeconds());
		r.setGrupoUserConfId(new GrupoUserConfId());
		

		r.setBlockMediaDownload(d.getBlockMediaDownload());

		return r;
	}

	

	public void doit(Grupo grupo, GrupoGralConfDTO d) {
		
		grupo.getGralConf().setAnonimo(d.getAnonimo());


		grupo.getGralConf().setBlockAudioMessages(d.isBlockAudioMessages());
		grupo.getGralConf().setAudiochatMaxTime(d.getAudiochatMaxTime());
		grupo.getGralConf().setBlackMessageAttachMandatory(d.isBlackMessageAttachMandatory());
		grupo.getGralConf().setRandomNickname(d.isRandomNickname());

		grupo.getGralConf().setBlockMediaDownload(d.isBlockMediaDownload());

		grupo.getGralConf().setExtraEncrypt(d.getExtraEncrypt());
		grupo.getGralConf().setHideMessageDetails(d.isHideMessageDetails());
		grupo.getGralConf().setHideMessageReadState(d.isHideMessageReadState());
		grupo.getGralConf().setHideMemberList(d.isHideMemberList());
		grupo.getGralConf().setBlockResend(d.isBlockResend());
		grupo.getGralConf().setTimeMessageMandatory(d.isTimeMessageMandatory());
		grupo.getGralConf().setTimeMessageMaxTimeAllow(d.getTimeMessageMaxTimeAllow());
		
	
	}


	public void doit(Usuario usuario, MyAccountConfDTO d) {
		
		usuario.getMyAccountConf().setBlackMessageAttachMandatory(d.isBlackMessageAttachMandatory());
		usuario.getMyAccountConf().setBlackMessageAttachMandatoryReceived(d.isBlackMessageAttachMandatoryReceived());

		usuario.getMyAccountConf().setBlockMediaDownload(d.isBlockMediaDownload());
		usuario.getMyAccountConf().setHideMyMessageState(d.isHideMyMessageState());
		usuario.getMyAccountConf().setResend(d.isBlockResend());
		usuario.getMyAccountConf().setTimeMessageAlways(d.isTimeMessageAlways());
		usuario.getMyAccountConf().setTimeMessageDefaultTime(d.getTimeMessageDefaultTime());
		usuario.getMyAccountConf().setLoginSkip(d.isLoginSkip());	
	}
	public MyAccountConfDTO doit(MyAccountConf d) {
		MyAccountConfDTO r = new MyAccountConfDTO();
		r.setBlackMessageAttachMandatory(d.isBlackMessageAttachMandatory());
		r.setBlackMessageAttachMandatoryReceived (d.isBlackMessageAttachMandatoryReceived());

		r.setBlockMediaDownload(d.isBlockMediaDownload());
		r.setHideMyMessageState(d.isHideMyMessageState());
		r.setBlockResend(d.isResend());
		r.setTimeMessageAlways(d.isTimeMessageAlways());
		r.setTimeMessageDefaultTime(d.getTimeMessageDefaultTime());
		
		r.setLock(new LockDTO());
		r.getLock().setEnabled(d.getLock().isEnabled());
		r.getLock().setSeconds(d.getLock().getSeconds());
		
		r.setLoginSkip(d.isLoginSkip());
		return r;
	}	
	
	public SaveGrupoGralConfLockResponseDTO doit(Grupo c) {
		SaveGrupoGralConfLockResponseDTO g = new SaveGrupoGralConfLockResponseDTO();
		g.setIdGrupo(c.getIdGrupo()+"");
		g.setPassword(new GrupoGralConfPasswordDTO());
		g.getPassword().setEnabled(c.getPassword().isEnabled());
		g.getPassword().setExtraEncryptDefaultEnabled(c.getPassword().isExtraEncryptDefaultEnabled());
		g.getPassword().setDeleteExtraEncryptEnabled(c.getPassword().isDeleteExtraEncryptEnabled());		
		//g.getPassword().setPassword(c.getPassword().getPassword());
		g.getPassword().setPasswordExtraEncrypt(c.getPassword().getPasswordExtraEncrypt());
		
		g.setLock(new LockDTO());
		g.getLock().setEnabled(c.getLock().isEnabled());
		g.getLock().setSeconds(c.getLock().getSeconds());

		return g;
	}		
	
}
