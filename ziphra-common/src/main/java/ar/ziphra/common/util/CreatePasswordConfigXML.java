package ar.ziphra.common.util;

import java.io.StringReader;

import ar.ziphra.common.model.Root;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

public class CreatePasswordConfigXML {


	public static void main(String[] args) throws Exception {

	    String xmlString = "<resources>\r\n"
	    		+ "<string name=\"	validation.rule.lenght.mandatory.min					\">			ValidationRuleLenghtMandatoryMin			</string>\r\n"
	    		+ "<string name=\"	validation.rule.lenght.mandatory.max			\">			ValidationRuleLenghtMandatoryMax			</string>\r\n"
	    		+ "<string name=\"	validation.rule.lenght.sugerencia.min.enabled					\">			ValidationRuleLenghtSugerenciaMinEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.lenght.sugerencia.min					\">			ValidationRuleLenghtSugerenciaMin			</string>\r\n"
	    		+ "<string name=\"	validation.rule.uppercase.mandatory.enabled			\">			ValidationRuleUppercaseMandatoryEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.uppercase.mandatory.quantity			\">			ValidationRuleUppercaseMandatoryQuantity			</string>\r\n"
	    		+ "<string name=\"	validation.rule.uppercase.sugerencia.enabled			\">			ValidationRuleUppercaseSugerenciaEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.uppercase.sugerencia.quantity			\">			ValidationRuleUppercaseSugerenciaQuantity			</string>\r\n"
	    		+ "<string name=\"	validation.rule.lowercase.mandatory.enabled			\">			ValidationRuleLowercaseMandatoryEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.lowercase.mandatory.quantity			\">			ValidationRuleLowercaseMandatoryQuantity			</string>\r\n"
	    		+ "<string name=\"	validation.rule.lowercase.sugerencia.enabled			\">			ValidationRuleLowercaseSugerenciaEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.lowercase.sugerencia.quantity			\">			ValidationRuleLowercaseSugerenciaQuantity			</string>\r\n"
	    		+ "<string name=\"	validation.rule.especialchar.mandatory.enabled			\">			ValidationRuleEspecialcharMandatoryEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.especialchar.mandatory.quantity			\">			ValidationRuleEspecialcharMandatoryQuantity			</string>\r\n"
	    		+ "<string name=\"	validation.rule.especialchar.sugerencia.enabled			\">			ValidationRuleEspecialcharSugerenciaEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.especialchar.sugerencia.quantity			\">			ValidationRuleEspecialcharSugerenciaQuantity			</string>\r\n"
	    		+ "<string name=\"	validation.rule.digit.number.mandatory.enabled			\">			ValidationRuleDigitNumberMandatoryEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.digit.number.mandatory.quantity			\">			ValidationRuleDigitNumberMandatoryQuantity			</string>\r\n"
	    		+ "<string name=\"	validation.rule.digit.number.sugerencia.enabled			\">			ValidationRuleDigitNumberSugerenciaEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.digit.number.sugerencia.quantity			\">			ValidationRuleDigitNumberSugerenciaQuantity			</string>\r\n"
	    		+ "<string name=\"	validation.rule.repeat.char.restrict.enabled			\">			ValidationRuleRepeatCharRestrictEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.repeat.char.restrict.quantity			\">			ValidationRuleRepeatCharRestrictQuantity			</string>\r\n"
	    		+ "<string name=\"	validation.rule.repeat.char.sugerencia.enabled			\">			ValidationRuleRepeatCharSugerenciaEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.repeat.char.sugerencia.quantity			\">			ValidationRuleRepeatCharSugerenciaQuantity			</string>\r\n"
	    		+ "<string name=\"	validation.rule.whitespace.restrict.enabled			\">			ValidationRuleWhitespaceRestrictEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.whitespace.sugerencia.enabled			\">			ValidationRuleWhitespaceSugerenciaEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.username.restrict.enabled			\">			ValidationRuleUsernameRestrictEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.username.sugerencia.enabled			\">			ValidationRuleUsernameSugerenciaEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.nickname.restrict.enabled			\">			ValidationRuleNicknameRestrictEnabled			</string>\r\n"
	    		+ "<string name=\"	validation.rule.nickname.sugerencia.enabled			\">			ValidationRuleNicknameSugerenciaEnabled			</string>\r\n"
	    		+ "</resources>";
	    {     

	        JAXBContext jaxbContext;
	         
	       xmlString= xmlString.replace("\t","");
	          jaxbContext = JAXBContext.newInstance(Root.class);
	          Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	          Root employee = (Root) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
	        //  System.out.println(employee);
	          //\t\t\t@
	      
	          String preProp="password.usuarioregistration.";
	          String preVar="passwordUsuarioRegistration_";
	          for (int i = 0 ; i < employee.getString().length; i++) {
	        	 // System.out.println(employee.getString()[i].string + "\t\t\t@");
	        	  System.out.println("@Value(\"${"+preProp +employee.getString()[i].name + "}\")") ; 
	        	 
	        			  if (employee.getString()[i].string.contains("nabled")) {
	        				  System.out.println("private boolean "+ preVar +Character.toLowerCase(employee.getString()[i].string.charAt(0)) + employee.getString()[i].string.substring(1) + ";");
	        			  }else {
	        				  System.out.println("private int " + preVar + Character.toLowerCase(employee.getString()[i].string.charAt(0)) + employee.getString()[i].string.substring(1) + ";");
	        			  }
	        			  
	          }
	}
	    {
		        JAXBContext jaxbContext;
			       
			       xmlString= xmlString.replace("\t","");
			       xmlString= xmlString.replace("ValidationRule","");
			          jaxbContext = JAXBContext.newInstance(Root.class);
			          Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			          Root employee = (Root) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
	         
	          
	          for (int i = 0 ; i < employee.getString().length; i++) {
	        	 // System.out.println(employee.getString()[i].string + "\t\t\t@");
	        	  //System.out.println("@Value(\"${"+preProp +employee.getString()[i].name + "}\")") ; 
	        	  
	        	  		String sh = Character.toLowerCase(employee.getString()[i].string.charAt(0)) + employee.getString()[i].string.substring(1);
	        	  
	        			  if (employee.getString()[i].string.contains("nabled")) {
	        				  System.out.println("private boolean "+sh + ";");
	        			  }else {
	        				  System.out.println("private int " + sh + ";");
	        			  }
	        			  
	          }

	    }
	    
	    
	    {     

	        JAXBContext jaxbContext;
	         
	       xmlString= xmlString.replace("\t","");
	          jaxbContext = JAXBContext.newInstance(Root.class);
	          Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	          Root employee = (Root) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
	        //  System.out.println(employee);
	          //\t\t\t@
	      
	          String preProp="password.usuarioregistration.";
	          String preVar="passwordUsuarioRegistration_";
	          for (int i = 0 ; i < employee.getString().length; i++) {
	        	 // System.out.println(employee.getString()[i].string + "\t\t\t@");
	        	 // System.out.println("@Value(\"${"+preProp +employee.getString()[i].name + "}\")") ; 
	        	 
	        				 System.out.println("set" + employee.getString()[i].string.replace("ValidationRule","")+ "("  + preVar + "validationRule"+ employee.getString()[i].string + ").");
	        			  
	          }
	}
	    
	    
	    {     

	        JAXBContext jaxbContext;
	         
	       xmlString= xmlString.replace("\t","");
	          jaxbContext = JAXBContext.newInstance(Root.class);
	          Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	          Root employee = (Root) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
	        //  System.out.println(employee);
	          //\t\t\t@
	      
	          String preProp="password.usuarioregistration.";
	          String preVar="passwordUsuarioRegistration_";
	          for (int i = 0 ; i < employee.getString().length; i++) {
	        	 // System.out.println(employee.getString()[i].string + "\t\t\t@");
	        	  System.out.println("@Value(\"${"+preProp +employee.getString()[i].name + "}\")") ; 
	        	 
	        			  if (employee.getString()[i].string.contains("nabled")) {
	        				  System.out.println("private boolean "+ preVar +Character.toLowerCase(employee.getString()[i].string.charAt(0)) + employee.getString()[i].string.substring(1) + ";");
	        			  }else {
	        				  System.out.println("private int " + preVar + Character.toLowerCase(employee.getString()[i].string.charAt(0)) + employee.getString()[i].string.substring(1) + ";");
	        			  }
	        			  
	          }
	}
	    {
		        JAXBContext jaxbContext;
			       
			       xmlString= xmlString.replace("\t","");
			       xmlString= xmlString.replace("ValidationRule","");
			          jaxbContext = JAXBContext.newInstance(Root.class);
			          Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			          Root employee = (Root) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
	         
			          String preProp="password.usuarioregistration.";
	          for (int i = 0 ; i < employee.getString().length; i++) {
	        	 // System.out.println(employee.getString()[i].string + "\t\t\t@");
	        	  //System.out.println("@Value(\"${"+preProp +employee.getString()[i].name + "}\")") ; 
	        	  
	        	  		String sh = employee.getString()[i].name;
	        	  
	        			  if (employee.getString()[i].string.contains("nabled")) {
	        				  System.out.println(preProp+sh + "=true");
	        			  }else {
	        				  System.out.println(preProp+sh + "=1");
	        			  }
	        			  
	          }

	    }
	}

}