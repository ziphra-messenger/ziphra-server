package ar.ziphra.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.enumeration.MediaTypeEnum;
import ar.ziphra.common.interfaces.IdGrupoInterface;

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
	@ZiphraId
	@ZiphraIdOrder
	private String idGrupo;

	@ZiphraIdOrder

	private String idMessage;
	@ZiphraIdExclude	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean downloadable;
	@ZiphraIdExclude	
	private MediaTypeEnum mediaType;
	@ZiphraIdExclude	
	private byte miniatura[];
	@ZiphraIdExclude	
	private byte data[];
	@Override
	public String toString() {
		return "MediaDTO [idGrupo=" + idGrupo + ", idMessage=" + idMessage + ", downloadable=" + downloadable
				+ ", mediaType=" + mediaType + "]";
	}
}