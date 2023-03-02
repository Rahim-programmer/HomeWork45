package kz.attractor.java.HW44;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.HW44.Lesson45Server;
import kz.attractor.java.server.Cookie;

import java.io.IOException;
import java.util.*;

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
        renderTemplate(exchange, "cookie.ftlh", data);
    }

    private void BooksHandler(HttpExchange exchange) {
        Map<String, Object> map = new HashMap<>();
        String queryParams = getQueryParams(exchange);
        Map<String, String> params = ProgramUtility.parseUrl(queryParams, "&");
        if (params.get("id") != null) {
            var books = new BookDataModel().getBooks();
            for (Book book : books) {
                if (book.getId().equals(params.get("id"))) {
                    map.put("book", book);
                    break;
                }
            }
            if (map.get("book") != null) {
                renderTemplate(exchange, "book.html", map);
            } else {
                respond404(exchange);
            }
        }
    }
}



