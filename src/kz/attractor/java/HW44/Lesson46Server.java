package kz.attractor.java.HW44;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.HW44.Lesson45Server;
import kz.attractor.java.server.Cookie;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lesson46Server extends Lesson45Server {
    public Lesson46Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/cookie", this::lesson46Handler);
    }

    private void lesson46Handler(HttpExchange exchange) {
        Cookie sessionCookie = Cookie.make("userId", "123");
        exchange.getResponseHeaders()
                .add("Set-Cookie", sessionCookie.toString());
        Map<String, Object> data = new HashMap<>();
        int times = 42;
        data.put("times", times);

        String cookieString = getCookies(exchange);
        Map<String, String> cookiees = Cookie.parse(cookieString);

        data.put("cookies", cookiees);

        String name = "times";
        String cookieStr = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieStr);

        String cookieValue = cookies.getOrDefault(name, "50");
        int timees = Integer.parseInt(cookieValue) + 1;

        Cookie response = new Cookie<>(name, timees);
        setCookie(exchange, response);
        data.put(name, timees);
        data.put("cookies", cookies);

        renderTemplate(exchange, "cookie.ftlh", data);
    }


}

