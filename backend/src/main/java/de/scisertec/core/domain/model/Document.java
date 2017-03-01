package de.scisertec.core.domain.model;

public interface Document extends TechnicalId {

    String getName();
    String getPath();
    String getExtension();
    String getDocType();

    String getLocale();

    Double getSize();

    byte[] getData();

    Document save();
    Document updateData();

}
