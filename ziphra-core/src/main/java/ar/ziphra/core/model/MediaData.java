package ar.ziphra.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
public class MediaData implements Serializable{

	private static final long serialVersionUID = -1945812217616090681L;

	@EmbeddedId
	private MediaId mediaId;

	@Lob
	@Column(columnDefinition="LONGBLOB")
	private byte[] data;
	
	   @Lob
	   @Column(columnDefinition="LONGBLOB")
	   private byte[] miniatura;
		   
}
