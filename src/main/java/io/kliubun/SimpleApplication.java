package io.kliubun;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@RestController
@SpringBootApplication
public class SimpleApplication {

    private AmazonS3 s3;

    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }

    @PostConstruct
    public void init() {
       s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(InstanceProfileCredentialsProvider.getInstance())
                .build();

    }

    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String index() throws IOException {
        final S3Object simpleResponse = s3.getObject("igneuslynx", "simple.json");
        final S3ObjectInputStream objectContent = simpleResponse.getObjectContent();
        final String content = IOUtils.toString(objectContent, StandardCharsets.UTF_8);
        return content;
    }

}
