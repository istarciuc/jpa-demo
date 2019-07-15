package ro.esolutions.internship.jpaDemo.jpademo.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    @Type(type = "org.hibernate.type.LocalDateType")
    private LocalDate fromDate;

    @Type(type = "org.hibernate.type.LocalDateType")
    private LocalDate toDate;

    @Type(type = "org.hibernate.type.LocalDateType")
    private LocalDate reservationDate;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @OneToMany
    private Client client;

    public Reservation() {

    }

    public Reservation(LocalDate fromDate, LocalDate toDate, RoomType roomType, Client client) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.roomType = roomType;
        this.client = client;
    }

    public enum Status {
        NEW, CANCELED, CONFIRMED
    }

    public enum RoomType {
        SINGLE, DOUBLE, TWIN
    }

    @PrePersist
    private void prePersist() {
        this.status = Status.NEW;
        this.reservationDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
