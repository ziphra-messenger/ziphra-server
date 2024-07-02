package com.privacity.server.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "aes")
@AllArgsConstructor
@NoArgsConstructor
public class AES implements Serializable{
	

	private static final long serialVersionUID = -4076483467987157286L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(length=4096, name = "secret_key_aes")
	private String secretKeyAES; 

	@Column(length=4096, name = "salt_aes")
	private String saltAES;

	@Column(length=4096, name = "iteration")
	private String iteration;
	
	@Override
	public String toString() {
		return "AES [id=" + id + ", secretKeyAES=" + secretKeyAES.length() + ", saltAES=" + saltAES.length() + ", iteration=" + iteration.length()
				+ "]";
	}
	
}
