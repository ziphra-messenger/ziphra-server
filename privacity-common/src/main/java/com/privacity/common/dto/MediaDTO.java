package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;

import lombok.Data;

@Data
public class MediaDTO {

//	@Id
//	@OneToOne
//    @JoinColumn(name = "id_grupo")
//	public Grupo grupo;
//	
	@PrivacityId
	public String idGrupo;

	@PrivacityIdOrder
	public String idMessage;
	private boolean downloadable;
    public byte data[];
    
    public byte miniatura[];
    
	public String mediaType;
	@Override
	public String toString() {
		return "MediaDTO [idGrupo=" + idGrupo + ", idMessage=" + idMessage + ", mediaType=" + mediaType + "]";
	}



    
}