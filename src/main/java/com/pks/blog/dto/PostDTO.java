package com.pks.blog.dto;

import com.pks.blog.entity.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@ApiModel(description = "Post model information")
public class PostDTO {

    @ApiModelProperty(value = "Blog post id")
    private Long id;
    @ApiModelProperty(value = "Blog post title")
    @NotEmpty
    @Size(min = 2,message = "post title should have atleast 2 charecters")
    private String title;
    @ApiModelProperty(value = "Blog post Description")
    @NotEmpty
    @Size(min = 10,message = "post Description should have atleast 10 charecters")
    private String description;
    @ApiModelProperty(value = "Blog post content")
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    public PostDTO(Long id, String title, String description, String content, Set<CommentDto> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.comments = comments;
    }

    public PostDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<CommentDto> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDto> comments) {
        this.comments = comments;
    }
}
