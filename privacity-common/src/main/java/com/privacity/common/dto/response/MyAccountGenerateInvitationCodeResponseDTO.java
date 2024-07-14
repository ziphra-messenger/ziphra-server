package com.privacity.common.dto.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class MyAccountGenerateInvitationCodeResponseDTO implements Serializable {

	public MyAccountGenerateInvitationCodeResponseDTO() {
		super();
	}

	public MyAccountGenerateInvitationCodeResponseDTO(String invitationCode) {
		super();
		this.invitationCode = invitationCode;
	}

	private static final long serialVersionUID = -5160106805073036926L;
	
	public String invitationCode;
	
}
