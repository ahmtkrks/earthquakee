package com.example.earthquakee.API;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class ApiClient {

    private static final String API_URL = "https://afk-backend-production.up.railway.app/";

    public static String saveToken(String token) throws IOException {
        URL url = new URL(API_URL+ "save");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        // Create the JSON request body
        String jsonBody = "{\"token\": \"" + token + "\" }";

        // Write the JSON body to the request
        DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
        outputStream.writeBytes(jsonBody);
        outputStream.flush();
        outputStream.close();

        // Get the response
        int responseCode = conn.getResponseCode();
        StringBuilder response = new StringBuilder();
        BufferedReader reader;

        if (responseCode == HttpURLConnection.HTTP_OK) {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Return the response as a string
        return response.toString();
    }



    public static String earthquake() throws IOException {
        String endpoint = API_URL + "tokens";

        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        // Get the response
        int responseCode = conn.getResponseCode();
        StringBuilder response = new StringBuilder();
        BufferedReader reader;

        if (responseCode == HttpURLConnection.HTTP_OK) {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Return the response as a string
        return response.toString();
    }
}
