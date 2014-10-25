package ebunders.test.domain.task;

import ebunders.test.common.domain.HibernateBaseDao;
import ebunders.test.domain.project.Project;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

/**
 * Created by Ernst Bunders on 21-10-14.
 */
public class HibernateTaskDao extends HibernateBaseDao<Task> implements TaskDao {
    @Override
    @Transactional
    public Iterator<Task> getByProject(Project project) {
        return getAllByParameters("project", project);
    }

    @Override
    @Transactional
    public boolean projectIsUsed(Project project) {
        //TODO: there must be a better way
        return getAllByParameters("project", project).hasNext();
    }
}
