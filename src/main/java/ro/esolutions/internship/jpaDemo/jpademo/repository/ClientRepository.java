package ro.esolutions.internship.jpaDemo.jpademo.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ro.esolutions.internship.jpaDemo.jpademo.entity.Client;

import java.util.Optional;

@Component
public interface ClientRepository extends JpaRepository<Client, Long>{

    @Query("select c from Client c where c.firstName=:firstName")
    Optional<Client> getClientByFirstName(@Param("firstName") String fName);

    Optional<Client> findClientByFirstName(String firstName);

    Optional<Client> findClientByEmailAddress(String emailAddress);

}
