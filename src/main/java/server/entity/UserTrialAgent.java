package server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_trial_agent")

@Getter
@Setter
@NoArgsConstructor
public class UserTrialAgent {
    @EmbeddedId
    private UserTrialAgentPK id;

    public UserTrialAgent(UserEntity user, TrialEntity trial, String status) {
        this.id = new UserTrialAgentPK(user.getUserID(), trial.getTrialID());
        this.user = user;
        this.trial = trial;
        this.status = status;
    }

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("trialID")
    @JoinColumn(name = "trial_id")
    private TrialEntity trial;

    @Column
    private String status;

}
