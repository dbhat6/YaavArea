package com.yaavarea.server.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private String message;
    private String errorMessage;
    private Object data;
}
