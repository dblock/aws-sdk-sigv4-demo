package org.dblock;

import java.io.IOException;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.signer.Aws4Signer;
import software.amazon.awssdk.auth.signer.AwsSignerExecutionAttribute;
import software.amazon.awssdk.core.interceptor.ExecutionAttributes;
import software.amazon.awssdk.http.HttpExecuteRequest;
import software.amazon.awssdk.http.HttpExecuteResponse;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.SdkHttpFullRequest;
import software.amazon.awssdk.http.SdkHttpResponse;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.utils.IoUtils;

public abstract class Sample {
    void makeRequest(final String serviceName, final Region region, final SdkHttpFullRequest request) throws IOException {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire", "DEBUG");

        SdkHttpClient httpClient = ApacheHttpClient.builder().build();
        Aws4Signer signer = Aws4Signer.create();
        ExecutionAttributes attrs = new ExecutionAttributes()
                .putAttribute(AwsSignerExecutionAttribute.AWS_CREDENTIALS,
                        DefaultCredentialsProvider.create().resolveCredentials())
                .putAttribute(AwsSignerExecutionAttribute.SERVICE_SIGNING_NAME, serviceName)
                .putAttribute(AwsSignerExecutionAttribute.SIGNING_REGION, region);
        SdkHttpFullRequest signedRequest = signer.sign(request, attrs);
        HttpExecuteRequest.Builder rb = HttpExecuteRequest.builder().request(signedRequest);
        request.contentStreamProvider().ifPresent(c -> rb.contentStreamProvider(c));
        HttpExecuteResponse executeResponse = httpClient.prepareRequest(rb.build()).call();

        SdkHttpResponse response = executeResponse.httpResponse();
        System.out.println(response.statusCode() + " " + response.statusText());
        if (!response.isSuccessful()) {
            throw new RuntimeException(response.statusCode() + " " + response.statusText());
        }

        System.out.println(IoUtils.toUtf8String(executeResponse.responseBody().orElse(null)));
        httpClient.close();
    }
}
