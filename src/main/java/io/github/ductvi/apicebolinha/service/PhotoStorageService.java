package io.github.ductvi.apicebolinha.service;

import io.github.ductvi.apicebolinha.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class PhotoStorageService {

    private final RestClient client;
    private final String publicBaseUrl;

    public PhotoStorageService(
            @Value("${supabase.url}")
            String url,

            @Value("${supabase.service-key}")
            String key,

            @Value("${supabase.bucket}")
            String bucket
    ) {
        this.client = RestClient.builder()
                .baseUrl(url + "/storage/v1/object/" + bucket)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + key)
                .build();

        this.publicBaseUrl = url + "/storage/v1/object/public/" + bucket;
    }

    public String upload(UUID recipeId, MultipartFile file) {
        String path = "recipes/" + recipeId;

        try {
            client.post()
                    .uri("/" + path)
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header("x-upsert", "true")
                    .body(file.getBytes())
                    .retrieve()
                    .toBodilessEntity();
        } catch (IOException | RestClientException e) {
            throw new StorageException("Não foi possível salvar a foto!");
        }

        return publicBaseUrl + "/" + path + "?v=" + System.currentTimeMillis();
    }
}
