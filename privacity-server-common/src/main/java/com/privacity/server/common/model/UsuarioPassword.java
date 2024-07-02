package com.privacity.server.common.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.privacity.common.annotations.ExcludeInterceptorLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_password")
public class UsuarioPassword implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2648617924882203314L;

	@Id
	@OneToOne
	//@Column(name="id_usuario")
	@ExcludeInterceptorLog
    private Usuario usuario;
	
	private String password;

	@Override
	public String toString() {
		return "";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioPassword other = (UsuarioPassword) obj;
		return Objects.equals(password, other.password) && Objects.equals(usuario.getIdUser(), other.usuario.getIdUser());
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, usuario.getIdUser());
	}
}
