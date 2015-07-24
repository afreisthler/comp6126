insert into worker values
  (1, "2012-01-01", "John", "DocMan"),
  (2, "2014-02-11", "Steve", "DocMan"),
  (3, "2014-03-20", "Bob", "DocMan"),
  (4, "2015-06-10", "Karen", "TechMan"),
  (5, "2013-02-19", "Bret", "TechMan"),
  (6, "2012-10-15", "Mark", "NurseMan"),
  (7, "2011-12-02", "Andy", "NurseMan"),
  (8, "2013-11-07", "Joe", "StaffMan"),
  (9, "2015-03-25", "Paul", "StaffMan"),
  (10, "2012-05-11", "Tim", "AdminMan"),
  (11, "2013-02-04", "Ben", "AdminMan"),
  (12, "2012-04-08", "James", "AdminMan"),
  (13, "2011-11-19", "Alex", "VolunteerMan"),
  (14, "2009-09-29", "Jessie", "VolunteerMan"),
  (15, "2010-06-01", "Ian", "VolunteerMan")
;

insert into medical_employee values
  (1), (2), (3), (4), (5), (6), (7)
;

insert into nurse values
  (6), (7)
;

insert into technician values
  (4), (5)
;

insert into doctor values
  (1, True), (2, False), (3, True)
;

insert into non_medical_employee values
  (8), (9), (10), (11), (12)
;

insert into staff values
  (8), (9)
;

insert into administrator values
  (10), (11), (12)
;

insert into volunteer values
  (13), (14), (15)
;

insert into volunteer_service values
  (1, "information desk"), (2, "gift shop"), (3, "snack cart"), (4, "reading cart")
;

insert into volunteer_service_assignment values
  (1, 13, 1, "tuesday"), (2, 14, 2, "friday"), (3, 15, 3, "monday"), (4, 15, 1, "tuesday")
;

insert into patient values 
  (1, "John", "PatientMan"),
  (2, "Jim", "PatientMan"),
  (3, "Ham", "PatientMan"),
  (4, "Jack", "PatientMan"),
  (5, "Steve", "PatientMan"),
  (6, "Bob", "PatientMan"),
  (7, "Kevin", "PatientMan"),
  (8, "Kit", "PatientMan")
;

insert into room values
  (1, 201), (2, 202), (3, 203), (4, 301), (5, 302), (6, 303)
;

insert into diagnosis values
  (1, "hypertension"), (2, "chest pain"), (3, "lower back pain")
;

insert into admission values
  (1, "uhc 1234", "Karen PatientWife", 3, 2, 10, "2014-01-01", 10, "2014-01-03", 1, 4),
  (2, "blue cross policy 1234", "Jane PatientWife", 1, 1, 10, "2014-03-11", 10, "2014-03-12", 1, 2),
  (3, "uhc 1234", "Karen PatientWife", 3, 2, 10, "2015-01-11", 11, "2015-01-13", 1, 4),
  (4, "uhc 1234", "Karen PatientWife", 3, 2, 10, "2015-02-01", 10, "2015-02-03", 1, 4),
  (5, "uhc 1234", "Karen PatientWife", 3, 2, 10, "2015-01-01", 10, "2015-01-03", 1, 4),
  (6, "uhc 1234", "Karen PatientWife", 3, 1, 10, "2015-02-20", 10, "2015-02-20", 1, 4),
  (7, "uhc 1234", "Karen PatientWife", 3, 1, 10, "2015-02-22", null, null, 1, 4),
  (8, "blue cross policy 1234", "Jane PatientWife", 1, 1, 10, "2015-03-11", null, null, 1, 2),
  (9, "blue cross policy 5678", "Kim PatientWife", 2, 3, 11, "2015-07-01", null, null, 1, 3)
;

insert into treatment values
  (1, "drug 1"), (2, "drug 2"), (3, "procedure 1"), (4, "brain surgery")
;

insert into outpatient_ordered_treatment_group values
  (1, 3, "2015-05-01", "2015-05-10", 8),
  (2, 3, "2015-05-20", null, 8)
;

insert into ordered_treatment values
  (1, 1, 2, "2015-05-01"),
  (2, 3, 1, "2015-04-01"),
  (3, 1, 2, "2015-05-20"),
  (4, 4, 2, "2015-01-12"),
  (5, 2, 1, "2015-04-01"),
  (6, 2, 1, "2015-07-02")
;

insert into outpatient_ordered_treatment values
  (1, 1),
  (3, 2)
;

insert into inpatient_ordered_treatment values
  (2, 7),
  (4, 3),
  (5, 8),
  (6, 9)
;

insert into medical_employee_administering_ordered_treatment values
  (1, 1, 1, "2015-05-01"),
  (2, 2, 1, "2015-05-02"),
  (3, 2, 2, "2015-04-02"),
  (4, 2, 2, "2015-04-03"),
  (5, 7, 2, "2015-04-02"),
  (6, 7, 2, "2015-04-03"),
  (7, 1, 4, "2015-01-13"),
  (8, 1, 4, "2015-01-12"),
  (9, 7, 5, "2015-07-03"),
  (10, 7, 6, "2015-07-03")
;


