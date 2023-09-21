package com.yaavarea.server.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Otp {
    @Id
    private String id;

    @Indexed(unique = true)
    private String phoneNumber;
    private int otp;

    @Indexed(expireAfterSeconds = 600)
    @CreatedDate
    private LocalDateTime createdAt;
    @CreatedBy
    private String createdBy;
}
