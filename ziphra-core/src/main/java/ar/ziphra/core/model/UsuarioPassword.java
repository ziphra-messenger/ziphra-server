package ar.ziphra.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity

@NoArgsConstructor
@Table(name = "user_password")
public class UsuarioPassword implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2648617924882203314L;


	  @Id
	    @Column(name = "id_user")
	    private Long idUsuarioPassword;
	  
	    @OneToOne
	    @MapsId
	    @JoinColumn(name = "id_user")
	  private Usuario usuario;
	    
	private String password;

	public UsuarioPassword(Usuario usuarioLogged, String newPassword) {
		this.usuario=usuarioLogged;
		this.password=newPassword;
	}

	@Override
	public String toString() {
		return "UsuarioPassword [usuario=" + getIdUsuario(usuario) + ", password=" + password + "]";
	}
	
	private Long getIdUsuario(Usuario u) {
		if (u!= null) {
			return usuario.getIdUser();
		}else {
			return 0L;
		}
		
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
		return Objects.equals(password, other.password) && Objects.equals(getIdUsuario(usuario), getIdUsuario(other.usuario));
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, usuario);
	}
}
