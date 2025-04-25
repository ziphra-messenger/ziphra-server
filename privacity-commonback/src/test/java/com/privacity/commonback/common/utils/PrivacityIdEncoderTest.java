package com.privacity.commonback.common.utils;

import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.common.exceptions.PrivacityException;

class PrivacityIdEncoderTest {

	
	void encryptIds() throws PrivacityException {
		


		UtilsString us = new UtilsString();

		

		ProtocoloDTO p = new ProtocoloDTO();
		p.setComponent(ProtocoloComponentsEnum.MESSAGE);
		p.setAction(ProtocoloActionsEnum.MESSAGE_SEND);
		
	//	p.setMessageDTO(getMessageDTO());
			


		//System.out.println(us.gsonPretty().toJson(m));
		

		
		//PrivacityIdEncoder pid = new PrivacityIdEncoder(new AESToUse(getAESDTO()));
		//pid.encryptIds(m);
		
		//System.out.println(us.gsonPretty().toJson(m));
	}

//	private AESDTO getAESDTO() {
//		
//		return Instancio.of(AESDTO.class) 
//				 .set(Select.field(AESDTO::getBitsEncrypt), (128+""))
//				 .set(Select.field(AESDTO::getIteration), (new Random()).nextInt(100)+"")
//				  .create();
//		
//	}
//	
//	private MessageDTO getMessageDTO() {
//		 MessageDTO m = Instancio.of(MessageDTO.class)
//				  .set(Select.field(MessageDTO::getIdMessage), (new Random()).nextLong()+"")
//				  .set(Select.field(MessageDTO::getIdGrupo), (new Random()).nextLong()+"")
//				  .set(Select.field(MessageDTO::getMessagesDetailDTO), (getMessageDetailDTO(5)))
//				  .set(Select.field(MessageDTO::getUsuarioCreacion), (getUsuarioDTO()))
//				  .ignore(Select.field(MessageDTO::getParentReply))
//				  .ignore(Select.field(MessageDTO::getParentResend))
//				  .ignore(Select.field(MessageDTO::getMediaDTO))
//				  .create();
//		 return m;
//	}
//	private UsuarioDTO getUsuarioDTO() {
//		
//		return Instancio.of(UsuarioDTO.class)
//				 .set(Select.field(UsuarioDTO::getIdUsuario), (new Random()).nextInt()+"")
//				 .ignore(Select.field(UsuarioDTO::getEncryptKeysDTO))
//				 
//				  .create();
//		
//	}
//	private MessageDetailDTO[] getMessageDetailDTO(int cantidad) {
//		
//		MessageDetailDTO[] r = new MessageDetailDTO[cantidad];
//		
//		for ( int i = 0; i < cantidad ;i++) {
//			r[i] = 		 Instancio.of(MessageDetailDTO.class)
//					  .set(Select.field(MessageDetailDTO::getIdMessage), (new Random()).nextLong()+"")
//					  .set(Select.field(MessageDetailDTO::getIdGrupo), (new Random()).nextLong()+"")
//					  .set(Select.field(MessageDetailDTO::getUsuarioDestino), (getUsuarioDTO()))
//					  .create();
//		}
//		
//		return r;
//		
//
//	}
}
