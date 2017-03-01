
package de.scisertec.person.client.resource.impl;

import de.scisertec.person.application.api.model.representation.CountryRepresentation;
import de.scisertec.person.application.impl.model.representation.CountryToRepresentation;
import de.scisertec.person.client.resource.api.CountryResource;
import de.scisertec.person.infrastructure.repository.CountryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CountryResourceBean implements CountryResource {

    @Inject
    CountryRepository countryRepository;

    @Override
    public List<CountryRepresentation> getCountries() {
        return countryRepository.findAll().stream().map(CountryToRepresentation::new).collect(Collectors.toList());
    }
}
