package kz.attractor.java.HW44;

import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import FileService.FileEmployeeService;
import kz.attractor.java.server.BasicServer;
import kz.attractor.java.server.ContentType;
import kz.attractor.java.server.ResponseCodes;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Lesson44Server extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();

    public Lesson44Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/sample", this::freemarkerSampleHandler);
        registerGet("/books", this::booksRender);
        registerGet("/books/book", this::bookRender);
        registerGet("/employees", this::employeesRender);
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
            cfg.setDirectoryForTemplateLoading(new File("data"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void freemarkerSampleHandler(HttpExchange exchange) {
        renderTemplate(exchange, "sample.html", getSampleDataModel());
    }

    private void booksRender(HttpExchange exchange) {
        renderTemplate(exchange, "books.html", getBooksDataModel());
    }

    private void bookRender(HttpExchange exchange) {
        renderTemplate(exchange, "book+jpeg.html", getBookDataModel());
    }

    private void employeesRender(HttpExchange exchange) {
        renderTemplate(exchange, "Employee.html", getEmpDataModel());
    }

    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {

            Template temp = freemarker.getTemplate(templateFile);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {
                temp.process(dataModel, writer);
                writer.flush();
                var data = stream.toByteArray();
                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private SampleDataModel getSampleDataModel() {
        return new SampleDataModel();
    }

    private Book getBookDataModel() {
        Random random = new Random();
        List<Book> books = new BookDataModel().getBooks();
        return books.get(random.nextInt(books.size()));
    }

    private BookDataModel getBooksDataModel() {
        return new BookDataModel();
    }

    public EmployeeDataModel getEmpDataModel() {
         return new EmployeeDataModel();
//        HashMap<String, Object> stringUserHashMap = new HashMap<>();
//        stringUserHashMap.put("users", FileEmployeeService.readFile());
//        return stringUserHashMap;
    }
}
