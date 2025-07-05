package ar.ziphra.common.dto.servergralconf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SGCAuth {
	private String tokenType;
	private int tokenLenght;
}
