package de.scisertec.person.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;

import javax.persistence.Entity;

@Entity
public class Occupation extends DomainModel<Occupation> {

    String institute = "";
    String department = "";
    String position = "";

    public String institute() {
        return institute != null ? institute : "";
    }
    public Occupation institute(String institute) {
        this.institute = institute;
        return this;
    }

    public String department() {
        return department != null ? department : "";
    }
    public Occupation department(String department) {
        this.department = department;
        return this;
    }

    public String position() {
        return position != null ? position : "";
    }
    public Occupation position(String position) {
        this.position = position;
        return this;
    }

    @Override
    public Occupation self() {
        return this;
    }
}
