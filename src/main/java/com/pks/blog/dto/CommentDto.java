package com.pks.blog.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;
    @NotEmpty
    @Size(min = 2,message = "Commenter name should have atleast 2 charecters")
    private String name;
    @NotEmpty
    @Email(message = "invalid email")
    private String email;
    @NotEmpty
    @Size(min = 10,message = "Comment body  should have atleast 10 charecters")
    private String body;
}
