package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GrupoDTO{
	@PrivacityId
	
	public String idGrupo;
	public String name;

	@JsonInclude(Include.NON_NULL)
	public GrupoInvitationDTO grupoInvitationDTO;
	@JsonInclude(Include.NON_NULL)
	public UserForGrupoDTO userForGrupoDTO;
	@JsonInclude(Include.NON_NULL)
	public GrupoGralConfDTO gralConfDTO;
	@JsonInclude(Include.NON_NULL)
	public GrupoUserConfDTO userConfDTO;
	    
	@JsonInclude(Include.NON_NULL)
	public int membersOnLine=0;
	
	public GrupoDTO(String name) {
		super();
		this.name = name;
	}
	
	public boolean isGrupoInvitation() {
		if (grupoInvitationDTO == null) {
			return false;
		}
		return true;
	}
	
	//olds
	
	//public String nicknameForGrupo;
	public String alias;
	
	@JsonInclude(Include.NON_NULL)
	public LockDTO lock;
	
	@JsonInclude(Include.NON_NULL)
	public GrupoGralConfPasswordDTO password;	
}
