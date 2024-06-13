package com.privacity.common.util;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.privacity.common.dto.servergralconf.SGCAESDTO;
import com.privacity.common.enumeration.RandomGeneratorType;

public class RandomGenerator {
	

	public static String AESKey(SGCAESDTO a) {
		return generate(
				a.getRandomGeneratorType(),
				a.getKeyMinLenght(),
				a.getKeyMaxLenght());
	}
	
	public static String AESSalt(SGCAESDTO a) {
		return generate(
				a.getRandomGeneratorType(),				
				a.getSaltMinLenght(),
				a.getSaltMaxLenght());
	}

	
	public static int AESIteration(SGCAESDTO a) {
		return betweenTwoNumber(
				a.getIterationMinValue(),
				a.getIterationMaxValue());
	}		
	
	public static int betweenTwoNumber(int min, int max) {
		return (int) ((Math.random() * (
				max
				- max)) 
				+ min);
	}	
	
	public static String generate(RandomGeneratorType caseSetEnum, int min, int max) {
		if (RandomGeneratorType.ALPHANUMERIC.equals(caseSetEnum)) {
			return RandomStringUtils.randomAlphanumeric(new Random().nextInt(max-min) + min);	
		}else if (RandomGeneratorType.NUMERIC.equals(caseSetEnum)) {
			return RandomStringUtils.randomNumeric(new Random().nextInt(max-min) + min);
		}else if (RandomGeneratorType.ALPHABETIC.equals(caseSetEnum)) {
			return RandomStringUtils.randomAlphabetic(new Random().nextInt(max-min) + min);
		}else if (RandomGeneratorType.ASCII.equals(caseSetEnum)) {
			return RandomStringUtils.randomAscii(new Random().nextInt(max-min) + min);
		}
		return null;


		
	}		
}
