package org.example.healthproject.medicalrecord;

import org.example.healthproject.patient.PatientRepository;
import org.example.healthproject.doctor.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository,
                                PatientRepository patientRepository,
                                DoctorRepository doctorRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<MedicalRecordDTO> getAllMedicalRecords() {
        return medicalRecordRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<MedicalRecordDTO> getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id)
                .map(this::convertToDTO);
    }

    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO dto) {
        MedicalRecord medicalRecord = convertToEntity(dto);

        // recordDate is set by default in the entity constructor
        MedicalRecord saved = medicalRecordRepository.save(medicalRecord);
        return convertToDTO(saved);
    }

    public MedicalRecordDTO updateMedicalRecord(Long id, MedicalRecordDTO dto) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medical record not found"));

        medicalRecord.setPatient(patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found")));

        medicalRecord.setDoctor(doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found")));

        medicalRecord.setDiagnosis(dto.getDiagnosis());
        medicalRecord.setPrescription(dto.getPrescription());
        medicalRecord.setRecordDate(dto.getRecordDate());

        MedicalRecord updated = medicalRecordRepository.save(medicalRecord);
        return convertToDTO(updated);
    }

    public void deleteMedicalRecord(Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            throw new IllegalArgumentException("Medical record not found");
        }
        medicalRecordRepository.deleteById(id);
    }

    private MedicalRecordDTO convertToDTO(MedicalRecord entity) {
        MedicalRecordDTO dto = new MedicalRecordDTO();
        dto.setId(entity.getId());
        dto.setPatientId(entity.getPatient().getId());
        dto.setDoctorId(entity.getDoctor().getId());
        dto.setDiagnosis(entity.getDiagnosis());
        dto.setPrescription(entity.getPrescription());
        dto.setRecordDate(entity.getRecordDate());
        return dto;
    }

    private MedicalRecord convertToEntity(MedicalRecordDTO dto) {
        MedicalRecord entity = new MedicalRecord();

        entity.setPatient(patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found")));

        entity.setDoctor(doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found")));

        entity.setDiagnosis(dto.getDiagnosis());
        entity.setPrescription(dto.getPrescription());

        // recordDate can be null in DTO, so use current time if null
        entity.setRecordDate(dto.getRecordDate() != null ? dto.getRecordDate() : entity.getRecordDate());

        return entity;
    }
}
