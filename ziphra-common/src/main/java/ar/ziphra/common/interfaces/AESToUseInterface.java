package ar.ziphra.common.interfaces;

public interface AESToUseInterface {
	public byte[] getAESData(byte[] data);
	public byte[] getAESDecryptData(byte[] data) throws Exception;
	public String getAES(String data) ;
	public String getAESDecrypt(String data) throws Exception;
	public String getAES(byte[] data);
}
