drop table if exists medical_employee_administering_ordered_treatment;
drop table if exists outpatient_ordered_treatment;
drop table if exists inpatient_ordered_treatment;
drop table if exists ordered_treatment;
drop table if exists outpatient_ordered_treatment_group;
drop table if exists treatment;
drop table if exists doctor_assigned_admission;
drop table if exists admission;
drop table if exists diagnosis;
drop table if exists room;
drop table if exists patient;
drop table if exists staff_assignment;
drop table if exists non_medical_area;
drop table if exists volunteer_service_assignment;
drop table if exists volunteer_service;
drop table if exists doctor;
drop table if exists technician;
drop table if exists nurse;
drop table if exists medical_employee;
drop table if exists administrator;
drop table if exists staff;
drop table if exists non_medical_employee;
drop table if exists volunteer;
drop table if exists worker;

create table worker (
  worker_id bigint unsigned not null auto_increment,
  hire_date datetime not null,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  primary key (worker_id)
);

create table volunteer (
  to_worker bigint unsigned not null,
  primary key (to_worker),
  foreign key (to_worker) references worker(worker_id)
);

create table non_medical_employee (
  to_worker bigint unsigned not null,
  primary key (to_worker),
  foreign key (to_worker) references worker(worker_id)
);

create table staff (
  to_non_medical_employee bigint unsigned not null,
  primary key (to_non_medical_employee),
  foreign key (to_non_medical_employee) references non_medical_employee(to_worker)
);

create table administrator (
  to_non_medical_employee bigint unsigned not null,
  primary key (to_non_medical_employee),
  foreign key (to_non_medical_employee) references non_medical_employee(to_worker)
);

create table medical_employee (
  to_worker bigint unsigned not null,
  primary key (to_worker),
  foreign key (to_worker) references worker(worker_id)
);

create table nurse (
  to_medical_employee bigint unsigned not null,
  primary key (to_medical_employee),
  foreign key (to_medical_employee) references medical_employee(to_worker)
);

create table technician (
  to_medical_employee bigint unsigned not null,
  primary key (to_medical_employee),
  foreign key (to_medical_employee) references medical_employee(to_worker)
);

create table doctor (
  to_medical_employee bigint unsigned not null,
  can_admit boolean default false not null,
  primary key (to_medical_employee),
  foreign key (to_medical_employee) references medical_employee(to_worker)
);

create table volunteer_service (
  volunteer_service_id bigint unsigned not null auto_increment,
  description varchar(255) not null,
  primary key (volunteer_service_id)
);

create table volunteer_service_assignment (
  volunteer_service_assignment_id bigint unsigned not null auto_increment,
  to_volunteer bigint unsigned not null,
  to_volunteer_service bigint unsigned not null,
  day varchar(255) not null,
  primary key (volunteer_service_assignment_id),
  key (to_volunteer, to_volunteer_service, day),
  foreign key (to_volunteer) references volunteer(to_worker),
  foreign key (to_volunteer_service) references volunteer_service(volunteer_service_id)
);

create table non_medical_area (
  non_medical_area_id bigint unsigned not null auto_increment,
  description varchar(255) not null,
  primary key (non_medical_area_id)
);

create table staff_assignment (
  to_staff bigint unsigned not null,
  to_non_medical_area bigint unsigned not null,
  primary key (to_staff, to_non_medical_area),
  foreign key (to_staff) references staff(to_non_medical_employee),
  foreign key (to_non_medical_area) references non_medical_area(non_medical_area_id)
);

create table patient (
  patient_id bigint unsigned not null auto_increment,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  primary key (patient_id)
); 

create table room (
  room_id bigint unsigned not null auto_increment,
  number bigint unsigned unique not null,
  primary key (room_id)
);

create table diagnosis (
  diagnosis_id bigint unsigned not null auto_increment,
  name varchar(255) not null,
  primary key (diagnosis_id)
);  

create table admission (
  admission_id bigint unsigned not null auto_increment,
  insurance_policy varchar(255) not null,
  emergency_contact varchar(255) not null,
  to_room bigint unsigned not null,
  to_diagnosis bigint unsigned not null,
  to_admitting_administrator bigint unsigned not null,
  admission_date datetime not null,
  to_discharging_administrator bigint unsigned,
  discharge_date datetime,
  to_doctor bigint unsigned not null,
  to_patient bigint unsigned not null,
  primary key (admission_id),
  foreign key (to_room) references room(room_id),
  foreign key (to_diagnosis) references diagnosis(diagnosis_id),
  foreign key (to_admitting_administrator) references administrator(to_non_medical_employee),
  foreign key (to_discharging_administrator) references administrator(to_non_medical_employee),
  foreign key (to_doctor) references doctor(to_medical_employee),
  foreign key (to_patient) references patient(patient_id)
);

create table doctor_assigned_admission (
  to_admission bigint unsigned not null,
  to_doctor bigint unsigned not null,
  primary key (to_admission, to_doctor),
  foreign key (to_admission) references admission(admission_id),
  foreign key (to_doctor) references doctor(to_medical_employee)
);

create table treatment (
  treatment_id bigint unsigned not null auto_increment,
  name varchar(255) not null,
  primary key (treatment_id)
);

create table outpatient_ordered_treatment_group (
  outpatient_ordered_treatment_group_id bigint unsigned not null auto_increment,
  to_diagnosis bigint unsigned not null,
  start_date datetime not null,
  end_date datetime,
  to_patient bigint unsigned not null,
  primary key (outpatient_ordered_treatment_group_id),
  foreign key (to_diagnosis) references diagnosis(diagnosis_id),
  foreign key (to_patient) references patient(patient_id)
);   

create table ordered_treatment (
  ordered_treatment_id bigint unsigned not null auto_increment,
  to_treatment bigint unsigned not null,
  to_ordering_doctor bigint unsigned not null,
  ordered_date datetime not null, 
  primary key (ordered_treatment_id),
  foreign key (to_treatment) references treatment(treatment_id),
  foreign key (to_ordering_doctor) references doctor(to_medical_employee) 
);

create table inpatient_ordered_treatment (
  to_ordered_treatment bigint unsigned not null,
  to_admission bigint unsigned not null,
  primary key (to_ordered_treatment),
  foreign key (to_ordered_treatment) references ordered_treatment(ordered_treatment_id),
  foreign key (to_admission) references admission(admission_id)
);

create table outpatient_ordered_treatment (
  to_ordered_treatment bigint unsigned not null,
  to_outpatient_ordered_treatment_group bigint unsigned,
  primary key (to_ordered_treatment),
  foreign key (to_ordered_treatment) references ordered_treatment(ordered_treatment_id),
  foreign key (to_outpatient_ordered_treatment_group) references outpatient_ordered_treatment_group (outpatient_ordered_treatment_group_id)
);

create table medical_employee_administering_ordered_treatment (
  medical_employee_administering_ordered_treatment_id bigint unsigned not null auto_increment,
  to_medical_employee bigint unsigned not null,
  to_ordered_treatment bigint unsigned not null,
  date datetime not null,
  primary key (medical_employee_administering_ordered_treatment_id),
  foreign key (to_medical_employee) references medical_employee(to_worker),
  foreign key (to_ordered_treatment) references ordered_treatment(ordered_treatment_id)
);
