package ebunders.test.domain.user;

import ebunders.test.common.domain.InMemoryBaseDao;
import ebunders.test.domain.task.InMemoryTaskDao;
import ebunders.test.domain.task.Task;
import ebunders.test.domain.task.TaskDao;

import java.util.Iterator;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
public final class InMemoryUserDao extends InMemoryBaseDao<User> {

    private InMemoryTaskDao taskDao;

    public InMemoryUserDao(InMemoryTaskDao taskDao) {
        super(User.class);
        this.taskDao = taskDao;
    }

    /**
     * make sure that all child tasks are saved as well
     * @param user
     */
    @Override
    public void saveOrUpdate(User user) {
        super.saveOrUpdate(user);
        for (Iterator<Task> tasks = user.getTasks(); tasks.hasNext();  ) {
            taskDao.saveOrUpdate(tasks.next());
        }
    }
}
