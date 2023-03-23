package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.fileUpload;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;


@RestController
@CrossOrigin
@Transactional
public class FileController {
    @Autowired
    FileUploadService uploadService;


    @PostMapping(path= "/fileUpload/")
    public ResponseEntity<Object> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        return uploadService.fileUploadEntryPoint(file);
    }

    //


}