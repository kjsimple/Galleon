package com.gydoc.galleon.domain;

import java.io.Serializable;
import java.util.Calendar;

/**
 *
 */
public abstract class BizObject implements Serializable {

    private Long id;
    private Calendar creationDate;
    private Long version;
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Calendar getCreationDate() {
        if (creationDate != null) {
            return (Calendar) creationDate.clone();
        }
        return null;
    }
    
    public void setCreationDate(Calendar c) {
        if (creationDate != null) {
            throw new UnsupportedOperationException("Cannot change creationDate.");
        }
        creationDate = (Calendar) c.clone();
    }

    /**
     * BizObject is considered optimistic locked (has version column). Overwrite this method and add annotation 
     * NoOptimisticLock can  prevent the default behavior.
     * @return current version
     */
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
