package com.rest.client.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Photos implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer albumId;
    private Integer id;
    private String title;
    private String url;
    private String thumbnailUrl;
}
