package ar.ziphra.appserver.main;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/free/arch")
public class FilesController {
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(
			@RequestParam("file") MultipartFile file)
			 throws IOException, ServletException {
		
		//request.getParameter("jorge");
		
		//request.getServletContext().getContext(null)
		InputStream fin =file.getInputStream();
		int i;
        try {
            //Leer bytes hasta que se encuentre el EOF
            //EOF es un concepto para determinar el final de un archivo
            do {
                i=fin.read();
                if (i !=-1) System.out.print((char)i);
            }while (i!=-1); //Cuando i es igual a -1, se ha alcanzado el final del archivo
        }catch (IOException exc){
            System.out.println("Error al leer el archivo");
        }

        try {
            fin.close();
            //Cerrar el archivo
        }catch (IOException exc){
            System.out.println("Error cerrando el archivo.");
        }
        
		//RequestContext requestContext = new ServletRequestContext(request);
		
		//ServletContext context = request.con.getServletContext();
		/*
	    final FileUploadRequest uploadRequest = new FileUploadRequest();
	    final List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(requestContext);
	    for (final FileItem item : items) {
	         if (item.isFormField()) {
	             String fieldValue = item.getString();
	             uploadRequest.setEnabled(Boolean.valueOf(fieldValue));
	         } else {
	             String fileName = FilenameUtils.getName(item.getName());
	             InputStream fileContent = item.getInputStream();
	             uploadRequest.setFileName(fileName);
	             uploadRequest.setFileStream(fileContent);
	         }
	    }*/
	   // FilesStorageService.save(file);
	    
	    return null;
		
	}
	
	/*
  @Autowired
  FilesStorageService storageService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      storageService.save(file);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
    	e.printStackTrace();
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<FileInfo>> getListFiles() {
    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

      return new FileInfo(filename, url);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }
  */
  @GetMapping("/download")
  @ResponseBody
  public ResponseEntity<byte[]> getFile() {
	  return ResponseEntity.status(HttpStatus.OK).body("HOLA".getBytes());
  }

}