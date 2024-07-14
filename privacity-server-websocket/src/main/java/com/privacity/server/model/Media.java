package com.privacity.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

import com.privacity.common.enumeration.MediaTypeEnum;

import lombok.Data;

@Entity
@Data
public class Media implements Serializable{


	private static final long serialVersionUID = -7043920171959892214L;

	@EmbeddedId
    private MediaId mediaId;

	   @Lob
	   @Column(columnDefinition="LONGBLOB")
    private byte[] data;
	   
	   @Lob
	   @Column(columnDefinition="LONGBLOB")
	   private byte[] miniatura;
	   
	private boolean downloadable;
	@Enumerated(EnumType.ORDINAL)
	private MediaTypeEnum mediaType;
	
	@Override
	public String toString() {
		return "Media [mediaId=" ; // + mediaId + ", mediaType=" + mediaType + "]";
	}
	
//    @ManyToOne
//    @JoinColumn(name = "id_comment", insertable = false, updatable = false)
//    public Comment comment;


    
}