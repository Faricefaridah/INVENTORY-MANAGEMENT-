package com.ims.app.Config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
public class ApiResponse<T>  implements Serializable {
    private String message;
    private T data;
    private int status;

    public ApiResponse() {
        this.message = message;
        this.data = data;
        this.status = status;
    }
}

