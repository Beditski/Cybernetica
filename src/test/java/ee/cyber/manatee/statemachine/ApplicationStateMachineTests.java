package ee.cyber.manatee.statemachine;


import jakarta.transaction.Transactional;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ee.cyber.manatee.model.Application;
import ee.cyber.manatee.model.Candidate;
import ee.cyber.manatee.repository.ApplicationRepository;
import ee.cyber.manatee.service.ApplicationService;

import static ee.cyber.manatee.statemachine.ApplicationState.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest
public class ApplicationStateMachineTests {

    @Autowired
    ApplicationService applicationService;

    @Autowired
    ApplicationStateMachine applicationStateMachine;

    @Autowired
    ApplicationRepository applicationRepository;

    @Test
    @Transactional
    public void applicationGetsRejected() {
        val newCandidate = Candidate.builder().firstName("Mari").lastName("Maasikas").build();
        val newApplication = Application.builder().candidate(newCandidate).build();

        val applicationSaved = applicationService.insertApplication(newApplication);
        val initialUpdatedOn = applicationSaved.getUpdatedOn();
        assertEquals(NEW, applicationSaved.getApplicationState());

        val stateMachine = applicationStateMachine.rejectApplication(applicationSaved.getId());
        assertEquals(REJECTED, stateMachine.getState().getId());

        val optionalRejectedApplication = applicationRepository.findById(applicationSaved.getId());
        assertFalse(optionalRejectedApplication.isEmpty());

        val rejectedApplication = optionalRejectedApplication.get();
        assertEquals(REJECTED, rejectedApplication.getApplicationState());
        assertNotEquals(initialUpdatedOn, rejectedApplication.getUpdatedOn());
    }

    @Test
    @Transactional
    public void applicationForInterview() {
        val newCandidate = Candidate.builder().firstName("Aleksandr").lastName("Beditski").build();
        val newApplication = Application.builder().candidate(newCandidate).build();

        val applicationSaved = applicationService.insertApplication(newApplication);
        val initialUpdatedOn = applicationSaved.getUpdatedOn();
        assertEquals(NEW, applicationSaved.getApplicationState());

        val stateMachine = applicationStateMachine.scheduleInterview(applicationSaved.getId());
        assertEquals(INTERVIEW, stateMachine.getState().getId());

        val optionalInterviewApplication = applicationRepository.findById(applicationSaved.getId());
        assertFalse(optionalInterviewApplication.isEmpty());

        val rejectedApplication = optionalInterviewApplication.get();
        assertEquals(INTERVIEW, rejectedApplication.getApplicationState());
        assertNotEquals(initialUpdatedOn, rejectedApplication.getUpdatedOn());
    }
}
