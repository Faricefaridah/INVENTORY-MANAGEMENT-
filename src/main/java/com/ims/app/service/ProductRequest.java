package com.ims.app.service;

import java.util.Date;

public class ProductRequest {
    private String requestId;
    private Date requestDate;

    // Constructor with both fields
    public ProductRequest(String requestId, Date requestDate) {
        this.requestId = requestId;
        this.requestDate = requestDate;
    }

    // Getter and Setter for requestId
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    // Getter and Setter for requestDate
    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
}
