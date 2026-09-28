package com.example.spring_member.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import jakarta.annotation.PostConstruct;

@Service
public class S3Service {

    private final S3Client s3Client;

    public S3Service() {
        this.s3Client = S3Client.builder()
                .region(Region.AP_NORTHEAST_2)
                .build();
    }
    @PostConstruct
    public void listObjects() {
    s3Client.listObjectsV2(builder -> builder
            .bucket("aws-portfolio-s3-794813215931"))
            .contents()
            .forEach(object ->
                    System.out.println("S3 Object: " + object.key())
            );
}
}