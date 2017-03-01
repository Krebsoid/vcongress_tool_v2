package de.scisertec.event.client.model.request;

import de.scisertec.event.application.api.model.command.EventLogoUploadCommand;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

public class EventLogoUploadRequest implements EventLogoUploadCommand {

    @NotEmpty
    @Size(max = 5242880)
    @FormParam("file")
    @PartType("image/*")
    byte[] data;

    @Override
    public byte[] getData() {
        return data;
    }

}
