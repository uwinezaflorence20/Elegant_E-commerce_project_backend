package org.example.healthproject.clinic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClinicService {

    private final ClinicRepository clinicRepository;

    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    public List<Clinic> getAllClinics() {
        return clinicRepository.findAll();
    }

    public Optional<Clinic> getClinicById(Long id) {
        return clinicRepository.findById(id);
    }

    public Clinic createClinic(Clinicdto clinicDTO) {
        if (clinicRepository.findByName(clinicDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("Clinic name must be unique");
        }
        if (clinicRepository.findByPhone(clinicDTO.getPhone()).isPresent()) {
            throw new IllegalArgumentException("Clinic phone must be unique");
        }

        Clinic clinic = new Clinic();
        clinic.setName(clinicDTO.getName());
        clinic.setAddress(clinicDTO.getAddress());
        clinic.setPhone(clinicDTO.getPhone());

        return clinicRepository.save(clinic);
    }

    public Clinic updateClinic(Long id, Clinicdto clinicDTO) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));

        if (!clinic.getName().equals(clinicDTO.getName()) &&
                clinicRepository.findByName(clinicDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("Clinic name must be unique");
        }

        if (!clinic.getPhone().equals(clinicDTO.getPhone()) &&
                clinicRepository.findByPhone(clinicDTO.getPhone()).isPresent()) {
            throw new IllegalArgumentException("Clinic phone must be unique");
        }

        clinic.setName(clinicDTO.getName());
        clinic.setAddress(clinicDTO.getAddress());
        clinic.setPhone(clinicDTO.getPhone());

        return clinicRepository.save(clinic);
    }

    public void deleteClinic(Long id) {
        if (!clinicRepository.existsById(id)) {
            throw new IllegalArgumentException("Clinic not found");
        }
        clinicRepository.deleteById(id);
    }
}
