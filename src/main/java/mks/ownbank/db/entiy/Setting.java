/**
 * Copyright 2018, MKS GROUP.
 */
package mks.ownbank.db.entiy;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ThachLN
 */
public class Setting implements Serializable {
    /** Auto increment key. */
    private Integer id;

    private boolean isOpen;

    private Date openTime;

    /**
    * Get value of id.
    * @return the id
    */
    public Integer getId() {
        return id;
    }

    /**
    * Get value of isOpen.
    * @return the isOpen
    */
    public boolean isOpen() {
        return isOpen;
    }

    /**
    * Get value of openTime.
    * @return the openTime
    */
    public Date getOpenTime() {
        return openTime;
    }

    /**
     * Set the value for id.
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set the value for isOpen.
     * @param isOpen the isOpen to set
     */
    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * Set the value for openTime.
     * @param openTime the openTime to set
     */
    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }
}
