package com.EasyFileSwap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class FileControllerTest {
	
	@Mock
	private FileConvert fileConvert;
	 
	@InjectMocks
	private FileController fileController;

	private MockMultipartFile JPG = new MockMultipartFile("jpgTest", "test.jpg", "image/jpeg", new byte[4096]);
	private MockMultipartFile PNG = new MockMultipartFile("pngTest", "test.png", "image/png", new byte[4096]);
//	private MockMultipartFile PDF = new MockMultipartFile("pdfTest", "test.pdf", "application/pdf", new byte[4096]);
	private MockMultipartFile HEIC = new MockMultipartFile("heicTest", "test.heic", "image/heic", new byte[4096]);
	private MockMultipartFile GIF = new MockMultipartFile("gifTest", "test.gif", "image/gif", new byte[4096]);
	private MockMultipartFile CSV = new MockMultipartFile("csvTest", "test.csv", "text/csv", new byte[4096]);
	private MockMultipartFile JSON = new MockMultipartFile("jsonTest", "test.json", "application/json", new byte[4096]);
	private MockMultipartFile XML = new MockMultipartFile("xmlTest", "test.xml", "text/xml", new byte[4096]);
	private MockMultipartFile YAML = new MockMultipartFile("yamlTest", "test.yaml", "application/yaml", new byte[4096]);

	@SuppressWarnings("static-access")
	@Test
	void convertPdfToTxtTest() throws Exception {
		byte[] pdfTest = Files.readAllBytes(Paths.get("TestFiles/pdfTest.pdf"));
		MockMultipartFile PDF = new MockMultipartFile("pdfTest", "test.pdf", "application/pdf", pdfTest);

		File converted = new File("converted.txt");
	    when(FileConvert.convertPdfToTxt((File) any(File.class))).thenReturn(converted);

		ResponseEntity<?> response = fileController.convertFile(PDF, "txt");
		
		String convertedFileName = response.getBody().toString();

		System.out.println("Response status: " + response.getStatusCode());
		System.out.println("Response body: " + response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(convertedFileName);
		assertTrue(convertedFileName.endsWith(".txt"));
		
	    verify(fileConvert, times(1)).convertPdfToTxt((File) any(File.class));



	}
}
