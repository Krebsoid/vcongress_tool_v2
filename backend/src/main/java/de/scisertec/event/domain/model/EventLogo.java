package de.scisertec.event.domain.model;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import de.scisertec.core.domain.model.base.DomainModel;
import de.scisertec.event.domain.model.event.EventLogoRemoved;
import de.scisertec.event.domain.model.event.EventLogoUploaded;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Entity
public class EventLogo extends DomainModel<EventLogo> {

    String linkLarge;
    String linkSmall;

    String website;

    @OneToOne(mappedBy = "eventLogo")
    Event event;

    @Inject
    @Transient
    AmazonS3Client amazonS3Client;

    public EventLogo event(Event event) {
        this.event = event;
        return this;
    }

    public EventLogo linkSmall(String linkSmall) {
        this.linkSmall = linkSmall;
        return this;
    }
    public String linkSmall() {
        return linkSmall;
    }

    public EventLogo linkLarge(String linkLarge) {
        this.linkLarge = linkLarge;
        return this;
    }
    public String linkLarge() {
        return linkLarge;
    }

    public EventLogo logo(byte[] data) {
        this.construct();
        if(data != null) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            BufferedImage bufferedImage;
            try {
                bufferedImage = ImageIO.read(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return this;
            }
            BufferedImage bufferedImage1 = Scalr.resize(bufferedImage, Scalr.Mode.FIT_TO_HEIGHT, 70);
            BufferedImage bufferedImage2 = Scalr.resize(bufferedImage, Scalr.Mode.FIT_TO_HEIGHT, 120);

            ByteArrayOutputStream os1 = new ByteArrayOutputStream();
            ByteArrayOutputStream os2 = new ByteArrayOutputStream();
            try {
                ImageIO.write(bufferedImage1, "png", os1);
                ImageIO.write(bufferedImage2, "png", os2);
            } catch (IOException e) {
                e.printStackTrace();
                return this;
            }
            InputStream bufferedImageStream1 = new ByteArrayInputStream(os1.toByteArray());
            InputStream bufferedImageStream2 = new ByteArrayInputStream(os2.toByteArray());


            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(os1.toByteArray().length);
            objectMetadata.setContentType("image/png");
            amazonS3Client.putObject("static.vcongress.de", "cms/" + event.identifier() + "_70.png", bufferedImageStream1, objectMetadata);
            amazonS3Client.setObjectAcl("static.vcongress.de", "cms/" + event.identifier() + "_70.png", CannedAccessControlList.PublicRead);
            ObjectMetadata objectMetadata2 = new ObjectMetadata();
            objectMetadata2.setContentLength(os2.toByteArray().length);
            objectMetadata2.setContentType("image/png");
            amazonS3Client.putObject("static.vcongress.de", "cms/" + event.identifier() + "_120.png", bufferedImageStream2, objectMetadata2);
            amazonS3Client.setObjectAcl("static.vcongress.de", "cms/" + event.identifier() + "_120.png", CannedAccessControlList.PublicRead);
            this.linkLarge = "http://static.vcongress.de/cms/" + event.identifier() + "_120.png";
            this.linkSmall = "http://static.vcongress.de/cms/" + event.identifier() + "_70.png";
            EventLogoUploaded.create(this);
        } else {
            amazonS3Client.deleteObject("static.vcongress.de", "cms/" + event.identifier() + "_120.png");
            amazonS3Client.deleteObject("static.vcongress.de", "cms/" + event.identifier() + "_70.png");
            this.linkLarge = null;
            this.linkSmall = null;
            EventLogoRemoved.create(this);
        }
        return this;
    }

    @Override
    public EventLogo self() {
        return this;
    }
}
