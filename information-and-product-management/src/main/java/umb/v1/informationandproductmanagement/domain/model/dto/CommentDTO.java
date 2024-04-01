package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentDTO {

    @JsonProperty("username")
    private String username;

    @JsonProperty("content")
    private String content;

}
