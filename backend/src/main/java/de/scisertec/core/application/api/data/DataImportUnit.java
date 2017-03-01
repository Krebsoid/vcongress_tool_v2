package de.scisertec.core.application.api.data;


import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;

@ApplicationScoped
public abstract class DataImportUnit {

    private static final Logger logger = Logger.getLogger(DataImportUnit.class.getName());

    @Inject
    DataImportManager dataImportManager;

    protected Boolean isAlreadyImported() {
        return dataImportManager.isAlreadyImported(importUnitClass());
    }

    protected void markAsImported() {
        dataImportManager.markAsImported(importUnitClass());
    }

    public void startImport() {
        if (!isAlreadyImported()) {
            logger.info("Import started for " + printPrettyClassName());
            initialize();
            logger.info("Import finished for " + printPrettyClassName());
            markAsImported();
        }
    }

    protected abstract void initialize();

    protected abstract Class importUnitClass();

    private String printPrettyClassName() {
        return Arrays.asList(importUnitClass().getSimpleName().split("[$]")).get(0);
    }

}
