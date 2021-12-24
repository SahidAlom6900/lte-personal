package com.technoelevate.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoelevate.file.dto.FileUpload;

@Repository
public interface FileRepository extends JpaRepository<FileUpload, Integer>{
    FileUpload findByFileId(int fileId);
}