package br.pucrs.imgnation.service;

import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.util.List;

public interface AWSS3Service {

    boolean doesBucketExist(String bucketName);

    Bucket createBucket(String bucketName);

    List<Bucket> listBuckets();

    void deleteBucket(String bucketName);

    PutObjectResult putObject(String bucketName, String key, File file);

    ObjectListing listObjects(String bucketName);

    S3Object getObject(String bucketName, String objectKey);

    CopyObjectResult copyObject(String sourceBucketName, String sourceKey, String destinationBucketName, String destinationKey);

    void deleteObject(String bucketName, String objectKey);

    DeleteObjectsResult deleteObjects(DeleteObjectsRequest delObjReq);
}
