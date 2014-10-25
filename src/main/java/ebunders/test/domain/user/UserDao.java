package ebunders.test.domain.user;

import com.google.common.base.Optional;
import ebunders.test.common.domain.BaseDao;
import ebunders.test.common.domain.InMemoryBaseDao;
import ebunders.test.domain.project.Project;
import org.eclipse.jetty.server.Authentication;

import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
public interface UserDao extends BaseDao<User> {
    public Optional<User> findByUsernameAndPassword(String userName, String password);
}
