package FileService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kz.attractor.java.HW44.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Paths.get;

public class FileEmployeeService {
    private static final Gson GSON = new GsonBuilder().create();
    public static List<Employee> readFile(){
        Path path = get("src/JsonInformation/employees.json");
        String json = "";
        try {
            json = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Employee[] employees = GSON.fromJson(json, Employee[].class);
        return List.of(employees);
    }

    public static void FileStore(List<Employee> employees){
        Path path = get("src/JsonInformation/employees.json");
        String json = GSON.toJson(employees);
        try {
            byte[] arr = json.getBytes();
            Files.write(path, arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
