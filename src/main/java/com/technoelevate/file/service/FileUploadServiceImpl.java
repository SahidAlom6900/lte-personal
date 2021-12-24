package com.technoelevate.file.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.technoelevate.file.common.Message;
import com.technoelevate.file.dto.EmergencyContact;
import com.technoelevate.file.dto.FileUpload;
import com.technoelevate.file.dto.Person;
import com.technoelevate.file.dto.RelationShip;
import com.technoelevate.file.exception.CustomException;
import com.technoelevate.file.exception.FileNotFoundException;
import com.technoelevate.file.repository.EmergencyContactRepository;
import com.technoelevate.file.repository.FileRepository;
import com.technoelevate.file.repository.PersonRepository;
import com.technoelevate.file.repository.RelationShipRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@Transactional
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private EmergencyContactRepository emergencyContactRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private RelationShipRepository relationShipRepository;

	@Autowired
	private Environment environment;
	private Path dirLocation;
	private String dir;

	private Path getPath(String fileName) {
		String dir = environment.getProperty("file.upload.location");
		this.dir = dir + "\\" + fileName;
		this.dirLocation = Paths.get(this.dir).toAbsolutePath().normalize();
		return this.dirLocation;
	}

	@Override
	public Person savePerson(List<MultipartFile> multipartFiles,@Valid Person person) {
		try {
			if (person.getPersonId() >99) {
				Person person1 = personRepository.findByPersonId(person.getPersonId());
				if (person1 != null) {
					FileUtils.forceDelete(
							new File(getPath(person1.getPersonName() + person1.getContactNumber()).toString()));
				}
			}
			this.dirLocation = getPath(person.getPersonName() + person.getContactNumber());
			Files.createDirectories(this.dirLocation);
		} catch (IOException exception) {
			throw new FileNotFoundException(Message.FILE_NOT_FOUND);
		}
		List<FileUpload> files = new ArrayList<>();
		int i=0;
		for (MultipartFile multipartFile : multipartFiles) {
			try {
				String fileName = multipartFile.getOriginalFilename();
				Path dfile = this.dirLocation.resolve(fileName);
				Files.copy(multipartFile.getInputStream(), dfile, StandardCopyOption.REPLACE_EXISTING);
				FileUpload file2 = new FileUpload(fileName, dfile.toString(), Message.FILE_ADDED,person);
				if (person.getPersonId() >99) {
					file2.setFileId(person.getFiles().get(i++).getFileId());
				}
				files.add(file2);
			} catch (Exception exception) {
				throw new FileNotFoundException(Message.FILE_NOT_FOUND);
			}
		}
		person.setFiles(files);
		EmergencyContact emergencyContact = person.getEmergencyContact();
		emergencyContact.setPerson(person);
		List<RelationShip> relationShips = emergencyContact.getRelationShips();
		emergencyContact.setRelationShips(relationShips);
		EmergencyContact save = emergencyContactRepository.save(emergencyContact);
		person.setEmergencyContact(save);
		Person person2 = personRepository.save(person);
		for (RelationShip relationShip : relationShips) {
			relationShip.setEmergencyContact(save);
			relationShipRepository.save(relationShip);
		}
		for (FileUpload file : files) {
			file.setPerson(person2);
			fileRepository.save(file);
		}
		return person2;
	}

	@Override
	public Person getPerson(int personId) {
		return personRepository.findByPersonId(personId);
	}

	@Override
	public Person deletePerson(int personId) {
		Person person = personRepository.findByPersonId(personId);
		if (person != null) {
			String urlPath = person.getPersonName() + person.getContactNumber();
			this.dirLocation = getPath(urlPath);
			try {
				FileUtils.forceDelete(new File(this.dirLocation.toString()));
			} catch (IOException e) {
				throw new FileNotFoundException(Message.FILE_NOT_FOUND);
			}
			List<FileUpload> files = person.getFiles();
			EmergencyContact emergencyContact = person.getEmergencyContact();
			List<RelationShip> relationShips = emergencyContact.getRelationShips();
			emergencyContactRepository.delete(emergencyContact);
			relationShipRepository.deleteAll(relationShips);
			fileRepository.deleteAll(files);
			personRepository.delete(person);
			return person;
		}
		log.error(Message.ID_NOT_FOUND);
		throw new CustomException(Message.ID_NOT_FOUND);
	}

}
