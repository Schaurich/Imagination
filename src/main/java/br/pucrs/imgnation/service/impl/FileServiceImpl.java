package br.pucrs.imgnation.service.impl;

import br.pucrs.imgnation.service.AWSS3Service;
import br.pucrs.imgnation.service.FileService;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private static final AWSCredentials credentials;

    private static String bucketName = "dbimgnation";

    @Autowired
    AWSS3Service awsService;

    public FileServiceImpl() {
        setAwsConnect();
    }

    @Override
    public PutObjectResult uploadObject(MultipartFile file) throws Exception {
        awsService.putObject(bucketName, file.getOriginalFilename(), createThumbnail(convert(file)));
        return null;
    }

    @Override
    public ObjectListing listObjects() {
        return awsService.listObjects(bucketName);
    }

    @Override
    public S3Object getObject(String objectKey) {
        return awsService.getObject("", objectKey);
    }

    @Override
    public List<String> getAllItensS3(String bucketName) {
//        return awsService.getAllItensS3(bucketName);
        return null;
    }

    private static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String getBucketName() {
        return "";
    }

    static {
        //put your accesskey and secretkey here
        credentials = new BasicAWSCredentials(
                "AKIAJZ4YBGW4237CGIOA",
                "+Gs/EpB0X18rhxziMb6h8FS9nJyxemj+FHl+sWdd"
        );
    }

    private void setAwsConnect() {
        //set-up the client
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();

        this.awsService = new AWSS3ServiceImpl(s3client);
    }

    private File createThumbnail(File file) throws Exception {
        File fileOut;
        BufferedImage img = ImageIO.read(file);
        BufferedImage thumb = new BufferedImage(100, 200,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = (Graphics2D) thumb.getGraphics();
        g2d.drawImage(img, 0, 0, thumb.getWidth() - 1, thumb.getHeight() - 1, 0, 0,
                img.getWidth() - 1, img.getHeight() - 1, null);
        g2d.dispose();
        ImageIO.write(thumb, "PNG", fileOut = new File("Thumb_"+file.getName()));
        return fileOut;
    }
}
