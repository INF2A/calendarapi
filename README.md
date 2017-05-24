# Calendar API

This api is made for project Cluster & SmartMirror, it is designed to run on a raspberry pi in a docker container. 
the application uses a tomcat server in order to work, the default port is 8080. 
make sure to setup a tomcat server if you havent already. 
this is a RESTfull api which will return calendar items in JSON format including the discription, startTime and the endTime of events
this api will only return events of the current day + 24 hours.

# Setup tomcat server
https://www.jetbrains.com/help/idea/2017.1/creating-and-running-your-first-web-application.html

# Examples

do the following to make a api call when the application is running
<strong>NOTE: first time you run this application, you should see a google login screen requesting permission for "Cluster & SmartMirror Calendar API". log in with your google account and accept this.</strong><br>
<br>
<b>connect to the application(should start when you run the application):</b><br>
{ip_cluster}:{port_api}/<br>
http://localhost:8080/<br>

<b>to get the calendar items</b><br> 
{ip_cluster}:{poort_api}/calendar <br>
http://localhost:8080/calendar<br>

<b>JSON feed example:</b> 
{"calendar":[<br>
{<br>
"discription":"Groepswerk_WC.2",<br>
"starttime":"2017-05-24T09:15:00.000+02:00",<br>
"endtime":"2017-05-24T14:15:00.000+02:00"<br>
},<br>
{<br>
"discription":"OIC#2_WC.2.G",<br>
"starttime":"2017-05-24T10:15:00.000+02:00",<br>
"endtime":"2017-05-24T11:45:00.000+02:00"<br>
},<br>
{<br>
"discription":"OIC#2_WC.2.O",<br>
"starttime":"2017-05-24T11:45:00.000+02:00",<br>
"endtime":"2017-05-24T13:15:00.000+02:00"<br>
},<br>
{<br>
"discription":"Hemelvaart/Ascension Day",<br>
"starttime":"2017-05-25T08:30:00.000+02:00",<br>
"endtime":"2017-05-25T17:00:00.000+02:00"<br>
},<br>
{<br>
"discription":"Hemelvaartsdag",<br>
"starttime":"null",<br>
"endtime":"null"<br>
}]<br>
}<br>

# Cluster

more information about the cluster: https://github.com/INF2A/RPI-docker-cluster

# SmartMirror

more information about the SmartMirror: https://github.com/INF2A/Smart-mirror
