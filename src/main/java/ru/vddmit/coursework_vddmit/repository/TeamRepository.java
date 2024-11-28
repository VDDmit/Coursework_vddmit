package ru.vddmit.coursework_vddmit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vddmit.coursework_vddmit.entity.Team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {

    Optional<Team> findByName(String name);

    Optional<Team> findById(UUID id);
}
