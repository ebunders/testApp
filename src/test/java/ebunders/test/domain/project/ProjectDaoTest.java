package ebunders.test.domain.project;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import ebunders.test.common.domain.InMemoryBaseDao;
import ebunders.test.domain.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class ProjectDaoTest extends BaseTest{

    @Autowired
    private ProjectDao projectDao;

    @Before
    public void setup(){
        projectDao = new InMemoryProjectDao();
        if (InMemoryBaseDao.class.isAssignableFrom(projectDao.getClass())) {
            ((InMemoryBaseDao)projectDao).clear();
        }
    }

    @Test
    public void testProjectStoreAndGet(){
        Project p = new Project("foo", new Date());
        projectDao.saveOrUpdate(p);

        List<Project> list = Lists.newArrayList(projectDao.getAll());
        assertEquals("there should be one project", 1, list.size());

        Optional<Project> po = projectDao.findById(p.getId());
        assertTrue("We should find an instance", po.isPresent());
        assertEquals("Instance should be one we stored", p, po.get());
    }

    @Test
    public void testMultipleProjectsStoreAndGet() {
        Project p1 = new Project("foo", new Date()), p2 = new Project("bar", new Date()), p3 = new Project("fez", new Date());
        projectDao.saveOrUpdate(p1);
        projectDao.saveOrUpdate(p2);
        projectDao.saveOrUpdate(p3);

        List<Project> list = Lists.newArrayList(projectDao.getAll());
        assertEquals("there should be three projects", 3, list.size());

        Optional<Project> po = projectDao.findById(p2.getId());
        assertTrue("We should find an instance", po.isPresent());
        assertEquals("Should get the right project", p2, po.get());
    }

    @Test
    public void testSaveOrUpdateShouldNotReplaceExistingVersion(){
        Project p1 = new Project("foo", new Date());
        projectDao.saveOrUpdate(p1);

        Project p2 = new Project("foo", new Date());
        projectDao.saveOrUpdate(p2);

        p1.setName("foozle");
        projectDao.saveOrUpdate(p1);

        Project pr = projectDao.findById(p1.getId()).get();
        assertTrue("should be the same", pr == p1);

    }

    @Test
    public void testDeleteProject(){
        Project p1 = new Project("foo");
        projectDao.saveOrUpdate(p1);

        assertTrue(projectDao.findById(p1.getId()).isPresent());
        projectDao.delete(p1);
        assertFalse(projectDao.findById(p1.getId()).isPresent());


    }

}