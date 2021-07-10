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
public class FloorException extends RuntimeException {
    private int status;
    private String msg;

    public FloorException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public FloorException(int status, String msg) {
        super(msg);
        this.status = status;
        this.msg = msg;
    }
}
