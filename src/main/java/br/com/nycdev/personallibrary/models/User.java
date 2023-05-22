package br.com.nycdev.personallibrary.models;

import br.com.nycdev.personallibrary.forms.UserForm;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="tb_users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String login;


    private String password;

    private String role;

    @OneToMany(mappedBy = "owner")
    private List<Book> books = new ArrayList<>();

    public User(String name, String login, String password, String role){
        this.name = name;
        this.login = login;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.role = role;
    }

    public User(UserForm userForm) {
        this.name = userForm.name();
        this.login = userForm.login();
        this.password = new BCryptPasswordEncoder().encode(userForm.password());
    }

    protected User() { }

//    public void addBook(Book book) {
//        this.books.add(book);
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String userPassword) {
        this.password = userPassword;
    }

    @Override
    public String getUsername() {
        return login;
    }

    public void setUsername(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }


}
