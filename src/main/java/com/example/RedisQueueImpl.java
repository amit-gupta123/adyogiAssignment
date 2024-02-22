package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RedisQueueImpl implements QueueService{

   //pass "df97ae92fdd34d06a6982fa8242fbb68"
   // user name: us1-saving-anteater-41352.upstash.io

    private final String baseUrl;
    private final  String queueName;

    public RedisQueueImpl(String baseUrl, String queueName) {
        this.baseUrl = baseUrl;
        this.queueName = queueName;
    }


    private String doRequest(String url) throws IOException,InterruptedException{
      HttpClient httpClient = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(url))
              .build();
        HttpResponse<String> response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();
   }


    @Override
    public void push(String queueUrl, String messageBody) {
        Message item = new Message(messageBody);

         String url = baseUrl+"/RPUSH/"+queueName+"/"+item;
        try {
            doRequest(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message pull(String queueUrl) {
        String url = queueUrl+"/LPOP/"+queueName;
        try {
            String msg =  doRequest(url);
            return new Message(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(String queueUrl, String receiptId) {
        String url = baseUrl+"/LREM/"+queueName+"1"+receiptId;
        try {
            doRequest(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
