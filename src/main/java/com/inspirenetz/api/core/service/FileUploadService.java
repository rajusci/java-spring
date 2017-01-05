package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.FileUploadResponse;
import com.inspirenetz.api.core.dictionary.ImageUploadResponse;
import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface FileUploadService {

    public File getTempFile(String name);
    public String getExtension(String filename);
    public String getFileUploadPathForType(Integer uploadType);
    public ImageUploadResponse uploadImage(MultipartFile file, String username, String authentication, Integer imageType, String filename) throws InspireNetzException;

    FileUploadResponse uploadFile(MultipartFile file, String username, String authentication, Integer fileType, String filename) throws InspireNetzException;
    FileUploadResponse bulkUploadFile(MultipartFile file, String username, String authentication, Integer fileType, String filename) throws InspireNetzException;
}
