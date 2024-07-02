package com.privacity.server.common.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.privacity.common.annotations.ExcludeInterceptorLog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyAccountConf implements Serializable{
	
	public MyAccountConf(Usuario usuario) {
		this.usuario=usuario;
	}
	
	private static final long serialVersionUID = -3324613031997024293L;

	@Id
	@OneToOne
	@ExcludeInterceptorLog
	@ToString.Exclude	
	private Usuario usuario;
	
	private boolean resend;
	
	private boolean timeMessageAlways;
	private long timeMessageDefaultTime;

	private boolean blackMessageAttachMandatory;

	private boolean downloadAttachAllowImage;
	
	private boolean hideMyMessageState;
	
	private boolean loginSkip;
    @OneToOne(mappedBy = "myAccountConf", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private MyAccountConfLock lock;
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
				&& downloadAttachAllowImage == other.downloadAttachAllowImage
				&& hideMyMessageState == other.hideMyMessageState && Objects.equals(lock, other.lock)
				&& loginSkip == other.loginSkip && resend == other.resend
				&& timeMessageAlways == other.timeMessageAlways
				&& timeMessageDefaultTime == other.timeMessageDefaultTime && Objects.equals(usuario.getIdUser(), other.usuario.getIdUser());
	}
	@Override
	public int hashCode() {
		return Objects.hash(blackMessageAttachMandatory, downloadAttachAllowImage, hideMyMessageState, lock, loginSkip,
				resend, timeMessageAlways, timeMessageDefaultTime, usuario.getIdUser());
	}		

	
}
