package com.privacity.server.websocket.listeners;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.model.Grupo;

/**
 * Created by baiguantao on 2017/8/4.
 * STOMP Monitoring category
 * Used for session registration and key value acquisition
 */
public class STOMPDisconnectEventListener  implements ApplicationListener<SessionDisconnectEvent>{

	@Autowired @Lazy
	private FacadeComponent comps;
    
    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        //login get from browser
        String username = event.getUser().getName();
        String sessionId = sha.getSessionId();

        
        comps.webSocket().socketSessionRegistry().unregisterSessionId(username,sessionId);
        
        Thread t = new Thread() {
        	
        public void run() {
        List<Grupo> grupos = comps.repo().userForGrupo().findByGrupoByUsername(username);
        
        for (Grupo g : grupos) {
        	GrupoDTO r = new GrupoDTO();
        	r.setIdGrupo(g.getIdGrupo()+"");
        	
        	try {
				r.setMembersOnLine(
						comps.webSocket().sender().getMembersOnlineByGrupo(r.getIdGrupo())
				);
				
				if (r.getMembersOnLine() != 0) {
					senderToGrupoMinusCreator(username,
							 g.getIdGrupo(), r);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        };
        

        //comps.service().usuarioSessionInfo().remove(username);

    }
        };
        
        t.start();
} 
	private void senderToGrupoMinusCreator(String username, long idGrupo, GrupoDTO g) throws Exception {

		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
				ProtocoloComponentsEnum.GRUPO,
		        ProtocoloActionsEnum.GRUPO_HOW_MANY_MEMBERS_ONLINE,
		        g);
		comps.webSocket().sender().senderToGrupo(p, idGrupo, username, true);
		
	
		
		
	}
}