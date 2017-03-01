package de.scisertec.core.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;

@Entity
@Table(name = "dataimport")
public class DataImport extends DomainModel<DataImport> {

    String importCls;

    public static DataImport create(Class importedClass) {
        DataImport dataImportBean = new DataImport();
        dataImportBean.importCls = Arrays.asList(importedClass.getSimpleName().split("[$]")).get(0);
        return dataImportBean.construct();
    }

    public String getImportCls() {
        return importCls;
    }

    public void setImportCls(String importCls) {
        this.importCls = importCls;
    }

    @Override
    public DataImport self() {
        return this;
    }
}
