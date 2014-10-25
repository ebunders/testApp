package ebunders.test.domain.project;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import ebunders.test.common.domain.InMemoryBaseDao;
import ebunders.test.domain.BaseTest;
import ebunders.test.domain.task.InMemoryTaskDao;
import ebunders.test.domain.task.Task;
import ebunders.test.domain.task.TaskDao;
import ebunders.test.domain.user.InMemoryUserDao;
import ebunders.test.domain.user.User;
import ebunders.test.domain.user.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
public class UserDaoTest extends BaseTest{

    @Autowired
    private UserDao userDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ProjectDao projectDao;

    @Before
    public void setup(){
        if (InMemoryBaseDao.class.isAssignableFrom(projectDao.getClass())) {
            ((InMemoryBaseDao)projectDao).clear();
        }
    }


    @Test
    public void testSaveUser(){
        User user = new User("Ernst Bunders", "ernst", "secret");
        userDao.saveOrUpdate(user);
        userDao.saveOrUpdate(user);
        assertEquals(1, Lists.newArrayList(userDao.getAll()).size());
    }

    @Test
    public void testSaveUserWithTasks(){
        User user = new User("Ernst Bunders", "ernst", "secret");
        Project project = new Project("a project");
        Task t1 = new Task("foo", new Date());
        Task t2 = new Task("bar", new Date());
        t1.setProject(project);
        t2.setProject(project);
        user.addTask(t1);
        user.addTask(t2);
        userDao.saveOrUpdate(user);

        assertEquals("one project should be stored", 1, Lists.newArrayList(projectDao.getAll()).size());
        assertEquals("one user should be stored", 1, Lists.newArrayList(userDao.getAll()).size());
        assertEquals("twoo tasks should be stored", 2, Lists.newArrayList(taskDao.getAll()).size());

        Optional<User> uo = userDao.findById(user.getId());
        assertTrue("we must find the user", uo.isPresent());
        assertEquals(user, uo.get());
        assertTrue(user == uo.get());
        List tasks = Lists.newArrayList(uo.get().getTasks());
        assertEquals("user has two tasks", 2, tasks.size());
        assertTrue(tasks.contains(t1));
        assertTrue(tasks.contains(t2));
    }
}
