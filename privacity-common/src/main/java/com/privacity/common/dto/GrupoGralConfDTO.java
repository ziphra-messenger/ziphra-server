package com.privacity.common.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.enumeration.ConfigurationStateEnum;
import com.privacity.common.enumeration.RulesConfEnum;
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
public class GrupoGralConfDTO implements IdGrupoInterface{

	@PrivacityId
	@PrivacityIdOrder
	
	private String idGrupo;
	
	@PrivacityIdExclude	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean blockResend;
	
	
	@PrivacityIdExclude
	private RulesConfEnum anonimo;
	
	@PrivacityIdExclude	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean timeMessageMandatory;
	
	@PrivacityIdExclude	
	private int timeMessageMaxTimeAllow;
	
	@PrivacityIdExclude	
	private boolean blockAudioMessages;
	
	@PrivacityIdExclude	
	private int audiochatMaxTime;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@PrivacityIdExclude	
	private boolean	 blackMessageAttachMandatory;
	
	@PrivacityIdExclude	
	private boolean blockMediaDownload;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@PrivacityIdExclude	
	private boolean randomNickname;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@PrivacityIdExclude	
	private boolean hideMessageDetails;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@PrivacityIdExclude	
	private boolean hideMessageReadState;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@PrivacityIdExclude	
	private boolean hideMemberList;
	
	@PrivacityIdExclude	
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
