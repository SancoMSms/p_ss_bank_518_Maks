package com.bank.history.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    @NonNull
    private String errorType;
    @NonNull
    private String message;
    @NonNull
    private String timestamp;
    @NonNull
    private String requestId;
}