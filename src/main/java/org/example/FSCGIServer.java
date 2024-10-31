package org.example;

import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class FSCGIServer {
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
                        Contetn-Type: application/javascript
                        Content-Lenght: %d
                        
                        %s
                        """.formatted(content.getBytes(StandardCharsets.UTF_8).length, content);
                System.out.println(httpResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readRequestBody() throws IOException {
        FCGIInterface.request.inStream.fill();

        var contentLength = FCGIInterface.request.inStream.available();
        var buffer = ByteBuffer.allocate(contentLength);
        var readBytes = FCGIInterface.request.inStream.read(buffer.array(), 0, contentLength);

        var requestBodyRaw = new byte[readBytes];
        buffer.get(requestBodyRaw);
        buffer.clear();

        return new String(requestBodyRaw, StandardCharsets.UTF_8);
    }
}