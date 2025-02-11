package com.EasyFileSwap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Files")
public class File {
	
	@jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;

	@Column(nullable = false)
	private String fileName;
	
	@Column(nullable = false)
	private String fileType;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User owner;
	
	@Lob
	private byte[] fileData;
	
	public File() {}
	
	public File(String fileName, String fileType, byte[] fileData, User owner) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = fileData;
        this.owner = owner;
    }

    public Long getId() {return Id;}
    public void setId(Long id) {this.Id = id;}
    public String getFileName() {return fileName;}
    public void setFileName(String fileName) {this.fileName = fileName;}
    public String getFileType() {return fileType;}
    public void setFileType(String fileType) {this.fileType = fileType;}
    public byte[] getFileData() {return fileData; }
    public void setFileData(byte[] fileData) {this.fileData = fileData;}

}
