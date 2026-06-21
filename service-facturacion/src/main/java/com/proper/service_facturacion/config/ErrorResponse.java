package com.proper.service_facturacion.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse 
{
    private int status;
    private String message;
    private long timestamp;
}