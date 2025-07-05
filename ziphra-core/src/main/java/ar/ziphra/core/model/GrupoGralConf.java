package ar.ziphra.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import ar.ziphra.common.enumeration.ConfigurationStateEnum;
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
public class GrupoGralConf implements Serializable{
	

	@Override
	public String toString() {
		return "GrupoGralConf [grupo=" + getIdGrupo(grupo) + ", blockResend=" + blockResend + ", anonimo=" + anonimo
				+ ", timeMessageMandatory=" + timeMessageMandatory + ", timeMessageMaxTimeAllow="
				+ timeMessageMaxTimeAllow + ", audiochat=" + blockAudioMessages + ", audiochatMaxTime=" + audiochatMaxTime
				+ ", blackMessageAttachMandatory=" + blackMessageAttachMandatory + ", blockMediaDownload="
				+ blockMediaDownload + 
				 ", randomNickname=" + randomNickname + ", hideMessageDetails="
				+ hideMessageDetails + ", hideMessageReadState=" + hideMessageReadState + ", hideMemberList=" + hideMemberList
				+ ", extraEncrypt=" + extraEncrypt + "]";
	}
	public GrupoGralConf(Grupo grupo) {
		this.grupo=grupo;
	}
	
	private static final long serialVersionUID = -3324613031997024293L;


	
	  @Id
	    @Column(name = "id_grupo")
	    private Long idGrupoGralConf;
	  
	    @OneToOne
	    @MapsId
	    @JoinColumn(name = "id_grupo")
	  private Grupo grupo;
	  
	private boolean blockResend;
	private RulesConfEnum anonimo;
	
	private boolean timeMessageMandatory;
	private int timeMessageMaxTimeAllow;
	private boolean blockAudioMessages;

	private int audiochatMaxTime;
	private boolean blackMessageAttachMandatory;

	private boolean blockMediaDownload;

	
	private boolean randomNickname;
	private boolean hideMessageDetails;
	private boolean hideMessageReadState;
	private boolean hideMemberList;
	private ConfigurationStateEnum extraEncrypt;
	
  
	
	private Long getIdGrupo(Grupo u) {
		if (u!= null) {
			return grupo.getIdGrupo();
		}else {
			return 0L;
		}
		
	}
	@Override
	public int hashCode() {
		return Objects.hash(anonimo, blockAudioMessages, audiochatMaxTime, blackMessageAttachMandatory, randomNickname,
				blockMediaDownload,  extraEncrypt, grupo, hideMemberList,
				hideMessageDetails, hideMessageReadState, blockResend, timeMessageMandatory, timeMessageMaxTimeAllow);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoGralConf other = (GrupoGralConf) obj;
		return anonimo == other.anonimo && blockAudioMessages == other.blockAudioMessages && audiochatMaxTime == other.audiochatMaxTime
				&& blackMessageAttachMandatory == other.blackMessageAttachMandatory
				&& randomNickname == other.randomNickname
				&& blockMediaDownload == other.blockMediaDownload
				 && extraEncrypt == other.extraEncrypt
				&& Objects.equals(grupo, other.grupo) && hideMemberList == other.hideMemberList
				&& hideMessageDetails == other.hideMessageDetails && hideMessageReadState == other.hideMessageReadState
				&& blockResend == other.blockResend && timeMessageMandatory == other.timeMessageMandatory
				&& timeMessageMaxTimeAllow == other.timeMessageMaxTimeAllow;
	}


}
