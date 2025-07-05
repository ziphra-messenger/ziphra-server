package ar.ziphra.common.dto.servergralconf;

import ar.ziphra.common.enumeration.RandomGeneratorType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SGCAESDTO {
	private int keyMinLenght;
	private int keyMaxLenght;
	
	private int saltMinLenght;
	private int saltMaxLenght;	
	
	private int iterationMinValue;
	private int iterationMaxValue;
	private String type="AES";
	private int bits;
	
	private RandomGeneratorType randomGeneratorType;
}
