package it.e.pgwt.client.shared;

/**
 * Created by mancini on 21/06/16.
 */

import org.jboss.errai.security.shared.api.annotation.RestrictedAccess;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("customers")
public interface CustomerService {

    @GET

    @Path("/{id}")

    @Produces(MediaType.APPLICATION_JSON)

    @RestrictedAccess(roles = "ROLE000")

    public Customer retrieveCustomerById(@PathParam("id") long id);

}
