package com.privacity.common.dto.servergralconf;

import com.privacity.common.enumeration.RandoGeneratorCase;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SGCInvitationCode {
	private MinMaxLenghtDTO lenght;
	private RandoGeneratorCase	caseSet;
}
