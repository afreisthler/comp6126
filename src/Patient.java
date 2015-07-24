import java.util.Scanner;

public class Patient extends HospitalSQLBase {

    // B.1
    public static String getPatients() {
        String query = "select * from patient";
        return queryToResults(query);
    }

    // B.2
    public static String getPatientsCurrentlyAdmitted() {
        String query = "select patient_id, first_name, last_name " +
                       "from patient, admission " +
                       "where admission.to_patient = patient.patient_id " +
                       "and admission.discharge_date is null";
        return queryToResults(query);
    }

    // B.3
    public static String getPatientsReceivingInpatientServicesInRange() {
        String date1 = HospitalAdmin.getDate("Enter start date (YYYY-MM-DD): ");
        String date2 = HospitalAdmin.getDate("Enter end date (YYYY-MM-DD): ");
        String query = "select distinct patient_id, first_name, last_name " +
                       "from patient, admission " +
                       "where admission.to_patient = patient.patient_id " +
                       "and admission.admission_date <= '" + date2 + "' " +
                       "and (admission.discharge_date >= '" + date1 + "' or admission.discharge_date is null)";
        return queryToResults(query);
    }

    // B.4
    public static String getPatientsDischargedInRange() {
        String date1 = HospitalAdmin.getDate("Enter start date (YYYY-MM-DD): ");
        String date2 = HospitalAdmin.getDate("Enter end date (YYYY-MM-DD): ");
        String query = "select patient_id, first_name, last_name " +
                       "from patient, admission " +
                       "where admission.to_patient = patient.patient_id " +
                       "and admission.discharge_date >= '" + date1 + "' " +
                       "and admission.discharge_date <= '" + date2 + "'";
        return queryToResults(query);
    }

    // B.5
    public static String getPatientsReceivingOutpatientServices() {
        String query = "select distinct patient_id, first_name, last_name " +
                       "from patient, outpatient_ordered_treatment_group " +
                       "where outpatient_ordered_treatment_group.to_patient = patient.patient_id " +
                       "and outpatient_ordered_treatment_group.end_date is null";
        return queryToResults(query);
    }

    // B.6
    public static String getPatientsReceivedOutpatientServicesInRange() {
        String date1 = HospitalAdmin.getDate("Enter start date (YYYY-MM-DD): ");
        String date2 = HospitalAdmin.getDate("Enter end date (YYYY-MM-DD): ");
        String query = "select distinct patient_id, first_name, last_name " +
                       "from patient, outpatient_ordered_treatment_group " +
                       "where outpatient_ordered_treatment_group.to_patient = patient.patient_id " +
                       "and outpatient_ordered_treatment_group.start_date <= '" + date2 + "' " +
                       "and (outpatient_ordered_treatment_group.end_date >= '" + date1 + "' or outpatient_ordered_treatment_group.end_date is null)";
        return queryToResults(query);
    }

    // B.7
    public static String getAdmissionsForPatient() {
        System.out.println("\nPatients:\n" + getPatients());

        String[] vals = HospitalAdmin.getPatient("Patient Name (first_name last_name) or ID");

        String query = "select admission_id, patient_id, first_name, last_name, date(admission_date) as date, " +
                       "       diagnosis.name as diagnosis " +
                       "from patient, admission, diagnosis " +
                       "where admission.to_patient = patient.patient_id " +
                       "and admission.to_diagnosis = diagnosis.diagnosis_id " +
                       "and (patient.patient_id = " + vals[0] + " " +
                       "or (patient.first_name = '" + vals[1] + "' " +
                       "and patient.last_name = '" + vals[2] + "'))";
        return queryToResults(query);
    }

    // B.8
    public static String getTreatmentsForPatient() {
        System.out.println("\nPatients who have been admitted:\n" + getPatientsWhoHaveBeenAdmitted());

        String[] vals = HospitalAdmin.getPatient("Patient Name (first_name last_name) or ID");
        String query = "select admission_id, date(admission_date) as admission_date, patient_id, first_name, last_name, " +
                       "       treatment.name as treatment, " +
                       "       date(medical_employee_administering_ordered_treatment.date) as treatment_date " +
                       "from patient, admission, inpatient_ordered_treatment, ordered_treatment, treatment, " +
                       "     medical_employee_administering_ordered_treatment " +
                       "where admission.to_patient = patient.patient_id " +
                       "and admission.admission_id = inpatient_ordered_treatment.to_admission " +
                       "and inpatient_ordered_treatment.to_ordered_treatment = ordered_treatment.ordered_treatment_id " +
                       "and ordered_treatment.to_treatment = treatment.treatment_id " +
                       "and medical_employee_administering_ordered_treatment.to_ordered_treatment = ordered_treatment.ordered_treatment_id " +
                       "and (patient.patient_id = " + vals[0] + " " +
                       "or (patient.first_name = '" + vals[1] + "' " +
                       "and patient.last_name = '" + vals[2] + "')) " +
                       "order by admission_date, treatment_date";
        return queryToResults(query);

    }

    // B.9
    public static String getPatients30Days() {
        String query = "select patient.patient_id, patient.first_name as patient_first_name, " +
                       "       patient.last_name as patient_last_name, " +
                       "       diagnosis.name as diagnosis, worker.first_name as doctor_first_name, " +
                       "       worker.last_name as doctor_last_name " +
                       "from doctor join admission on doctor.to_medical_employee = admission.to_doctor " +
                       "join diagnosis on admission.to_diagnosis = diagnosis.diagnosis_id " +
                       "join worker on doctor.to_medical_employee = worker.worker_id " +
                       "join patient on admission.to_patient = patient.patient_id " +
                       "left join (select patient.patient_id, patient.first_name, patient.last_name, " +
                       "                  max(discharge_date) as max_discharge " +
                       "           from admission, patient " +
                       "           where admission.to_patient = patient.patient_id " +
                       "           and admission.discharge_date is not null " +
                       "           group by to_patient) as max_discharge " +
                       "on patient.patient_id = max_discharge.patient_id " +
                       "where admission_date > max_discharge " +
                       "and to_days(admission_date) - to_days(max_discharge) <= 30";
        return queryToResults(query);
    }

    // B.10
    public static String getPatientsStats() {
        String query = "select count_table.patient_id, count_table.first_name, count_table.last_name, " +
                       "       admissions, COALESCE(duration_avg, '') as duration_avg, " +
                       "       COALESCE(span_max, '') as span_max, " +
                       "       COALESCE(span_min, '') as span_min, COALESCE(span_avg, '') as span_avg " +
                       "from " +
                       "    (select patient.patient_id, patient.first_name, patient.last_name, " +
                       "            count(*) as admissions, " +
                       "            avg(to_days(discharge_date) - to_days(admission_date)) as duration_avg " +
                       "    from admission, patient " +
                       "    where admission.to_patient = patient.patient_id " +
                       "    group by to_patient) as count_table " +
                       "left join" +
                       "    (select admission1.patient_id, " +
                       "       max(to_days(admission2.admission_date) - to_days(admission1.admission_date)) as span_max, " +
                       "       min(to_days(admission2.admission_date) - to_days(admission1.admission_date)) as span_min, " +
                       "       avg(to_days(admission2.admission_date) - to_days(admission1.admission_date)) as span_avg " +
                       "     from " +
                       "         (select admission_id, patient_id, admission_date " +
                       "          from admission, patient " +
                       "          where admission.to_patient = patient.patient_id " +
                       "          order by to_patient, admission_date) as admission1 " +
                       "     left join " +
                       "         (select admission_id, patient_id, admission_date " +
                       "          from admission, patient " +
                       "          where admission.to_patient = patient.patient_id " +
                       "          order by to_patient, admission_date) as admission2 " +
                       "     on admission1.patient_id = admission2.patient_id " +
                       "     and admission1.admission_date = (select max(admission_date) " +
                       "                                      from (select admission_id, patient_id, admission_date " +
                       "                                            from admission, patient " +
                       "                                            where admission.to_patient = patient.patient_id " +
                       "                                            order by to_patient, admission_date) as admission3 " +
                       "                                      where admission3.patient_id = admission2.patient_id " +
                       "                                      and admission3.admission_date < admission2.admission_date) " +
                       "     group by patient_id) as span_table " +
                       "on count_table.patient_id = span_table.patient_id";
        return queryToResults(query);
    }


    // -=-=-=-=-=- Queries below are in support of the application and are not used by the direct project queries required

    public static String getAdmissions() {
        String query = "select admission.admission_id, date(admission.admission_date) as admission_date, patient_id, first_name, last_name " +
                "from patient, admission " +
                "where admission.to_patient = patient.patient_id " +
                "and admission.discharge_date is null " +
                "order by admission.admission_id";
        return queryToResults(query);
    }

    public static String getAdmissionsWithDiagnosis() {
        String query = "select admission.admission_id, date(admission.admission_date) as admission_date, patient_id, " +
                "first_name, last_name, diagnosis.name as current_diagnosis " +
                "from patient, admission, diagnosis " +
                "where admission.to_patient = patient.patient_id " +
                "and admission.discharge_date is null " +
                "and admission.to_diagnosis = diagnosis.diagnosis_id " +
                "order by admission.admission_id";
        return queryToResults(query);
    }

    public static String getPatientsWhoHaveBeenAdmitted() {
        String query = "select distinct patient_id, first_name, last_name " +
                "from patient, admission " +
                "where admission.to_patient = patient.patient_id " +
                "order by patient_id";
        return queryToResults(query);
    }

    public static String getAdmissionsWithDiagnosisForDoc(String doc) {
        String query = "select admission.admission_id, date(admission.admission_date) as admission_date, patient_id, " +
                "first_name, last_name, diagnosis.name as current_diagnosis " +
                "from patient, admission, diagnosis " +
                "where admission.to_patient = patient.patient_id " +
                "and admission.discharge_date is null " +
                "and admission.to_diagnosis = diagnosis.diagnosis_id " +
                "and admission.to_doctor = " + doc + " " +
                "order by admission.admission_id";
        return queryToResults(query);
    }

    public static String getPatientsNotCurrentlyAdmitted() {
        String query = "select patient_id, first_name, last_name " +
                       "from patient " +
                       "where patient.patient_id not in (" +
                       "    select patient_id " +
                       "    from patient, admission "+
                       "    where admission.to_patient = patient.patient_id " +
                       "    and admission.discharge_date is null )";
        return queryToResults(query);
    }

    public static String getPatientsNotCurrentlyAdmittedOrOutpatient() {
        String query = "select patient_id, first_name, last_name " +
                "from patient " +
                "where patient.patient_id not in (" +
                "    select patient_id " +
                "    from patient, admission "+
                "    where admission.to_patient = patient.patient_id " +
                "    and admission.discharge_date is null ) " +
                "and patient.patient_id not in (" +
                "    select to_patient " +
                "    from outpatient_ordered_treatment_group )";
        return queryToResults(query);
    }

    public static String addPatient() {
        System.out.println("\nCurrent Patients:\n" + getPatients());
        String fn = HospitalAdmin.getString("First Name: ");
        String ln = HospitalAdmin.getString("Last Name: ");
        String query = "insert into patient(first_name, last_name) values('" + fn + "','" + ln + "')";
        return executeUpdate(query);
    }

    public static String updatePatient() {
        System.out.println("\nCurrent Patients:\n" + getPatients());
        String id = HospitalAdmin.getString("patient_id of patient to edit: ");
        String fn = HospitalAdmin.getString("New First Name: ");
        String ln = HospitalAdmin.getString("New Last Name: ");
        String query = "update patient set first_name='" + fn +"', last_name='" + ln + "' where patient_id=" + id;
        return executeUpdate(query);
    }

    public static String deletePatient() {
        System.out.println("\nNote: This system will only allow you to delete entities that have not been used by other entities");
        System.out.println("\nCurrent Patients:\n" + getPatients());
        String id = HospitalAdmin.getString("patient_id of patient to delete: ");
        String query = "delete patient from patient where patient_id=" + id;
        return executeUpdate(query);
    }

    public static String admitPatient() {
        System.out.println("\nCurrent Administrators:\n" + Employee.getAdmins());
        String admin_id = HospitalAdmin.getString("your admin_id: ");
        System.out.println("\nCurrent Patients Not Admitted:\n" + getPatientsNotCurrentlyAdmitted());
        String patient_id = HospitalAdmin.getString("patient_id of patient to admit: ");
        System.out.println("\nCurrent Doctors With Admitting Privileges:\n" + Employee.getDoctorsWithAdmit());
        String doctor_id = HospitalAdmin.getString("doctor_id of primary doctor: ");
        System.out.println("\nCurrent Unoccupied Rooms:\n" + Room.getRoomsUnoccupiedWithId());
        String room_id = HospitalAdmin.getString("room_id of room to put them in: ");
        System.out.println("\nCurrent Diagnosis:\n" + DiagAndTreatInfo.getDiagnoses());
        String diagnosis_id = HospitalAdmin.getString("diagnosis_id of initial diagnosis from doctor: ");
        String insurance_info = HospitalAdmin.getString("please enter patient's insurance information: ");
        String emergency_info = HospitalAdmin.getString("please enter patient's emergency contact: ");

        String query = "insert into admission(insurance_policy, emergency_contact, to_room, to_diagnosis, " +
                       "                      to_admitting_administrator, admission_date, to_doctor, to_patient) " +
                       "       values('" + insurance_info + "', '" + emergency_info + "', " + room_id + ", " + diagnosis_id +
                       "              , " + admin_id + ", now(), " + doctor_id + ", " + patient_id + ")";
        return executeUpdate(query);
    }

    public static String dischargePatient() {
        System.out.println("\nCurrent Administrators:\n" + Employee.getAdmins());
        String admin_id = HospitalAdmin.getString("your admin_id: ");
        System.out.println("\nCurrent Patients Admitted:\n" + getPatientsCurrentlyAdmitted());
        String id = HospitalAdmin.getString("patient_id of patient to discharge: ");

        String query = "update admission set discharge_date=now(), to_discharging_administrator=" + admin_id + " where discharge_date is null and to_patient=" + id;
        return executeUpdate(query);
    }

}
