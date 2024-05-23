package com.ims.app.service;

import java.util.Date;

public class ProductDispatch {
    private Date dispatchDate;
    private String dispatchId;

    // Constructor
    public ProductDispatch(Date dispatchDate, String dispatchId) {
        this.dispatchDate = dispatchDate;
        this.dispatchId = dispatchId;
    }

    // Getter and Setter for dispatchDate
    public Date getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(Date dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    // Getter and Setter for dispatchId
    public String getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(String dispatchId) {
        this.dispatchId = dispatchId;
    }
}
