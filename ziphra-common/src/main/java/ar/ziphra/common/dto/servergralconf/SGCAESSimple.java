package ar.ziphra.common.dto.servergralconf;

import lombok.Data;

@Data
public class SGCAESSimple {
	private int iteration=1;
	private int bits=128;
	private String type="AES";
}
