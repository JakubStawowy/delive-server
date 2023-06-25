package com.example.rentiaserver.user.model.to;

import com.example.rentiaserver.base.model.to.BaseEntityTo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackTo extends BaseEntityTo {
    private Long id;
    private String content;
    private int rate;
    private String authorName;
    private String authorSurname;
    private Long userId;
    private Long authorId;
    private Long messageId;
}
