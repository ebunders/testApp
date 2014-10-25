package ebunders.test.domain.task;

import ebunders.test.common.entity.BaseEntity;
import ebunders.test.domain.project.Project;
import javassist.compiler.ast.CastExpr;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
//import org.hibernate.metamodel.binding.InheritanceType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

import static org.hibernate.annotations.CascadeType.*;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
@Entity
@Table(name="tasks")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public final class Task extends BaseEntity {

    @Column(nullable = false)
    private String name = "";

    private String description;

    private Date dueDate;

    @Column(nullable = false)
    private boolean priority = false;

    @Column(nullable = false)
    private boolean done = false;

    @OneToOne(optional = true)
    @Cascade(SAVE_UPDATE)
    @JoinColumn(name="id")
    private Project project;

    public Task() { }

    public Task(String name, Date dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }

    public Task(String name, Date dueDate, Project project) {
        this.name = name;
        this.dueDate = dueDate;
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (done != task.done) return false;
        if (priority != task.priority) return false;
        if (description != null ? !description.equals(task.description) : task.description != null) return false;
        if (dueDate != null ? !dueDate.equals(task.dueDate) : task.dueDate != null) return false;
        if (!name.equals(task.name)) return false;
        if (project != null ? !project.equals(task.project) : task.project != null) return false;

        return getId().equals(task.getId());
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
        result = 31 * result + (priority ? 1 : 0);
        result = 31 * result + (done ? 1 : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + getId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                ", done=" + done +
                ", project=" + project +
                ", id=" + getId() +
                '}';
    }
}

