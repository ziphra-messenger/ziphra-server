package ar.ziphra.common.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.enumeration.ConfigurationStateEnum;
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
@JsonInclude(JsonInclude.Include.CUSTOM)
public class GrupoGralConfDTO implements IdGrupoInterface{

	@ZiphraId
	@ZiphraIdOrder
	
	private String idGrupo;
	
	@ZiphraIdExclude	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean blockResend;
	
	
	@ZiphraIdExclude
	private RulesConfEnum anonimo;
	
	@ZiphraIdExclude	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean timeMessageMandatory;
	
	@ZiphraIdExclude	
	private int timeMessageMaxTimeAllow;
	
	@ZiphraIdExclude	
	private boolean blockAudioMessages;
	
	@ZiphraIdExclude	
	private int audiochatMaxTime;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@ZiphraIdExclude	
	private boolean	 blackMessageAttachMandatory;
	
	@ZiphraIdExclude	
	private boolean blockMediaDownload;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@ZiphraIdExclude	
	private boolean randomNickname;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@ZiphraIdExclude	
	private boolean hideMessageDetails;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@ZiphraIdExclude	
	private boolean hideMessageReadState;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@ZiphraIdExclude	
	private boolean hideMemberList;
	
	@ZiphraIdExclude	
	private ConfigurationStateEnum extraEncrypt;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoGralConfDTO other = (GrupoGralConfDTO) obj;
		return blockAudioMessages == other.blockAudioMessages && anonimo == other.anonimo
				&& audiochatMaxTime == other.audiochatMaxTime
				&& blackMessageAttachMandatory == other.blackMessageAttachMandatory

				&& blockMediaDownload == other.blockMediaDownload && extraEncrypt == other.extraEncrypt
				&& hideMemberList == other.hideMemberList && hideMessageDetails == other.hideMessageDetails
				&& hideMessageReadState == other.hideMessageReadState && Objects.equals(idGrupo, other.idGrupo)
				&& randomNickname == other.randomNickname && blockResend == other.blockResend
				&& timeMessageMandatory == other.timeMessageMandatory
				&& timeMessageMaxTimeAllow == other.timeMessageMaxTimeAllow;
	}

	@Override
	public int hashCode() {
		return Objects.hash(blockAudioMessages, anonimo, audiochatMaxTime, blackMessageAttachMandatory,
				blockMediaDownload,  extraEncrypt, hideMemberList,
				hideMessageDetails, hideMessageReadState, idGrupo, randomNickname, blockResend, timeMessageMandatory,
				timeMessageMaxTimeAllow);
	}


}
