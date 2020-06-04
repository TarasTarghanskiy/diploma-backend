package server.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class StoryDTO {
    private long storyID;

    private String text;

    private String link;

    private Date creationDate;
}
