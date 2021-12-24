package com.technoelevate.file.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technoelevate.file.common.Message;
import com.technoelevate.file.dto.Person;
import com.technoelevate.file.exception.CustomException;
import com.technoelevate.file.response.ResponseMessage;
import com.technoelevate.file.service.FileUploadService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequestMapping("api/v1/file-upload")
public class FileUploadConttroller {
	@Autowired
	private FileUploadService fileUploadService;
	@Value("${file.upload.location}")
	private String dir;

	@Autowired
	private ObjectMapper mapper;

	@PostMapping("/add-person")
	public ResponseEntity<ResponseMessage> savePerson(@Valid @RequestParam("files") MultipartFile[] files,@RequestParam("person") String person1) {
		List<MultipartFile> multipartFiles = Arrays.asList(files).stream().collect(Collectors.toList());
		Person person=new Person();
		try {
			person = mapper.readValue(person1, Person.class);
		} catch (JsonProcessingException e) {
			log.error(Message.SOMETHING_WENT_WRONG);
			throw new CustomException(Message.SOMETHING_WENT_WRONG);
		}
		Person savePerson = fileUploadService.savePerson(multipartFiles, person);
		if (savePerson != null) {
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
					Message.FILE_ADDED, savePerson);
			log.info(Message.FILE_ADDED);
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		log.error(Message.SOMETHING_WENT_WRONG);
		throw new CustomException(Message.SOMETHING_WENT_WRONG);
	}

	@GetMapping(path = "/person/{personId}")
	public ResponseEntity<ResponseMessage> getPerson(@PathVariable("personId") int personId) {
		Person savePerson = fileUploadService.getPerson(personId);
		if (savePerson != null) {
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
					Message.FETCH_DATA, savePerson);
			log.info(Message.FETCH_DATA);
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		log.error(Message.SOMETHING_WENT_WRONG);
		throw new CustomException(Message.SOMETHING_WENT_WRONG);	
	}
	
	@DeleteMapping(path = "/person/{personId}")
	public ResponseEntity<ResponseMessage> deletePerson(@PathVariable("personId") int personId) {
		Person savePerson = fileUploadService.deletePerson(personId);
		if (savePerson != null) {
			ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
					Message.DATA_DELETE, savePerson);
			log.info(Message.DATA_DELETE);
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		log.error(Message.SOMETHING_WENT_WRONG);
		throw new CustomException(Message.SOMETHING_WENT_WRONG);
	}
}
