package ebunders.test.domain.project;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import ebunders.test.common.domain.InMemoryBaseDao;
import ebunders.test.domain.BaseTest;
import ebunders.test.domain.task.InMemoryTaskDao;
import ebunders.test.domain.task.Task;
import ebunders.test.domain.task.TaskDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
public class TaskDaoTest extends BaseTest{

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ProjectDao projectDao;

    @Before
    public void setup(){
        cleanup();
    }

    private void cleanup() {
        taskDao.deleteAll();
        projectDao.deleteAll();
    }

    @Test
    public void testSaveTask() {
        Task task = new Task("foo", new Date());
        taskDao.saveOrUpdate(task);

        Optional<Task> to = taskDao.findById(task.getId());
        assertTrue("we must find the task", to.isPresent());
        assertEquals(task, to.get());
    }

    @Test
    public void testSaveWithNewProject() {
        Task task = new Task("foo", new Date());
        Project p = new Project("bar");
        task.setProject(p);
        taskDao.saveOrUpdate(task);

        Optional<Project> po = projectDao.findById(p.getId());
        assertTrue("we must find the object", po.isPresent());
    }

    @Test
    public void testGetByProject() {
        Project foo = new Project("foo");
        Project bar = new Project("bar");
        projectDao.saveOrUpdate(foo);
        projectDao.saveOrUpdate(bar);

        taskDao.saveOrUpdate(new Task("een", new Date(), foo));
        taskDao.saveOrUpdate(new Task("twee", new Date(), foo));
        taskDao.saveOrUpdate(new Task("drie", new Date(), bar));
        taskDao.saveOrUpdate(new Task("vier", new Date()));

        assertEquals(Lists.newArrayList(taskDao.getByProject(foo)).size(), 2);
        assertEquals(Lists.newArrayList(taskDao.getByProject(bar)).size(), 1);
    }

    @Test
    public void TestProjectIsUsed() {
        cleanup();
        Project foo = new Project("foo", new Date());
        Project bar = new Project("bar", new Date());
        Project zip = new Project("zip", new Date());
        projectDao.saveOrUpdate(foo);
        projectDao.saveOrUpdate(bar);
        projectDao.saveOrUpdate(zip);

        taskDao.saveOrUpdate(new Task("een", new Date(), foo));
        taskDao.saveOrUpdate(new Task("twee", new Date(), foo));
        taskDao.saveOrUpdate(new Task("drie", new Date(), bar));
        taskDao.saveOrUpdate(new Task("vier", new Date()));

        assertTrue(taskDao.projectIsUsed(foo));
        assertTrue(taskDao.projectIsUsed(bar));
        assertFalse(taskDao.projectIsUsed(zip));
    }
}
