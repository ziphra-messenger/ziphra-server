package com.privacity.common.dto.response;

import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.MessageDetailDTO;
import com.privacity.common.dto.UsuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitGrupoResponse {

	private UsuarioDTO[] usersDTO;
	private MessageDTO[] messagesDTO;
	private MessageDetailDTO[] messagesDetailState;
	
}
