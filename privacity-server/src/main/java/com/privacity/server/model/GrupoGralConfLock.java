package com.privacity.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "user_password")
public class GrupoGralConfLock implements Serializable{
	private static final long serialVersionUID = 5332391329597095242L;

	public GrupoGralConfLock(Grupo grupo) {
		this.grupo=grupo;
	}
			
	@Id
	@OneToOne
	@ToString.Exclude		
	private Grupo grupo;
	
	private boolean enabled;
	private Integer seconds;
}
