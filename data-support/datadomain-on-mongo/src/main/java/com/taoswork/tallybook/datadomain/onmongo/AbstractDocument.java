package com.taoswork.tallybook.datadomain.onmongo;

import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by Gao Yuan on 2016/2/5.
 */
public abstract class AbstractDocument implements PersistableDocument {
    @Id
    @PersistField(fieldType = FieldType.ID)
    private ObjectId id;

    /**
     * Returns the identifier of the document.
     *
     * @return the id
     */
    @Override
    public ObjectId getId() {
        return id;
    }

    @Override
    public void setId(ObjectId id) {
        this.id = id;
    }

    /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        AbstractDocument that = (AbstractDocument) obj;

        return this.id.equals(that.getId());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
