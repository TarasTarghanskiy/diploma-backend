package server.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VoteDTO {

    private long voteID;

    private Date voteCreateDate;

    private long voteMinReputation;

    private boolean isVoteActive;

    private List<VoteVariantDTO> voteVariantList;

    private String description;

    private boolean voting;
}
