package server.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ServerDTO {

    private long serverID;

    private String name;

    private String description;

    private long viewsCount;

    private Date creationDate;
}
