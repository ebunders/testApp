package ebunders.test.domain.project;

import com.google.common.base.Optional;
import ebunders.test.common.domain.InMemoryBaseDao;
import ebunders.test.domain.task.InMemoryTaskDao;
import ebunders.test.domain.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
public class InMemoryTaskDaoTest {

    private InMemoryTaskDao taskDao;
    private InMemoryProjectDao projectDao;

    @Before
    public void setup(){
        projectDao = new InMemoryProjectDao();
        taskDao = new InMemoryTaskDao(projectDao);
        InMemoryBaseDao.repository.clear();
    }

    @Test
    public void testSaveTask() {
        assertEquals("repo should be empty",InMemoryProjectDao.repository.size(), 0);
        Task task = new Task("foo", new Date());
        taskDao.saveOrUpdate(task);

        assertEquals("one object should be stored", 1, InMemoryBaseDao.repository.size());
        Optional<Task> to = taskDao.findById(task.getId());
        assertTrue("we must find the task", to.isPresent());
        assertEquals(task, to.get());
    }

    @Test
    public void testSaveWithNewProject(){
        assertEquals("repo should be empty",InMemoryProjectDao.repository.size(), 0);
        Task task = new Task("foo", new Date());
        Project p = new Project("bar");
        task.setProject(p);
        taskDao.saveOrUpdate(task);
        assertEquals("two objects should be stored", 2, InMemoryProjectDao.repository.size());

        Optional<Project> po = projectDao.findById(p.getId());
        assertTrue("we must find the object", po.isPresent());
    }
}
