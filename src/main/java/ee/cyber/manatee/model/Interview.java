package ee.cyber.manatee.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor()
public class Interview {
    @Id
    @GeneratedValue
    private Integer interviewId;

    @NotNull
    private Integer applicationId;

    @NotNull
    private OffsetDateTime interviewDate;

    @NotBlank
    private String interviewName;

    @NotBlank
    private String interviewType;


    @Override
    public String toString() {
        return "\n\n" +
                "Interview id:            " + interviewId + "\n" +
                "Application id:          " + applicationId + "\n" +
                "Interview date and time: " + interviewDate + "\n" +
                "Interview name:          " + interviewName + "\n" +
                "Interview type:          " + interviewType;
    }
}
