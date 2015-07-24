Delimiter ///
create trigger admission_constraint before insert on admission
     for each row 
     begin
     declare msg varchar(255);
     if (new.to_patient in (select distinct patient_id from patient, admission where admission.to_patient = patient.patient_id and admission.discharge_date is null))
     then
        set msg = 'Constraint admission_constraint violated: patient is already admitted';
        signal sqlstate '45000' SET message_text = msg;
     end if;
     if (new.to_room in (select room.room_id from room, admission where admission.to_room = room.room_id and admission.discharge_date is null))
     then
        set msg = 'Constraint admission_constraint violated: room is already occupied';
        signal sqlstate '45000' SET message_text = msg;
     end if;
     if (new.to_doctor in (select worker.worker_id from doctor join worker on worker.worker_id = doctor.to_medical_employee where doctor.can_admit = False))
     then
        set msg = 'Constraint admission_constraint violated: doctor does not have admitting privileges';
        signal sqlstate '45000' SET message_text = msg;
     end if;
     end
///

Delimiter ///
create trigger inpatient_ordered_treatment_constraint before insert on inpatient_ordered_treatment
     for each row 
     begin
     declare msg varchar(255);
     if (new.to_admission not in (select distinct admission_id from admission where admission.discharge_date is null))
     then
        set msg = 'inpatient_ordered_treatment_constraint violated: patient is not currently admitted';
        signal sqlstate '45000' SET message_text = msg;
     end if;
     end
///

Delimiter ///
create trigger outpatient_ordered_treatment_constraint before insert on outpatient_ordered_treatment
     for each row 
     begin
     declare msg varchar(255);
     if (new.to_outpatient_ordered_treatment_group not in (select distinct outpatient_ordered_treatment_group_id from outpatient_ordered_treatment_group where end_date is null))
     then
        set msg = 'outpatient_ordered_treatment_constraint violated: patient is not currently receiving outpatient services';
        signal sqlstate '45000' SET message_text = msg;
     end if;
     end
///
