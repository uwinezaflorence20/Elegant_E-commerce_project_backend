package org.example.healthproject.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByPatientIdAndDoctorIdAndAppointmentDate(Long patientId, Long doctorId, LocalDateTime appointmentDate);

    boolean existsByPatientIdAndDoctorIdAndAppointmentDate(Long patientId, Long doctorId, LocalDateTime appointmentDate);
}