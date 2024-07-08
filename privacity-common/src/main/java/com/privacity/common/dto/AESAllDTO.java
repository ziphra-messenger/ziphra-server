package com.privacity.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AESAllDTO {
	public AESDTO sessionAESDTOServerIn;
	public AESDTO sessionAESDTOWS;
	public AESDTO sessionAESDTOServerOut;
}
