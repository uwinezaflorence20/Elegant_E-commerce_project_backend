package org.example.healthproject.doctor;

import org.example.healthproject.clinic.Clinic;
import org.example.healthproject.clinic.ClinicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;

    public DoctorService(DoctorRepository doctorRepository, ClinicRepository clinicRepository) {
        this.doctorRepository = doctorRepository;
        this.clinicRepository = clinicRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public Doctor createDoctor(DoctorDTO doctorDTO) {
        if (doctorRepository.existsByEmail(doctorDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Clinic clinic = clinicRepository.findById(doctorDTO.getClinicId())
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));

        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());
        doctor.setSpecialty(Doctor.Specialty.valueOf(doctorDTO.getSpecialty()));
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setClinic(clinic);

        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Long id, DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Clinic clinic = clinicRepository.findById(doctorDTO.getClinicId())
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));

        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());
        doctor.setSpecialty(Doctor.Specialty.valueOf(doctorDTO.getSpecialty()));
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setClinic(clinic);

        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new IllegalArgumentException("Doctor not found");
        }
        doctorRepository.deleteById(id);
    }
}
