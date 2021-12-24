package com.technoelevate.file.dto;

import java.io.Serializable;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Validated
@JsonIgnoreProperties({"person"})
@Table(name = "emergency_contact")
public class EmergencyContact implements Serializable {
	@Id
	@SequenceGenerator(name = "emergency_contact_sequence_generator", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "emergency_contact_sequence_generator")
	@Column(name = "emergency_contact_id")
	private int emergencyContactId;
	@NotNull(message = "Father Name Can not be Null")
	@NotEmpty(message = "Father Name Can Not Be Empty")
	private String fatherName;
	private long fatherContactNumber;
	@NotNull(message = "Father Occupation Can not be Null")
	@NotEmpty(message = "Father Occupation Can Not Be Empty")
	private String fatherOccupation;
	@NotNull(message = "Mother Name Can not be Null")
	@NotEmpty(message = "Mother Name Can Not Be Empty")
	private String motherName;
	private long motherContactNumber;
	@NotNull(message = "Mother Occupation Can not be Null")
	@NotEmpty(message = "Mother Occupation Can Not Be Empty")
	private String motherOccupation;
	@OneToMany(mappedBy = "emergencyContact")
	private List<@Valid RelationShip> relationShips;
	@OneToOne(mappedBy = "emergencyContact")
	@Valid
	private Person person;
}
