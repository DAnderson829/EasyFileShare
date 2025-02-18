package com.EasyFileSwap;

import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Path;
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
    public ResponseEntity<?> convertFile(@RequestParam MultipartFile file, @RequestParam String targetFormat) {
        try {
            byte[] convertedData = convertFileFormat(file, targetFormat);
            String outputFileName = "converted-file." + targetFormat.toLowerCase();
            return ResponseEntity.ok()
            		//It sets the Content-Disposition header to indicate that the response should prompt a download with the filename specified (outputFileName).
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + outputFileName)
                    //The Content-Type is set to MediaType.APPLICATION_OCTET_STREAM, which signifies that the body contains binary data.
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    //sets the response body to convertedData, which is the byte array containing the converted file.
                    .body(convertedData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private byte[] convertFileFormat(MultipartFile file, String targetFormat) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        String originalFileName = file.getOriginalFilename();
        String extension = (originalFileName != null) ?
                originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase() : "";

        Path tempFile = Files.createTempFile("upload-", "." + extension);
        file.getInputStream().transferTo(Files.newOutputStream(tempFile));

        File inputFile = tempFile.toFile();
        File outputFile;

        switch (extension) {
            case "pdf":
                switch (targetFormat.toLowerCase()) {
                    case "txt":
                        outputFile = FileConvert.convertPdfToTxt(inputFile);
                        break;
                    case "png":
                        outputFile = FileConvert.convertPdfToPngOrJpg(inputFile, "PNG");
                        break;
                    case "jpg":
                        outputFile = FileConvert.convertPdfToPngOrJpg(inputFile, "JPG");
                        break;
                    case "html":
                        outputFile = FileConvert.convertPdfToHtml(inputFile);
                        break;
                    case "heic":
                        outputFile = FileConvert.convertPdfToHeic(inputFile);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported target format for PDF: " + targetFormat);
                }
                break;
            case "jpg":
            case "jpeg":
                switch (targetFormat.toLowerCase()) {
                    case "png":
                        outputFile = FileConvert.convertJpgToPng(inputFile);
                        break;
                    case "heic":
                        outputFile = FileConvert.convertJpgToHeic(inputFile);
                        break;
                    case "pdf":
                        outputFile = FileConvert.convertJpgOrPngToPdf(inputFile);
                        break;
                    case "gif":
                        outputFile = FileConvert.convertJpgToGif(inputFile);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported target format for JPG: " + targetFormat);
                }
                break;
            case "png":
                switch (targetFormat.toLowerCase()) {
                    case "jpg":
                        outputFile = FileConvert.convertPngToJpg(inputFile);
                        break;
                    case "heic":
                        outputFile = FileConvert.convertPngToHeic(inputFile);
                        break;
                    case "pdf":
                        outputFile = FileConvert.convertJpgOrPngToPdf(inputFile);
                        break;
                    case "gif":
                        outputFile = FileConvert.convertPngToGif(inputFile);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported target format for PNG: " + targetFormat);
                }
                break;
            case "csv":
                switch (targetFormat.toLowerCase()) {
                    case "json":
                        outputFile = FileConvert.convertCsvToJson(inputFile);
                        break;
                    case "xml":
                        outputFile = FileConvert.convertCsvToXml(inputFile);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported target format for CSV: " + targetFormat);
                }
                break;
            case "json":
                switch (targetFormat.toLowerCase()) {
                    case "csv":
                        outputFile = FileConvert.convertJsonToCsv(inputFile);
                        break;
                    case "xml":
                        outputFile = FileConvert.convertJsonToXml(inputFile);
                        break;
                    case "yaml":
                        outputFile = FileConvert.convertJsonToYaml(inputFile);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported target format for JSON: " + targetFormat);
                }
                break;
            case "xml":
                switch (targetFormat.toLowerCase()) {
                    case "csv":
                        outputFile = FileConvert.convertXmlToCsv(inputFile);
                        break;
                    case "json":
                        outputFile = FileConvert.convertXmlToJson(inputFile);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported target format for XML: " + targetFormat);
                }
                break;
            case "yaml":
                if ("json".equalsIgnoreCase(targetFormat)) {
                    outputFile = FileConvert.convertYamlToJson(inputFile);
                } else {
                    throw new IllegalArgumentException("Unsupported target format for YAML: " + targetFormat);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + extension);
        }

        byte[] convertedData = Files.readAllBytes(outputFile.toPath());

        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(outputFile.toPath());

        return convertedData;
    }
}