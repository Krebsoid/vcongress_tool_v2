package de.scisertec.person.domain.model;

import javax.persistence.Entity;

@Entity
public class PersonalizedAddress extends Address {

    String fullName;
    String company;
    String department;


    public String fullName() {
        return fullName;
    }
    public PersonalizedAddress fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String company() {
        return company;
    }
    public PersonalizedAddress company(String company) {
        this.company = company;
        return this;
    }

    public String department() {
        return department;
    }
    public PersonalizedAddress department(String department) {
        this.department = department;
        return this;
    }

    @Override
    public PersonalizedAddress self() {
        return this;
    }
}
