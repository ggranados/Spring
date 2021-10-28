package com.linkedinlerning.reactive.controller;

import com.linkedinlerning.reactive.model.Reservation;
import com.linkedinlerning.reactive.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ReservationResource.ROOM_V_1_RESERVATION)
@CrossOrigin
public class ReservationResource {

    public static final String ROOM_V_1_RESERVATION = "/room/v1/reservation/";

    public final ReservationService reservationService;

    @Autowired
    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Reservation> getReservationById(@PathVariable String id){

        return reservationService.getReservation(id);
    }

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Reservation> createReservation(@RequestBody  Mono<Reservation> reservation){

        return reservationService.createReservation(reservation);
    }

    @PutMapping(path = "{roomId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> updatePrice(
            @PathVariable String roomId,
            @RequestBody  Mono<Reservation> reservation){
        return Mono.just("{}");
    }

    @DeleteMapping(path = "{roomId}")
    public Mono<Boolean> deleteReservation(@PathVariable String roomId){
        return Mono.just(true);
    }
}
