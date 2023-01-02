package vaccopsjava11;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
 
public class VaccOps implements IVaccOps {
 
    private Map<String, Doctor> doctors;
    private Map<String, Patient> patients;
    private Map<String, List<Patient>> doctorsWithPatients;
 
    public VaccOps() {
        this.doctors = new HashMap<>();
        this.patients = new HashMap<>();
        this.doctorsWithPatients = new HashMap<>();
    }
 
    public void addDoctor(Doctor d) {
        if (exist(d)) {
            throw new IllegalArgumentException();
        }
 
        this.doctors.put(d.name, d);
        this.doctorsWithPatients.put(d.name, new ArrayList<>());
    }
 
    public void addPatient(Doctor d, Patient p) {
        if (!exist(d) || exist(p)) {
            throw new IllegalArgumentException();
        }
        p.doctor = d;
        this.patients.put(p.name, p);
        this.doctorsWithPatients.get(d.name).add(p);
    }
 
    public Collection<Doctor> getDoctors() {
        return this.doctors.values();
    }
 
    public Collection<Patient> getPatients() {
        return this.patients.values();
    }
 
    public boolean exist(Doctor d) {
        return this.doctors.containsKey(d.name);
    }
 
    public boolean exist(Patient p) {
        return this.patients.containsKey(p.name);
    }
 
    public Doctor removeDoctor(String name) {
        Doctor doctor = this.doctors.get(name);
        if (doctor == null) {
            throw new IllegalArgumentException();
        }
        this.doctors.remove(doctor.name);
        this.doctorsWithPatients.get(doctor.name).forEach(p -> this.patients.remove(p.name));//Is it correct?
        this.doctorsWithPatients.remove(doctor.name);
        return doctor;
    }
 
    public void changeDoctor(Doctor from, Doctor to, Patient p) {
        if (!exist(from) || !exist(to) || !exist(p)) {
            throw new IllegalArgumentException();
        }
        p.doctor = to;
        this.doctorsWithPatients.get(from.name).remove(p);
        this.doctorsWithPatients.get(to.name).add(p);
    }
 
    public Collection<Doctor> getDoctorsByPopularity(int populariry) {
        return getDoctors().stream().filter(d -> d.popularity == populariry).collect(Collectors.toList());
    }
 
    public Collection<Patient> getPatientsByTown(String town) {
        return getPatients().stream().filter(p -> p.town.equals(town)).collect(Collectors.toList());
    }
 
    public Collection<Patient> getPatientsInAgeRange(int lo, int hi) {
        return getPatients().stream().filter(p -> p.age >= lo && p.age <= hi).collect(Collectors.toList());
    }
 
    public Collection<Doctor> getDoctorsSortedByPatientsCountDescAndNameAsc() {
        return getDoctors().stream().sorted((d1, d2) -> {
 
            int firstDPsize = this.doctorsWithPatients.get(d1.name).size();
            int secondDPsize = this.doctorsWithPatients.get(d2.name).size();
 
            int result = Integer.compare(secondDPsize, firstDPsize);
            if (result == 0) {
                result = d1.name.compareTo(d2.name);
            }
 
            return result;
        }).collect(Collectors.toList());
    }
 
    public Collection<Patient> getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge() {
        return getPatients().stream().sorted((p1, p2) -> {
 
            int result = Integer.compare(p1.doctor.popularity, p2.doctor.popularity);
            if (result == 0) {
                result = Integer.compare(p2.height, p1.height);
                if (result == 0) {
                    result = Integer.compare(p1.age, p2.age);
                }
            }
 
            return result;
        }).collect(Collectors.toList());
    }
 
}