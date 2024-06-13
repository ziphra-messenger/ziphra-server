package com.privacity.server.encrypt;

import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.UserForGrupoDTO;
import com.privacity.common.dto.UsuarioDTO;
import com.privacity.common.enumeration.GrupoRolesEnum;
import com.privacity.common.util.RandomGenerator;

@Service
public class PrivacityIdServicesMainTest {

	private final String AesKey="1";
	private final String AesSalt="1";
	private final int AesIteration=1;
	public Cipher decrypt;
	public Cipher encrypt;

	private long privacityIdOrderSeed;
	private int bitsEncrypt=128;
	
	public PrivacityIdServicesMainTest()

			 throws Exception {
		{
			
			privacityIdOrderSeed = RandomGenerator.betweenTwoNumber(1483647,7483647);
			//AesKey = randomGeneratorService.privacityIdAesSecretKey();
//			AesSalt = randomGeneratorService.privacityIdAesSecretSalt();
//			AesIteration = randomGeneratorService.privacityIdAesIteration();
			byte[] iv = new byte[16];
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec keySpec = new PBEKeySpec((AesKey).toCharArray(), (AesSalt).getBytes(), AesIteration, bitsEncrypt);
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
			KeySpec keySpec = new PBEKeySpec((AesKey).toCharArray(), (AesSalt).getBytes(), AesIteration, bitsEncrypt);
			SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
			SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
			encrypt=cipher;
		}
	}



	public String getAES(String data) {
		try {

			return Base64.getEncoder().encodeToString(encrypt.doFinal(data.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getAESDecrypt(String data) {
		
		
		if (data == null) return null;
		if (data == "") return "";
		////////System.out.println(data);

		try {

			return new String(decrypt.doFinal(Base64.getDecoder().decode(data)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public Object transformarEncriptarOut(Object g) throws IllegalAccessException,  NoSuchFieldException, SecurityException {
		

		if (g == null) return null;

		if ( g.getClass().getEnumConstants() != null) return null;
		
		if ("com.privacity.common.enumeration.ConfigurationStateEnum".equals(g.getClass().getName())){
			////////System.out.println(g.getClass().getName());
		}
		////////System.out.println(g.getClass().getName());


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

				if ( g.getClass().getDeclaredFields()[i].toString().contains("[]")){
					Object[] lista = (Object[])g.getClass().getDeclaredFields()[i].get(g);
					if (lista == null) return null;
					for ( int k = 0 ; k < lista.length ; k++) {
						transformarEncriptarOut(lista[k]);
					}	
				} else if ((g.getClass().getDeclaredFields()[i].getType().getName().contains("com.privacity.common"))) {

					if (g.getClass().getDeclaredFields()[i].get(g) != null){
						transformarEncriptarOut(g.getClass().getDeclaredFields()[i].get(g));	
					}

				}else {

					if ( g.getClass().getDeclaredFields()[i].isAnnotationPresent(PrivacityId.class)) {
						System.out.println(g.getClass().getDeclaredFields()[i]); 
						System.out.println(g.getClass().getDeclaredFields()[i].getName());

							
							
						if (g.getClass().getDeclaredFields()[i].get(g) != null){
								try {
								//	Long.parseLong(g.getClass().getDeclaredFields()[i].get(g)+"");
									
									
							g.getClass().getDeclaredFields()[i].set(g, this.getAES(g.getClass().getDeclaredFields()[i].get(g)+""));
							//g.getClass().getDeclaredFields()[i].set(g,"xxxxxxxxx");
				        } catch (NumberFormatException e) {
				         
				        	e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						
						////////System.out.println(g.getClass().getDeclaredFields()[i]); 


						}
					}



				} 



			}	
		}
		
		return g;
	}
	public Object transformarDesencriptarOut(Object g) throws IllegalAccessException,  NoSuchFieldException, SecurityException {
		if (g == null) return null;

		System.out.println(g.getClass().getName());

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

				if ( g.getClass().getDeclaredFields()[i].toString().contains("[]")){
					Object[] lista = (Object[])g.getClass().getDeclaredFields()[i].get(g);
					if (lista == null) return null;
					for ( int k = 0 ; k < lista.length ; k++) {
						transformarDesencriptarOut(lista[k]);
					}	
				} else if ((g.getClass().getDeclaredFields()[i].getType().getName().contains("com.privacity.common"))) {

					if (g.getClass().getDeclaredFields()[i].get(g) != null){
						transformarDesencriptarOut(g.getClass().getDeclaredFields()[i].get(g));
					}

				}else {

					if ( g.getClass().getDeclaredFields()[i].isAnnotationPresent(PrivacityId.class)) {
					System.out.println(g.getClass().getDeclaredFields()[i]); 


						
						if (g.getClass().getDeclaredFields()[i].get(g) != null){

							try {
								g.getClass().getDeclaredFields()[i].set(g, this.getAESDecrypt(g.getClass().getDeclaredFields()[i].get(g)+""));
							} catch (Exception e) {
								// TODO Auto-generated catch block
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


	public Object transformarEncriptarOutOrder(Object g) throws IllegalAccessException,  NoSuchFieldException, SecurityException {
		if (g == null) return null;

		if ( g.getClass().getEnumConstants() != null) return null;
		
		if ("com.privacity.common.enumeration.ConfigurationStateEnum".equals(g.getClass().getName())){
			////////System.out.println(g.getClass().getName());
		}
		////////System.out.println(g.getClass().getName());


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

				if ( g.getClass().getDeclaredFields()[i].toString().contains("[]")){
					Object[] lista = (Object[])g.getClass().getDeclaredFields()[i].get(g);
					if (lista == null) return null;
					for ( int k = 0 ; k < lista.length ; k++) {
						transformarEncriptarOutOrder(lista[k]);
					}	
				} else if ((g.getClass().getDeclaredFields()[i].getType().getName().contains("com.privacity.common"))) {

					if (g.getClass().getDeclaredFields()[i].get(g) != null){
						transformarEncriptarOutOrder(g.getClass().getDeclaredFields()[i].get(g));	
					}

				}else {

					if ( g.getClass().getDeclaredFields()[i].isAnnotationPresent(PrivacityIdOrder.class)) {
						////System.out.println(g.getClass().getDeclaredFields()[i]); 
						////System.out.println(g.getClass().getDeclaredFields()[i].getName());
						if (g.getClass().getDeclaredFields()[i].get(g) != null){
							try {
								Long newId = Long.parseLong(g.getClass().getDeclaredFields()[i].get(g).toString()) +privacityIdOrderSeed;
								newId = newId + 9288511499769L;
								g.getClass().getDeclaredFields()[i].set(g, newId+"" );
						//g.getClass().getDeclaredFields()[i].set(g,"xxxxxxxxx");
					} catch (Exception e) {
						////System.out.println(g.getClass().getDeclaredFields()[i]); 
						////System.out.println(g.getClass().getDeclaredFields()[i].getName());

						////System.out.println(g.getClass().getDeclaredFields()[i].get(g));
						
						e.printStackTrace();
					} 
							

						
						////////System.out.println(g.getClass().getDeclaredFields()[i]); 


						}
					}



				} 



			}	
		}
		
		return g;
	}
	public Object transformarDesencriptarOutOrder(Object g) throws IllegalAccessException,  NoSuchFieldException, SecurityException {
		if (g == null) return null;
		if ( g.getClass().getEnumConstants() != null) return null;
		////////System.out.println(g.getClass().getName());


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

				if ( g.getClass().getDeclaredFields()[i].toString().contains("[]")){
					Object[] lista = (Object[])g.getClass().getDeclaredFields()[i].get(g);
					if (lista == null) return null;
					for ( int k = 0 ; k < lista.length ; k++) {
						transformarDesencriptarOutOrder(lista[k]);
					}	
				} else if ((g.getClass().getDeclaredFields()[i].getType().getName().contains("com.privacity.common"))) {

					if (g.getClass().getDeclaredFields()[i].get(g) != null){
						transformarDesencriptarOutOrder(g.getClass().getDeclaredFields()[i].get(g));
					}

				}else {

					if ( g.getClass().getDeclaredFields()[i].isAnnotationPresent(PrivacityIdOrder.class)) {
		

						if (g.getClass().getDeclaredFields()[i].get(g) != null){

							try {
								
								
								Long newId = Long.parseLong(g.getClass().getDeclaredFields()[i].get(g).toString()) - privacityIdOrderSeed;
								newId = newId - 9288511499769L;
								
								g.getClass().getDeclaredFields()[i].set(g, newId+"" );
							} catch (Exception e) {
								// TODO Auto-generated catch block
								////System.out.println(g.getClass().getDeclaredFields()[i].get(g));
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

	public static void main(String...strings ) throws Exception {
		PrivacityIdServicesMainTest p = new PrivacityIdServicesMainTest();
	
		GrupoDTO[] arr = new GrupoDTO[1];
		
		String data="bVcK/lqjhMW/GeLyG/MGGg==";
		
		p.decrypt.doFinal(Base64.getDecoder().decode(data));
		
		   //idGrupo="155998";
		GrupoDTO o = new GrupoDTO();
		//o.setIdGrupo(idGrupo);
//		o.setName("name232");
//
////
//		o.setUserForGrupoDTO( new UserForGrupoDTO());
//		o.getUserForGrupoDTO().setIdGrupo("bVcK/lqjhMW/GeLyG/MGGg\u003d\u003d");
//		o.getUserForGrupoDTO().setUsuario(new UsuarioDTO());
//		//o.getUserForGrupoDTO().getUsuario().setRole(GrupoRolesEnum.ADMIN);
//		o.getUserForGrupoDTO().getUsuario().setIdUsuario("21313");
//		o.getUserForGrupoDTO().getUsuario().setNickname("feewfew");
//
//		arr[0]=o;
		//p.transformarEncriptarOut(arr);
		
		//System.out.println(o.toString());
		//p.transformarDesencriptarOut(arr);
		//System.out.println(o.toString());
		
		
		//System.out.println(p.transformarEncriptarOut(o).toString());
		//System.out.println(p.transformarEncriptarOut(o).toString());

		//System.out.println(p.transformarDesencriptarOut(o).toString());
	
		
	}
}    

