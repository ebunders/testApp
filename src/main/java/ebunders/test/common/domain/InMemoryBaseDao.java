package ebunders.test.common.domain;

import com.google.common.base.Optional;
import ebunders.test.common.entity.Identifiable;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
public abstract class InMemoryBaseDao<T extends Identifiable> implements BaseDao<T>{
    public static final Set<Identifiable> repository = new CopyOnWriteArraySet<Identifiable>();

    private final Class clazz;

    public InMemoryBaseDao(Class clazz) {
        this.clazz = clazz;
    }


    @SuppressWarnings("unchecked")
    @Override
    public Iterator<T> getAll() {
        List<T> result = new ArrayList<T>();
        for(Identifiable identifiable: repository ){
            if(clazz.isAssignableFrom(identifiable.getClass())){
                result.add((T)identifiable);
            }
        }
        return result.iterator();

    }

    @Override
    public Optional<T> findById(String id){
        for(Identifiable i:repository){
            if(i.getId().equals(id)){
                return (Optional<T>) Optional.of(i);
            }
        }
        return Optional.absent();
    }

    /*
    if it already exists, copy the new values into the existing instance
     */
    @Override
    public void saveOrUpdate(T entity) {
        if(! findById(entity.getId()).isPresent()) {
            repository.add((Identifiable)entity);
        }
    }

}
