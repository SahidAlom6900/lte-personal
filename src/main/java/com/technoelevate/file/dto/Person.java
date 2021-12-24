package com.technoelevate.file.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Table(name = "person")
//,uniqueConstraints =@UniqueConstraint(columnNames = { "contactNumber" ,"whatsAppNumber","dlNumber","panNumber","email"})
public class Person implements Serializable {
	@Id
	@SequenceGenerator(name = "person_sequence_generator", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "person_sequence_generator")
	@Column(name = "person_id")
	private int personId;
	@NotNull(message = "Person Name Can not be Null")
	@NotEmpty(message = "Person Name Can Not Be Empty")
	private String personName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "Asia/Kolkata")
	private Date dob;
	@NotNull(message = "Gender Can not be Null")
	@NotEmpty(message = "Gender Can Not Be Empty")
	private String gender;
	private long contactNumber;
	private long whatsAppNumber;
	private long aadharNumber;
	@NotNull(message = "dl Number Can not be Null")
	@NotEmpty(message = "dl Number Can Not Be Empty")
	private String dlNumber;
	@NotNull(message = "Pan Number Can not be Null")
	@NotEmpty(message = "Pan Number Can Not Be Empty")
	private String panNumber;
	@Email(message = "Email COn Not Be Null")
	private String email;
	@NotNull(message = "Present Address Can not be Null")
	@NotEmpty(message = "Present Address Can Not Be Empty")
	private String presentAddress;
	@NotNull(message = "Permanen Address Can not be Null")
	@NotEmpty(message = "Permanent Address Can Not Be Empty")
	private String permanentAddress;
	@OneToMany(mappedBy = "person")
	private List<@Valid FileUpload> files;
	@OneToOne
	@Valid
	private EmergencyContact emergencyContact;
}
