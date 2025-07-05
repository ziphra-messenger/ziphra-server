package ar.ziphra.server.component.myaccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.servergralconf.SGCMyAccountConfDTO;
import ar.ziphra.core.model.MyAccountConfLock;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.server.component.common.service.facade.FacadeComponent;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MyAccountConfUtilService {
	
	@Autowired @Lazy
	private FacadeComponent comps;
	
	public Usuario getDefaultConf(Usuario g) {
		SGCMyAccountConfDTO maC = comps.common().serverConf().getSystemGralConf().getMyAccountConf();
		g.getMyAccountConf().setResend(maC.isResend());
		g.getMyAccountConf().setBlackMessageAttachMandatory(maC.isBlackMessageAttachMandatory());
		g.getMyAccountConf().setBlackMessageAttachMandatoryReceived(maC.isBlackMessageAttachMandatoryReceived());

		g.getMyAccountConf().setBlockMediaDownload(maC.isDownloadAttachAllowImage());
		g.getMyAccountConf().setHideMyMessageState(maC.isHideMyMessageState());
		g.getMyAccountConf().setTimeMessageAlways(maC.isTimeMessageAlways());
		g.getMyAccountConf().setTimeMessageDefaultTime(maC.getTimeMessageDefaultTime());
		
		g.getMyAccountConf().setUsuario(g);
		
		g.getMyAccountConf().setLock(new MyAccountConfLock());
		g.getMyAccountConf().getLock().setMyAccountConf(g.getMyAccountConf());
		g.getMyAccountConf().getLock().setEnabled(false);
		g.getMyAccountConf().getLock().setSeconds(900);
		//g.getMyAccountConf().getLock().setMyAccountConf(g.getMyAccountConf());;
		
		
		return g;

	}
	
}
