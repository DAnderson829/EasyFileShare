package com.EasyFileSwap;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

	FileConvert convert = new FileConvert();
	
	@PostMapping("/convert")
	public ResponseEntity<?> convertFile(@RequestParam MultipartFile file, @RequestParam String targetFormat) {
		try {
			byte[] convertedData = convertFileFormat(file, targetFormat);
			return ResponseEntity.ok()
					// The Content-Disposition header is used to instruct the client on how to
					// display or download the file.
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted-file.ext")
					// MediaType.APPLICATION_OCTET_STREAM is a predefined constant in Spring that
					// represents the MIME type for binary data
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					//This byte array will be sent as the content of the response.
					.body(convertedData);
		} catch (IOException e) {
			//indicates that the server encountered an unexpected condition preventing it from fulfilling the request.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file");
		}
	}

	private byte[] convertFileFormat(MultipartFile file, String targetFormat) throws IOException {
		String contentType = file.getContentType();
		String originalFileName = file.getOriginalFilename();
		
	    String extension = (originalFileName != null) ? originalFileName
	    		.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase() : "";
	    
	    if(contentType != null) {
	    	
	    	
	    	
	    	
	    	
	    }else {
	    	throw new IOException("Unsupported file type");
	    }
	}
}
