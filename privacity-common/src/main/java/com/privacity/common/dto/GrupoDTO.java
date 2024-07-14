package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class GrupoDTO implements IdGrupoInterface{
	@PrivacityId
	@PrivacityIdOrder
	public String idGrupo;
	@JsonInclude(Include.NON_NULL)
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
	public GrupoDTO(Long id) {
		super();
		this.idGrupo = id+"";
	}	
	public boolean isGrupoInvitation() {
		if (grupoInvitationDTO == null) {
			return false;
		}
		return true;
	}
	
	//olds
    public Long convertIdGrupoToLong() {
		return Long.parseLong(idGrupo);
	}
	//public String nicknameForGrupo;
	@JsonInclude(Include.NON_NULL)
	public String alias;
	
	@JsonInclude(Include.NON_NULL)
	public LockDTO lock;
	
	@JsonInclude(Include.NON_NULL)
	public GrupoGralConfPasswordDTO password;

	@Override
	public String getIdGrupo() {
		// TODO Auto-generated method stub
		return idGrupo;
	}

	@Override
	public void setIdGrupo(String idGrupo) {
		this.idGrupo=idGrupo;
		
	}	
}
