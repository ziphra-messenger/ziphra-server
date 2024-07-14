package com.privacity.server.sessionmanager.services;

import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.dto.AESDTO;
import com.privacity.common.util.RandomGenerator;
import com.privacity.server.sessionmanager.util.MathBaseConverter;
import com.privacity.server.sessionmanager.util.MixBytesUtil;
import com.privacity.server.sessionmanager.util.MutateDigitUtil;

import lombok.Data;
import lombok.Getter;


@Data
public class PrivacityIdServices {
	private static final Logger log = Logger.getLogger(PrivacityIdServices.class.getCanonicalName());
	private Cipher decrypt;
	private Cipher encrypt;

	@Getter
	private long privacityIdOrderSeed;
	
	@Getter
	private MutateDigitUtil mutateDigitUtil;
	@Getter
	
	private MathBaseConverter mathBaseConverter;
	private MixBytesUtil mixBytesUtil;

	@Getter
	private long orderRamdomNumber;
	
	
	private final boolean encryptIds;
	
	private String aesKey;
	private String aesSalt;
	private int aesIteration;
	private int base;
	private AESDTO AESDTO;
	
	public PrivacityIdServices(boolean encryptIds, AESDTO aesDTO,  long privacityIdOrderSeed,long orderRamdomNumber,int base,
			 Map<String, String> porLetra ,  Map<String, String> porNro
			)throws Exception {
		{
			this.AESDTO = aesDTO;
			this.encryptIds=encryptIds;
			mixBytesUtil = new MixBytesUtil();
			this.base=base;
			mutateDigitUtil = new MutateDigitUtil(base);
			mutateDigitUtil.setPorLetra(porLetra);
			mutateDigitUtil.setPorNro(porNro);
			
			mathBaseConverter = new MathBaseConverter();
			this.privacityIdOrderSeed = privacityIdOrderSeed;
			this.orderRamdomNumber=orderRamdomNumber;
			this.aesKey = aesDTO.getSecretKeyAES();
			this.aesSalt = aesDTO.getSaltAES();
			this.aesIteration = Integer.parseInt( aesDTO.getIteration());
			
			build();
		}
	}
	
	public PrivacityIdServices(boolean encryptIds, AESDTO aesDTO )throws Exception {
		{
			this.AESDTO = aesDTO;
			this.encryptIds=encryptIds;
			mixBytesUtil = new MixBytesUtil();
			mutateDigitUtil = new MutateDigitUtil();
			mathBaseConverter = new MathBaseConverter();
			privacityIdOrderSeed = RandomGenerator.betweenTwoNumber(1483647,7483647);
			
			aesKey = aesDTO.getSecretKeyAES();
			aesSalt = aesDTO.getSaltAES();
			aesIteration = Integer.parseInt( aesDTO.getIteration());
			base= mutateDigitUtil.getBase();
			build();
		}
	}

	public void build()throws Exception {
		{
		byte[] iv = new byte[16];
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec keySpec = new PBEKeySpec((aesKey).toCharArray(), (aesSalt).getBytes(), aesIteration, Integer.parseInt( AESDTO.getBitsEncrypt()));
		SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
		SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
		decrypt = cipher;
		
	}
	{
		byte[] iv = new byte[16];
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec keySpec = new PBEKeySpec((aesKey).toCharArray(), (aesSalt).getBytes(), aesIteration, Integer.parseInt( AESDTO.getBitsEncrypt()));
		SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
		SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
		encrypt=cipher;
	}

	}

	public Object encryptIds(Object g) throws Exception {
		if (!encryptIds) return g;
		
		 //transformarEncriptarOutOrder(g);
		 transformarEncriptarOut(g);
		 
		 return g;
	}
	
	public Object decryptIds(Object g) throws Exception {
		if (!encryptIds) return g;
		
			transformarDesencriptarOut(g);
		// transformarDesencriptarOutOrder(g);
		 return g;
	}
	
	private String getAES(String obj, String data) {
		log.fine("Encr obj: " + obj + " entrada: " + data );

		
		try {
			byte[] encriptado = encrypt.doFinal(data.getBytes());
			String r = Base64.getEncoder().encodeToString(mixBytesUtil.mix(encriptado));
			log.fine("Encr obj: " + obj + " salida: " + r );
			return r; 
		} catch (Exception e) {
			e.printStackTrace();
			log.fine("Encr obj: " + obj + " salida: " + null + " error: " + e.getMessage() );
		}
		
		return null;
	}

	private String getAESDecrypt(String obj, String data) {
		
		log.fine("Decr obj: " + obj + " entrada: " + data );

		if (data == null) return null;
		if (data == "") return "";
		////////log.fine(data);

		try {
			String r = new String(decrypt.doFinal(mixBytesUtil.unmix(Base64.getDecoder().decode(data))));
			log.fine("Decr obj: " + obj + " salida: " + r );
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			log.fine("Decr obj: " + obj + " salida: " + null + " error: " + e.getMessage() );
		}
		return null;
	}


	private Object transformarEncriptarOut(Object g) throws IllegalAccessException,  NoSuchFieldException, SecurityException {
		
		
		if (g == null) return null;
		//log.fine(g.getClass().getName());
		if ( g.getClass().getEnumConstants() != null) return null;
		
		if ("com.privacity.common.enumeration.ConfigurationStateEnum".equals(g.getClass().getName())){
			////////log.fine(g.getClass().getName());
		}
		///////


		if(
				(g.getClass().getName().equals("java.util.List"))
				|| 
				(g.getClass().getName().equals("java.util.ArrayList"))
				)
		{
			
			List list = (List)g;
			for ( int k = 0 ; k < list.size() ; k++) {
				transformarEncriptarOut(list.get(k));
			}

		} else if ( g.getClass().isArray()){
			Object[] lista = (Object[])g;
			for ( int k = 0 ; k < lista.length ; k++) {
				transformarEncriptarOut(lista[k]);
			}				
		}
		else  {
			for ( int i = 0 ; i < g.getClass().getDeclaredFields().length ; i++) {

				if ( g.getClass().getDeclaredFields()[i].toString().contains("[]") && !g.getClass().getDeclaredFields()[i].toString().contains("byte[]")){
					Object[] lista = (Object[])g.getClass().getDeclaredFields()[i].get(g);
					if (lista == null) return null;
					for ( int k = 0 ; k < lista.length ; k++) {
						transformarEncriptarOut(lista[k]);
					}	
				} else if ((g.getClass().getDeclaredFields()[i].getType().getName().contains("com.privacity.common"))) {

					if (g.getClass().getDeclaredFields()[i].get(g) != null &&  !g.getClass().getDeclaredFields()[i].toString().contains("byte[]")){
						transformarEncriptarOut(g.getClass().getDeclaredFields()[i].get(g));	
					}

				}else {
					if ( g.getClass().getDeclaredFields()[i].isAnnotationPresent(PrivacityIdOrder.class)) {
						////log.fine(g.getClass().getDeclaredFields()[i]); 
						////log.fine(g.getClass().getDeclaredFields()[i].getName());
						if (g.getClass().getDeclaredFields()[i].get(g) != null){
							try {
								/*
								 * 
								 								
								String newIdS = this.mutateDigitUtil.unmutate(newId);
								log.fine("Desc Order obj: " + obj + " unmutate: " + newIdS );
								long newIdL = mathBaseConverter.convertirBase10(base, newIdS);
								log.fine("Desc Order obj: " + obj + " convertirBase10: " + newIdL );
								newIdL = newIdL - privacityIdOrderSeed - this.orderRamdomNumber;
								log.fine("Desc Order obj: " + obj + " seed-ramdom: " + newIdL );
								g.getClass().getDeclaredFields()[i].set(g, newIdL+"" );
								log.fine("Desc Order obj: " + obj + " salida: " + newIdL );
								
								 */
								String obj = g.getClass().getDeclaredFields()[i].getName();
								
								log.fine("Encr Order obj: " + obj + " entrada: " + g.getClass().getDeclaredFields()[i].get(g).toString() );
								
								Long newId = Long.parseLong(g.getClass().getDeclaredFields()[i].get(g).toString()) +privacityIdOrderSeed;
								newId = newId + this.orderRamdomNumber;
								log.fine("Encr Order obj: " + obj + " seed+ramdom: " + newId );
								String newIdS = mathBaseConverter.convertirBaseN(base, newId);
								log.fine("Encr Order obj: " + obj + " convertirBase "+ base + ": " + newIdS );
								newIdS = this.mutateDigitUtil.mutate(newIdS);
								log.fine("Encr Order obj: " + obj + " mutate: " + newIdS );
								g.getClass().getDeclaredFields()[i].set(g, newIdS );
								log.fine("Encr Order obj: " + obj + " salida: " + newIdS );
						//g.getClass().getDeclaredFields()[i].set(g,"xxxxxxxxx");
					} catch (Exception e) {
						////log.fine(g.getClass().getDeclaredFields()[i]); 
						////log.fine(g.getClass().getDeclaredFields()[i].getName());

						////log.fine(g.getClass().getDeclaredFields()[i].get(g));
						
						e.printStackTrace();
					} 
							

						
						////////log.fine(g.getClass().getDeclaredFields()[i]); 


						}
					}
					if ( g.getClass().getDeclaredFields()[i].isAnnotationPresent(PrivacityId.class)) {

						//log.fine(g.getClass().getDeclaredFields()[i].getName());

							
							
						if (g.getClass().getDeclaredFields()[i].get(g) != null){
//								try {
//									Long.parseLong(g.getClass().getDeclaredFields()[i].get(g)+"");
									
							g.getClass().getDeclaredFields()[i].set(g, this.getAES(g.getClass().getDeclaredFields()[i].getName(), 
									g.getClass().getDeclaredFields()[i].get(g)+""));
							//g.getClass().getDeclaredFields()[i].set(g,"xxxxxxxxx");
//				        } catch (NumberFormatException e) {
//				         
//				        	System.out.println ("YA ESTA ENCRIPTADO");
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} 
						
						////////log.fine(g.getClass().getDeclaredFields()[i]); 


						}
					}



				} 



			}	
		}
		
		return g;
	}
	private Object transformarDesencriptarOut(Object g) throws IllegalAccessException,  NoSuchFieldException, SecurityException {
		

		
		if (g == null) return null;
		log.fine(g.getClass().getName());
		if ( g.getClass().getEnumConstants() != null){
			return null;
		}
		
		if(
				(g.getClass().getName().equals("java.util.List"))
				|| 
				(g.getClass().getName().equals("java.util.ArrayList"))
				)
		{
			List list = (List)g;
			for ( int k = 0 ; k < list.size() ; k++) {
				transformarDesencriptarOut(list.get(k));
			}

		} else if ( g.getClass().isArray()){
			Object[] lista = (Object[])g;
			for ( int k = 0 ; k < lista.length ; k++) {
				transformarDesencriptarOut(lista[k]);
			}				
		}
		else  {
			for ( int i = 0 ; i < g.getClass().getDeclaredFields().length ; i++) {

				if ( g.getClass().getDeclaredFields()[i].toString().contains("[]") && !g.getClass().getDeclaredFields()[i].toString().contains("byte[]")){					Object[] lista = (Object[])g.getClass().getDeclaredFields()[i].get(g);
					if (lista == null) return null;
					for ( int k = 0 ; k < lista.length ; k++) {
						transformarDesencriptarOut(lista[k]);
					}	
				} else if ((g.getClass().getDeclaredFields()[i].getType().getName().contains("com.privacity.common"))) {

					if (g.getClass().getDeclaredFields()[i].get(g) != null &&  !g.getClass().getDeclaredFields()[i].toString().contains("byte[]")){
						transformarDesencriptarOut(g.getClass().getDeclaredFields()[i].get(g));
					}

				}else {

					if ( g.getClass().getDeclaredFields()[i].isAnnotationPresent(PrivacityId.class)) {
						////////log.fine(g.getClass().getDeclaredFields()[i]); 


						
						if (g.getClass().getDeclaredFields()[i].get(g) != null){

//							try {
								g.getClass().getDeclaredFields()[i].set(g, this.getAESDecrypt(g.getClass().getDeclaredFields()[i].getName(), g.getClass().getDeclaredFields()[i].get(g)+""));
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							} 
							//g.getClass().getDeclaredFields()[i].set(g,"xxxxxxxxx");

						}


						
					}
					if ( g.getClass().getDeclaredFields()[i].isAnnotationPresent(PrivacityIdOrder.class)) {
						

						if (g.getClass().getDeclaredFields()[i].get(g) != null){

							try {
								String obj = g.getClass().getDeclaredFields()[i].getName();
							

								String newId = g.getClass().getDeclaredFields()[i].get(g).toString() ;
								log.fine("Desc Order obj: " + obj + " entrada: " + newId );
								String newIdS = this.mutateDigitUtil.unmutate(newId);
								log.fine("Desc Order obj: " + obj + " unmutate: " + newIdS );
								long newIdL = mathBaseConverter.convertirBase10(base, newIdS);
								log.fine("Desc Order obj: " + obj + " convertirBase10: " + newIdL );
								newIdL = newIdL - privacityIdOrderSeed - this.orderRamdomNumber;
								log.fine("Desc Order obj: " + obj + " seed-ramdom: " + newIdL );
								g.getClass().getDeclaredFields()[i].set(g, newIdL+"" );
								log.fine("Desc Order obj: " + obj + " salida: " + newIdL );
							} catch (Exception e) {
								// TODO Auto-generated catch block
								////log.fine(g.getClass().getDeclaredFields()[i].get(g));
								e.printStackTrace();
							} 
							//g.getClass().getDeclaredFields()[i].set(g,"xxxxxxxxx");

						}
						
					}



				} 



			}	
		}
		return g;
	}


	private Object transformarEncriptarOutOrder(Object g) throws IllegalAccessException,  NoSuchFieldException, SecurityException {

		if (g == null) return null;
		log.fine(g.getClass().getName());
		if ( g.getClass().getEnumConstants() != null) return null;
		
		if ("com.privacity.common.enumeration.ConfigurationStateEnum".equals(g.getClass().getName())){
			////////log.fine(g.getClass().getName());
		}
		////////log.fine(g.getClass().getName());


		if(
				(g.getClass().getName().equals("java.util.List"))
				|| 
				(g.getClass().getName().equals("java.util.ArrayList"))
				)
		{
			List list = (List)g;
			for ( int k = 0 ; k < list.size() ; k++) {
				transformarEncriptarOutOrder(list.get(k));
			}

		} else if ( g.getClass().isArray()){
			Object[] lista = (Object[])g;
			for ( int k = 0 ; k < lista.length ; k++) {
				transformarEncriptarOutOrder(lista[k]);
			}				
		}
		else  {
			for ( int i = 0 ; i < g.getClass().getDeclaredFields().length ; i++) {

				if ( g.getClass().getDeclaredFields()[i].toString().contains("[]") && !g.getClass().getDeclaredFields()[i].toString().contains("byte[]")){

					log.fine(g.getClass().getDeclaredFields()[i].getName());
					Object[] lista = (Object[])g.getClass().getDeclaredFields()[i].get(g);
					if (lista == null) return null;
					for ( int k = 0 ; k < lista.length ; k++) {
						transformarEncriptarOutOrder(lista[k]);
					}	
				} else if ((g.getClass().getDeclaredFields()[i].getType().getName().contains("com.privacity.common"))) {

					if (g.getClass().getDeclaredFields()[i].get(g) != null &&  !g.getClass().getDeclaredFields()[i].toString().contains("byte[]")){
						transformarEncriptarOutOrder(g.getClass().getDeclaredFields()[i].get(g));	
					}

				}else {

					if ( g.getClass().getDeclaredFields()[i].isAnnotationPresent(PrivacityIdOrder.class)) {
						////log.fine(g.getClass().getDeclaredFields()[i]); 
						////log.fine(g.getClass().getDeclaredFields()[i].getName());
						if (g.getClass().getDeclaredFields()[i].get(g) != null){
							try {
								/*
								 * 
								 								
								String newIdS = this.mutateDigitUtil.unmutate(newId);
								log.fine("Desc Order obj: " + obj + " unmutate: " + newIdS );
								long newIdL = mathBaseConverter.convertirBase10(base, newIdS);
								log.fine("Desc Order obj: " + obj + " convertirBase10: " + newIdL );
								newIdL = newIdL - privacityIdOrderSeed - this.orderRamdomNumber;
								log.fine("Desc Order obj: " + obj + " seed-ramdom: " + newIdL );
								g.getClass().getDeclaredFields()[i].set(g, newIdL+"" );
								log.fine("Desc Order obj: " + obj + " salida: " + newIdL );
								
								 */
								String obj = g.getClass().getDeclaredFields()[i].getName();
								
								log.fine("Encr Order obj: " + obj + " entrada: " + g.getClass().getDeclaredFields()[i].get(g).toString() );
								
								Long newId = Long.parseLong(g.getClass().getDeclaredFields()[i].get(g).toString()) +privacityIdOrderSeed;
								newId = newId + this.orderRamdomNumber;
								log.fine("Encr Order obj: " + obj + " seed+ramdom: " + newId );
								String newIdS = mathBaseConverter.convertirBaseN(base, newId);
								log.fine("Encr Order obj: " + obj + " convertirBase "+ base + ": " + newIdS );
								newIdS = this.mutateDigitUtil.mutate(newIdS);
								log.fine("Encr Order obj: " + obj + " mutate: " + newIdS );
								g.getClass().getDeclaredFields()[i].set(g, newIdS );
								log.fine("Encr Order obj: " + obj + " salida: " + newIdS );
						//g.getClass().getDeclaredFields()[i].set(g,"xxxxxxxxx");
					} catch (Exception e) {
						////log.fine(g.getClass().getDeclaredFields()[i]); 
						////log.fine(g.getClass().getDeclaredFields()[i].getName());

						////log.fine(g.getClass().getDeclaredFields()[i].get(g));
						
						e.printStackTrace();
					} 
							

						
						////////log.fine(g.getClass().getDeclaredFields()[i]); 


						}
					}



				} 



			}	
		}
		
		return g;
	}
	private Object transformarDesencriptarOutOrder(Object g) throws IllegalAccessException,  NoSuchFieldException, SecurityException {
	
		if (g == null) return null;
		log.fine(g.getClass().getName());
		if ( g.getClass().getEnumConstants() != null) return null;
		////////log.fine(g.getClass().getName());


		if(
				(g.getClass().getName().equals("java.util.List"))
				|| 
				(g.getClass().getName().equals("java.util.ArrayList"))
				)
		{
			List list = (List)g;
			for ( int k = 0 ; k < list.size() ; k++) {
				transformarDesencriptarOutOrder(list.get(k));
			}

		} else if ( g.getClass().isArray()){
			Object[] lista = (Object[])g;
			for ( int k = 0 ; k < lista.length ; k++) {
				transformarDesencriptarOutOrder(lista[k]);
			}				
		}
		else  {
			for ( int i = 0 ; i < g.getClass().getDeclaredFields().length ; i++) {

				if ( g.getClass().getDeclaredFields()[i].toString().contains("[]") && !g.getClass().getDeclaredFields()[i].toString().contains("byte[]")){
					Object[] lista = (Object[])g.getClass().getDeclaredFields()[i].get(g);
					if (lista == null) return null;
					for ( int k = 0 ; k < lista.length ; k++) {
						transformarDesencriptarOutOrder(lista[k]);
					}	
				} else if ((g.getClass().getDeclaredFields()[i].getType().getName().contains("com.privacity.common"))) {

					if (g.getClass().getDeclaredFields()[i].get(g) != null &&  !g.getClass().getDeclaredFields()[i].toString().contains("byte[]")){
						transformarDesencriptarOutOrder(g.getClass().getDeclaredFields()[i].get(g));
					}

				}else {

					if ( g.getClass().getDeclaredFields()[i].isAnnotationPresent(PrivacityIdOrder.class)) {
		

						if (g.getClass().getDeclaredFields()[i].get(g) != null){

							try {
								String obj = g.getClass().getDeclaredFields()[i].getName();
							

								String newId = g.getClass().getDeclaredFields()[i].get(g).toString() ;
								log.fine("Desc Order obj: " + obj + " entrada: " + newId );
								String newIdS = this.mutateDigitUtil.unmutate(newId);
								log.fine("Desc Order obj: " + obj + " unmutate: " + newIdS );
								long newIdL = mathBaseConverter.convertirBase10(base, newIdS);
								log.fine("Desc Order obj: " + obj + " convertirBase10: " + newIdL );
								newIdL = newIdL - privacityIdOrderSeed - this.orderRamdomNumber;
								log.fine("Desc Order obj: " + obj + " seed-ramdom: " + newIdL );
								g.getClass().getDeclaredFields()[i].set(g, newIdL+"" );
								log.fine("Desc Order obj: " + obj + " salida: " + newIdL );
							} catch (Exception e) {
								// TODO Auto-generated catch block
								////log.fine(g.getClass().getDeclaredFields()[i].get(g));
								e.printStackTrace();
							} 
							//g.getClass().getDeclaredFields()[i].set(g,"xxxxxxxxx");

						}
						
					}



				} 



			}	
		}
		return g;
	}
//
//	public static void main(String...strings ) throws Exception {
//		//PrivacityIdServices p = new PrivacityIdServices();
//	/*
//		GrupoDTO[] arr = new GrupoDTO[1];
//		
//		GrupoDTO o = new GrupoDTO();
//		o.setIdGrupo("1");
//		o.setName("name232");
//
//
//		o.setUsersForGrupoDTO(new UserForGrupoDTO[1]);
//		o.getUsersForGrupoDTO()[0]= new UserForGrupoDTO();
//		o.getUsersForGrupoDTO()[0].setIdGrupo("323312");
//		o.getUsersForGrupoDTO()[0].setRole("ROLE");
//		o.getUsersForGrupoDTO()[0].setUsuario(new UsuarioDTO());
//		o.getUsersForGrupoDTO()[0].getUsuario().setIdUsuario("21313");
//		o.getUsersForGrupoDTO()[0].getUsuario().setNickname("feewfew");
//
//		arr[0]=o;
//		p.transformarEncriptarOut(arr);
//		
//		log.fine(o.toString());
//		p.transformarDesencriptarOut(arr);
//		log.fine(o.toString());
//		*/
//		//////log.fine(p.getAES("hola"));
////		
////		////////log.fine(p.getAESDecrypt("CjDTyilsYWIPtzZ7k6dE9w=="));
////		
//		
//	}
}    

