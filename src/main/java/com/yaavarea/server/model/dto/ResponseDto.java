package com.yaavarea.server.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class ResponseDto {
    private String message;
    private String errorMessage;
}
