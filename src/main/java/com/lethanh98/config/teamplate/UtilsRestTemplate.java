package com.lethanh98.config.teamplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@Slf4j
public class UtilsRestTemplate {

    public void trace(HttpRequest request, byte[] body, String domain, ClientHttpResponse response) throws IOException {
        StringBuilder str = new StringBuilder();
        try {
            // request
            str.append("\n Request = ").append(" \n")
                    .append("To Service : ").append(domain).append(" \n")
                    .append("Request to : ").append(request.getURI().toString()).append(" \n")
                    .append("Method     : ").append(request.getMethodValue()).append(" \n")
                    .append("Header     : ").append(request.getHeaders()).append(" \n")
                    .append("Body       : ").append(new String(body, "UTF-8")).append(" \n")
                    .append(" \n")
            ;

            str.append("Response = ").append(" \n")
                    .append("Status code  : {}").append(response.getStatusCode()).append(" \n")
                    .append("Headers      : {}").append(response.getHeaders()).append(" \n");
            //Response
            StringBuilder inputStringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));

            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }

            str.append("Response body: {}").append(inputStringBuilder.toString()).append(" \n")
                    .append(" \n")
            ;

        } catch (Exception ignored) {

        } finally {
            log.info(str.toString());

        }
    }

    public void addHeader(HttpHeaders header, String headerName, String headerValue) {
        if (!header.containsKey(headerName)) {
            header.add(headerName, headerValue);
        }
    }

}
