package ebunders.test.common.domain;

import com.google.common.base.Optional;
import ebunders.test.common.entity.Identifiable;

import java.util.Iterator;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
public interface BaseDao<T extends Identifiable> {

    public Iterator<T> getAll();
    public void saveOrUpdate(T entiry);
    public Optional<T> findById(String id);
}
