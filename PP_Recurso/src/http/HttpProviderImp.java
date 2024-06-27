/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package http;

import com.estg.io.HTTPProvider;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author fabio
 */
public class HttpProviderImp {

    private HTTPProvider httpProvider;

    public HttpProviderImp() {
        this.httpProvider = new HTTPProvider();
    }

    public String getAidBoxes() throws IOException, ParseException {
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/aidboxes";
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONArray aidboxes = (JSONArray) parser.parse(jsonResponse);
        return aidboxes.toJSONString();
    }

    public String getTypes() throws IOException, ParseException {
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/types";
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONArray types = (JSONArray) parser.parse(jsonResponse);
        return types.toJSONString();
    }

    public String getDistancesAidbox() throws IOException, ParseException {
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/distances";
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONArray distances = (JSONArray) parser.parse(jsonResponse);
        return distances.toJSONString();
    }

    public String getContainers() throws IOException, ParseException {
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/containers";
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONArray containers = (JSONArray) parser.parse(jsonResponse);
        return containers.toJSONString();
    }

    public String getVehicles() throws IOException, ParseException {
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/vehicles";
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONArray vehicles = (JSONArray) parser.parse(jsonResponse);
        return vehicles.toJSONString();
    }

    public String getReadings() throws ParseException, IOException {
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/readings";
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONArray readings = (JSONArray) parser.parse(jsonResponse);
        return readings.toJSONString();

    }
}
