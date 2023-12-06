package com.cquisper.msvc.enquire.repositories;

import com.cquisper.msvc.enquire.models.Enquire;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface EnquireRepository extends ReactiveMongoRepository<Enquire, String> {

}
