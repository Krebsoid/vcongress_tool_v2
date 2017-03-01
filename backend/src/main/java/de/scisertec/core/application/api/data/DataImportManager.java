package de.scisertec.core.application.api.data;

public interface DataImportManager {

    Boolean isAlreadyImported(Class importClass);
    void markAsImported(Class importClass);

}
