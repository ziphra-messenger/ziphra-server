package ar.ziphra.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GrupoGralConfPassword implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2648617924882203314L;

	public GrupoGralConfPassword(Grupo grupo) {
		this.grupo=grupo;
	}
		
	  @Id
	    @Column(name = "id_grupo")
	    private Long idGrupoGralConfPassword;
	  
	    @OneToOne
	    @MapsId
	    @JoinColumn(name = "id_grupo")
	  private Grupo grupo;
	  
	
	private boolean extraEncryptDefaultEnabled;
	private boolean deleteExtraEncryptEnabled;
	
	private boolean enabled;
	private String password;
	private String passwordExtraEncrypt;

	@Override
	public String toString() {
		return "GrupoGralConfPassword [grupo=" + getIdGrupo(grupo) + ", extraEncryptDefaultEnabled=" + extraEncryptDefaultEnabled
				+ ", deleteExtraEncryptEnabled=" + deleteExtraEncryptEnabled + ", enabled=" + enabled + ", password="
				+ password + ", passwordExtraEncrypt=" + passwordExtraEncrypt + "]";
	}
	
	private Long getIdGrupo(Grupo u) {
		if (u!= null) {
			return grupo.getIdGrupo();
		}else {
			return 0L;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(deleteExtraEncryptEnabled, enabled, extraEncryptDefaultEnabled, getIdGrupo(grupo), password,
				passwordExtraEncrypt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoGralConfPassword other = (GrupoGralConfPassword) obj;
		return deleteExtraEncryptEnabled == other.deleteExtraEncryptEnabled && enabled == other.enabled
				&& extraEncryptDefaultEnabled == other.extraEncryptDefaultEnabled && Objects.equals(getIdGrupo(grupo), getIdGrupo(other.grupo))
				&& Objects.equals(password, other.password)
				&& Objects.equals(passwordExtraEncrypt, other.passwordExtraEncrypt);
	}
}
