package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.enumeration.MediaTypeEnum;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.CUSTOM)
public class MediaDTO implements IdGrupoInterface{

//	@Id
//	@OneToOne
//    @JoinColumn(name = "id_grupo")
//	public Grupo grupo;
//	
	@PrivacityId
	@PrivacityIdOrder
	private String idGrupo;

	@PrivacityIdOrder

	private String idMessage;
	@PrivacityIdExclude	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean downloadable;
	@PrivacityIdExclude	
	private MediaTypeEnum mediaType;
	@PrivacityIdExclude	
	private byte miniatura[];
	@PrivacityIdExclude	
	private byte data[];
	@Override
	public String toString() {
		return "MediaDTO [idGrupo=" + idGrupo + ", idMessage=" + idMessage + ", downloadable=" + downloadable
				+ ", mediaType=" + mediaType + "]";
	}
}