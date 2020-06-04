package server.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class LawDTO {

    private long lawID;

    private String lawText;

    private long punishmentCount;

    private Date creationDate;

    private long serverID;
}
