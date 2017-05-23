package main.java;

/**
 * Created by basva on 2-5-2017.
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.IOException;


@Path("/")
public class CalendarApi {
    Quickstart quickstart = new Quickstart();

    @Path("/calendar")
    @GET
    @Produces("application/json")
    public String test() throws Exception {
        return quickstart.calendar();
    }
}