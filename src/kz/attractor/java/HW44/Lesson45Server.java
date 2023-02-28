package kz.attractor.java.HW44;

import com.sun.net.httpserver.HttpExchange;
import FileService.FileEmployeeService;
import kz.attractor.java.server.ContentType;
import kz.attractor.java.server.Cookie;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Lesson45Server extends Lesson44Server {
    protected Employee user = null;
    String generateID = String.valueOf(new Random().nextInt(99999) + 1);

    public Lesson45Server(String host, int port) throws IOException {
        super(host, port);

        registerGet("/login", this::loginGet);
        registerGet("/register", this::regGet);
        registerGet("/profile", this::profileGet);
        registerPost("/login", this::loginPost);
        registerPost("/register", this::registerPost);
    }

    private void loginGet(HttpExchange exchange) {
        Path path = makeFilePath("login.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void registerPost(HttpExchange exchange) {
        Map<String, Object> map = new HashMap<>();
        getContentType(exchange);
        String msg = getBody(exchange);
        Map<String, String> parsed = ProgramUtility.parseUrl(msg, "&");
        try {
            if (parsed.size() == Employee.class.getDeclaredFields().length - 5) {
                List<Employee> users = new ArrayList<>(FileEmployeeService.readFile());
                Employee employees = Employee.createUser(users.size() + 1, parsed);
                for (Employee employee : users) {
                        if (employee.getEmail().equals(parsed.get("email")) && employee.getPassword().equals(parsed.get("user-password"))) {
                            makeCookie(exchange, employee);
                            user = employee;

                    }
                }
                users.add(employees);
                FileEmployeeService.FileStore(users);
                redirect303(exchange, "/login");
            } else {
                map.put("fail", "Add new fields!");
                renderTemplate(exchange, "register.html", map);
            }
        } catch (Exception e) {
            e.printStackTrace();

            map.put("fail", "fatal error!");
            renderTemplate(exchange, "register.html", map);
        }
    }

    private void regGet(HttpExchange exchange) {
        Path path = makeFilePath("register.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    protected void profileGet(HttpExchange exchange) {
            Cookie ses = Cookie.make("User-ID", generateID);
            ses.setMaxAge(600);
            ses.setHttpOnly(true);

            setCookie(exchange, ses);

            getCookies(exchange);

            if(user != null) {
                user.setBooks();
            }
            renderTemplate(exchange, "profile.html", user);

    }

    private void loginPost(HttpExchange exchange) {
        Map<String, Object> map = new HashMap<>();
        getContentType(exchange);

        String msg = getBody(exchange);
        Map<String, String> parsed = ProgramUtility.parseUrl(msg, "&");
        try {
            if (parsed.size() == 2) {
                List<Employee> users = FileEmployeeService.readFile();
                for (Employee employee : users) {
                    if (employee.getEmail().equals(parsed.get("email")) && employee.getPassword().equals(parsed.get("user-password"))) {
                        user = employee;
                        throw new RuntimeException();
                    }
                }
            }
            map.put("fail", true);
            renderTemplate(exchange, "index.html", map);
        } catch (Exception exception) {
            redirect303(exchange, "/profile");
        }

    }
    protected void makeCookie(HttpExchange exchange, Employee employee) {
        Cookie session = Cookie.make("User-ID", generateID);
        Map<String, Object> data = new HashMap<>();
        session.setMaxAge(600);
        session.setHttpOnly(true);
        setCookie(exchange, session);
        String cookieStr = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieStr);
        data.put("cookies", cookies);
    }
}
