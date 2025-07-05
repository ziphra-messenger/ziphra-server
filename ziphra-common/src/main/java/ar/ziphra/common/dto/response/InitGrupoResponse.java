package ar.ziphra.common.dto.response;

import ar.ziphra.common.dto.MessageDTO;
import ar.ziphra.common.dto.MessageDetailDTO;
import ar.ziphra.common.dto.UsuarioDTO;

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
