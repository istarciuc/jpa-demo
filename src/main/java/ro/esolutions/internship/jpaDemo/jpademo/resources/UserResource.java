package ro.esolutions.internship.jpaDemo.jpademo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.esolutions.internship.jpaDemo.jpademo.entity.Client;
import ro.esolutions.internship.jpaDemo.jpademo.entity.Reservation;
import ro.esolutions.internship.jpaDemo.jpademo.repository.ClientRepository;
import ro.esolutions.internship.jpaDemo.jpademo.repository.ConfigProfileInterface;
import ro.esolutions.internship.jpaDemo.jpademo.repository.ReservationRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Transactional
public class UserResource {


    private final ConfigProfileInterface configProfileInterface;

    private final ClientRepository clientRepository;

    private final ReservationRepository reservationRepository;

    @PostConstruct
    public void initDB() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client("Cojocaru", "Mihai", "Bucuresti", "mihai@mail.com", "034562356246"));
        clients.add(new Client("Branda", "Teodor", "Bucuresti", "branda@mail.com", "034562356567"));

        List<Client> persistedClients = clientRepository.saveAll(clients);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(LocalDate.now(), LocalDate.now().plusDays(3L), Reservation.RoomType.SINGLE, persistedClients.get(0)));
        reservationRepository.saveAll(reservations);


    }

    @Autowired
    public UserResource(ConfigProfileInterface configProfileInterface, ClientRepository clientRepository, ReservationRepository reservationRepository) {
        this.configProfileInterface = configProfileInterface;
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
    }


    @GetMapping
    public ResponseEntity<Client> getClient(@RequestParam String firstName) {
        return clientRepository.findClientByFirstName(firstName)
                .map(client -> ResponseEntity.ok(client))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping
    public ResponseEntity<Object> addClient(@RequestBody Client client) {
        clientRepository.save(client);
        return ResponseEntity.ok(client.getId());
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers() {
        configProfileInterface.getConfig();
        return ResponseEntity.ok(clientRepository.findAll());
    }

    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestParam String firstName) {
        return clientRepository.findClientByFirstName(firstName)
        .map(client -> {
            client.setActive(false);
            clientRepository.save(client);
            saveClientReservation(client);
            return ResponseEntity.ok(client);
        }).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    private void saveClientReservation(Client client) {
        reservationRepository.findFirstByClient(client)
                .map(reservation -> {
                    reservation.setToDate(LocalDate.now().minusDays(10));
                    return reservationRepository.save(reservation);
                }).orElseThrow(() -> new RuntimeException("No reseravation for client"));
    }




}
