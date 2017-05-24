package main.java;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Calendar {
    /** Application name. */
    private static final String APPLICATION_NAME =
            "Google Calendar API Java Calendar";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-calendar");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this calendar.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-calendar
     */
    private static final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    /** Authorizes the installed application to access user's protected data. */
    private static Credential authorize() throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(Calendar.class.getResourceAsStream("/client_secret.json")));
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
                Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(DATA_STORE_FACTORY)
                .build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar
    getCalendarService() throws Exception {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public String calendar() throws Exception {
        String json = "{\"calendar\":[\n";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        String date = dtf.format(localDate);
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
                getCalendarService();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        DateTime tomorrow = new DateTime(System.currentTimeMillis() + (1000 * 60 * 60 * 24));

        CalendarList calendarList = service.calendarList().list().execute();
        List<CalendarListEntry> calendarListEntries = calendarList.getItems();
        List<String> calendarIds = new ArrayList<>();
        for (CalendarListEntry event : calendarListEntries) {
            calendarIds.add(event.getId());
        }

        for (String calendarID : calendarIds) {
            Events events = service.events().list(calendarID)
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setTimeMax(tomorrow)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            if (items.size() == 0) {
            } else {
                for (Event event : items) {
                    DateTime start = event.getStart().getDateTime();
                    DateTime end = event.getEnd().getDateTime();
                    if (start == null) {
                        start = event.getStart().getDateTime();
                    }
                    json += "{\n\"discription\":" + "\"" + event.getSummary() + "\",\n" +
                            "\"starttime\":" + "\"" + start + "\",\n" +
                            "\"endtime\":" + "\"" + end + "\"\n" +"},\n";
                }
            }


        }
        json +="]\n";
        json = json.substring(0, json.lastIndexOf(","));
        json +="]\n";
        json +="}\n";
        return json;
    }

}