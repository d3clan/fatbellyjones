package com.viviquity.readmy.db.utils.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.viviquity.readmy.db.utils.model.GenericEntity;

public class EntityGenericDaoJpa<T, PK extends Serializable> extends
        GenericDaoJpa<T, PK> {

    private Class<T> persistentClass;

    public EntityGenericDaoJpa(Class<T> persistentClass) {
        super(persistentClass);
        this.persistentClass = persistentClass;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.semantico.rh.ddc.db.utils.jpa.GenericDaoJpa#save(java.lang.Object)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public T save(Object object) {
        GenericEntity entity = (GenericEntity) object;
        // Set the last updated field to the current date.
        entity.setLastUpdated(new Date());
        return super.save(entity);
    }

    /**
     * Find a {@link List} of the entities with a field equal to a certain
     * value.
     * 
     * @param fieldName
     * @param fieldValue
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> find(String fieldName, String fieldValue) {
        return find(fieldName, fieldValue, true);
    }

    /**
     * @see #find(String, String)
     * @param fieldName
     * @param fieldValue
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> find(String fieldName, int fieldValue) {
        return find(fieldName, Integer.toString(fieldValue), false);
    }

    /**
     * @see #find(String, String)
     * @param fieldName
     * @param fieldValue
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> find(String fieldName, long fieldValue) {
        return find(fieldName, Long.toString(fieldValue), false);
    }

    /**
     * @see #find(String, String)
     * @param fieldName
     * @param fieldValue
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> find(String fieldName, float fieldValue) {
        return find(fieldName, Float.toString(fieldValue), false);
    }

    /**
     * @see #find(String, String)
     * @param fieldName
     * @param fieldValue
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> find(String fieldName, double fieldValue) {
        return find(fieldName, Double.toString(fieldValue), false);
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    private List<T> find(String fieldName, String fieldValue,
            Boolean stringField) {
        if (stringField) {
            fieldValue = "'" + fieldValue + "'";
        }
        return getJpaTemplate().find(
                "from " + persistentClass.getSimpleName() + " where " + fieldName
                        + "=" + fieldValue);
    }
}
