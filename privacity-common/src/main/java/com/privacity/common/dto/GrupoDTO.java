package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.CUSTOM)

public class GrupoDTO implements IdGrupoInterface{
	@PrivacityId
	@PrivacityIdOrder
	private String idGrupo;
	
	@PrivacityIdExclude	
	private String name;

	private GrupoInvitationDTO grupoInvitationDTO;
	private UserForGrupoDTO userForGrupoDTO;
	private GrupoGralConfDTO gralConfDTO;
	private GrupoUserConfDTO userConfDTO;
	private MembersQuantityDTO membersQuantityDTO;
	
	@PrivacityIdExclude	
	private String alias;
	private LockDTO lock;
	private GrupoGralConfPasswordDTO password;

	@Override
	public String getIdGrupo() {
		// TODO Auto-generated method stub
		return idGrupo;
	}


	
	public Long convertIdGrupoToLong() {
		return Long.parseLong(idGrupo);
	}
    
    public GrupoDTO(String name) {
		super();
		this.name = name;
	}
	public GrupoDTO(Long id) {
		super();
		this.idGrupo = id+"";
	}	

	@JsonIgnore
	public boolean isGrupoInvitation() {
		if (grupoInvitationDTO == null) {
			return false;
		}
		return true;
	}
}
