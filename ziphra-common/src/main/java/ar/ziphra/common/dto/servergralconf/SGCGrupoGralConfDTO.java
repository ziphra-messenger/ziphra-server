package ar.ziphra.common.dto.servergralconf;

import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.enumeration.ConfigurationStateEnum;
import ar.ziphra.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SGCGrupoGralConfDTO implements IdGrupoInterface{

	@ZiphraId
	private String idGrupo;
	
	private ConfigurationStateEnum resend;
	private ConfigurationStateEnum anonimo;
	
	private boolean timeMessageMandatory;
	private int timeMessageMaxTimeAllow;
	private ConfigurationStateEnum audiochat;

	private int audiochatMaxTime;
	private boolean blackMessageAttachMandatory;

	private boolean downloadAllowImage;
	private boolean downloadAllowAudio;
	private boolean downloadAllowVideo;
	
	private boolean changeNicknameToNumber;
	private boolean hideMessageDetails;
	private boolean hideMessageState;
	private boolean hideMemberList;
	private ConfigurationStateEnum extraEncrypt;

}
