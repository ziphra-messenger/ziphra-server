package ar.ziphra.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(	name = "usuario_invitation_code", 
uniqueConstraints = { 
	@UniqueConstraint(columnNames = "invitationCode"),
})
public class UserInvitationCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8724044696278202403L;

	  @Id
	    @Column(name = "id_user")
	    private Long idUserInvitationCode;
	  
	    @OneToOne
	    @MapsId
	    @JoinColumn(name = "id_user")
	  private Usuario usuario;
	
	
	private String invitationCode;
	
	

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name="id_encrypt_keys")
    private EncryptKeys encryptKeys;



	@Override
	public int hashCode() {
		return Objects.hash(encryptKeys, invitationCode, getIdUsuario(usuario));
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
				&& Objects.equals(getIdUsuario(usuario), getIdUsuario(other.usuario));
	}



	private Long getIdUsuario(Usuario u) {
		if (u!= null) {
			return usuario.getIdUser();
		}else {
			return 0L;
		}
		
	}


}
