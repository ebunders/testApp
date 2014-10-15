package ebunders.test.domain.project;

import ebunders.test.common.domain.InMemoryBaseDao;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
public final class InMemoryProjectDao extends InMemoryBaseDao<Project> implements ProjectDao {
    protected InMemoryProjectDao() {
        super(Project.class);
    }


}
