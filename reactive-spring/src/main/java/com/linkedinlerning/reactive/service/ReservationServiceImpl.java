package com.linkedinlerning.reactive.service;

import com.linkedinlerning.reactive.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReservationServiceImpl implements ReservationService{

    private final ReactiveMongoOperations reactiveMongoOperations;

    @Autowired
    public ReservationServiceImpl(ReactiveMongoOperations reactiveMongoOperations) {
        this.reactiveMongoOperations = reactiveMongoOperations;
    }

    @Override
    public Mono<Reservation> getReservation(String id) {
        return reactiveMongoOperations.findById(id, Reservation.class);
    }

    @Override
    public Mono<Reservation> createReservation(Mono<Reservation> reservation) {
        return reactiveMongoOperations.save(reservation);
    }

    @Override
    public Mono<Reservation> updateReservation(String id, Mono<Reservation> reservation) {
        var res = reservation.flatMap( r -> reactiveMongoOperations
                .findAndModify(
                    Query.query(Criteria.where("id").is(id)),
                    Update.update("price",r.getPrice()), Reservation.class
        ).flatMap(result -> {
            result.setPrice(r.getPrice());
            return Mono.just(result);
        }));
        return res;
    }

    @Override
    public Mono<Boolean> deleteReservation(String id) {
        return reactiveMongoOperations.remove(
                Query.query(Criteria.where("id").is(id)), Reservation.class
        ).flatMap( deleteResult ->
                Mono.just(deleteResult.wasAcknowledged()
                )
        );
    }
}