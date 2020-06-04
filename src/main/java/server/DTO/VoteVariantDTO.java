package server.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoteVariantDTO {
    private long voteVariantID;

    private String voteVariantDescription;

    private long voteVariantCount;
}
