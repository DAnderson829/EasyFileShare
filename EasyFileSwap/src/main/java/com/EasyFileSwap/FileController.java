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

	@PostMapping("/convert")
    public ResponseEntity<?> convertFile(@RequestParam MultipartFile file) {
        try {	
            byte[] convertedData = convertFileFormat(file);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted-file.ext")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(convertedData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            	.body("Error processing file");
        }
    }
	
	private byte[] convertFileFormat(MultipartFile file) throws IOException {
        
       
    }
}
