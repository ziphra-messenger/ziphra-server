package ar.ziphra.common.dto.servergralconf;

import ar.ziphra.common.enumeration.RandoGeneratorCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SGCInvitationCode {
	private MinMaxLenghtDTO lenght;
	private RandoGeneratorCase	caseSet;
}
