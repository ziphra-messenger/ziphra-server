package com.privacity.common.dto.servergralconf;

public class PasswordFormat {
	
	private int minLenght;
	private int maxLenght;
	private boolean specialCharNeeded;
	private boolean oneNumberNeeded;
	private boolean oneLowerCaseNeeded;
	private boolean oneUpperCaseNeeded;
	private boolean allowSpace;
    boolean SPECIAL_CHAR_NEEDED = false;

    /*
    String ONE_DIGIT = "(?=.*[0-9])";
    String LOWER_CASE = "(?=.*[a-z])";
    String UPPER_CASE = "(?=.*[A-Z])";
    String SPECIAL_CHAR = SPECIAL_CHAR_NEEDED ? "(?=.*[@#$%^&+=])" : "";
    String NO_SPACE = "(?=\\S+$)";
*/
}
