package com.privacity.server.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "user_password")
public class GrupoGralConfPassword implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2648617924882203314L;

	public GrupoGralConfPassword(Grupo grupo) {
		this.grupo=grupo;
	}
		
	@Id
	@OneToOne
	@ToString.Exclude	
	private Grupo grupo;
	
	private boolean extraEncryptDefaultEnabled;
	private boolean deleteExtraEncryptEnabled;
	
	private boolean enabled;
	private String password;
	private String passwordExtraEncrypt;

	@Override
	public String toString() {
		return "";
	}
}
