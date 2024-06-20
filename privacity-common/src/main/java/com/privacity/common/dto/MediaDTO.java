package com.privacity.common.dto;

import java.util.Arrays;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.enumeration.MediaTypeEnum;

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
    
	public MediaTypeEnum mediaType;

	@Override
	public String toString() {
		return "MediaDTO [idGrupo=" + idGrupo + ", idMessage=" + idMessage + ", downloadable=" + downloadable
				+ ", data=" + 
				
				 
				
				", miniatura=" + Arrays.toString(miniatura) + ", mediaType="
				+ mediaType + "]";
	}
	



    
}