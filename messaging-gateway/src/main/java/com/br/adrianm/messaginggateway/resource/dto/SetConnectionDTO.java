package com.br.adrianm.messaginggateway.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetConnectionDTO {
    private String username;
    private String password;
    private String host;
}
