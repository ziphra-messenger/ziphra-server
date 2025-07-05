package ar.ziphra.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.interfaces.IdGrupoInterface;

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
	@ZiphraId
	@ZiphraIdOrder
	private String idGrupo;
	
	@ZiphraIdExclude	
	private String name;

	private GrupoInvitationDTO grupoInvitationDTO;
	private UserForGrupoDTO userForGrupoDTO;
	private GrupoGralConfDTO gralConfDTO;
	private GrupoUserConfDTO userConfDTO;
	private MembersQuantityDTO membersQuantityDTO;
	
	@ZiphraIdExclude	
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
