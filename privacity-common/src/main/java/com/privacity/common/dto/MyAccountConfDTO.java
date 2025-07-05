package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.enumeration.RulesConfEnum;

import lombok.Data;

@Data
public class MyAccountConfDTO {

	@PrivacityIdExclude	
	private boolean blockResend;
	
	@PrivacityIdExclude	
	private boolean timeMessageAlways;
	@PrivacityIdExclude	
	private long timeMessageDefaultTime;

	@PrivacityIdExclude	
	private boolean blackMessageAttachMandatory;
	private boolean blackMessageAttachMandatoryReceived;
	@PrivacityIdExclude	
	private boolean blockMediaDownload;
	@PrivacityIdExclude	
	private boolean hideMyMessageState;
	
	private LockDTO lock;
	@PrivacityIdExclude	
	private boolean loginSkip;

}
