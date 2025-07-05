package ar.ziphra.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.annotations.ZiphraIdOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.CUSTOM)
public class UsuarioDTO{
	@ZiphraId
	@ZiphraIdOrder
	@JsonInclude(Include.NON_NULL)
	private String idUsuario;
	@ZiphraIdExclude	
	@JsonInclude(Include.NON_NULL)
	private String nickname;
	@ZiphraIdExclude	
	@JsonInclude(Include.NON_NULL)
	private String alias;
	
//	public UserInvitationCodeDTO UserInvitationCodeDTO;
	@JsonInclude(Include.NON_NULL)
	private EncryptKeysDTO encryptKeysDTO;


}
