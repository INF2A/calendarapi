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
    @Path("/calendar/login")
    @GET
    @Produces("application/json")
    public String login() throws Exception {
        return "Click this link to log in: https://accounts.google.com/o/oauth2/auth?client_id=790527037592-5lrlt0utjd3432hj6p7tti0o0lg5m1s5.apps.googleusercontent.com&redirect_uri=http://localhost:8090/Callback&response_type=code&scope=https://www.googleapis.com/auth/calendar\n";
    }
}