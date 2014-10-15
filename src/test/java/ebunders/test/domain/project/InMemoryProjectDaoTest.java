package ebunders.test.domain.project;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import ebunders.test.domain.BaseTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class InMemoryProjectDaoTest extends BaseTest{

    private InMemoryProjectDao projectDao;

    @Before
    public void setup(){
        projectDao = new InMemoryProjectDao();
        InMemoryProjectDao.repository.clear();
    }

    @Test
    public void testProjectStoreAndGet(){
        assertEquals("repo should be empty",InMemoryProjectDao.repository.size(), 0);
        Project p = new Project("foo", new Date());
        projectDao.saveOrUpdate(p);

        assertEquals("instance should be stored", 1, InMemoryProjectDao.repository.size());

        List<Project> list = Lists.newArrayList(projectDao.getAll());
        assertEquals("there should be one project", 1, list.size());

        Optional<Project> po = projectDao.findById(p.getId());
        assertTrue("We should find an instance", po.isPresent());
        assertEquals("Instance should be one we stored", p, po.get());
    }

    @Test
    public void testMultipleProjectsStoreAndGet() {
        assertEquals("repo should be empty",InMemoryProjectDao.repository.size(), 0);
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
        assertEquals("repo should be empty",InMemoryProjectDao.repository.size(), 0);
        Project p1 = new Project("foo", new Date());
        projectDao.saveOrUpdate(p1);

        Project p2 = new Project("foo", new Date());
        projectDao.saveOrUpdate(p2);
        assertEquals("there should be two projects", 2, InMemoryProjectDao.repository.size());

        p1.setName("foozle");
        projectDao.saveOrUpdate(p1);
        assertEquals("there still should be two projects", 2, InMemoryProjectDao.repository.size());

        Project pr = projectDao.findById(p1.getId()).get();
        assertTrue("should be the same", pr == p1);

    }

}