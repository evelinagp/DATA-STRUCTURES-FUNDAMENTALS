package vaccopsjava11.test;

import org.junit.Before;
import org.junit.Test;
import vaccopsjava11.Doctor;
import vaccopsjava11.Patient;
import vaccopsjava11.VaccOps;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class Tests {

    private VaccOps vaccOps;
    Doctor d1 = new Doctor("a", 1);
    Doctor d2 = new Doctor("b", 2);
    Doctor d3 = new Doctor("c", 3);
    Patient p1 = new Patient("a", 1, 3, "a");
    Patient p2 = new Patient("b", 3, 2, "b");
    Patient p3 = new Patient("c", 2, 1, "c");

    Doctor d4 = new Doctor("d", 3);
    Doctor d5 = new Doctor("e", 4);
    Doctor d6 = new Doctor("f", 4);
    Doctor d7 = new Doctor("g", 2);
    Doctor d8 = new Doctor("h", 2);
    Patient p4 = new Patient("d", 3, 1, "a");
    Patient p5 = new Patient("e", 3, 1, "a");
    Patient p6 = new Patient("f", 2, 1, "a");
    Patient p7 = new Patient("g", 5, 10, "a");
    Patient p8 = new Patient("h", 5, 5, "a");
    Patient p9 = new Patient("i", 5, 50, "a");
    Patient p10 = new Patient("j", 2, 2, "a");
    Patient p11 = new Patient("k", 1, 2, "a");

    @Before
    public void Setup() {
        this.vaccOps = new VaccOps();
    }

    @Test // 1
    public void TestAddingDoctor() {
        vaccOps.addDoctor(d1);
        var d = new ArrayList<>(this.vaccOps.getDoctors());


        assertEquals(1, d.size());
        assertSame(d.get(0).name, d1.name);
        assertEquals(d.get(0).popularity, d1.popularity);
    }

    @Test // 2
    public void TestAddingMultipleDoctors() {
        for (int i = 0; i < 1000; i++) {
            this.vaccOps.addDoctor(new Doctor(i + "", i));
        }

        assertTrue(this.vaccOps.getDoctors().size() == 1000);
    }

    // 3
    @Test(expected = IllegalArgumentException.class)
    public void TestAddingDoctorAlreadyExistThrowException() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d1);
    }

    @Test // 4
    public void TestAddingPatient() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addPatient(d1, p1);
        var p = new ArrayList<>(this.vaccOps.getPatients());

        assertTrue(p.size() == 1);
        assertTrue(p.get(0).name.equals(p1.name));
        assertTrue(p.get(0).height == p1.height);
        assertTrue(p.get(0).town.equals(p1.town));
        assertTrue(p.get(0).age == p1.age);
    }

    @Test // 5
    public void TestAddingMultiplePatients() {
        this.vaccOps.addDoctor(d1);
        for (int i = 0; i < 1000; i++) {
            var p = new Patient(i + "", i, i, i + "");
            this.vaccOps.addPatient(d1, p);
        }

        assertTrue(this.vaccOps.getPatients().size() == 1000);
    }

    // 6
    @Test(expected = IllegalArgumentException.class)
    public void TestAddingPatientWithNonExistentDoctorThrowException() {
        this.vaccOps.addPatient(d1, p1);
    }


    @Test // 7
    public void TestNotAddingAnyDoctors() {
        assertTrue(this.vaccOps.getPatients().size() == 0);
    }


    @Test // Performance
    public void TestAddDoctorPerf() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            this.vaccOps.addDoctor(new Doctor(i + "", i));
        }

        long stop = System.currentTimeMillis();
        assertTrue(stop - start <= 20);
    }

    @Test // 8
    public void TestRemoveDoctorsWithPatients() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addPatient(d1, p1);
        var p = new ArrayList<>(this.vaccOps.getPatients());
        var d = new ArrayList<>(this.vaccOps.getDoctors());

        assertTrue(p.size() == 1);
        assertTrue(d.size() == 1);
        this.vaccOps.removeDoctor("m");
        assertTrue(d.size() == 0);



     /*   assertTrue(p.get(0).name == p1.name);
        assertTrue(p.get(0).height == p1.height);
        assertTrue(p.get(0).town == p1.town);
        assertTrue(p.get(0).age == p1.age);*/
    }

    public void TestGetPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addPatient(d1, p1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addPatient(d2, p2);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addPatient(d3, p3);

        this.vaccOps.getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge();


        var p = new ArrayList<>(this.vaccOps.getPatients());

        assertTrue(p.size() == 3);
        assertTrue(p.get(0).name.equals(p1.name));
        assertTrue(p.get(0).height == p1.height);
        assertTrue(p.get(0).town.equals(p1.town));
        assertTrue(p.get(0).age == p1.age);
    }

    @Test //9
    public void TestMovePatientFromToDoctor() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addPatient(d1, p1);

        var p = new ArrayList<>(this.vaccOps.getPatients());
        var d = new ArrayList<>(this.vaccOps.getDoctors());
      //  var dP = new ArrayList<>(this.vaccOps.getDoctors());

        assertTrue(p.size() == 1);
        assertTrue(d.size() == 2);

        this.vaccOps.changeDoctor(d1, d2, p1);

        assertTrue(d.get(0).name.equals(d1.name));
        assertTrue(d.get(0).popularity == d1.popularity);

        assertTrue(d.get(1).name.equals(d2.name));
        assertTrue(d.get(1).popularity == d2.popularity);

        assertTrue(p.get(0).name.equals(p1.name));
        assertTrue(p.get(0).height == p1.height);
        assertTrue(p.get(0).town.equals(p1.town));
        assertTrue(p.get(0).age == p1.age);




    }
}
