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

public class IdGrupoDTO implements IdGrupoInterface{
	@ZiphraId
	@ZiphraIdOrder
	private String idGrupo;
	
}
