import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Employee extends HospitalSQLBase {

    // D.1
    public static String getWorkers() {
        String query = "select * from ( " +
                       "    select worker_id, first_name, last_name, 'Doctor' as 'job_category', date(hire_date) as 'hire_date' " +
                       "    from worker, doctor where worker.worker_id = doctor.to_medical_employee " +
                       "  union " +
                       "    select worker_id, first_name, last_name, 'Technician' as 'job_category', date(hire_date) as 'hire_date' " +
                       "    from worker, technician where worker.worker_id = technician.to_medical_employee " +
                       "  union " +
                       "    select worker_id, first_name, last_name, 'Nurse' as 'job_category', date(hire_date) as 'hire_date' " +
                       "   from worker, nurse where worker.worker_id = nurse.to_medical_employee " +
                       "  union " +
                       "    select worker_id, first_name, last_name, 'Administrator' as 'job_category', date(hire_date) as 'hire_date' " +
                       "    from worker, administrator where worker.worker_id = administrator.to_non_medical_employee " +
                       "  union " +
                       "    select worker_id, first_name, last_name, 'Staff' as 'job_category', date(hire_date) as 'hire_date' " +
                       "    from worker, staff where worker.worker_id = staff.to_non_medical_employee " +
                       "  union " +
                       "    select worker_id, first_name, last_name, 'Volunteer' as 'job_category', date(hire_date) as 'hire_date' " +
                       "    from worker, volunteer where worker.worker_id = volunteer.to_worker ) as table1 " +
                       "order by last_name, first_name";
        return queryToResults(query);
    }

    // D.2
    public static String getVolunteersInfoDeskTuesdays() {
        String query = "select worker_id, first_name, last_name, date(hire_date) as 'hire_date' " +
                       "from worker, volunteer, volunteer_service, volunteer_service_assignment " +
                       "where worker.worker_id = volunteer.to_worker " +
                       "and volunteer_service.description = 'information desk' " +
                       "and volunteer_service.volunteer_service_id = volunteer_service_assignment.to_volunteer_service " +
                       "and volunteer.to_worker = volunteer_service_assignment.to_volunteer " +
                       "and volunteer_service_assignment.day = 'tuesday'";
        return queryToResults(query);
    }

    // D.3
    public static String getPrimaryDoctorsHighAdmissionRate() {
        String query = "select distinct worker.first_name as doc_first_name, worker.last_name as doc_first_name, " +
                       "                p1.patient_id, p2.total_admissions as admissions_in_year " +
                       "from patient p1 " +
                       "join ( " +
                       "    select patient.patient_id, count(*) as total_admissions " +
                       "    from admission, patient " +
                       "    where admission.to_patient = patient.patient_id " +
                       "    and to_days(admission.admission_date) > to_days(now()) - 365 " +
                       "    group by to_patient ) as p2 " +
                       "  on p1.patient_id = p2.patient_id " +
                       "join admission " +
                       "  on p1.patient_id = admission.to_patient " +
                       "join worker " +
                       "  on admission.to_doctor = worker.worker_id " +
                       "where p2.total_admissions >= 4";
        return queryToResults(query);
    }

    // D.4
    public static String getDiagnosesForDoctor() {
        System.out.println("\nDoctors:\n" + getDoctors());
        String[] vals = HospitalAdmin.getPatient("Doctor Name (first_name last_name) or ID");
        String query = "select worker.worker_id as doc_id, " +
                       "       worker.first_name as doc_first_name, " +
                       "       worker.last_name as doc_last_name, " +
                       "       diagnosis.diagnosis_id as diag_id, " +
                       "       diagnosis.name as diagnosis, " +
                       "       count(*) as diagnosis_count " +
                       "from doctor " +
                       "join worker " +
                       "  on doctor.to_medical_employee = worker.worker_id " +
                       "join admission " +
                       "  on worker.worker_id = admission.to_doctor " +
                       "join diagnosis " +
                       "  on admission.to_diagnosis = diagnosis.diagnosis_id " +
                       "where (worker.worker_id = " + vals[0] + " " +
                       "or (worker.first_name = '" + vals[1] + "' " +
                       "and worker.last_name = '" + vals[2] + "'))" +
                       "group by diagnosis.diagnosis_id " +
                       "order by diagnosis_count desc";
        return queryToResults(query);
    }

    // D.5
    public static String getTreatmentsOrderedByDoctor() {
        System.out.println("\nDoctors:\n" + getDoctors());
        String[] vals = HospitalAdmin.getPatient("Doctor Name (first_name last_name) or ID");
        String query = "select worker.worker_id as doc_id, " +
                       "       worker.first_name as doc_first_name, " +
                       "       worker.last_name as doc_last_name, " +
                       "       treatment.treatment_id as treat_id, " +
                       "       treatment.name as treatment, " +
                       "       count(*) as treatment_count " +
                       "from treatment " +
                       "join ordered_treatment " +
                       "  on treatment.treatment_id = ordered_treatment.to_treatment " +
                       "join worker " +
                       "  on ordered_treatment.to_ordering_doctor = worker.worker_id " +
                       "where (worker.worker_id = " + vals[0] + " " +
                       "or (worker.first_name = '" + vals[1] + "' " +
                       "and worker.last_name = '" + vals[2] + "'))" +
                       "group by treatment.treatment_id " +
                       "order by treatment_count desc";
        return queryToResults(query);
    }

    // D.6
    public static String getTreatmentsParticipatedByDoctor() {
        System.out.println("\nDoctors:\n" + getDoctors());
        String[] vals = HospitalAdmin.getPatient("Doctor Name (first_name last_name) or ID");
        String query = "select worker.worker_id as doc_id, " +
                       "       worker.first_name as doc_first_name, " +
                       "       worker.last_name as doc_last_name, " +
                       "       treatment.treatment_id as treat_id, " +
                       "       treatment.name as treatment, " +
                       "       count(*) as treatment_count " +
                       "from treatment " +
                       "join ordered_treatment " +
                       "  on treatment.treatment_id = ordered_treatment.to_treatment " +
                       "join medical_employee_administering_ordered_treatment as meaot " +
                       "  on meaot.to_ordered_treatment = ordered_treatment.ordered_treatment_id " +
                       "join worker " +
                       "  on meaot.to_medical_employee = worker.worker_id " +
                       "join doctor " +
                       "  on worker.worker_id = doctor.to_medical_employee " +
                       "where (worker.worker_id = " + vals[0] + " " +
                       "or (worker.first_name = '" + vals[1] + "' " +
                       "and worker.last_name = '" + vals[2] + "'))" +
                       "group by treatment.treatment_id " +
                       "order by treatment_count desc";
        return queryToResults(query);
    }

    // D.7
    public static String getEmployeesInvolvedInTreatmentOfEveryAdmittedPatient() {
        String query = "select distinct em1.to_medical_employee as employee_id, em1.first_name, em1.last_name " +
                       "from ( " +
                       "    select distinct meaot.to_medical_employee, worker.first_name, worker.last_name, a.to_patient " +
                       "    from medical_employee_administering_ordered_treatment as meaot " +
                       "    join inpatient_ordered_treatment as iot " +
                       "      on meaot.to_ordered_treatment = iot.to_ordered_treatment " +
                       "    join admission as a " +
                       "      on a.admission_id = iot.to_admission " +
                       "    join worker " +
                       "      on worker.worker_id = meaot.to_medical_employee) as em1 " +
                       "where not exists ( " +
                       "    select * " +
                       "    from (select distinct to_patient from admission) as p1 " +
                       "    where not exists ( " +
                       "        select * " +
                       "        from ( " +
                       "            select distinct meaot.to_medical_employee, a.to_patient " +
                       "            from medical_employee_administering_ordered_treatment as meaot " +
                       "            join inpatient_ordered_treatment as iot " +
                       "              on meaot.to_ordered_treatment = iot.to_ordered_treatment " +
                       "            join admission as a " +
                       "              on a.admission_id = iot.to_admission) as em2 " +
                       "         where (em1.to_medical_employee = em2.to_medical_employee) " +
                       "         and (em2.to_patient = p1.to_patient)))";
        return queryToResults(query);
    }

    // -=-=-=-=-=- Queries below are in support of the application and are not used by the direct project queries required

    public static String addDoctor() {
        System.out.println("\nCurrent Doctors:\n" + getDoctors());
        String fn = HospitalAdmin.getString("First Name of doctor to add: ");
        String ln = HospitalAdmin.getString("Last Name of doctor to add: ");
        String d = HospitalAdmin.getDate("Hire Date of doctor to add (YYYY-MM-DD): ");
        String admit = HospitalAdmin.getString("Can admit (True or False): ");

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into worker(hire_date, first_name, last_name) values('" + d + "','" + fn + "','" + ln + "')");
            statement.executeUpdate("insert into medical_employee values(last_insert_id())");
            statement.executeUpdate("insert into doctor values(last_insert_id(), " + admit + ")");
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String deleteDoctor() {
        System.out.println("\nNote: This system will only allow you to delete entities that have not been used by other entities");
        System.out.println("\nCurrent Doctors:\n" + getDoctors());
        String id = HospitalAdmin.getString("doctor_id of doctor to delete: ");
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete doctor from doctor where to_medical_employee=" + id);
            statement.executeUpdate("delete medical_employee from medical_employee where to_worker=" + id);
            statement.executeUpdate("delete worker from worker where worker_id=" + id);
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String updateDoctor() {
        System.out.println("\nCurrent Doctors:\n" + getDoctors());
        String id = HospitalAdmin.getString("doctor_id of doctor to edit: ");
        String fn = HospitalAdmin.getString("New First Name: ");
        String ln = HospitalAdmin.getString("New Last Name: ");
        String d = HospitalAdmin.getDate("New Hire Date (YYYY-MM-DD): ");
        String admit = HospitalAdmin.getString("New Can admit (True or False): ");
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update worker set hire_date='" + d + "', first_name='" + fn + "', last_name='" + ln + "' where worker_id=" + id);
            statement.executeUpdate("update doctor set can_admit=" + admit + " where to_medical_employee=" + id);
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String addVolunteer() {
        System.out.println("\nCurrent Volunteers:\n" + getVolunteers());
        String fn = HospitalAdmin.getString("First Name of volunteer to add: ");
        String ln = HospitalAdmin.getString("Last Name of volunteer to add: ");
        String d = HospitalAdmin.getDate("Hire Date of volunteer to add (YYYY-MM-DD): ");

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into worker(hire_date, first_name, last_name) values('" + d + "','" + fn + "','" + ln + "')");
            statement.executeUpdate("insert into volunteer values(last_insert_id())");
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String deleteVolunteer() {
        System.out.println("\nNote: This system will only allow you to delete entities that have not been used by other entities");
        System.out.println("\nCurrent Volunteers:\n" + getVolunteers());
        String id = HospitalAdmin.getString("volunteer_id of volunteer to delete: ");
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete volunteer from volunteer where to_worker=" + id);
            statement.executeUpdate("delete worker from worker where worker_id=" + id);
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String updateVolunteer() {
        System.out.println("\nCurrent Volunteers:\n" + getVolunteers());
        String id = HospitalAdmin.getString("volunteer_id of volunteer to edit: ");
        String fn = HospitalAdmin.getString("New First Name: ");
        String ln = HospitalAdmin.getString("New Last Name: ");
        String d = HospitalAdmin.getDate("New Hire Date (YYYY-MM-DD): ");
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update worker set hire_date='" + d + "', first_name='" + fn + "', last_name='" + ln + "' where worker_id=" + id);
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String addStaff() {
        System.out.println("\nCurrent Staff Members:\n" + getStaff());
        String fn = HospitalAdmin.getString("First Name of staff member to add: ");
        String ln = HospitalAdmin.getString("Last Name of staff member to add: ");
        String d = HospitalAdmin.getDate("Hire Date of staff member to add (YYYY-MM-DD): ");

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into worker(hire_date, first_name, last_name) values('" + d + "','" + fn + "','" + ln + "')");
            statement.executeUpdate("insert into non_medical_employee values(last_insert_id())");
            statement.executeUpdate("insert into staff values(last_insert_id())");
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String deleteStaff() {
        System.out.println("\nNote: This system will only allow you to delete entities that have not been used by other entities");
        System.out.println("\nCurrent Staff:\n" + getStaff());
        String id = HospitalAdmin.getString("staff_id of staff member to delete: ");
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete staff from staff where to_non_medical_employee=" + id);
            statement.executeUpdate("delete non_medical_employee from non_medical_employee where to_worker=" + id);
            statement.executeUpdate("delete worker from worker where worker_id=" + id);
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String updateStaff() {
        System.out.println("\nCurrent Staff Members:\n" + getStaff());
        String id = HospitalAdmin.getString("staff_id of staff member to edit: ");
        String fn = HospitalAdmin.getString("New First Name: ");
        String ln = HospitalAdmin.getString("New Last Name: ");
        String d = HospitalAdmin.getDate("New Hire Date (YYYY-MM-DD): ");
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update worker set hire_date='" + d + "', first_name='" + fn + "', last_name='" + ln + "' where worker_id=" + id);
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String addAdmin() {
        System.out.println("\nCurrent Administrators:\n" + getAdmins());
        String fn = HospitalAdmin.getString("First Name of admin to add: ");
        String ln = HospitalAdmin.getString("Last Name of admin to add: ");
        String d = HospitalAdmin.getDate("Hire Date of admin to add (YYYY-MM-DD): ");

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into worker(hire_date, first_name, last_name) values('" + d + "','" + fn + "','" + ln + "')");
            statement.executeUpdate("insert into non_medical_employee values(last_insert_id())");
            statement.executeUpdate("insert into administrator values(last_insert_id())");
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String deleteAdmin() {
        System.out.println("\nNote: This system will only allow you to delete entities that have not been used by other entities");
        System.out.println("\nCurrent Admins:\n" + getAdmins());
        String id = HospitalAdmin.getString("admin_id of administrator to delete: ");
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete administrator from administrator where to_non_medical_employee=" + id);
            statement.executeUpdate("delete non_medical_employee from non_medical_employee where to_worker=" + id);
            statement.executeUpdate("delete worker from worker where worker_id=" + id);
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String updateAdmin() {
        System.out.println("\nCurrent Administrators:\n" + getAdmins());
        String id = HospitalAdmin.getString("admin_id of admin to edit: ");
        String fn = HospitalAdmin.getString("New First Name: ");
        String ln = HospitalAdmin.getString("New Last Name: ");
        String d = HospitalAdmin.getDate("New Hire Date (YYYY-MM-DD): ");
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update worker set hire_date='" + d + "', first_name='" + fn + "', last_name='" + ln + "' where worker_id=" + id);
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String addNurse() {
        System.out.println("\nCurrent Nurses:\n" + getNurses());
        String fn = HospitalAdmin.getString("First Name of nurse to add: ");
        String ln = HospitalAdmin.getString("Last Name of nurse to add: ");
        String d = HospitalAdmin.getDate("Hire Date of nurse to add (YYYY-MM-DD): ");

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into worker(hire_date, first_name, last_name) values('" + d + "','" + fn + "','" + ln + "')");
            statement.executeUpdate("insert into medical_employee values(last_insert_id())");
            statement.executeUpdate("insert into nurse values(last_insert_id())");
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String deleteNurse() {
        System.out.println("\nNote: This system will only allow you to delete entities that have not been used by other entities");
        System.out.println("\nCurrent Nurses:\n" + getNurses());
        String id = HospitalAdmin.getString("nurse_id of nurse to delete: ");
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete nurse from nurse where to_medical_employee=" + id);
            statement.executeUpdate("delete medical_employee from medical_employee where to_worker=" + id);
            statement.executeUpdate("delete worker from worker where worker_id=" + id);
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }


    public static String updateNurse() {
        System.out.println("\nCurrent Nurses:\n" + getNurses());
        String id = HospitalAdmin.getString("nurse_id of nurse to edit: ");
        String fn = HospitalAdmin.getString("New First Name: ");
        String ln = HospitalAdmin.getString("New Last Name: ");
        String d = HospitalAdmin.getDate("New Hire Date (YYYY-MM-DD): ");
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update worker set hire_date='" + d + "', first_name='" + fn + "', last_name='" + ln + "' where worker_id=" + id);
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String addTech() {
        System.out.println("\nCurrent Technicians:\n" + getTechs());
        String fn = HospitalAdmin.getString("First Name of tech to add: ");
        String ln = HospitalAdmin.getString("Last Name of tech to add: ");
        String d = HospitalAdmin.getDate("Hire Date of tech to add (YYYY-MM-DD): ");

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into worker(hire_date, first_name, last_name) values('" + d + "','" + fn + "','" + ln + "')");
            statement.executeUpdate("insert into medical_employee values(last_insert_id())");
            statement.executeUpdate("insert into technician values(last_insert_id())");
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String deleteTech() {
        System.out.println("\nNote: This system will only allow you to delete entities that have not been used by other entities");
        System.out.println("\nCurrent Techs:\n" + getTechs());
        String id = HospitalAdmin.getString("tech_id of tech to delete: ");
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete technician from technician where to_medical_employee=" + id);
            statement.executeUpdate("delete medical_employee from medical_employee where to_worker=" + id);
            statement.executeUpdate("delete worker from worker where worker_id=" + id);
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String updateTech() {
        System.out.println("\nCurrent Technicians:\n" + getTechs());
        String id = HospitalAdmin.getString("tech_id of tech to edit: ");
        String fn = HospitalAdmin.getString("New First Name: ");
        String ln = HospitalAdmin.getString("New Last Name: ");
        String d = HospitalAdmin.getDate("New Hire Date (YYYY-MM-DD): ");
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update worker set hire_date='" + d + "', first_name='" + fn + "', last_name='" + ln + "' where worker_id=" + id);
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }

    public static String getDoctors() {
        String query = "select worker.worker_id as doctor_id, " +
                       "       worker.first_name, " +
                       "       worker.last_name, " +
                       "       date(worker.hire_date) as hire_date, " +
                       "       doctor.can_admit " +
                       "       from doctor join worker on worker.worker_id = doctor.to_medical_employee";
        return queryToResults(query);
    }

    public static String getVolunteers() {
        String query = "select worker.worker_id as volunteer_id, " +
                "       worker.first_name, " +
                "       worker.last_name, " +
                "       date(worker.hire_date) as hire_date " +
                "       from volunteer join worker on worker.worker_id = volunteer.to_worker";
        return queryToResults(query);
    }

    public static String getStaff() {
        String query = "select worker.worker_id as staff_id, " +
                "       worker.first_name, " +
                "       worker.last_name, " +
                "       date(worker.hire_date) as hire_date " +
                "       from staff join worker on worker.worker_id = staff.to_non_medical_employee";
        return queryToResults(query);
    }


    public static String getNurses() {
        String query = "select worker.worker_id as nurse_id, " +
                "       worker.first_name, " +
                "       worker.last_name, " +
                "       date(worker.hire_date) as hire_date " +
                "       from nurse join worker on worker.worker_id = nurse.to_medical_employee";
        return queryToResults(query);
    }

    public static String getTechs() {
        String query = "select worker.worker_id as tech_id, " +
                "       worker.first_name, " +
                "       worker.last_name, " +
                "       date(worker.hire_date) as hire_date " +
                "       from technician join worker on worker.worker_id = technician.to_medical_employee";
        return queryToResults(query);
    }

    public static String getDoctorsWithAdmit() {
        String query = "select worker.worker_id as doctor_id, " +
                "       worker.first_name, " +
                "       worker.last_name, " +
                "       date(worker.hire_date) as hire_date, " +
                "       doctor.can_admit " +
                "       from doctor join worker on worker.worker_id = doctor.to_medical_employee " +
                "       where doctor.can_admit = True";
        return queryToResults(query);
    }
    public static String getAdmins() {
        String query = "select worker.worker_id as admin_id, " +
                       "       worker.first_name, " +
                       "       worker.last_name, " +
                       "       date(worker.hire_date) as hire_date " +
                       "       from administrator join worker on worker.worker_id = administrator.to_non_medical_employee";
        return queryToResults(query);
    }

    public static String addTreatment() {
        System.out.println("\nCurrent Treatments:\n" + DiagAndTreatInfo.getTreatmentsPlain());
        String tn = HospitalAdmin.getString("New Treatment Name: ");
        String query = "insert into treatment(name) values('" + tn + "')";
        return executeUpdate(query);
    }

    public static String editTreatment() {
        System.out.println("\nCurrent Treatments:\n" + DiagAndTreatInfo.getTreatmentsPlain());
        String id = HospitalAdmin.getString("treatment_id of treatment to edit: ");
        String n = HospitalAdmin.getString("New Name: ");
        String query = "update treatment set name='" + n +"' where treatment_id=" + id;
        return executeUpdate(query);
    }

    public static String deleteTreatment() {
        System.out.println("\nNote: This system will only allow you to delete entities that have not been used by other entities");
        System.out.println("\nCurrent Treatments:\n" + DiagAndTreatInfo.getTreatmentsPlain());
        String id = HospitalAdmin.getString("treatment_id of treatment to delete: ");
        String query = "delete treatment from treatment where treatment_id=" + id;
        return executeUpdate(query);
    }

    public static String addDiagnosis() {
        System.out.println("\nCurrent Diagnosis Options:\n" + DiagAndTreatInfo.getDiagnoses());
        String n = HospitalAdmin.getString("New Diagnosis name: ");
        String query = "insert into diagnosis(name) values('" + n + "')";
        return executeUpdate(query);
    }

    public static String editDiagnosis() {
        System.out.println("\nCurrent Diagnosis Options:\n" + DiagAndTreatInfo.getDiagnoses());
        String id = HospitalAdmin.getString("diagnosis_id of diagnosis to edit: ");
        String n = HospitalAdmin.getString("New Diagnosis name: ");
        String query = "update diagnosis set name='" + n +"' where diagnosis_id=" + id;
        return executeUpdate(query);
    }

    public static String deleteDiagnosis() {
        System.out.println("\nNote: This system will only allow you to delete entities that have not been used by other entities");
        System.out.println("\nCurrent Diagnosis Options:\n" + DiagAndTreatInfo.getDiagnoses());
        String id = HospitalAdmin.getString("diagnosis_id of diagnosis to delete: ");
        String query = "delete diagnosis from diagnosis where diagnosis_id=" + id;
        return executeUpdate(query);
    }

    public static String orderTreatment() {
        System.out.println("\nCurrent Doctors:\n" + Employee.getDoctors());
        String doctor_id = HospitalAdmin.getString("Your doctor_id: ");
        System.out.println("\nCurrent Treatments:\n" + DiagAndTreatInfo.getTreatmentsPlain());
        String treatment_id = HospitalAdmin.getString("treatment_id of treatment to order: ");
        System.out.println("\nOrder type:\n1) Existing Admission\n2) Existing Outpatient\n3) New Outpatient");

        String order_type = "";
        while (!order_type.equals("1") && !order_type.equals("2") && !order_type.equals("3")) {
            order_type = HospitalAdmin.getString("Order Type (1, 2, or 3): ");
        }

        if (order_type.equals("1")) {
            System.out.println("\nCurrent Admissions:\n" + Patient.getAdmissions());
            String admission_id = HospitalAdmin.getString("admission_id of admission to order treatment for: ");
            try {
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                statement.executeUpdate("insert into ordered_treatment(to_treatment, to_ordering_doctor, ordered_date) values('" + treatment_id + "','" + doctor_id + "', now())");

                // todo: trigger that admission is not discharged
                // todo: trigger that admitting doctor is primary or assigned
                statement.executeUpdate("insert into inpatient_ordered_treatment values(last_insert_id(), " + admission_id +")");
                return "Success!\n";
            } catch (SQLException e) {
                return "Failure!\n" + e.toString() + "\n";
            }

        } else if (order_type.equals("2")) {
            System.out.println("\nCurrent Outpatient Treatment Groups:\n" + DiagAndTreatInfo.getOutpatientTreatmentGroups());
            String og_id = HospitalAdmin.getString("outpatient_group_id of outpatient treatment group to order treatment for: ");

            try {
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                statement.executeUpdate("insert into ordered_treatment(to_treatment, to_ordering_doctor, ordered_date) values('" + treatment_id + "','" + doctor_id + "', now())");

                // todo: trigger that otg is not ended
                statement.executeUpdate("insert into outpatient_ordered_treatment values(last_insert_id(), " + og_id +")");
                return "Success!\n";
            } catch (SQLException e) {
                return "Failure!\n" + e.toString() + "\n";
            }
        } else {
            System.out.println("\nCurrent Patients not Admitted or Receiving Outpatient Services:\n" + Patient.getPatientsNotCurrentlyAdmittedOrOutpatient());
            String patient_id = HospitalAdmin.getString("patient_id of patient to begin outpatient services on: ");
            System.out.println("\nCurrent Diagnosis:\n" + DiagAndTreatInfo.getDiagnoses());
            String diagnosis_id = HospitalAdmin.getString("diagnosis_id of initial diagnosis: ");

            try {
                Connection connection = getConnection();
                Statement statement = connection.createStatement();

                // todo: trigger that patient is not admitted or already in otg
                statement.executeUpdate("insert into outpatient_ordered_treatment_group(to_diagnosis, to_patient, start_date) values('" + diagnosis_id + "','" + patient_id + "', now())");
                statement.executeQuery("set @last_id_in_ootg = last_insert_id()");
                statement.executeUpdate("insert into ordered_treatment(to_treatment, to_ordering_doctor, ordered_date) values('" + treatment_id + "','" + doctor_id + "', now())");
                statement.executeUpdate("insert into outpatient_ordered_treatment values(last_insert_id(), @last_id_in_ootg)");
                return "Success!\n";
            } catch (SQLException e) {
                return "Failure!\n" + e.toString() + "\n";
            }
        }

    }

    public static String changeDiagnosis() {
        System.out.println("\nPatient type:\n1) Admission/Inpatient\n2) Outpatient");
        String order_type = "";
        while (!order_type.equals("1") && !order_type.equals("2")) {
            order_type = HospitalAdmin.getString("Patient Type (1 or 2): ");
        }

        if (order_type.equals("1")) {
            System.out.println("\nCurrent Doctors:\n" + Employee.getDoctors());
            String doctor_id = HospitalAdmin.getString("Your doctor_id: ");
            System.out.println("\nYour Current Admissions:\n" + Patient.getAdmissionsWithDiagnosisForDoc(doctor_id));
            String admission_id = HospitalAdmin.getString("admission_id of admission to change diagnosis for: ");
            System.out.println("\nCurrent Diagnosis:\n" + DiagAndTreatInfo.getDiagnoses());
            String diagnosis_id = HospitalAdmin.getString("diagnosis_id of new diagnosis: ");

            // todo: trigger make sure admission is not discharged
            String query = "update admission set to_diagnosis=" + diagnosis_id + " where admission_id=" + admission_id + " and to_doctor=" + doctor_id;
            return executeUpdate(query);
        } else {
            System.out.println("\nCurrent Outpatient Treatment Groups:\n" + DiagAndTreatInfo.getOutpatientTreatmentGroupsWithDiagnosis());
            String og_id = HospitalAdmin.getString("outpatient_group_id of outpatient treatment group to change diagnosis for: ");
            System.out.println("\nCurrent Diagnosis:\n" + DiagAndTreatInfo.getDiagnoses());
            String diagnosis_id = HospitalAdmin.getString("diagnosis_id of new diagnosis: ");

            // todo: trigger make sure og is not discharged
            String query = "update outpatient_ordered_treatment_group set to_diagnosis=" + diagnosis_id + " where outpatient_ordered_treatment_group_id=" + og_id;
            return executeUpdate(query);
        }
    }

    public static String administerTreatment(String emp_type) {
        String emp_id = "";
        if (emp_type.equals("doctor")) {
            System.out.println("\nCurrent Doctors:\n" + Employee.getDoctors());
            emp_id = HospitalAdmin.getString("Your doctor_id: ");
        } else if (emp_type.equals("nurse")) {
            System.out.println("\nCurrent Nurses:\n" + Employee.getNurses());
            emp_id = HospitalAdmin.getString("Your nurse_id: ");
        } else {
            System.out.println("\nCurrent Technician:\n" + Employee.getTechs());
            emp_id = HospitalAdmin.getString("Your tech_id: ");
        }


        System.out.println("\nPatient type:\n1) Admission/Inpatient\n2) Outpatient");
        String order_type = "";
        while (!order_type.equals("1") && !order_type.equals("2")) {
            order_type = HospitalAdmin.getString("Patient Type (1 or 2): ");
        }

        if (order_type.equals("1")) {
            System.out.println("\nCurrent Ordered Treatments for Admitted Patients:\n" + DiagAndTreatInfo.getCurrentOrderedTreatmentsForInpatient());
            String ot_id = HospitalAdmin.getString("ordered_treatment_id to administer: ");
            String query = "insert into medical_employee_administering_ordered_treatment(to_medical_employee, to_ordered_treatment, date) values('" + emp_id + "','" + ot_id + "', now())";
            return executeUpdate(query);
        } else {
            System.out.println("\nCurrent Ordered Treatments for Outpatient Patients:\n" + DiagAndTreatInfo.getCurrentOrderedTreatmentsForOutpatient());
            String ot_id = HospitalAdmin.getString("ordered_treatment_id to administer: ");
            String query = "insert into medical_employee_administering_ordered_treatment(to_medical_employee, to_ordered_treatment, date) values('" + emp_id + "','" + ot_id + "', now())";
            return executeUpdate(query);
        }

    }

    public static String assignDoctor() {
        System.out.println("\nCurrent Doctors:\n" + Employee.getDoctors());
        String doctor_id = HospitalAdmin.getString("Your doctor_id: ");

        System.out.println("\nYour Current Admissions:\n" + Patient.getAdmissionsWithDiagnosisForDoc(doctor_id));
        String admission_id = HospitalAdmin.getString("admission_id of admission to assign doctors to: ");

        // todo: ideally this would exclude doctor_id from above
        System.out.println("\nCurrent Doctors:\n" + Employee.getDoctors());
        String doctor_id2 = HospitalAdmin.getString("doctor_id of doctor to assign: ");

        String query = "insert into doctor_assigned_admission(to_admission, to_doctor) values('" + admission_id + "', '" + doctor_id2 + "')";
        return executeUpdate(query);
    }

    public static String concludeOutpatientGroup() {
        System.out.println("\nCurrent Outpatient Treatment Groups:\n" + DiagAndTreatInfo.getOutpatientTreatmentGroupsWithDiagnosis());
        String og_id = HospitalAdmin.getString("outpatient_group_id of outpatient treatment group to conclude: ");

        // todo: ideally we'd make sure og_id is for a otg where end_date is null
        String query = "update outpatient_ordered_treatment_group set end_date=now() where end_date is null and outpatient_ordered_treatment_group_id=" + og_id;
        return executeUpdate(query);
    }

    public static String getVolunteersServiceOptions() {
        String query = "select volunteer_service_id as option_id, description from volunteer_service";
        return queryToResults(query);
    }

    public static String addVolunteersServiceOption() {
        System.out.println("\nCurrent Service Options:\n" + getVolunteersServiceOptions());
        String n = HospitalAdmin.getString("New Option: ");
        String query = "insert into volunteer_service(description) values('" + n + "')";
        return executeUpdate(query);
    }

    public static String updateVolunteersServiceOption() {
        System.out.println("\nCurrent Service Options:\n" + getVolunteersServiceOptions());
        String id = HospitalAdmin.getString("option_id of option to edit: ");
        String n = HospitalAdmin.getString("New description: ");
        String query = "update volunteer_service set description='" + n +"' where volunteer_service_id=" + id;
        return executeUpdate(query);
    }

    public static String deleteVolunteersServiceOption() {
        System.out.println("\nNote: This system will only allow you to delete entities that have not been used by other entities");
        System.out.println("\nCurrent Service Options:\n" + getVolunteersServiceOptions());
        String id = HospitalAdmin.getString("option_id of option to delete: ");
        String query = "delete volunteer_service from volunteer_service where volunteer_service_id=" + id;
        return executeUpdate(query);
    }

    public static String getVolunteersServiceAssignments() {
        String query = "select volunteer_service_assignment.volunteer_service_assignment_id as assignment_id, " +
                       "       first_name, last_name, day, description " +
                       "from volunteer_service_assignment " +
                       "join worker " +
                       "  on worker.worker_id = volunteer_service_assignment.to_volunteer " +
                       "join volunteer_service " +
                       "  on volunteer_service.volunteer_service_id = volunteer_service_assignment.to_volunteer_service " +
                       "order by assignment_id";
        return queryToResults(query);
    }

    public static String addVolunteersServiceAssignment() {
        System.out.println("\nCurrent Service Assignments:\n" + getVolunteersServiceAssignments());
        System.out.println("\nCurrent Volunteers:\n" + getVolunteers());
        String id1 = HospitalAdmin.getString("volunteer_id of volunteer to add assignemnt for: ");

        System.out.println("\nCurrent Service Options:\n" + getVolunteersServiceOptions());
        String id2 = HospitalAdmin.getString("option_id of service option to assignemnt volunteer to: ");

        String day = HospitalAdmin.getString("Which day (monday/tuesday/wednesday/thursday/friday/saturday/sunday): ");
        String query = "insert into volunteer_service_assignment(to_volunteer, to_volunteer_service, day) values('" + id1 + "', '" + id2 +"', '" + day + "')";

        return executeUpdate(query);

    }

    public static String updateVolunteersServiceAssignment() {
        System.out.println("\nCurrent Service Assignments:\n" + getVolunteersServiceAssignments());
        String id = HospitalAdmin.getString("assignment_id of assignment to edit: ");

        System.out.println("\nCurrent Volunteers:\n" + getVolunteers());
        String id1 = HospitalAdmin.getString("volunteer_id of volunteer to assignemnt to this assignment: ");

        System.out.println("\nCurrent Service Options:\n" + getVolunteersServiceOptions());
        String id2 = HospitalAdmin.getString("option_id of service option to assignemnt to this assignment: ");

        String day = HospitalAdmin.getString("New day (monday/tuesday/wednesday/thursday/friday/saturday/sunday): ");

        String query = "update volunteer_service_assignment set to_volunteer='" + id1 +"', to_volunteer_service='" + id2 + "', day='" + day + "' where volunteer_service_assignment_id=" + id;

        return executeUpdate(query);

    }


    public static String deleteVolunteersServiceAssignment() {
        System.out.println("\nNote: This system will only allow you to delete entities that have not been used by other entities");
        System.out.println("\nCurrent Service Assignments:\n" + getVolunteersServiceAssignments());
        String id = HospitalAdmin.getString("assignment_id of assignment to delete: ");
        String query = "delete volunteer_service_assignment from volunteer_service_assignment where volunteer_service_assignment_id=" + id;
        return executeUpdate(query);
    }

}
