package org.example.healthproject.clinic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    Optional<Clinic> findByName(String name);
    Optional<Clinic> findByPhone(String phone);
}
