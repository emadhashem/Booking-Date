package com.book.date.BookingDate.features.seats.dao;

import com.book.date.BookingDate.features.rooms.dao.RoomDao;
import com.book.date.BookingDate.features.rooms.entity.Room;
import com.book.date.BookingDate.features.seats.entity.Seat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeatDao {

    private final RoomDao roomDao;
    private final EntityManager entityManager;

    @Transactional
    public void addSeatsToRoom(Room room, List<Seat> seats) {
        seats.forEach(newSeat -> room.addSeat(newSeat));
        roomDao.save(room);
    }

    public List<Seat> fetchSeats(String roomName, String search) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Seat> criteriaQuery = criteriaBuilder.createQuery(Seat.class);
        Root<Seat> root = criteriaQuery.from(Seat.class);
        criteriaQuery.select(root);

        if (!roomName.isEmpty()) {
            Predicate idPredicate = criteriaBuilder.equal(root.get("room").get("name"), roomName);
            criteriaQuery.where(idPredicate);
        }
        if (!search.isEmpty()) {
            Predicate searchPredicate = criteriaBuilder.like(root.get("title"), "%" + search + "%");
            criteriaQuery.where(searchPredicate);
        }
        TypedQuery<Seat> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
