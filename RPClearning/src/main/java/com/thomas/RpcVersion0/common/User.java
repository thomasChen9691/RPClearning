package com.thomas.RpcVersion0.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //client and server args
    private Integer id;
    private String userName;
    private Boolean sex;
}
