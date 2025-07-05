package com.privacity.core.model;

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
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MyAccountConfLock implements Serializable{

	private static final long serialVersionUID = 5332391329597095242L;

	  @Id
	    @Column(name = "id_user")
	    private Long idMyAccountConfLock;
	  
	    @OneToOne
	    @MapsId
	    @JoinColumn(name = "id_user")
	@ToString.Exclude	
	private MyAccountConf myAccountConf;
	
	public boolean enabled;
	public Integer seconds;
	@Override
	public int hashCode() {
		return Objects.hash(enabled, getIdMyAccountConf(myAccountConf), seconds);
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
		return enabled == other.enabled && Objects.equals(getIdMyAccountConf(myAccountConf), getIdMyAccountConf(other.myAccountConf))
				&& Objects.equals(seconds, other.seconds);
	}
	
	private Long getIdMyAccountConf(MyAccountConf u) {
		if (u!= null) {
			return u.getIdMyAccountConf();
		}else {
			return 0L;
		}
		
	}
}
