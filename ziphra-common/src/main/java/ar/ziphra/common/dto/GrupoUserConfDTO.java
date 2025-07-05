
package ar.ziphra.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.enumeration.RulesConfEnum;
import ar.ziphra.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GrupoUserConfDTO implements IdGrupoInterface{

	@ZiphraId
	@ZiphraIdOrder
	@JsonInclude(Include.NON_NULL)
	public String idGrupo;
	
	@ZiphraId
	@ZiphraIdOrder
	@JsonInclude(Include.NON_NULL)
	public String idUsuario; 


	@ZiphraIdExclude	
	public RulesConfEnum extraAesAlways;
	@ZiphraIdExclude	
	public RulesConfEnum blackMessageAttachMandatory;
	@ZiphraIdExclude	
	public RulesConfEnum timeMessageAlways;
	@ZiphraIdExclude	
	public RulesConfEnum anonimoAlways;
	@ZiphraIdExclude	
	public RulesConfEnum blockResend;
	@ZiphraIdExclude	
	public Integer timeMessageSeconds;
	@ZiphraIdExclude	
	public RulesConfEnum blackMessageAttachMandatoryReceived;
	@ZiphraIdExclude	
	public boolean anonimoRecived;
	@ZiphraIdExclude	
	private boolean anonimoRecivedMyMessage;
	@ZiphraIdExclude	
	public RulesConfEnum blockMediaDownload;


	

}