package ro.esolutions.internship.jpaDemo.jpademo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.esolutions.internship.jpaDemo.jpademo.entity.Client;
import ro.esolutions.internship.jpaDemo.jpademo.entity.Reservation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByClient(Client client);

    Optional<Reservation> findFirstByClient(Client client);
}
