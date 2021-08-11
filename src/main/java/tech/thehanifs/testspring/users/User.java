package tech.thehanifs.testspring.users;

import tech.thehanifs.testspring.Util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User {
    private @Id @GeneratedValue Long id;
    private String name;
    @Column()
    private int role;
    private String username;
    private String password;

    // role: 1 for admin 2 for student
    public User() {}
    public User(String name, int role, String username, String password) throws Exception {
        this.name = name;
        this.role = role;
        this.username = username;
        this.password = Util.encryption_aes256gcm.encrypt(password);
    }

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public int getRole() {
        return this.role;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword(boolean decrypted) throws Exception {
        return decrypted ? Util.encryption_aes256gcm.decrypt(this.password) : this.password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(int RoleID) {
        this.role = RoleID;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof User)) {
            return false;
        } else {
            User user = (User) obj;
            return Objects.equals(this.id, user.id) && Objects.equals(this.name, user.name) && Objects.equals(this.role, user.role);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role);
    }

}
