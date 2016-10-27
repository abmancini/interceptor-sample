package it.e.pgwt.server;


import javax.ws.rs.Path;

import it.e.pgwt.client.shared.CustomerService;
import it.e.pgwt.client.shared.Customer;

/**
 * Created by mancini on 21/06/16.
 */

@Path("customers")
public class CustomerServiceImpl implements CustomerService {

    @Override
    public Customer retrieveCustomerById(long id) {


        System.err.println("received request for id:" + id);


        if(id == 0) {
            //error case
            throw  new RuntimeException("Userid 0, throwing error !");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Customer c =  new Customer("NomeDelCustomer " + id );
        return c;
    }
}
