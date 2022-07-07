package org.dblock;

import java.io.IOException;
import java.net.URI;

import software.amazon.awssdk.http.SdkHttpFullRequest;
import software.amazon.awssdk.http.SdkHttpMethod;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.utils.StringInputStream;

public final class AmazonOpenSearchServiceSample extends Sample {

    private static final String ENDPOINT = "https://search-dblock-test-opensearch-21-tu5gqrjd4vg4qazjsu6bps5zsy.us-west-2.es.amazonaws.com";
    private static final Region REGION = Region.US_WEST_2;

    public static void main(final String[] args) throws IOException {
        AmazonOpenSearchServiceSample sample = new AmazonOpenSearchServiceSample();
        sample.getInstanceInfo();
        sample.indexDocument();
    }

    private void getInstanceInfo() throws IOException {
        SdkHttpFullRequest request = SdkHttpFullRequest.builder()
                .method(SdkHttpMethod.GET)
                .uri(URI.create(ENDPOINT))
                .build();
        makeRequest("es", REGION, request);
    }

    private void indexDocument() throws IOException {
        String payload = "{\"test\": \"val\"}";
        SdkHttpFullRequest request = SdkHttpFullRequest.builder()
                .method(SdkHttpMethod.POST)
                .uri(URI.create(ENDPOINT + "/index_name/type_name/document_id"))
                .appendHeader("Content-Type", "application/json")
                .contentStreamProvider(() -> new StringInputStream(payload))
                .build();
        makeRequest("es", REGION, request);
    }
}
