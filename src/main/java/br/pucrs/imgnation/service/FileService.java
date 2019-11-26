package br.pucrs.imgnation.service;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    PutObjectResult uploadObject(MultipartFile file) throws Exception;

    ObjectListing listObjects();

    S3Object getObject(String objectKey);

    List<String> getAllItensS3(String bucketName);
}
