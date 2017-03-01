package de.scisertec.core.application.impl.data;


import de.scisertec.core.application.api.data.DataImportManager;
import de.scisertec.core.domain.model.DataImport;
import de.scisertec.core.infrastructure.repository.DataImportRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DataImportManagerBean implements DataImportManager {

    @Inject
    protected DataImportRepository repository;

    public Boolean isAlreadyImported(Class importedClass) {
        return repository.findByImportCls(importedClass.getSimpleName()) != null;
    }

    public void markAsImported(Class importClass) {
        DataImport.create(importClass).save();
    }
}
