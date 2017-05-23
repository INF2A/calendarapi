package main.java;

/**
 * Created by basva on 2-5-2017.
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/")
public class CalendarApi {
    Calendar calendar = new Calendar();

    /**
     * @return json containing the calendar events between now and 24 hours
     * @throws Exception
     */
    @Path("/calendar")
    @GET
    @Produces("application/json")
    public String getCalendar() throws Exception {
        return calendar.calendar();
    }
}