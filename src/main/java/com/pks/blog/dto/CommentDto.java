package com.pks.blog.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;
    private String name;
    private String email;
    private String body;
}
