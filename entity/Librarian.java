package by.module6.library.entity;

/** Librarian entity
 * @author Yury
 * @since JDK1.8
 **/
public class Librarian implements User{
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private int id;


    public Librarian(int id, String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        this.id = id;
        this.password = password;
        this.login = login;
        this.lastName = lastName;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
