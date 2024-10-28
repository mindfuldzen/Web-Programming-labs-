package org.example;

import com.fastcgi.FCGIInterface;

import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        var fcgiInterface = new FCGIInterface();
        try {
            while (fcgiInterface.FCGIaccept() >= 0) {
                var content = """
                        <html>
                            <head><title>Java FastCGI Hello World</title></head>
                            <body><h1>Hello, World!</h1></body>
                        </html>""";
                var httpResponse = """
                        HTTP/1.1 200 OK
                        Contetn-Type: text/html
                        Content-Lenght: %d
                        
                        %s
                        """.formatted(content.getBytes(StandardCharsets.UTF_8).length, content);
                System.out.println(httpResponse);
                //System.out.println(fcgiInterface.request.params.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} //stop slide 123