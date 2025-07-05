package ar.ziphra.common.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
public class MyAccountGenerateInvitationCodeResponseDTO implements Serializable {



	private static final long serialVersionUID = -5160106805073036926L;
	@ZiphraIdExclude	
	private String invitationCode;
	
}
