package com.zoyberlo.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    public static String httpRequest(String url) throws IOException {
        HttpURLConnection connection;
        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect();

        StringBuilder buf = new StringBuilder();
        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            buf.append(line).append("rn");
        }
        inputStream.close();
        connection.disconnect();
        return buf.toString();
    }
}
