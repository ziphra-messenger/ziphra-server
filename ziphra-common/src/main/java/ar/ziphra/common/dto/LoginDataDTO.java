package ar.ziphra.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.annotations.ZiphraIdOrder;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoginDataDTO implements Serializable{

	private static final long serialVersionUID = -4081347203080964793L;
	@ZiphraIdExclude	
	private String token;
	
	@ZiphraIdOrder
	@ZiphraId
	private String id;
	
	@JsonInclude(Include.NON_NULL)
	@ZiphraIdExclude	
	private String nickname;
	@ZiphraIdExclude	
	private String invitationCode;
	@ZiphraIdExclude	
	private String publicKey;
	
	private AESDTO sessionAESDTOWS;
	private AESDTO sessionAESDTOServerEncrypt;
	private MyAccountConfDTO myAccountGralConfDTO;
	
}
