package com.privacity.core.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
		})


public class Usuario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5694310689191434637L;

	public static final Long  CONSTANT_ID_STARTS_AT=150000L;

	@Id
	private Long idUser;

	
	private String username;

	private String nickname;
	
	
    @OneToOne(mappedBy = "usuario", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UsuarioPassword usuarioPassword;

    @OneToOne(mappedBy = "usuario", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private MyAccountConf myAccountConf;

    
    
//    @OneToOne(mappedBy = "usuario", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn
//    private MessageConf messageConf;
    
    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name="id_encrypt_keys")
    private EncryptKeys encryptKeys;

    @OneToOne(mappedBy = "usuario", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserInvitationCode UserInvitationCode;   
    
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "usuario_roles", 
				joinColumns = @JoinColumn(name = "usuario_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public Usuario() {
	}

	public Usuario(String username, String password) {
		this.username = username;
		usuarioPassword = new UsuarioPassword(this, password);
		
	}


}
