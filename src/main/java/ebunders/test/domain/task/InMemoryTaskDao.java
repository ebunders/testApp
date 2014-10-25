package ebunders.test.domain.task;

import com.google.common.collect.Lists;
import ebunders.test.common.domain.InMemoryBaseDao;
import ebunders.test.domain.project.InMemoryProjectDao;
import ebunders.test.domain.project.Project;
import ebunders.test.domain.project.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
@Repository
public final class InMemoryTaskDao extends InMemoryBaseDao<Task> implements TaskDao {

    private ProjectDao projectDao;

    @Autowired
    public InMemoryTaskDao(InMemoryProjectDao projectDao) {
        super(Task.class);
        this.projectDao = projectDao;
    }

    /**
     * make sure we also save the embedded project (if any)
     * @param entity
     */
    @Override
    public void saveOrUpdate(Task entity) {
        super.saveOrUpdate(entity);
        if(entity.getProject() != null){
            projectDao.saveOrUpdate(entity.getProject());
        }
    }

    @Override
    public Iterator<Task> getByProject(Project project) {
        List<Task> tasks = new ArrayList<Task>();
        for(Task task: Lists.newArrayList(getAll())) {
            if (task.getProject() != null && project.getId().equals(task.getProject().getId())) {
                tasks.add(task);
            }
        }
        return tasks.iterator();
    }

    @Override
    public boolean projectIsUsed(Project project) {
        return getByProject(project).hasNext();
    }
}
