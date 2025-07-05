package ar.ziphra.common.dto.request;

import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ZiphraIdExclude	
@AllArgsConstructor
@NoArgsConstructor
public class MyAccountNicknameRequestDTO {
	@ZiphraIdExclude	
	private String nickname;

}
