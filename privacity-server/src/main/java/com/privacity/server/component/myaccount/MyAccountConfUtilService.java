package com.privacity.server.component.myaccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.LockDTO;
import com.privacity.common.dto.servergralconf.SGCMyAccountConfDTO;
import com.privacity.server.common.model.MyAccountConfLock;
import com.privacity.server.common.model.Usuario;
import com.privacity.server.component.common.service.facade.FacadeComponent;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MyAccountConfUtilService {
	
	@Autowired @Lazy
	private FacadeComponent comps;
	
	public void getDefaultConf(Usuario g) {
		SGCMyAccountConfDTO maC = comps.common().serverConf().getSystemGralConf().getMyAccountConf();
		g.getMyAccountConf().setResend(maC.isResend());
		g.getMyAccountConf().setBlackMessageAttachMandatory(maC.isBlackMessageAttachMandatory());

		g.getMyAccountConf().setDownloadAttachAllowImage(maC.isDownloadAttachAllowImage());
		g.getMyAccountConf().setHideMyMessageState(maC.isHideMyMessageState());
		g.getMyAccountConf().setTimeMessageAlways(maC.isTimeMessageAlways());
		g.getMyAccountConf().setTimeMessageDefaultTime(maC.getTimeMessageDefaultTime());
		
		
		g.getMyAccountConf().setLock(new MyAccountConfLock());
		g.getMyAccountConf().getLock().setMyAccountConf(g.getMyAccountConf());
		g.getMyAccountConf().getLock().setEnabled(false);
		g.getMyAccountConf().getLock().setSeconds(900);

	}
	
}
