package me.dizzy.api;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestApi {

    public JSONObject sendRequest(String apiUrl) {
        try {

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                return null;
            }

            String responseBody = response.body();

            return new JSONObject(responseBody);

        } catch (Exception ignore) {

        }
        return null;
    }


    public int sendPremiumRequest(String nickName) {

        String apiUrl = "https://api.ashcon.app/mojang/v2/user/" + nickName;

        System.out.println("API URL: " + apiUrl);

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            return response.statusCode();
        } catch (Exception ignore) {

        }

        return 0;
    }
}
