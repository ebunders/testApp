package ebunders.test.domain.task;

import ebunders.test.common.domain.InMemoryBaseDao;
import ebunders.test.domain.project.InMemoryProjectDao;
import ebunders.test.domain.project.ProjectDao;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
public final class InMemoryTaskDao extends InMemoryBaseDao<Task> {

    private ProjectDao projectDao;

    public InMemoryTaskDao(InMemoryProjectDao projectDao) {
        super(Task.class);
        this.projectDao = projectDao;
    }

    /**
     * make sure we also save the embedded project (if any)
     * @param task
     */
    @Override
    public void saveOrUpdate(Task task) {
        super.saveOrUpdate(task);
        if(task.getProject() != null){
            projectDao.saveOrUpdate(task.getProject());
        }
    }
}
