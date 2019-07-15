package ro.esolutions.internship.jpaDemo.jpademo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.esolutions.internship.jpaDemo.jpademo.entity.Reservation;
import ro.esolutions.internship.jpaDemo.jpademo.repository.ClientRepository;
import ro.esolutions.internship.jpaDemo.jpademo.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationResource {

    private final ReservationRepository reservationRepository;

    private final ClientRepository clientRepository;


    @Autowired
    public ReservationResource(ReservationRepository reservationRepository, ClientRepository clientRepository) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public ResponseEntity<Object> getReservationForClient(@RequestParam String email) {
        List<Reservation> clientReservations = clientRepository.findClientByEmailAddress(email)
                .map(reservationRepository::findAllByClient)
                .orElse(new ArrayList<>());

        if (clientReservations.size() != 0) {
            return ResponseEntity.ok(clientReservations);
        }

        return ResponseEntity.badRequest().build();

    }

}
