package ar.ziphra.appserver.component.files;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import lombok.Data;

@Data
public class FileUploadRequest {
    private String fileName="jorge";
    private InputStream fileStream = new ByteArrayInputStream("jorg13131231321321e".getBytes());;
    private boolean enabled = true;

}