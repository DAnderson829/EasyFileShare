package com.EasyFileSwap;

import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;



public class FileConvert {
	
	//PDFBox, PDFBox Tools
	public static File convertPdfToTxt(File pdfFile) throws IOException {
        String outputPath = pdfFile.getParent() + File.separator + pdfFile.getName().replaceFirst("[.][^.]+$", "") + ".txt";
        File txtOutputFile = new File(outputPath);

        try (PDDocument document = PDDocument.load(pdfFile);
             FileWriter writer = new FileWriter(txtOutputFile, false)) {  // `false` to overwrite
             
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            writer.write(text);
        }

        return txtOutputFile;  
    }
	
	public static File convertPdfToPngOrJpg(File pdfFile, String format) throws IOException {
        String outputPath = pdfFile.getParent() + File.separator + pdfFile.getName().replaceFirst("[.][^.]+$", "") + "_page_1." + format;
        File imageOutputFile = new File(outputPath);

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = renderer.renderImageWithDPI(0, 300); 
            ImageIO.write(image, format, imageOutputFile);  	
        }

        return imageOutputFile;  
    }
	
	public static File convertJpgOrPngToPdf(File imageFile) throws IOException {
        String outputPath = imageFile.getParent() + File.separator + imageFile.getName().replaceFirst("[.][^.]+$", "") + ".pdf";
        File pdfOutputFile = new File(outputPath);

        BufferedImage bufferedImage = ImageIO.read(imageFile);
        
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDImageXObject pdImage = PDImageXObject.createFromFile(imageFile.getAbsolutePath(), document);
            
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
            contentStream.close();

            document.save(pdfOutputFile);
        }

        return pdfOutputFile;  
    }
	
	public static File convertJpgToPng(File jpgFile) throws IOException {
        String outputPath = jpgFile.getParent() + File.separator + jpgFile.getName().replaceFirst("[.][^.]+$", "") + ".png";
        File pngOutputFile = new File(outputPath);

        BufferedImage image = ImageIO.read(jpgFile);

        ImageIO.write(image, "PNG", pngOutputFile);

        return pngOutputFile;  
    }
	
	public static File convertPngToJpg(File pngFile) throws IOException {
        String outputPath = pngFile.getParent() + File.separator + pngFile.getName().replaceFirst("[.][^.]+$", "") + ".jpg";
        File jpgOutputFile = new File(outputPath);

        BufferedImage image = ImageIO.read(pngFile);

        ImageIO.write(image, "JPG", jpgOutputFile);

        return jpgOutputFile;  
    }
	
	public static File convertTextToPdf(String text) throws IOException {
        File pdfOutputFile = new File("output.pdf");

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);  
            contentStream.newLineAtOffset(100, 750); 
            contentStream.showText(text);  
            contentStream.endText();
            contentStream.close();

            document.save(pdfOutputFile);
        }

        return pdfOutputFile;  
    }
	
	public static File convertTextToPngOrJpg(String text, String format) throws IOException {
        int width = 600; 
        int height = 400; 
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height); 

        graphics.setFont(new Font("Arial", Font.PLAIN, 20));
        graphics.setColor(Color.BLACK);

        graphics.drawString(text, 50, 100);  

        graphics.dispose();

        File outputFile = new File("output." + format);
        ImageIO.write(image, format, outputFile);  

        return outputFile; 
    }
	
	public static File convertPdfToHtml(File pdfFile) throws IOException {
	    String outputPath = pdfFile.getParent() + File.separator + pdfFile.getName().replaceFirst("[.][^.]+$", "") + ".html";
	    File htmlOutputFile = new File(outputPath);

	    try (PDDocument document = PDDocument.load(pdfFile);
	         FileWriter writer = new FileWriter(htmlOutputFile)) {
	        
	        PDFTextStripper stripper = new PDFTextStripper();
	        String text = stripper.getText(document);
	        
	        writer.write("<html><body><pre>" + text + "</pre></body></html>");
	    }

	    return htmlOutputFile;
	}
	
	public static File convertHeicToJpg(File heicFile) throws IOException {
        String outputPath = heicFile.getParent() + File.separator + heicFile.getName().replaceFirst("[.][^.]+$", "") + ".jpg";
        File jpgOutputFile = new File(outputPath);

        BufferedImage image = ImageIO.read(heicFile);
        ImageIO.write(image, "JPG", jpgOutputFile);

        return jpgOutputFile;
	}
	
	 public static File convertHeicToPng(File heicFile) throws IOException {
	        String outputPath = heicFile.getParent() + File.separator + heicFile.getName().replaceFirst("[.][^.]+$", "") + ".png";
	        File pngOutputFile = new File(outputPath);

	        BufferedImage image = ImageIO.read(heicFile);
	        ImageIO.write(image, "PNG", pngOutputFile);

	        return pngOutputFile;
	 }
	 
	 public static File convertHeicToPdf(File heicFile) throws IOException {
	        String outputPath = heicFile.getParent() + File.separator + heicFile.getName().replaceFirst("[.][^.]+$", "") + ".pdf";
	        File pdfOutputFile = new File(outputPath);

	        BufferedImage image = ImageIO.read(heicFile);

	        try (PDDocument document = new PDDocument()) {
	            PDPage page = new PDPage();
	            document.addPage(page);

	            PDImageXObject pdImage = PDImageXObject.createFromFile(heicFile.getAbsolutePath(), document);

	            PDPageContentStream contentStream = new PDPageContentStream(document, page);
	            contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
	            contentStream.close();

	            document.save(pdfOutputFile);
	        }

	        return pdfOutputFile;
	    }
	 public static File convertJpgToHeic(File jpgFile) throws IOException {
	        String outputPath = jpgFile.getParent() + File.separator + jpgFile.getName().replaceFirst("[.][^.]+$", "") + ".heic";
	        File heicOutputFile = new File(outputPath);

	        BufferedImage image = ImageIO.read(jpgFile);
	        ImageIO.write(image, "HEIF", heicOutputFile);

	        return heicOutputFile;
	    }

	    public static File convertPngToHeic(File pngFile) throws IOException {
	        String outputPath = pngFile.getParent() + File.separator + pngFile.getName().replaceFirst("[.][^.]+$", "") + ".heic";
	        File heicOutputFile = new File(outputPath);

	        BufferedImage image = ImageIO.read(pngFile);
	        ImageIO.write(image, "HEIF", heicOutputFile);

	        return heicOutputFile;
	    }

	    public static File convertPdfToHeic(File pdfFile) throws IOException {
	        String outputPath = pdfFile.getParent() + File.separator + pdfFile.getName().replaceFirst("[.][^.]+$", "") + ".heic";
	        File heicOutputFile = new File(outputPath);

	        try (PDDocument document = PDDocument.load(pdfFile)) {
	            PDFRenderer renderer = new PDFRenderer(document);
	            BufferedImage image = renderer.renderImageWithDPI(0, 300); // Render the first page
	            ImageIO.write(image, "HEIF", heicOutputFile);
	        }

	        return heicOutputFile;
	    }
	    
	    public static File convertCsvToJson(File csvFile) throws IOException {
	        String outputPath = csvFile.getParent() + File.separator + csvFile.getName().replaceFirst("[.][^.]+$", "") + ".json";
	        File jsonOutputFile = new File(outputPath);

	        CsvMapper csvMapper = new CsvMapper();
	        CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
	        MappingIterator<Map<String, String>> mappingIterator = csvMapper.readerFor(Map.class).with(csvSchema).readValues(csvFile);

	        List<Map<String, String>> data = mappingIterator.readAll();
	        ObjectMapper jsonMapper = new ObjectMapper();
	        jsonMapper.writerWithDefaultPrettyPrinter().writeValue(jsonOutputFile, data);

	        return jsonOutputFile;
	    }

	    // CSV to XML
	    public static File convertCsvToXml(File csvFile) throws IOException {
	        String outputPath = csvFile.getParent() + File.separator + csvFile.getName().replaceFirst("[.][^.]+$", "") + ".xml";
	        File xmlOutputFile = new File(outputPath);

	        CsvMapper csvMapper = new CsvMapper();
	        CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
	        MappingIterator<Map<String, String>> mappingIterator = csvMapper.readerFor(Map.class).with(csvSchema).readValues(csvFile);

	        List<Map<String, String>> data = mappingIterator.readAll();
	        XmlMapper xmlMapper = new XmlMapper();
	        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(xmlOutputFile, data);

	        return xmlOutputFile;
	    }

	    // JSON to CSV
	    public static File convertJsonToCsv(File jsonFile) throws IOException {
	        String outputPath = jsonFile.getParent() + File.separator + jsonFile.getName().replaceFirst("[.][^.]+$", "") + ".csv";
	        File csvOutputFile = new File(outputPath);

	        ObjectMapper jsonMapper = new ObjectMapper();
	        List<Map<String, String>> data = jsonMapper.readValue(jsonFile, List.class);

	        CsvMapper csvMapper = new CsvMapper();
	        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
	        csvMapper.writerFor(List.class).with(csvSchema).writeValue(csvOutputFile, data);

	        return csvOutputFile;
	    }

	    // JSON to XML
	    public static File convertJsonToXml(File jsonFile) throws IOException {
	        String outputPath = jsonFile.getParent() + File.separator + jsonFile.getName().replaceFirst("[.][^.]+$", "") + ".xml";
	        File xmlOutputFile = new File(outputPath);

	        ObjectMapper jsonMapper = new ObjectMapper();
	        List<Map<String, String>> data = jsonMapper.readValue(jsonFile, List.class);

	        XmlMapper xmlMapper = new XmlMapper();
	        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(xmlOutputFile, data);

	        return xmlOutputFile;
	    }

	    // XML to CSV
	    public static File convertXmlToCsv(File xmlFile) throws IOException {
	        String outputPath = xmlFile.getParent() + File.separator + xmlFile.getName().replaceFirst("[.][^.]+$", "") + ".csv";
	        File csvOutputFile = new File(outputPath);

	        XmlMapper xmlMapper = new XmlMapper();
	        List<Map<String, String>> data = xmlMapper.readValue(xmlFile, List.class);

	        CsvMapper csvMapper = new CsvMapper();
	        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
	        csvMapper.writerFor(List.class).with(csvSchema).writeValue(csvOutputFile, data);

	        return csvOutputFile;
	    }

	    // XML to JSON
	    public static File convertXmlToJson(File xmlFile) throws IOException {
	        String outputPath = xmlFile.getParent() + File.separator + xmlFile.getName().replaceFirst("[.][^.]+$", "") + ".json";
	        File jsonOutputFile = new File(outputPath);

	        XmlMapper xmlMapper = new XmlMapper();
	        List<Map<String, String>> data = xmlMapper.readValue(xmlFile, List.class);

	        ObjectMapper jsonMapper = new ObjectMapper();
	        jsonMapper.writerWithDefaultPrettyPrinter().writeValue(jsonOutputFile, data);

	        return jsonOutputFile;
	    }
}
