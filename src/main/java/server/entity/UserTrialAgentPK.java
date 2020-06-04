package server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class UserTrialAgentPK implements Serializable {
    @Column(name = "user_id")
    private long userID;

    @Column(name = "trial_id")
    private Long trialID;

    public UserTrialAgentPK(long userID, Long trialID) {
        this.userID = userID;
        this.trialID = trialID;
    }
}
