package ebunders.test.domain.project;

import ebunders.test.common.entity.BaseEntity;

import java.util.Date;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
public final class Project extends BaseEntity {
    private String name;
    private Date dueDate;

    public Project(String name, Date dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }

    public Project(String name) {
        this(name, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        if (!dueDate.equals(project.dueDate)) return false;
        if (!name.equals(project.name)) return false;

        return getId().equals(project.getId());
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + dueDate.hashCode();
        result = 31 * result + getId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", dueDate=" + dueDate +
                ", id=" + getId() +
                '}';
    }
}
