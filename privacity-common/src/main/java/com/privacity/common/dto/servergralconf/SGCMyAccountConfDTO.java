package com.privacity.common.dto.servergralconf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.dto.LockDTO;
import com.privacity.common.enumeration.RulesConfEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.CUSTOM)
public class SGCMyAccountConfDTO {
	@JsonInclude(Include.NON_EMPTY)
	private LockDTO lock;
	@JsonInclude(Include.NON_DEFAULT)
	private boolean resend;
	@JsonInclude(Include.NON_DEFAULT)
	private boolean blackMessageAttachMandatory=false;
	@JsonInclude(Include.NON_DEFAULT)
	private boolean blackMessageAttachMandatoryReceived=false;
	@JsonInclude(Include.NON_DEFAULT)
	private boolean downloadAttachAllowImage;
	@JsonInclude(Include.NON_DEFAULT)
	private boolean hideMyMessageState;
	@JsonInclude(Include.NON_DEFAULT)
	private boolean hideMyInDetails;
	@JsonInclude(Include.NON_DEFAULT)
	private boolean timeMessageAlways;
	@JsonInclude(Include.NON_DEFAULT)
	private long timeMessageDefaultTime;
	@JsonInclude(Include.NON_DEFAULT)
	private int audioMaxTimeInSeconds;
	@JsonInclude(Include.NON_EMPTY)
	private SGCInvitationCode invitationCode;
	@JsonInclude(Include.NON_DEFAULT)
	private boolean loginSkip;

}

