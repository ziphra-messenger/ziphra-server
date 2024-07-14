package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.enumeration.MediaTypeEnum;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.Data;

@Data
public class MediaDTO implements IdGrupoInterface{

//	@Id
//	@OneToOne
//    @JoinColumn(name = "id_grupo")
//	public Grupo grupo;
//	
	@PrivacityId
	@PrivacityIdOrder
	public String idGrupo;

	@PrivacityIdOrder
	public String idMessage;
	private boolean downloadable;

	public MediaTypeEnum mediaType;
	
    public byte miniatura[];
    public byte data[];
	@Override
	public String toString() {
		return "MediaDTO [idGrupo=" + idGrupo + ", idMessage=" + idMessage + ", downloadable=" + downloadable
				+ ", mediaType=" + mediaType + "]";
	}
}