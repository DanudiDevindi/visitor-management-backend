package com.swehg.visitormanagement.dto;

import com.swehg.visitormanagement.enums.PassCardStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author kavindu
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassCardDTO {
    private long passCardId;
    private String name;
    private PassCardStatus status;
}
