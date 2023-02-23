package kz.attractor.java.HW44;

import FileService.FileBooksService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Employee {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Integer> booksId;
    private List<Integer> regBooksId;
    private transient List<Book> curBooks;
    private transient List<Book> prevBooks;

    public Employee(Integer id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void setBooks() {
        List<Book> books = FileBooksService.readFile();
        if (booksId != null) {
            curBooks = booksId.stream().map(id -> books.get(id - 1)).collect(Collectors.toList());
        }
        if (regBooksId != null) {
            prevBooks = regBooksId.stream().map(id -> books.get(id - 1)).collect(Collectors.toList());
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static Employee createUser(Integer id, Map<String, String> map) {
        return new Employee(id, map.get("name"), map.get("surname"), map.get("email"), map.get("user-password"));
    }

    public static Boolean compareUser(Employee user, Employee user2) {
        return user.getEmail().equals(user2.getEmail());
    }
}
