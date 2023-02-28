package kz.attractor.java.HW44;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.HW44.Lesson45Server;
import kz.attractor.java.server.Cookie;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Lesson46Server extends Lesson45Server {

    public Lesson46Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/cookie", this::cookieRender);
    }

    protected void cookieRender(HttpExchange exchange) {
        Cookie sessionCookie = Cookie.make("User-ID", generateID);
        Map<String, Object> data = new HashMap<>();
        sessionCookie.setMaxAge(600);
        sessionCookie.setHttpOnly(true);

        setCookie(exchange, sessionCookie);
        getCookies(exchange);
        renderTemplate(exchange, "cookie.html", data);
    }


}


