package com.privacity.server.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.privacity.common.annotations.ExcludeInterceptorLog;
import com.privacity.server.security.Usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(	name = "user_invitation_code", 
uniqueConstraints = { 
	@UniqueConstraint(columnNames = "invitationCode"),
})
public class UserInvitationCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8724044696278202403L;

	@Id
	@OneToOne
	@ExcludeInterceptorLog
	@ToString.Exclude	
    private Usuario usuario;
	
	
	private String invitationCode;
	
	

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name="id_encrypt_keys")
    private EncryptKeys encryptKeys;



	@Override
	public int hashCode() {
		return Objects.hash(encryptKeys, invitationCode, usuario.getIdUser());
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInvitationCode other = (UserInvitationCode) obj;
		return Objects.equals(encryptKeys, other.encryptKeys) && Objects.equals(invitationCode, other.invitationCode)
				&& Objects.equals(usuario.getIdUser(), other.usuario.getIdUser());
	}






}
