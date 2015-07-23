
public class DiagAndTreatInfo extends HospitalSQLBase {

    // C.1
    public static String getDiagnosesForAdmittedPatients() {
        String query = "select diagnosis.diagnosis_id, diagnosis.name, count(*) as total_occurrences " +
                       "from diagnosis, admission " +
                       "where admission.to_diagnosis = diagnosis.diagnosis_id " +
                       "group by diagnosis_id " +
                       "order by count(*) desc";
        return queryToResults(query);
    }

    // C.2
    public static String getDiagnosesForOutpatients() {
        String query = "select diagnosis.diagnosis_id, diagnosis.name, count(*) as total_occurrences " +
                       "from diagnosis, outpatient_ordered_treatment_group " +
                       "where outpatient_ordered_treatment_group.to_diagnosis = diagnosis.diagnosis_id " +
                       "group by diagnosis_id " +
                       "order by count(*) desc";
        return queryToResults(query);
    }

    // C.3
    public static String getDiagnosesForPatients() {
        String query = "select diagnosis_id, name, count(*) as total_occurrences from ( " +
                       "    select diagnosis.diagnosis_id, diagnosis.name " +
                       "    from diagnosis, admission " +
                       "    where admission.to_diagnosis = diagnosis.diagnosis_id " +
                       "  union all " +
                       "    select diagnosis.diagnosis_id, diagnosis.name " +
                       "    from diagnosis, outpatient_ordered_treatment_group " +
                       "    where outpatient_ordered_treatment_group.to_diagnosis = diagnosis.diagnosis_id) as t1 " +
                       "group by diagnosis_id " +
                       "order by count(*) desc";
        return queryToResults(query);
    }

    // C.4
    public static String getTreatments() {
        String query = "select treatment_id, treatment.name, count(*) as total_occurrences " +
                       "from treatment, ordered_treatment, medical_employee_administering_ordered_treatment " +
                       "where medical_employee_administering_ordered_treatment.to_ordered_treatment = ordered_treatment.ordered_treatment_id " +
                       "and ordered_treatment.to_treatment = treatment.treatment_id " +
                       "group by treatment_id " +
                       "order by count(*) desc";
        return queryToResults(query);
    }

    // C.5
    public static String getTreatmentsForAdmittedPatients() {
        String query = "select treatment_id, treatment.name, count(*) as total_occurrences " +
                       "from treatment, ordered_treatment, inpatient_ordered_treatment, medical_employee_administering_ordered_treatment " +
                       "where medical_employee_administering_ordered_treatment.to_ordered_treatment = ordered_treatment.ordered_treatment_id " +
                       "and inpatient_ordered_treatment.to_ordered_treatment = ordered_treatment.ordered_treatment_id " +
                       "and ordered_treatment.to_treatment = treatment.treatment_id " +
                       "group by treatment_id " +
                       "order by count(*) desc";
        return queryToResults(query);
    }

    // C.6
    public static String getTreatmentsForOutpatients() {
        String query = "select treatment_id, treatment.name, count(*) as total_occurrences " +
                       "from treatment, ordered_treatment, outpatient_ordered_treatment, medical_employee_administering_ordered_treatment " +
                       "where medical_employee_administering_ordered_treatment.to_ordered_treatment = ordered_treatment.ordered_treatment_id " +
                       "and outpatient_ordered_treatment.to_ordered_treatment = ordered_treatment.ordered_treatment_id " +
                       "and ordered_treatment.to_treatment = treatment.treatment_id " +
                       "group by treatment_id " +
                       "order by count(*) desc";
        return queryToResults(query);
    }

    // C.7
    public static String getDiagnosisHighAdmissions() {
        String query = "select patient.patient_id, patient.first_name, patient.last_name, \n" +
                       "       ta.total_admissions, diagnosis.name as diagnosis, count(*) as diagnosis_admissions\n" +
                       "from admission join patient on admission.to_patient = patient.patient_id\n" +
                       "join diagnosis on diagnosis.diagnosis_id = admission.to_diagnosis\n" +
                       "join (select patient.patient_id, count(*) as total_admissions\n" +
                       "      from admission, patient \n" +
                       "      where admission.to_patient = patient.patient_id \n" +
                       "      group by to_patient) ta on ta.patient_id = patient.patient_id\n" +
                       "group by to_patient, diagnosis.diagnosis_id\n" +
                       "order by ta.total_admissions desc, diagnosis_admissions desc";
        return queryToResults(query);
    }

    // C.8
    public static String getAllEmployeesForTreatment() {
        System.out.println("\nOrdered Treatments for Admitted Patients:\n" + DiagAndTreatInfo.getOrderedTreatmentsForInpatient());
        System.out.println("\nOrdered Treatments for Outpatient Patients:\n" + DiagAndTreatInfo.getOrderedTreatmentsForOutpatient());

        String id = HospitalAdmin.getString("Enter Ordered Treatment ID: ");
        String query = "select distinct w1.first_name as employee_first_name, w1.last_name as employee_last_name, " +
                       "                patient.first_name as patient_first_name, patient.last_name as patient_last_name, " +
                       "                w2.first_name as ord_doc_first_name, w2.last_name as ord_doc_last_name " +
                       "from ordered_treatment " +
                       "join (select ordered_treatment.ordered_treatment_id, admission.to_patient " +
                       "      from ordered_treatment, inpatient_ordered_treatment, admission " +
                       "      where inpatient_ordered_treatment.to_ordered_treatment = ordered_treatment.ordered_treatment_id " +
                       "      and inpatient_ordered_treatment.to_admission = admission.admission_id " +
                       "      union " +
                       "      select ordered_treatment.ordered_treatment_id, outpatient_ordered_treatment_group.to_patient " +
                       "      from ordered_treatment, outpatient_ordered_treatment, outpatient_ordered_treatment_group " +
                       "      where outpatient_ordered_treatment.to_ordered_treatment = ordered_treatment.ordered_treatment_id " +
                       "      and outpatient_ordered_treatment.to_outpatient_ordered_treatment_group = outpatient_ordered_treatment_group.outpatient_ordered_treatment_group_id) o_to_p " +
                       "  on o_to_p.ordered_treatment_id = ordered_treatment.ordered_treatment_id " +
                       "join patient " +
                       "  on o_to_p.to_patient = patient.patient_id " +
                       "join medical_employee_administering_ordered_treatment as meaot " +
                       "  on meaot.to_ordered_treatment = ordered_treatment.ordered_treatment_id " +
                       "join worker w1 " +
                       "  on meaot.to_medical_employee = w1.worker_id " +
                       "join worker w2 " +
                       "  on ordered_treatment.to_ordering_doctor = w2.worker_id " +
                       "where ordered_treatment.ordered_treatment_id = " + id;
        return queryToResults(query);
    }

    // -=-=-=-=-=- Queries below are in support of the application and are not used by the direct project queries required

    public static String getDiagnoses() {
        String query = "select * from diagnosis";
        return queryToResults(query);
    }

    public static String getTreatmentsPlain() {
        String query = "select * from treatment";
        return queryToResults(query);
    }

    public static String getOutpatientTreatmentGroups() {
        String query = "select outpatient_ordered_treatment_group.outpatient_ordered_treatment_group_id as outpatient_group_id, " +
                       "       patient.first_name, patient.last_name, date(outpatient_ordered_treatment_group.start_date) as start_date " +
                       "from outpatient_ordered_treatment_group, patient " +
                       "where outpatient_ordered_treatment_group.to_patient = patient.patient_id " +
                       "and outpatient_ordered_treatment_group.end_date is null";
        return queryToResults(query);
    }

    public static String getOutpatientTreatmentGroupsWithDiagnosis() {
        String query = "select outpatient_ordered_treatment_group.outpatient_ordered_treatment_group_id as outpatient_group_id, " +
                "       patient.first_name, patient.last_name, date(outpatient_ordered_treatment_group.start_date) as start_date, " +
                "       diagnosis.name as current_diagnosis " +
                "from outpatient_ordered_treatment_group, patient, diagnosis " +
                "where outpatient_ordered_treatment_group.to_patient = patient.patient_id " +
                "and outpatient_ordered_treatment_group.end_date is null " +
                "and outpatient_ordered_treatment_group.to_diagnosis = diagnosis.diagnosis_id " +
                "order by outpatient_group_id";
        return queryToResults(query);
    }

    public static String getCurrentOrderedTreatmentsForInpatient() {
        String query = "select ot.ordered_treatment_id, t.name, " +
                "       date(ot.ordered_date) as ordered_date, " +
                "       patient.first_name, patient.last_name " +
                "from ordered_treatment as ot " +
                "join treatment as t " +
                "  on ot.to_treatment = t.treatment_id " +
                "join inpatient_ordered_treatment as iot " +
                "  on iot.to_ordered_treatment = ot.ordered_treatment_id " +
                "join admission " +
                "  on admission.admission_id = iot.to_admission " +
                "join patient " +
                "  on admission.to_patient = patient.patient_id " +
                "where admission.discharge_date is null " +
                "order by ordered_treatment_id";
        return queryToResults(query);
    }

    public static String getOrderedTreatmentsForInpatient() {
        String query = "select ot.ordered_treatment_id, t.name, " +
                "       date(ot.ordered_date) as ordered_date, " +
                "       patient.first_name, patient.last_name " +
                "from ordered_treatment as ot " +
                "join treatment as t " +
                "  on ot.to_treatment = t.treatment_id " +
                "join inpatient_ordered_treatment as iot " +
                "  on iot.to_ordered_treatment = ot.ordered_treatment_id " +
                "join admission " +
                "  on admission.admission_id = iot.to_admission " +
                "join patient " +
                "  on admission.to_patient = patient.patient_id " +
                "order by ordered_treatment_id";
        return queryToResults(query);
    }

    public static String getCurrentOrderedTreatmentsForOutpatient() {
        String query = "select ot.ordered_treatment_id, t.name, " +
                "       date(ot.ordered_date) as ordered_date, " +
                "       patient.first_name, patient.last_name " +
                "from ordered_treatment as ot " +
                "join treatment as t " +
                "  on ot.to_treatment = t.treatment_id " +
                "join outpatient_ordered_treatment as oot " +
                "  on oot.to_ordered_treatment = ot.ordered_treatment_id " +
                "join outpatient_ordered_treatment_group as ootg " +
                "  on ootg.outpatient_ordered_treatment_group_id = oot.to_outpatient_ordered_treatment_group " +
                "join patient " +
                "  on ootg.to_patient = patient.patient_id " +
                "where ootg.end_date is null " +
                "order by ordered_treatment_id";
        return queryToResults(query);
    }

    public static String getOrderedTreatmentsForOutpatient() {
        String query = "select ot.ordered_treatment_id, t.name, " +
                "       date(ot.ordered_date) as ordered_date, " +
                "       patient.first_name, patient.last_name " +
                "from ordered_treatment as ot " +
                "join treatment as t " +
                "  on ot.to_treatment = t.treatment_id " +
                "join outpatient_ordered_treatment as oot " +
                "  on oot.to_ordered_treatment = ot.ordered_treatment_id " +
                "join outpatient_ordered_treatment_group as ootg " +
                "  on ootg.outpatient_ordered_treatment_group_id = oot.to_outpatient_ordered_treatment_group " +
                "join patient " +
                "  on ootg.to_patient = patient.patient_id " +
                "order by ordered_treatment_id";
        return queryToResults(query);
    }
}
