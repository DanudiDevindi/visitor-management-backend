package com.swehg.visitormanagement.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorMessageResponseDTO {
    private boolean success;
    private int status;
    private String msg;
}