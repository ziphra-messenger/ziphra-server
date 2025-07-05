package ar.ziphra.server.component.encryptkeys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ar.ziphra.commonback.components.RSAComponentAbstract;

@Component
public class RSAComponent extends RSAComponentAbstract{

	@Value("${serverconf.asymEncrypt.bits}")
	private int asymEncryptbits;
	@Override
	protected int getRsaKeySize() {
		// TODO Auto-generated method stub
		return asymEncryptbits;
	}

}
