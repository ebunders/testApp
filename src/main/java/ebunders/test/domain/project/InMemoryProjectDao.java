package ebunders.test.domain.project;

import ebunders.test.common.domain.InMemoryBaseDao;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
@Repository
public final class InMemoryProjectDao extends InMemoryBaseDao<Project> implements ProjectDao {
    public InMemoryProjectDao() {
        super(Project.class);

        //TODO:where to put the bootstrap code
        if (!getAll().hasNext()) {
            saveOrUpdate(new Project("bootstrap", new Date()));
        }
    }
}
