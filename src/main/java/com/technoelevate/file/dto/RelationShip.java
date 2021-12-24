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
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Table(name = "relation_ship")
@JsonIgnoreProperties({"emergencyContact"})
public class RelationShip implements Serializable {
	@Id
	@SequenceGenerator(name = "relation_ship_sequence_generator", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "relation_ship_sequence_generator")
	@Column(name = "relation_ship_id")
	private int relationShipId;
	@NotNull(message = "Type Of RelationShip  Can not be Null")
	@NotEmpty(message = "Type Of RelationShip  Can Not Be Empty")
	private String typeOfRelationShip;
	@NotNull(message = "Name Can not be Null")
	@NotEmpty(message = "Name Can Not Be Empty")
	private String name;
	private long contactNumber;
	@NotNull(message = "Occupation Can not be Null")
	@NotEmpty(message = "Occupation Can Not Be Empty")
	private String occupation;
	@ManyToOne
	@Valid
	private EmergencyContact emergencyContact;
}
