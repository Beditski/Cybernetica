package ee.cyber.manatee.controller;


import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ee.cyber.manatee.api.InterviewApi;
import ee.cyber.manatee.dto.InterviewDto;
import ee.cyber.manatee.mapper.InterviewMapper;
import ee.cyber.manatee.service.InterviewService;

import static org.springframework.http.HttpStatus.CREATED;


@Slf4j
@RestController
@RequiredArgsConstructor
public class InterviewApiImpl implements InterviewApi {

    private final InterviewMapper interviewMapper;
    private final InterviewService interviewService;

    @GetMapping(path = "/interviews")
    public ResponseEntity<List<InterviewDto>> getInterviews() {
        val interviews = interviewService.getInterviews();
        return ResponseEntity.ok(interviewMapper.entitiesToDtoList(interviews));
    }

    @PostMapping(path = "/interviews")
    public ResponseEntity<InterviewDto> addInterview(InterviewDto interviewDto) {
        val draftInterview = interviewMapper.dtoToEntity(interviewDto);
        val interview = interviewService.insertInterview(draftInterview);

        return ResponseEntity.status(CREATED)
                .body(interviewMapper.entityToDto(interview));
    }
}