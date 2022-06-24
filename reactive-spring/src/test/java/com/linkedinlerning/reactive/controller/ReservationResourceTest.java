package com.linkedinlerning.reactive.controller;

import com.linkedinlerning.reactive.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.linkedinlerning.reactive.controller.ReservationResource.ROOM_V_1_RESERVATION;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationResourceTest {

    @Autowired
    private ApplicationContext context;
    private WebTestClient webTestClient;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToApplicationContext(this.context)
                .build();

        reservation = new Reservation(123l, LocalDate.now(), LocalDate.now().plus(10, ChronoUnit.DAYS), 150);
    }

    @Test
    void createReservation() {
        webTestClient.post()
                .uri(ROOM_V_1_RESERVATION)
                .body(Mono.just(reservation), Reservation.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Reservation.class);
    }

    @Test
    void getAllReservationById() {
        webTestClient.get()
                .uri(ROOM_V_1_RESERVATION)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Reservation.class);
    }
}