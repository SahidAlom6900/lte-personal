package com.technoelevate.file.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.technoelevate.file.dto.Person;

public interface FileUploadService {

	Person savePerson(List<MultipartFile> multipartFiles,@Valid Person person);

	Person getPerson(int personId);

	Person deletePerson(int personId);
}
