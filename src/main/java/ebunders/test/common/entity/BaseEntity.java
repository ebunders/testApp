package ebunders.test.common.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Ernst Bunders on 15-10-14.
 * We create a unique id straight away. All entities in this application are unique.
 * So from that perspective equals() should always return false.
 * TODO: find out about hibernate requirements for equals() and hashcode()
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntity implements Identifiable, Serializable{
    //TODO: replace this with nanoseconds.
    private static final AtomicLong counter = new AtomicLong(0);

    @Id
    private final String id;

    public BaseEntity() {
        id = this.getClass().getName() + new Date().getTime() + Math.random() + counter.incrementAndGet();
    }

    @Override
    public String getId() {
        return id;
    }
}
