package ebunders.test.domain.task;

import ebunders.test.common.domain.BaseDao;
import ebunders.test.domain.project.Project;
import ebunders.test.domain.user.User;

import java.util.Iterator;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
public interface TaskDao extends BaseDao<Task> {
    public Iterator<Task> getByProject(Project project);
    public boolean projectIsUsed(Project project);
}
