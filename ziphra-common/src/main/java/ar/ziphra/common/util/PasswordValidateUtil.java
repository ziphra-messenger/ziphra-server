package ar.ziphra.common.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.passay.CharacterRule;
import org.passay.DictionaryRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordGenerator;
import org.passay.PasswordValidator;
import org.passay.RepeatCharacterRegexRule;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.UsernameRule;
import org.passay.WhitespaceRule;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;

import ar.ziphra.common.dto.servergralconf.PasswordRulesDTO;

public class PasswordValidateUtil {
//
//	public static void validate(PasswordRulesDTO rulesConfig, String username, String nickname, String pasword, String passwordConfirm) {
//		
//		List<Rule> rules = new ArrayList<>();     
//		
//		List<CharacterRule> rulesC = new ArrayList<>();        
//
//		if (rulesConfig.isLenghtEnabled()){
//			rules.add(new LengthRule(rulesConfig.getLenghtMin(), rulesConfig.getLenghtMax()));	
//		}else {
//			rules.add(new LengthRule(1,256));
//		}
//		        
//		if (rulesConfig.isWhitespaceRestrictEnabled()) {
//			rules.add(new WhitespaceRule());        
//		}
//		
//		if (rulesConfig.isUppercaseMandatoryEnabled()) {
//			rules.add(new CharacterRule(EnglishCharacterData.UpperCase, rulesConfig.getUppercaseMandatoryQuantity()));	
//		}
//        
//		if (rulesConfig.isLowercaseMandatoryEnabled()) {
//			rules.add(new CharacterRule(EnglishCharacterData.UpperCase, rulesConfig.getLowercaseMandatoryQuantity()));	
//		}
//
//		if (rulesConfig.isDigitNumberMandatoryEnabled()) {
//			rules.add(new CharacterRule(EnglishCharacterData.UpperCase, rulesConfig.getDigitNumberMandatoryQuantity()));	
//		}
//
//		if (rulesConfig.isEspecialCharMandatoryEnabled()) {
//			rules.add(new CharacterRule(EnglishCharacterData.UpperCase, rulesConfig.getEspecialCharMandatoryQuantity()));	
//		}
//        PasswordValidator validator = new PasswordValidator(rules); 
//        PasswordData password = new PasswordData(passwordIn1);
//        password.setUsername(username);
//        rules.add(new UsernameRule());
//	}
//	
//
//	public static void main(String[] args) throws Exception, IOException {
//		DictionaryRule dictionaryRule = new DictionaryRule(
//				  new WordListDictionary(WordLists.createFromReader(
//				    // Reader around the word list file
//				    new FileReader[] {new FileReader("passwordlist.txt")},
//				    // True for case sensitivity, false otherwise
//				    false,
//				    // Dictionaries must be sorted
//				    new ArraysSort())));
//		
//
//		
//		
//
//        
//        
//
//
//         rules.add(dictionaryRule);    
//
//        
//        rules.add(new RepeatCharacterRegexRule(3));
//        
//
//        RuleResult result = validator.validate(password);
//        
//        if(result.isValid()){
//            System.out.println("Password validated.");
//        }else{
//        	List<String> rr = validator.getMessages(result);
//        	for ( String tt : rr) {
//        		System.out.println(tt);	
//        	}
//            
//            
//
//        }
//        
//
//        PasswordGenerator passwordGenerator = new PasswordGenerator();
//
//        String passwordG = passwordGenerator.generatePassword(30, rulesC);
//       //String passwordG = passwordGenerator.generatePassword(10);
//       System.out.println(passwordG);
//	}
}
