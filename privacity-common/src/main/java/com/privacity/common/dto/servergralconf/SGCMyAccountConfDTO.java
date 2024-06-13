package com.privacity.common.dto.servergralconf;

import com.privacity.common.dto.LockDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SGCMyAccountConfDTO {
	private LockDTO lock;
	private boolean resend;
	private boolean blackMessageAttachMandatory;
	private boolean downloadAttachAllowImage;
	private boolean hideMyMessageState;
	private boolean hideMyInDetails;
	private boolean timeMessageAlways;
	private long timeMessageDefaultTime;
	private int audioMaxTimeInSeconds;
	private SGCInvitationCode invitationCode;
	
	private boolean loginSkip;

}

