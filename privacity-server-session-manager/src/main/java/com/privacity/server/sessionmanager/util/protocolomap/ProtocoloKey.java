package com.privacity.server.sessionmanager.util.protocolomap;

import java.util.Objects;

import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.server.sessionmanager.enumeration.Urls;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProtocoloKey {
	private Urls url;
	private ProtocoloComponentsEnum component;
	private ProtocoloActionsEnum action;
	
	public ProtocoloKey(Urls url, ProtocoloComponentsEnum component, ProtocoloActionsEnum action) {
		super();
		this.url = url;
		this.component = component;
		this.action = action;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(action, component, url);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProtocoloKey other = (ProtocoloKey) obj;
		return action == other.action && component == other.component && url == other.url;
	}

	public static ProtocoloKey build(Urls url, ProtocoloComponentsEnum component, ProtocoloActionsEnum action) {
		return new ProtocoloKey(url,component,action);
	}

	@Override
	public String toString() {
		return "Key [url=" + url.name() + ", component=" + component + ", action=" + action + "]";
	}

//	public static void main(String[] args) {
//		Key k = build(Urls.CONSTANT_URL_PATH_PRIVATE, ProtocoloComponentsEnum.GRUPO, ProtocoloActionsEnum.GRUPO_SAVE_GRUPO_USER_CONF);
//		
//		System.out.println(k.toString());
//		System.out.println(k.hashCode());
//		
//		Key k2 = build(Urls.CONSTANT_URL_PATH_PRIVATE, ProtocoloComponentsEnum.GRUPO, ProtocoloActionsEnum.GRUPO_SAVE_GRUPO_USER_CONF);
//		System.out.println(k2.toString());
//		System.out.println(k2.hashCode());
//		System.out.println(k2.equals(k));
//	}
//			
//	@Override
//	public String toString() {
//		return "Key [url=" + url + ", component=" + component + ", action=" + action + "]";
//	}

}
