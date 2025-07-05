package ar.ziphra.common.dto;

import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.enumeration.RulesConfEnum;

import lombok.Data;

@Data
public class MyAccountConfDTO {

	@ZiphraIdExclude	
	private boolean blockResend;
	
	@ZiphraIdExclude	
	private boolean timeMessageAlways;
	@ZiphraIdExclude	
	private long timeMessageDefaultTime;

	@ZiphraIdExclude	
	private boolean blackMessageAttachMandatory;
	private boolean blackMessageAttachMandatoryReceived;
	@ZiphraIdExclude	
	private boolean blockMediaDownload;
	@ZiphraIdExclude	
	private boolean hideMyMessageState;
	
	private LockDTO lock;
	@ZiphraIdExclude	
	private boolean loginSkip;

}
