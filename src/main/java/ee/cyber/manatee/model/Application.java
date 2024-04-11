package ee.cyber.manatee.model;


import java.time.OffsetDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import ee.cyber.manatee.statemachine.ApplicationState;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ApplicationState applicationState;

    @ManyToOne
    private Application application;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Candidate candidate;

    @NotNull
    private OffsetDateTime updatedOn;

    @Override
    public String toString() {
        return "\n\nApplication id:    " + id + "\n" +
                   "Application state: " + applicationState + "\n" +
                   "Candidate:         " + candidate + "\n" +
                   "Submitted:         " + updatedOn;
    }
}
