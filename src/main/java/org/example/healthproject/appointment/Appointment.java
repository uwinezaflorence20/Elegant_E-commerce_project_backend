package org.example.healthproject.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthproject.doctor.Doctor;
import org.example.healthproject.patient.Patient;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "appointment",
        uniqueConstraints = @UniqueConstraint(columnNames = {"patient_id", "doctor_id", "appointment_date"}))
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many appointments belong to one patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Many appointments belong to one doctor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Future(message = "Appointment date must be in the future")
    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Size(max = 500)
    private String notes;

    // Getters and setters...

    public enum Status {
        SCHEDULED,
        COMPLETED,
        CANCELLED
    }
}