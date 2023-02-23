package FileService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kz.attractor.java.HW44.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Paths.get;

public class FileBooksService {
    private static final Gson GSON = new GsonBuilder().create();

    public static List<Book> readFile(){
        Path path = get("src/JsonInformation/books.json");
        String json = "";
        try{
            json = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Book[] books = GSON.fromJson(json, Book[].class);
        return List.of(books);
    }
}
