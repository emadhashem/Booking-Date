package com.book.date.BookingDate.features.rooms.dao;

import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.rooms.entity.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RoomDao {

    private final EntityManager entityManager;

    public Room save(Room newRoom) {

        return entityManager.merge(newRoom);
    }

    public Room findByName(String name) {

        return entityManager.find(Room.class, name);
    }

    public List<Room> fetchRooms(String search, String ownerId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
        Root<Room> root = criteriaQuery.from(Room.class);
        criteriaQuery.select(root);
        if (search != null && !search.isEmpty()) {
            Predicate searchPredicate = criteriaBuilder.like(root.get("name"), "%" + search + "%");
            criteriaQuery.where(searchPredicate);
        }

        if (ownerId != null && !ownerId.isEmpty()) {
            Predicate idPredicate = criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
            criteriaQuery.where(idPredicate);
        }

        TypedQuery<Room> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public boolean isUserInRoom(int userId, Room room) {
        List<Member> members = room.getUsers().stream().filter(item -> item.getId() == userId)
                .toList();

        return !members.isEmpty();
    }

    public boolean checkAccess(String access, Room room) {
        return room.getAccessCode().equals(access);
    }

}
