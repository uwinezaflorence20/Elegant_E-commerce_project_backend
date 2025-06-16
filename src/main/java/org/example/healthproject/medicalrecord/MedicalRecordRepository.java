package org.example.healthproject.medicalrecord;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    // Additional custom queries if needed
}
