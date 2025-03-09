package org.example.shopquanao.uploadfile.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service

public interface UploadImageFile {

    String uploadImage(MultipartFile file) throws IOException;
}
