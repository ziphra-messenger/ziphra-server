package ar.ziphra.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.dto.GrupoGralConfPasswordDTO;
import ar.ziphra.common.dto.LockDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SaveGrupoGralConfLockResponseDTO {

	@ZiphraId
	@ZiphraIdOrder
	@JsonInclude(Include.NON_NULL)
	private String idGrupo;
	
	private GrupoGralConfPasswordDTO password;
	private LockDTO lock;


}
