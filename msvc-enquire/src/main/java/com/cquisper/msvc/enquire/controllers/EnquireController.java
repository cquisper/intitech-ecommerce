package com.cquisper.msvc.enquire.controllers;

import com.cquisper.msvc.enquire.models.Enquire;
import com.cquisper.msvc.enquire.services.EnquireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController @Slf4j
@RequiredArgsConstructor
public class EnquireController {

    private final EnquireService enquireService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Enquire> getAllEnquires() {
        return enquireService.getAllEnquires();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Enquire> createEnquire(@RequestBody Enquire enquire) {
        log.info("Enquire to : {}", enquire);
        return enquireService.createEnquire(enquire);
    }

    @GetMapping("/find-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Enquire> getEnquireById(@PathVariable String id) {
        return enquireService.getEnquireById(id);
    }

    @PatchMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Enquire> updateEnquire(@RequestBody Map<Object, Object> fields, @PathVariable String id) {
        return enquireService.updateEnquire(fields, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteEnquire(@PathVariable String id) {
        return enquireService.deleteEnquire(id);
    }
}
