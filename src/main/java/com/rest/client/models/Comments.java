package com.rest.client.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer postId;
    private Integer id;
    private String name;
    private String email;
    private String body;

}
