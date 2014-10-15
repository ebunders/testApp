package ebunders.test.common.entity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ernst Bunders on 15-10-14.
 */
public class BaseEntity implements Identifiable {
    private static final AtomicInteger index = new AtomicInteger(0);

    private final String id;

    public BaseEntity() {
        id = this.getClass().getName() + index.incrementAndGet();
    }

    @Override
    public String getId() {
        return id;
    }
}
