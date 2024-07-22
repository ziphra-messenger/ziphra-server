package com.privacity.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class AESAllDTO {
	public AESDTO sessionAESDTOServerIn;
	public AESDTO sessionAESDTOWS;
	public AESDTO sessionAESDTOServerOut;
}
