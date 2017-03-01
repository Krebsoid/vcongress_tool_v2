
package de.scisertec.person.client.resource.api;

import de.scisertec.account.application.api.security.LoggedIn;
import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;
import de.scisertec.person.client.model.PersonAdminEditRequest;
import de.scisertec.person.client.model.PersonEditRequest;
import de.scisertec.person.client.model.InvoiceAddressEditRequest;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@LoggedIn
@Path("person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PersonEditResource extends Resource {

    @POST
    @Path("edit")
    PersonRepresentation editPerson(@Valid PersonEditRequest personEditRequest);

    @POST
    @Path("edit/invoice-address")
    PersonRepresentation editPersonInvoiceAddress(@Valid InvoiceAddressEditRequest invoiceAddressEditRequest);

    @POST
    @Path("edit/{id}")
    PersonRepresentation editPerson(@PathParam("id") Long personId, @Valid PersonEditRequest personEditRequest);

    @POST
    @Path("edit/{id}/admin")
    PersonRepresentation editAdmin(@PathParam("id") Long personId, @Valid PersonAdminEditRequest personEditRequest);

    @GET
    @NoCache
    @Path("{id}")
    PersonRepresentation getPerson(@PathParam("id") Long personId);

    @GET
    @NoCache
    PersonRepresentation activeState();

}
