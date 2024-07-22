package com.privacity.common.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.adapters.LocalDateAdapter;
import com.privacity.common.config.ConstantGeneral;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.PrivacityException;

public abstract class UtilsStringAbstract {

	private final int MAX_LOG = 2000;

	public final String cutStringToGson(Object s) {
		return cutString(gson().toJson(s));
	}

	public final String cutString(String s) {

		if (s == null) return s;
		if (s.length() < MAX_LOG) return s;

		String r = replacingToShow(s);

		return r.substring(0, (r.length()> MAX_LOG)? MAX_LOG : r.length()-1);
	}

	public final String uncompressB64(String s) throws PrivacityException {

		try {
			if (s==null)return s;
			byte[] b = Base64.getDecoder().decode(s);
			byte[] unz = new ZipUtil().decompress(b);

			return new String(unz, ConstantGeneral.CHARSET_DEFAULT);

		} catch (IOException e) {
			throw new PrivacityException(ExceptionReturnCode.ZIP_DECOMPRESS);
		}
	}

	public final String compressB64(String s) throws PrivacityException  {
		try {
			if (s==null)return s;
			s=replacing(s);
			byte[] compressed;

			compressed = new ZipUtil().compress(s.getBytes());

			s=Base64.getEncoder().withoutPadding().encodeToString(compressed);
			return s;

		} catch (IOException e) {
			throw new PrivacityException(ExceptionReturnCode.ZIP_COMPRESS);
		}
	}

	public String gsonToSendCompress(Object s) throws PrivacityException {
		if (s==null)return null;
		String s1 = gsonToSend(s);

		return compressB64(s1);
	}


	public String gsonToSend(Object s) throws PrivacityException {
		if (s==null)return null;
		String s1 = new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
				.create().toJson(s);
		return replacing(s1);
	}	

	public final Gson gson() {
		return new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
				.create();

	}

	public final String replacing(String s) {
		if (s == null) return s;

		String r = s.toString().replace("\n", "").replace("\t", "").replace("\r", "").replace("\b", "").replace("\f", "")
				.replace("  ", " ").replace("  ", " ").replace("  ", " ").replace(", " , ",");

		return r;
	}
	
	private final String replacingToShow(String s) {
		if (s == null) return s;

		String r = s.toString().replace("\n", "").replace("\t", "").replace("\r", "").replace("\b", "").replace("\f", "")
				.replace("  ", " ").replace("  ", " ").replace("  ", " ").replace(", " , "");

		return r;
	}

	public final Object clon(Class<?> clazz, Object o ) {
		
	       
        String j = gson().toJson(o);
        
        Object fromJson = gson().fromJson(j, clazz);
		return fromJson;
		
	}
}
