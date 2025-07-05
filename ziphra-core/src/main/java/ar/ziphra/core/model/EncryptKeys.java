package ar.ziphra.core.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "encrypt_keys")
public class EncryptKeys implements Serializable{
	
	private static final long serialVersionUID = -4076483467987157286L;

	public static final Long  CONSTANT_ID_STARTS_AT=40000L;
   

	@Id
    private Long idEncryptKeys;
	
	@Lob
	@ToString.Exclude	
	private String publicKey; 
	@Lob
	@ToString.Exclude	
	private String privateKey;
	@ToString.Exclude	
	@Lob
	private String publicKeyNoEncrypt; 	
}
