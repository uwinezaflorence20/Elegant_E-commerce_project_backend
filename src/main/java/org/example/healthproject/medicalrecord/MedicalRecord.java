package org.example.healthproject.medicalrecord;

import org.example.healthproject.patient.Patient;
import org.example.healthproject.doctor.Doctor;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
@AllArgsConstructor
@Data
@Entity
@Table(name = "medical_record")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many records belong to one patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Many records are created by one doctor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @NotBlank
    @Size(max = 255)
    private String diagnosis;

    @Size(max = 500)
    private String prescription;

    @PastOrPresent(message = "Record date cannot be in the future")
    @Column(name = "record_date", nullable = false)
    private LocalDateTime recordDate;



    public MedicalRecord() {
        this.recordDate = LocalDateTime.now(); // default current datetime
    }

}