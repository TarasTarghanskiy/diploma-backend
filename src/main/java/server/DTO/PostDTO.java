package server.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private long postID;

    private String title;

    private String text;

    private Date creationDate;

    private long serverID;

}
