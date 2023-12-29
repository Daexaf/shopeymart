package com.engima.shopeymart.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
public class FileStorage {
    private String fileName;
    private LocalDateTime dateTime;
}
