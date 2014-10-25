package ebunders.test.domain.user;

import com.google.common.base.Optional;
import ebunders.test.common.domain.InMemoryBaseDao;
import ebunders.test.domain.project.Project;
import ebunders.test.domain.task.InMemoryTaskDao;
import ebunders.test.domain.task.Task;
import ebunders.test.domain.task.TaskDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
@Repository()
public final class InMemoryUserDao extends InMemoryBaseDao<User> implements UserDao{

    private InMemoryTaskDao taskDao;

    @Autowired
    public InMemoryUserDao(InMemoryTaskDao taskDao) {
        super(User.class);
        this.taskDao = taskDao;

        //create a first user for logging in
        //TODO:where to put the bootstrap code
        if(! getAll().hasNext()) {
            saveOrUpdate(new User("Ernst Bunders", "foo", "bar"));
            System.out.println("Created user 'Ernst Bunders'");
        }
    }

    /**
     * make sure that all child tasks are saved as well
     * @param entity
     */
    @Override
    public void saveOrUpdate(User entity) {
        super.saveOrUpdate(entity);
        for (Iterator<Task> tasks = entity.getTasks(); tasks.hasNext();  ) {
            taskDao.saveOrUpdate(tasks.next());
        }
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String userName, String password) {
        for (Iterator<User> userIterator = getAll(); userIterator.hasNext(); ) {
            User u = userIterator.next();
            if (userName.equals(u.getAccount()) && password.equals(u.getPasswd())) {
                return Optional.of(u);
            }
        }
        return Optional.absent();
    }
}
