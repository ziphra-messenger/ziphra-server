package ar.ziphra.appserver.component.files;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;

public class MultipartFileResource extends ByteArrayResource {

	private String filename;

	public MultipartFileResource(String multipartFile) throws IOException {
		super(multipartFile.getBytes());
		this.filename = "archivo";
	}

	@Override
	public String getFilename() {
		return this.filename;
	}
}