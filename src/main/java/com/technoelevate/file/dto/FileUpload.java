package com.technoelevate.file.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "file")
@JsonIgnoreProperties({"person"})
public class FileUpload implements Serializable {
	@Id
	@SequenceGenerator(name = "file_sequence_generator", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "file_sequence_generator")
	@Column(name = "file_id")
	private int fileId;
	@NotNull(message = "File Name Can not be Null")
	@NotEmpty(message = "File Name Can Not Be Empty")
	private String fileName;
	@NotNull(message = "File URL Name Can not be Null")
	@NotEmpty(message = "File URL Name Can Not Be Empty")
	private String fileUrl;
	@NotNull(message = "Message Can not be Null")
	@NotEmpty(message = "Message Can Not Be Empty")
	private String message;
	
	@ManyToOne
	@Valid
	private Person person;

	public FileUpload(
			@NotNull(message = "File Name Can not be Null") @NotEmpty(message = "File Name Can Not Be Empty") String fileName,
			@NotNull(message = "File URL Name Can not be Null") @NotEmpty(message = "File URL Name Can Not Be Empty") String fileUrl,
			@NotNull(message = "Message Can not be Null") @NotEmpty(message = "Message Can Not Be Empty") String message,
			Person person) {
		super();
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.message = message;
		this.person = person;
	}

	

}
