package de.scisertec.event.application.api.service;

import de.scisertec.core.application.api.model.service.Service;
import de.scisertec.event.application.api.model.command.PdfUploadCommand;

public interface PdfUploadService extends Service {

    void uploadPdfDocument(String identifier, PdfUploadCommand pdfUploadCommand);

}
