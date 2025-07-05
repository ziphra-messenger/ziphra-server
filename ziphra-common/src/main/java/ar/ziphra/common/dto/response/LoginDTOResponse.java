package ar.ziphra.common.dto.response;

import java.io.Serializable;

import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.dto.AESDTO;
import ar.ziphra.common.dto.LoginDataDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTOResponse implements Serializable {

	private static final long serialVersionUID = -2374084006427173088L;
	
	private AESDTO sessionAESDTO;
	@ZiphraIdExclude	
	private String privateKey;
	
	private LoginDataDTO loginDataDTO;

}
