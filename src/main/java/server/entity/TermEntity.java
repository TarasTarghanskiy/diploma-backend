package server.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "terms")
public class TermEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    private long termID;

    @Column(unique = true, length = 30)
    private String term;

    @Column(columnDefinition="TEXT")
    private String description;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column(nullable = false, length = 40)
    private String status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "term_server_id")
    private ServerEntity termServer;
}
