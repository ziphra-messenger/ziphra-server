package com.privacity.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
public class MyAccountConfLock implements Serializable{

	private static final long serialVersionUID = 5332391329597095242L;

	@Id
	@OneToOne
	@ExcludeInterceptorLog
	@ToString.Exclude	
	private MyAccountConf myAccountConf;
	
	public boolean enabled;
	public Integer seconds;
	@Override
	public int hashCode() {
		return Objects.hash(enabled, myAccountConf.getUsuario().getIdUser(), seconds);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyAccountConfLock other = (MyAccountConfLock) obj;
		return enabled == other.enabled && Objects.equals(myAccountConf.getUsuario().getIdUser(), other.myAccountConf.getUsuario().getIdUser())
				&& Objects.equals(seconds, other.seconds);
	}
}
