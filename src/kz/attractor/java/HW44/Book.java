package kz.attractor.java.HW44;
import FileService.FileEmployeeService;
import java.util.List;

public class Book {
    private Integer id;
    private String name;
    private String author;
    private boolean state;
    private transient Employee user;
    private String imgBook;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgBook() {
        return imgBook;
    }

    public void setImgBook(String imgBook) {
        this.imgBook = imgBook;
    }

    public Book(String imgBook) {
        this.imgBook = imgBook;
    }

    public Book(String name, String author, String imgBook, String description,boolean state){
        this.name = name;
        this.author = author;
        this.state = state;
        this.imgBook = imgBook;
        this.description = description;
        User();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    private void User() {
        if (state) {
            List<Employee> users = FileEmployeeService.readFile();
            for (Employee user : users) {
                if (user.getId().equals(this.user)) {
                    this.user = user;
                }
            }
        }
    }
}
