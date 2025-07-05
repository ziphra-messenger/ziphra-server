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

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Accessors(chain = true)
public class GrupoGralConfLock implements Serializable{
	private static final long serialVersionUID = 5332391329597095242L;

	public GrupoGralConfLock(Grupo grupo) {
		this.grupo=grupo;
	}
			
	  @Id
	    @Column(name = "id_grupo")
	    private Long idGrupoGralConfLock;
	  
	    @OneToOne
	    @MapsId
	    @JoinColumn(name = "id_grupo")
	  private Grupo grupo;
	  
	
	private boolean enabled;
	private Integer seconds;
	
	private Long getIdGrupo(Grupo u) {
		if (u!= null) {
			return grupo.getIdGrupo();
		}else {
			return 0L;
		}
	}

	@Override
	public String toString() {
		return "GrupoGralConfLock [grupo=" + getIdGrupo(grupo) + ", enabled=" + enabled + ", seconds=" + seconds + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(enabled, getIdGrupo(grupo), seconds);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoGralConfLock other = (GrupoGralConfLock) obj;
		return enabled == other.enabled && Objects.equals(getIdGrupo(grupo), getIdGrupo(other.grupo)) && Objects.equals(seconds, other.seconds);
	}
}
