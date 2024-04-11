package ee.cyber.manatee.api;


import ee.cyber.manatee.dto.InterviewDto;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ee.cyber.manatee.dto.ApplicationDto;
import ee.cyber.manatee.dto.CandidateDto;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class ApplicationApiTests {

    @Autowired
    private ApplicationApi applicationApi;
    @Autowired
    private InterviewApi interviewApi;


    @Test
    public void submitApplicationWithValidData() {
        val draftCandidate = CandidateDto
                .builder().firstName("Mari").lastName("Maasikas").build();
        val draftApplication = ApplicationDto
                .builder().candidate(draftCandidate).build();

        val response = applicationApi.addApplication(draftApplication);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        val application = response.getBody();
        assertNotNull(application);
        assertNotNull(application.getId());
        assertNotNull(application.getApplicationState());
        assertNotNull(application.getUpdatedOn());

        assertEquals(draftApplication.getCandidate().getFirstName(),
                     application.getCandidate().getFirstName());
        assertEquals(draftApplication.getCandidate().getLastName(),
                     application.getCandidate().getLastName());
    }

    @Test
    public void scheduleAnInterview() {
        val draftCandidate = CandidateDto
                .builder().firstName("Aleksandr").lastName("Beditski").build();
        val draftApplication = ApplicationDto.builder().id(1).candidate(draftCandidate).build();
        val draftInterview = InterviewDto
                .builder().applicationId(draftApplication.getId()).interviewId(1)
                .interviewDate(OffsetDateTime.parse("2024-04-25T22:23:29.376Z"))
                .interviewName("Technical Interview nr1")
                .interviewType("Technical")
                .build();

        val applicationResponse = applicationApi.addApplication(draftApplication);
        assertEquals(HttpStatus.CREATED, applicationResponse.getStatusCode());

        val interviewResponse = interviewApi.addInterview(draftInterview);
        assertEquals(HttpStatus.CREATED, interviewResponse.getStatusCode());

        val interview = interviewResponse.getBody();
        assertNotNull(interview);
        assertNotNull(interview.getInterviewId());
        assertNotNull(interview.getApplicationId());
        assertNotNull(interview.getApplicationId());
        assertNotNull(interview.getInterviewDate());

        assertEquals(draftInterview.getInterviewId(),
                interview.getInterviewId());
        assertEquals(draftInterview.getApplicationId(),
                draftApplication.getId());
        assertEquals(draftInterview.getInterviewDate(),
                interview.getInterviewDate());
        assertEquals(draftInterview.getInterviewName(),
                interview.getInterviewName());
        assertEquals(draftInterview.getInterviewType(),
                interview.getInterviewType());
    }
}
