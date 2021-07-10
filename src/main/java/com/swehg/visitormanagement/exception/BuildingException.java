package com.swehg.visitormanagement.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hp
 */

@Getter
@Setter
@NoArgsConstructor
public class BuildingException extends RuntimeException {
    private int status;
    private String msg;

    public BuildingException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BuildingException(int status, String msg) {
        super(msg);
        this.status = status;
        this.msg = msg;
    }
}
