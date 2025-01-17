package ee.cyber.manatee.controller;


import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ee.cyber.manatee.api.ApplicationApi;
import ee.cyber.manatee.dto.ApplicationDto;
import ee.cyber.manatee.mapper.ApplicationMapper;
import ee.cyber.manatee.service.ApplicationService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ApplicationApIImpl implements ApplicationApi {

    private final ApplicationMapper applicationMapper;
    private final ApplicationService applicationService;

    @GetMapping(path = "/applications")
    public ResponseEntity<List<ApplicationDto>> getApplications() {
        val applications = applicationService.getApplications();
        return ResponseEntity.ok(applicationMapper.entitiesToDtoList(applications));
    }

    @PostMapping(path = "/applications")
    public ResponseEntity<ApplicationDto> addApplication(ApplicationDto applicationDto) {
        val draftApplication = applicationMapper.dtoToEntity(applicationDto);
        val application = applicationService.insertApplication(draftApplication);

        return ResponseEntity.status(CREATED)
                             .body(applicationMapper.entityToDto(application));
    }

    @PatchMapping(path = "/applications/{applicationId}")
    public ResponseEntity<Void> rejectApplication(@PathVariable Integer applicationId) {
        try {
            applicationService.rejectApplication(applicationId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(NOT_FOUND, "Invalid application id", exception);
        }
    }
}
