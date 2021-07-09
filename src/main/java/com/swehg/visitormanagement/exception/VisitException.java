package com.swehg.visitormanagement.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VisitException extends RuntimeException {
    private int status;
    private String msg;

    public VisitException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public VisitException(int status, String msg) {
        super(msg);
        this.status = status;
        this.msg = msg;
    }
}
