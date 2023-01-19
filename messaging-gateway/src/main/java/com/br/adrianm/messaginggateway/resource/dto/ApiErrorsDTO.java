package com.br.adrianm.messaginggateway.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorsDTO {
    private List<String> errors;
}
