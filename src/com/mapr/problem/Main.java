package com.mapr.problem;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class Main {

    private static final String FEATURES = "features";
    private static final String PROPERTIES = "properties";
    private static final String PLACE = "place";
    private static final String MAGNITUDE = "mag";
    private static final String TIME = "time";

    /**
     * Date formatter
     * @param date
     * @return formatted date
     */
    private static String getFormattedDate(Long date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ'+00:00'");
        return sdf.format(new Date(date));
    }

    /**
     *  Generates list of earthquakes for last week.
     * @param jsonFile
     * @return list of earthquakes from last week.
     */
   private static List<Earthquake> getLastWeekEarthquakes(String jsonFile) {

       List<Earthquake> earthquakes = new ArrayList<>();

       try {
           JSONParser parser = new JSONParser();
           Object obj = parser.parse(new FileReader(jsonFile));

           JSONObject jsonObject = (JSONObject) obj;
           JSONArray features = (JSONArray) jsonObject.get(FEATURES);
           LastweekDateRange dateRange = new LastweekDateRange();

           for (int i = 0; i < features.size(); i++) {

               JSONObject feature = (JSONObject) features.get(i);
               JSONObject properties = (JSONObject) feature.get(PROPERTIES);

               Long timeOfEarthquake = (Long) properties.get(TIME);

               // if the date of earthquake not in last week then skip.
               if (!dateRange.isDateInLastWeek(timeOfEarthquake)) {
                   continue;
               }

               String place = (String) properties.get(PLACE);
               Object magnitudeObj = properties.get(MAGNITUDE);
               Double magnitude = 0.0;

               if (magnitudeObj instanceof Long) {
                   magnitude = ((Long) magnitudeObj).doubleValue();
               } else {
                   magnitude = (Double) magnitudeObj;
               }

               earthquakes.add(new Earthquake(place, magnitude, timeOfEarthquake));

           }
       } catch (Exception e) {
           e.printStackTrace();
       }

       return earthquakes;
   }

   private static void printEarthquakesInfo(String jsonFile) {

       List<Earthquake> earthquakes = getLastWeekEarthquakes(jsonFile);
       Collections.sort(earthquakes);

       for (Earthquake earthquake : earthquakes) {
           System.out.println(getFormattedDate(earthquake.getTime()) + "|" + earthquake.getPlaceOfEarthquake()
           + "| Magnitude: " + earthquake.getMagnitude());
       }

   }

    public static void main(String[] args) {
        printEarthquakesInfo("C:/mapr/src/com/mapr/problem/all_week.geojson");
    }
}
