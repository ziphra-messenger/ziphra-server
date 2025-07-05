package ar.ziphra.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ar.ziphra.commonback.common.enumeration.RolesSecurityAccessToServerEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "roles")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role implements Serializable{
	
	private static final long serialVersionUID = -7096490481619023464L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RolesSecurityAccessToServerEnum name;


}