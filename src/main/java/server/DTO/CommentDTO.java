package server.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {

    private long commentID;

    private String text;

    private long postID;

    private Date creationDate;

    private String userName;

    private long ownerCommentID;

    private long accountID;
}
