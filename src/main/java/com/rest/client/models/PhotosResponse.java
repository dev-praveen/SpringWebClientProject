package com.rest.client.models;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class PhotosResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Photos> photosList;
}
