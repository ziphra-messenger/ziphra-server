package ar.ziphra.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import ar.ziphra.common.enumeration.RulesConfEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MyAccountConf implements Serializable{
	
	public MyAccountConf(Usuario usuario) {
		this.usuario=usuario;
	}
	
	private static final long serialVersionUID = -3324613031997024293L;

	  @Id
	    @Column(name = "id_user")
	    private Long idMyAccountConf;
	  
	    @OneToOne
	    @MapsId
	    @JoinColumn(name = "id_user")
	  private Usuario usuario;
	
	private boolean resend;
	
	private boolean timeMessageAlways;
	private long timeMessageDefaultTime;

	private boolean blackMessageAttachMandatory;
	private boolean blackMessageAttachMandatoryReceived;
	private boolean blockMediaDownload;
	
	private boolean hideMyMessageState;
	
	private boolean loginSkip;
    @OneToOne(mappedBy = "myAccountConf", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private MyAccountConfLock lock;
	@Override
	public int hashCode() {
		return Objects.hash(blackMessageAttachMandatory, blockMediaDownload, hideMyMessageState,
				idMyAccountConf, lock, loginSkip, resend, timeMessageAlways, timeMessageDefaultTime, getIdUsuario(usuario));
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyAccountConf other = (MyAccountConf) obj;
		return blackMessageAttachMandatory == other.blackMessageAttachMandatory
				&& blockMediaDownload == other.blockMediaDownload
				&& hideMyMessageState == other.hideMyMessageState
				&& Objects.equals(idMyAccountConf, other.idMyAccountConf) && Objects.equals(lock, other.lock)
				&& loginSkip == other.loginSkip && resend == other.resend
				&& timeMessageAlways == other.timeMessageAlways
				&& timeMessageDefaultTime == other.timeMessageDefaultTime && Objects.equals(getIdUsuario(usuario), getIdUsuario(other.usuario));
	}

	private Long getIdUsuario(Usuario u) {
		if (u!= null) {
			return usuario.getIdUser();
		}else {
			return 0L;
		}
		
	}
	
}
