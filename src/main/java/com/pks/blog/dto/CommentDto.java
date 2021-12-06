package com.pks.blog.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Comment model information")
public class CommentDto {

    @ApiModelProperty(value = "Blog comment id")
    private long id;
    @ApiModelProperty(value = "Blog commenter name")
    @NotEmpty
    @Size(min = 2,message = "Commenter name should have atleast 2 charecters")
    private String name;
    @ApiModelProperty(value = "Blog commenter email")
    @NotEmpty
    @Email(message = "invalid email")
    private String email;
    @ApiModelProperty(value = "Blog comment body")
    @NotEmpty
    @Size(min = 10,message = "Comment body  should have atleast 10 charecters")
    private String body;
}
