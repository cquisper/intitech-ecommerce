package com.cquisper.msvc.enquire.services;

import com.cquisper.msvc.enquire.models.Enquire;
import com.cquisper.msvc.enquire.repositories.EnquireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.Map;

@Service @Slf4j
@RequiredArgsConstructor
public class EnquireService {

    private final EnquireRepository enquireRepository;

    public Flux<Enquire> getAllEnquires() {
        return enquireRepository.findAll()
                .doOnNext(e -> log.info("Enquire found: {}", e));
    }

    public Mono<Enquire> getEnquireById(String id) {
        return enquireRepository.findById(id)
                .doOnNext(e -> log.info("Enquire found: {}", e));
    }

    public Mono<Enquire> createEnquire(Enquire enquire) {
        return Mono.just(enquire)
                .doOnNext(enq -> enq.setStatus("Submitted"))
                .doOnNext(enquire1 -> log.info("Enquire to create: {}", enquire1))
                .flatMap(this.enquireRepository::save)
                .doOnNext(enquire1 -> log.info("Enquire created: 2 {}", enquire1))
                .doOnSuccess(e -> log.info("Enquire created: {}", e));
    }

    public Mono<Enquire> updateEnquire(Map<Object, Object> fields, String id) {
        return enquireRepository.findById(id)
                .doOnNext(enquire -> {
                    fields.forEach((key, value) -> {
                        Field field = ReflectionUtils.findRequiredField(Enquire.class, key.toString());
                        field.setAccessible(true);
                        log.info("Modification: " + field.getName());
                        ReflectionUtils.setField(field, enquire, value);
                    });
                })
                .flatMap(enquireRepository::save)
                .doOnSuccess(e -> log.info("Enquire updated: {}", e));
    }

    public Mono<Void> deleteEnquire(String id) {
        return enquireRepository.deleteById(id)
                .doOnSuccess(e -> log.info("Enquire deleted: {}", e));
    }
}
