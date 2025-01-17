package com.thomas.RpcVersion2.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    //client and server args
    private Integer id;
    private String userName;
    private Boolean sex;
}
