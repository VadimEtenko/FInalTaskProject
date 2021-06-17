package project.db.entity;

import java.io.Serializable;

/**
 * Root of all entities which have identifier field.
 *
 * @author V. Etenko
 *
 */

public abstract class Entity implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public interface BuilderInterface{

        BuilderInterface withId(Long id);

    }

}
