package com.alura.literalura.services;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;

public class GutendexClient {


    public String getDataGutendex(String address) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(Redirect.ALWAYS) // <- ESSENCIAL!
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro na requisição", e);
        }
    }


}
