package com.privacity.common.dto;

import lombok.Data;

@Data
public class MyAccountConfDTO {

	public boolean resend;
	public boolean timeMessageAlways;
	public long timeMessageDefaultTime;

	public boolean blackMessageAttachMandatory;

	public boolean downloadAttachAllowImage;
	
	public boolean hideMyMessageState;
	
	public LockDTO lock;
	public boolean loginSkip;



}
