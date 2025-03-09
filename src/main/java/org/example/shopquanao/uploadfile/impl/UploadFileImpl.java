package org.example.shopquanao.uploadfile.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadFileImpl implements UploadImageFile {
    private static final Logger log = LoggerFactory.getLogger(UploadFileImpl.class);

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File không hợp lệ!");
        }

        String publicValue = generatePublicValue(file.getOriginalFilename());
        File fileUpload = convert(file);

        log.info("File uploaded: {}", fileUpload);

        Map uploadResult = cloudinary.uploader().upload(fileUpload, ObjectUtils.asMap("public_id", publicValue));

        cleanDisk(fileUpload);

        return uploadResult.get("secure_url").toString();
    }

    private void cleanDisk(File file) {
        try {
            log.info("Deleting file: {}", file.toPath());
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            log.error("Error deleting file", e);
        }
    }

    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, convFile.toPath());
        }
        return convFile;
    }

    private String generatePublicValue(String originalName) {
        return UUID.randomUUID().toString();
    }
}
