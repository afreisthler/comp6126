import java.util.Scanner;

public class HospitalAdmin {

    // Poor mans clear console
    public static void clear() {
        for (int i = 0; i < 1000; ++i) {
            System.out.println();
        }
    }

    public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        do {
            HospitalAdmin.clear();
            System.out.println("Main Menu");
            System.out.println("1.  Query Menu");
            System.out.println("2.  Admin Action Menu");
            System.out.println("3.  Doctor Action Menu");
            System.out.println("4.  Technician Action Menu");
            System.out.println("5.  Nurse Action Menu");
            System.out.println("6.  Volunteer Action Menu");
            System.out.print("Your choice, 0 to quit: ");
            int in = scanner.nextInt();
            switch (in) {
                case 1: HospitalAdmin.queryMenu(); break;
                case 2: HospitalAdmin.adminMenu(); break;
                case 3: HospitalAdmin.doctorMenu(); break;
                case 4: HospitalAdmin.techMenu(); break;
                case 5: HospitalAdmin.nurseMenu(); break;
                case 6: HospitalAdmin.volunteerMenu(); break;
                case 0: quit = true; break;
            }
        } while (!quit);
    }

    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        do {
            HospitalAdmin.clear();
            System.out.println("Main Menu -> Admin Action Menu");
            System.out.println("1.  List Patients");
            System.out.println("2.  Add a Patient");
            System.out.println("3.  Edit a Patient");
            System.out.println("4.  Delete a Patient");
            System.out.println("5.  Admit a Patient");
            System.out.println("6.  Discharge a Patient");
            System.out.println("7.  List Doctors");
            System.out.println("8.  Add a Doctor");
            System.out.println("9.  Edit a Doctor");
            System.out.println("10. Delete a Doctor");
            System.out.println("11. List Volunteers");
            System.out.println("12. Add a Volunteer");
            System.out.println("13. Edit a Volunteer");
            System.out.println("14. Delete a Volunteer");
            System.out.println("15. List Staff Members");
            System.out.println("16. Add a Staff Member");
            System.out.println("17. Edit a Staff Member");
            System.out.println("18. Delete a Staff Member");
            System.out.println("19. List Administrators");
            System.out.println("20. Add an Administrator");
            System.out.println("21. Edit an Administrator");
            System.out.println("22. Delete an Administrator");
            System.out.println("23. List Nurses");
            System.out.println("24. Add a Nurse");
            System.out.println("25. Edit a Nurse");
            System.out.println("26. Delete a Nurse");
            System.out.println("27. List Technicians");
            System.out.println("28. Add a Technician");
            System.out.println("29. Edit a Technician");
            System.out.println("30. Delete a Technician");
            System.out.println("31. List Rooms");
            System.out.println("32. Add a Room");
            System.out.println("33. Edit a Room");
            System.out.println("34. Delete a Room");
            System.out.print("Your choice, 0 to return to Main Menu: ");
            int in = scanner.nextInt();
            switch (in) {
                case 1: HospitalAdmin.displayResults(Patient.getPatients()); break;
                case 2: HospitalAdmin.displayResults(Patient.addPatient()); break;
                case 3: HospitalAdmin.displayResults(Patient.updatePatient()); break;
                case 4: HospitalAdmin.displayResults(Patient.deletePatient()); break;
                case 5: HospitalAdmin.displayResults(Patient.admitPatient()); break;
                case 6: HospitalAdmin.displayResults(Patient.dischargePatient()); break;
                case 7: HospitalAdmin.displayResults(Employee.getDoctors()); break;
                case 8: HospitalAdmin.displayResults(Employee.addDoctor()); break;
                case 9: HospitalAdmin.displayResults(Employee.updateDoctor()); break;
                case 10: HospitalAdmin.displayResults(Employee.deleteDoctor()); break;
                case 11: HospitalAdmin.displayResults(Employee.getVolunteers()); break;
                case 12: HospitalAdmin.displayResults(Employee.addVolunteer()); break;
                case 13: HospitalAdmin.displayResults(Employee.updateVolunteer()); break;
                case 14: HospitalAdmin.displayResults(Employee.deleteVolunteer()); break;
                case 15: HospitalAdmin.displayResults(Employee.getStaff()); break;
                case 16: HospitalAdmin.displayResults(Employee.addStaff()); break;
                case 17: HospitalAdmin.displayResults(Employee.updateStaff()); break;
                case 18: HospitalAdmin.displayResults(Employee.deleteStaff()); break;
                case 19: HospitalAdmin.displayResults(Employee.getAdmins()); break;
                case 20: HospitalAdmin.displayResults(Employee.addAdmin()); break;
                case 21: HospitalAdmin.displayResults(Employee.updateAdmin()); break;
                case 22: HospitalAdmin.displayResults(Employee.deleteAdmin()); break;
                case 23: HospitalAdmin.displayResults(Employee.getNurses()); break;
                case 24: HospitalAdmin.displayResults(Employee.addNurse()); break;
                case 25: HospitalAdmin.displayResults(Employee.updateNurse()); break;
                case 26: HospitalAdmin.displayResults(Employee.deleteNurse()); break;
                case 27: HospitalAdmin.displayResults(Employee.getTechs()); break;
                case 28: HospitalAdmin.displayResults(Employee.addTech()); break;
                case 29: HospitalAdmin.displayResults(Employee.updateTech()); break;
                case 30: HospitalAdmin.displayResults(Employee.deleteTech()); break;
                case 31: HospitalAdmin.displayResults(Room.getRoomsWithId()); break;
                case 32: HospitalAdmin.displayResults(Room.addRoom()); break;
                case 33: HospitalAdmin.displayResults(Room.updateRoom()); break;
                case 34: HospitalAdmin.displayResults(Room.deleteRoom()); break;
                case 0: quit = true; break;
            }
        } while (!quit);
    }

    public static void doctorMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        do {
            HospitalAdmin.clear();
            System.out.println("Main Menu -> Doctor Action Menu");
            System.out.println("1.  List Treatment Options");
            System.out.println("2.  Add Treatment Option");
            System.out.println("3.  Edit Treatment Option");
            System.out.println("4.  Delete Treatment Option");
            System.out.println("5.  List Diagnosis Options");
            System.out.println("6.  Add Diagnosis Option");
            System.out.println("7.  Edit Diagnosis Option");
            System.out.println("8.  Delete Diagnosis Option");
            System.out.println("9.  Order a Treatment");
            System.out.println("10. Change a Diagnosis for a Patient");
            System.out.println("11. Administer a Treatment");
            System.out.println("12. Assign a Doctor to my patient");
            System.out.println("13. Conclude an Outpatient Treatment Group");
            System.out.print("Your choice, 0 to return to Main Menu: ");
            int in = scanner.nextInt();
            switch (in) {
                case 1: HospitalAdmin.displayResults(DiagAndTreatInfo.getTreatmentsPlain()); break;
                case 2: HospitalAdmin.displayResults(Employee.addTreatment()); break;
                case 3: HospitalAdmin.displayResults(Employee.editTreatment()); break;
                case 4: HospitalAdmin.displayResults(Employee.deleteTreatment()); break;
                case 5: HospitalAdmin.displayResults(DiagAndTreatInfo.getDiagnoses()); break;
                case 6: HospitalAdmin.displayResults(Employee.addDiagnosis()); break;
                case 7: HospitalAdmin.displayResults(Employee.editDiagnosis()); break;
                case 8: HospitalAdmin.displayResults(Employee.deleteDiagnosis()); break;
                case 9: HospitalAdmin.displayResults(Employee.orderTreatment()); break;
                case 10: HospitalAdmin.displayResults(Employee.changeDiagnosis()); break;
                case 11: HospitalAdmin.displayResults(Employee.administerTreatment("doctor")); break;
                case 12: HospitalAdmin.displayResults(Employee.assignDoctor()); break;
                case 13: HospitalAdmin.displayResults(Employee.concludeOutpatientGroup()); break;
                case 0: quit = true; break;
            }
        } while (!quit);
    }

    public static void techMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        do {
            HospitalAdmin.clear();
            System.out.println("Main Menu -> Technician Action Menu");
            System.out.println("1.  Administer a Treatment");
            System.out.print("Your choice, 0 to return to Main Menu: ");
            int in = scanner.nextInt();
            switch (in) {
                case 1: HospitalAdmin.displayResults(Employee.administerTreatment("technician")); break;
                case 0: quit = true; break;
            }
        } while (!quit);
    }

    public static void nurseMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        do {
            HospitalAdmin.clear();
            System.out.println("Main Menu -> Nurse Action Menu");
            System.out.println("1.  Administer a Treatment");
            System.out.print("Your choice, 0 to return to Main Menu: ");
            int in = scanner.nextInt();
            switch (in) {
                case 1: HospitalAdmin.displayResults(Employee.administerTreatment("nurse")); break;
                case 0: quit = true; break;
            }
        } while (!quit);
    }

    public static void volunteerMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        do {
            HospitalAdmin.clear();
            System.out.println("Main Menu -> Volunteer Action Menu");
            System.out.println("1.  List Volunteer Options");
            System.out.println("2.  Add Volunteer Option");
            System.out.println("3.  Edit Volunteer Option");
            System.out.println("4.  Delete Volunteer Option");
            System.out.println("5.  List Volunteer Assignments");
            System.out.println("6.  Add Volunteer Assignment");
            System.out.println("7.  Edit Volunteer Assignment");
            System.out.println("8.  Delete Volunteer Assignment");
            System.out.print("Your choice, 0 to return to Main Menu: ");
            int in = scanner.nextInt();
            switch (in) {
                case 1: HospitalAdmin.displayResults(Employee.getVolunteersServiceOptions()); break;
                case 2: HospitalAdmin.displayResults(Employee.addVolunteersServiceOption()); break;
                case 3: HospitalAdmin.displayResults(Employee.updateVolunteersServiceOption()); break;
                case 4: HospitalAdmin.displayResults(Employee.deleteVolunteersServiceOption()); break;
                case 5: HospitalAdmin.displayResults(Employee.getVolunteersServiceAssignments()); break;
                case 6: HospitalAdmin.displayResults(Employee.addVolunteersServiceAssignment()); break;
                case 7: HospitalAdmin.displayResults(Employee.updateVolunteersServiceAssignment()); break;
                case 8: HospitalAdmin.displayResults(Employee.deleteVolunteersServiceAssignment()); break;
                case 0: quit = true; break;
            }
        } while (!quit);
    }

    public static void queryMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        do {
            HospitalAdmin.clear();
            System.out.println("Main Menu -> Query Menu");
            System.out.println("1.  Room Utilization");
            System.out.println("2.  Patient Information");
            System.out.println("3.  Diagnosis and Treatment Information");
            System.out.println("4.  Employee Information");
            System.out.print("Your choice, 0 to return to Main Menu: ");
            int in = scanner.nextInt();
            switch (in) {
                case 1: HospitalAdmin.roomUtilizationQueryMenu(); break;
                case 2: HospitalAdmin.patientInformationQueryMenu(); break;
                case 3: HospitalAdmin.diagnosisAndTreatmentInformationQueryMenu(); break;
                case 4: HospitalAdmin.employeeInformationQueryMenu(); break;
                case 0: quit = true; break;
            }
        } while (!quit);
    }

    public static void roomUtilizationQueryMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        do {
            HospitalAdmin.clear();
            System.out.println("Main Menu -> Query Menu -> Room Utilization");
            System.out.println("1. List the rooms that are occupied");
            System.out.println("2. List the rooms that are currently unoccupied");
            System.out.println("3. List all rooms in the hospital");
            System.out.print("Your choice, 0 to return to Query Menu: ");
            int in = scanner.nextInt();
            switch (in) {
                case 1: HospitalAdmin.displayResults(Room.getRoomsOccupied()); break;
                case 2: HospitalAdmin.displayResults(Room.getRoomsUnoccupied()); break;
                case 3: HospitalAdmin.displayResults(Room.getRooms()); break;
                case 0: quit = true; break;
            }
        } while (!quit);
    }

    public static void patientInformationQueryMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        do {
            HospitalAdmin.clear();
            System.out.println("Main Menu -> Query Menu -> Patient Information");
            System.out.println("1.  List all patients");
            System.out.println("2.  List all patients currently admitted");
            System.out.println("3.  List all patients who were receiving inpatient services within a given date range");
            System.out.println("4.  List all patients who were discharged in a given date range");
            System.out.println("5.  List all patients who are currently receiving outpatient services");
            System.out.println("6.  List all patients who have received outpatient services within a given date range");
            System.out.println("7.  List all admissions to the hospital for a patient");
            System.out.println("8.  List all treatments that were administered for an admitted patient");
            System.out.println("9.  List patients who were admitted to the hospital within 30 days of their last discharge date");
            System.out.println("10. List stats for each patient that has ever been admitted to the hospital");
            System.out.print("Your choice, 0 to return to Query Menu: ");
            int in = scanner.nextInt();
            switch (in) {
                case 1: HospitalAdmin.displayResults(Patient.getPatients()); break;
                case 2: HospitalAdmin.displayResults(Patient.getPatientsCurrentlyAdmitted()); break;
                case 3: HospitalAdmin.displayResults(Patient.getPatientsReceivingInpatientServicesInRange()); break;
                case 4: HospitalAdmin.displayResults(Patient.getPatientsDischargedInRange()); break;
                case 5: HospitalAdmin.displayResults(Patient.getPatientsReceivingOutpatientServices()); break;
                case 6: HospitalAdmin.displayResults(Patient.getPatientsReceivedOutpatientServicesInRange()); break;
                case 7: HospitalAdmin.displayResults(Patient.getAdmissionsForPatient()); break;
                case 8: HospitalAdmin.displayResults(Patient.getTreatmentsForPatient()); break;
                case 9: HospitalAdmin.displayResults(Patient.getPatients30Days()); break;
                case 10: HospitalAdmin.displayResults(Patient.getPatientsStats()); break;
                case 0: quit = true; break;
            }
        } while (!quit);
    }

    public static void diagnosisAndTreatmentInformationQueryMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        do {
            HospitalAdmin.clear();
            System.out.println("Main Menu -> Query Menu -> Diagnosis and Treatment Information");
            System.out.println("1. List the diagnoses given to admitted patients");
            System.out.println("2. List the diagnoses given to outpatients");
            System.out.println("3. List the diagnoses given to hospital patients (both inpatient and outpatient)");
            System.out.println("4. List the treatments performed at the hospital");
            System.out.println("5. List the treatments performed on admitted patients");
            System.out.println("6. List the treatments performed on outpatients");
            System.out.println("7. List the diagnoses associated with patients who have the highest occurrences of admissions");
            System.out.println("8. List all the hospital employees that were involved for a given ordered treatment");
            System.out.print("Your choice, 0 to return to Query Menu: ");
            int in = scanner.nextInt();
            switch (in) {
                case 1: HospitalAdmin.displayResults(DiagAndTreatInfo.getDiagnosesForAdmittedPatients()); break;
                case 2: HospitalAdmin.displayResults(DiagAndTreatInfo.getDiagnosesForOutpatients()); break;
                case 3: HospitalAdmin.displayResults(DiagAndTreatInfo.getDiagnosesForPatients()); break;
                case 4: HospitalAdmin.displayResults(DiagAndTreatInfo.getTreatments()); break;
                case 5: HospitalAdmin.displayResults(DiagAndTreatInfo.getTreatmentsForAdmittedPatients()); break;
                case 6: HospitalAdmin.displayResults(DiagAndTreatInfo.getTreatmentsForOutpatients()); break;
                case 7: HospitalAdmin.displayResults(DiagAndTreatInfo.getDiagnosisHighAdmissions()); break;
                case 8: HospitalAdmin.displayResults(DiagAndTreatInfo.getAllEmployeesForTreatment()); break;
                case 0: quit = true; break;
            }
        } while (!quit);
    }

    public static void employeeInformationQueryMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        do {
            HospitalAdmin.clear();
            System.out.println("Main Menu -> Query Menu -> Employee Information");
            System.out.println("1. List all workers at the hospital");
            System.out.println("2. List the volunteers who work at the information desk on Tuesdays");
            System.out.println("3. List the primary doctors of patients with a high admission rate ");
            System.out.println("4. List all associated diagnoses for a given doctor");
            System.out.println("5. List all treatments that a given doctor ordered");
            System.out.println("6. List all treatments in which a given doctor participated");
            System.out.println("7. List employees who have been involved in the treatment of every admitted patient");
            System.out.print("Your choice, 0 to return to Query Menu: ");
            int in = scanner.nextInt();
            switch (in) {
                case 1: HospitalAdmin.displayResults(Employee.getWorkers()); break;
                case 2: HospitalAdmin.displayResults(Employee.getVolunteersInfoDeskTuesdays()); break;
                case 3: HospitalAdmin.displayResults(Employee.getPrimaryDoctorsHighAdmissionRate()); break;
                case 4: HospitalAdmin.displayResults(Employee.getDiagnosesForDoctor()); break;
                case 5: HospitalAdmin.displayResults(Employee.getTreatmentsOrderedByDoctor()); break;
                case 6: HospitalAdmin.displayResults(Employee.getTreatmentsParticipatedByDoctor()); break;
                case 7: HospitalAdmin.displayResults(Employee.getEmployeesInvolvedInTreatmentOfEveryAdmittedPatient()); break;
                case 0: quit = true; break;
            }
        } while (!quit);
    }

    public static void displayResults(String string) {
        System.out.println("");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println("Results");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.print(string);
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.print("Press return to continue");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static String getDate(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        String date = scanner.nextLine();
        return date;
    }

    public static String getString(String message) {
        String str = "";
        boolean valid = false;
        while (!valid) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(message);
            str = scanner.nextLine();
            if(str.equals("")) {
                System.out.println("Invalid, please try again");
            } else {
                valid = true;
            }
        }
        return str;
    }

    public static String[] getPatient(String message) {
        String[] returnArray = new String[3];
        Integer id = -1;
        String first_name = "";
        String last_name = "";
        Boolean valid = false;
        while (!valid) {
            String str = HospitalAdmin.getString(message);
            try {
                id = Integer.valueOf(str);
                valid = true;
            } catch (NumberFormatException nfe) {
                String[] names = str.split(" ");
                if (names.length == 2) {
                    first_name = names[0];
                    last_name = names[1];
                    valid = true;
                } else {
                    System.out.println("Invalid entry.");
                }

            }
        }
        returnArray[0] = id.toString();
        returnArray[1] = first_name;
        returnArray[2] = last_name;
        return returnArray;
    }

    public static void main(String[] args) {
        HospitalAdmin.mainMenu();
    }
}