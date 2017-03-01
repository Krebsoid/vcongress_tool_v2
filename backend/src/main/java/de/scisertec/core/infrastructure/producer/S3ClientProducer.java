package de.scisertec.core.infrastructure.producer;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import de.scisertec.core.application.api.environment.ConfigurationManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class S3ClientProducer {

    AmazonS3Client amazonS3Client = null;

    @Inject
    ConfigurationManager configurationManager;

    @Produces
    public AmazonS3Client getAmazonS3Client() {
        if(amazonS3Client == null) {
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(configurationManager.getProperty("amazon.s3.accessKey"),
                    configurationManager.getProperty("amazon.s3.secretKey"));
            amazonS3Client = new AmazonS3Client(awsCreds);
            amazonS3Client.setRegion(Region.getRegion(Regions.EU_WEST_1));
            return amazonS3Client;
        } else {
            return amazonS3Client;
        }
    }

}
