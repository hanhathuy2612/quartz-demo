package com.example.quatzdemo.email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class EmailResponse {
    private boolean success;
    private String jobId;
    private String jobGroup;
    private String message;
}
