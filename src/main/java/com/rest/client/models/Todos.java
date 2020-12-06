package com.rest.client.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Todos implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer userId;
    private Integer id;
    private String title;
    private boolean completed;
}
