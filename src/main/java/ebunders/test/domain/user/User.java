package ebunders.test.domain.user;

import ebunders.test.common.entity.BaseEntity;
import ebunders.test.common.entity.Identifiable;
import ebunders.test.domain.task.Task;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public final class User extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String passwd;

    @OneToMany
    @Cascade(SAVE_UPDATE)
    @JoinColumn(name="id")
    private List<Task> tasks = new ArrayList<Task>();

    public User(String name, String account, String passwd) {
        this.name = name;
        this.account = account;
        this.passwd = passwd;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    /**
     * @param task
     * @return {@link java.util.List#remove(Object)}
     */
    public boolean removeTask(Task task){
        return tasks.remove(task);
    }

    public Iterator<Task> getTasks(){
        return tasks.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!account.equals(user.account)) return false;
        if (!name.equals(user.name)) return false;
        if (!passwd.equals(user.passwd)) return false;
        if (!tasks.equals(user.tasks)) return false;

        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + account.hashCode();
        result = 31 * result + passwd.hashCode();
        result = 31 * result + tasks.hashCode();
        result = 31 * result + getId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", passwd='" + passwd + '\'' +
                ", tasks=" + tasks +
                ", id=" + getId() +
                '}';
    }
}
