package com.rest.client.models;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class CommentsResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Comments> commentsList;
}
