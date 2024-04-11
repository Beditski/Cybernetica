package ee.cyber.manatee.service;


import java.util.List;

import ee.cyber.manatee.model.Interview;
import ee.cyber.manatee.statemachine.ApplicationStateMachine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ee.cyber.manatee.repository.InterviewRepository;


@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationStateMachine applicationStateMachine;

    public List<Interview> getInterviews() {
        var interviewsList = interviewRepository.findAll();
        System.out.println(interviewsList);
        return interviewsList;
    }

    public Interview insertInterview(Interview interview) {
        interview.setInterviewId(interview.getInterviewId());
        interview.setInterviewDate(interview.getInterviewDate());

        applicationStateMachine.scheduleInterview(interview.getApplicationId());

        return interviewRepository.save(interview);
    }
}
