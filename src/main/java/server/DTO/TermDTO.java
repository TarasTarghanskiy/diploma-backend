package server.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TermDTO {
    private long termID;

    private String term;

    private boolean block;

    private String description;

    private Date creationDate;

    private long serverID;
}
