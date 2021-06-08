CREATE SEQUENCE "HIBERNATE_SEQUENCE" START WITH 17;  
CREATE CACHED TABLE "LECTURE_INFORMATION"(
    "ID" BIGINT NOT NULL,
    "CONTENT" VARCHAR(2147483647) NOT NULL,
    "ECTS" INTEGER NOT NULL,
    "LINK" VARCHAR(255) NOT NULL,
    "NUMBER" VARCHAR(255) NOT NULL,
    "SEMESTER" VARCHAR(255) NOT NULL,
    "TAG" VARCHAR(255) NOT NULL,
    "TITLE" VARCHAR(255) NOT NULL
);             
ALTER TABLE "LECTURE_INFORMATION" ADD CONSTRAINT "CONSTRAINT_7" PRIMARY KEY("ID");          
CREATE CACHED TABLE "LECTURE_INFORMATION_DATES"(
    "LECTURE_INFORMATION_ID" BIGINT NOT NULL,
    "DATES_ID" BIGINT NOT NULL
);     
CREATE CACHED TABLE "LECTURE_INFORMATION_LECTURER"(
    "LECTURE_INFORMATION_ID" BIGINT NOT NULL,
    "LECTURER" VARCHAR(255) NOT NULL
);         
ALTER TABLE "LECTURE_INFORMATION_LECTURER" ADD CONSTRAINT "CONSTRAINT_9" PRIMARY KEY("LECTURE_INFORMATION_ID", "LECTURER");      
CREATE CACHED TABLE "LECTURE_DATE"(
    "ID" BIGINT NOT NULL,
    "DURATION" BIGINT,
    "TIME" TIMESTAMP
);     
ALTER TABLE "LECTURE_DATE" ADD CONSTRAINT "CONSTRAINT_E" PRIMARY KEY("ID"); 
CREATE CACHED TABLE "USER_INFORMATION"(
    "ID" BIGINT NOT NULL,
    "PASSWORD" VARCHAR(255),
    "USERNAME" VARCHAR(255),
    "GIVEN_NAME" VARCHAR(255),
    "LAST_NAME" VARCHAR(255)
);     
ALTER TABLE "USER_INFORMATION" ADD CONSTRAINT "CONSTRAINT_1" PRIMARY KEY("ID");             
ALTER TABLE "LECTURE_INFORMATION" ADD CONSTRAINT "UK_IH5AQ7WAG1SKK4TPYV5I3N0VM" UNIQUE("LINK");             
ALTER TABLE "LECTURE_INFORMATION" ADD CONSTRAINT "UK_7HOAJVBIUWJA2I4ICMQ3IL5MW" UNIQUE("NUMBER");           
ALTER TABLE "LECTURE_INFORMATION_DATES" ADD CONSTRAINT "UK_LS36TEWS23P23TELITRUGK8K0" UNIQUE("DATES_ID");   
ALTER TABLE "LECTURE_INFORMATION" ADD CONSTRAINT "UK_ECCB5W6ET33IVM75TEA6N2YAC" UNIQUE("TITLE");            
ALTER TABLE "LECTURE_INFORMATION" ADD CONSTRAINT "UK_A3H6QT3LT9I99HC4S642X69VD" UNIQUE("TAG");              
ALTER TABLE "LECTURE_INFORMATION_LECTURER" ADD CONSTRAINT "FK7XS9K4QTE4AE7HKIOXSVNPHKP" FOREIGN KEY("LECTURE_INFORMATION_ID") REFERENCES "LECTURE_INFORMATION"("ID") NOCHECK;      
ALTER TABLE "LECTURE_INFORMATION_DATES" ADD CONSTRAINT "FKA716DNQHU3KF7FP3TLAKNHVNH" FOREIGN KEY("DATES_ID") REFERENCES "LECTURE_DATE"("ID") NOCHECK;              
ALTER TABLE "LECTURE_INFORMATION_DATES" ADD CONSTRAINT "FKEOW08LJ5TESU2U3I3GFPGW54S" FOREIGN KEY("LECTURE_INFORMATION_ID") REFERENCES "LECTURE_INFORMATION"("ID") NOCHECK;         

INSERT INTO user_information (id, username, password, given_name, last_name) VALUES (1, 'floebl','password', 'Florian Patrick', 'Löbl');
INSERT INTO lecture_information (id, tag, title, number, semester, ects, content, link) VALUES (2, 'ags','Agile Software Development', 'INH.04062UF', 'Summer semester 2021',5,'Successful organization of software development projects with special consideration of knowledge management aspects. The course emphasises agile methods, in particular extreme programming, and their relation to informal techniques in requirements analysis, design, implementation and validation of software.\n\nThe focus of the exercise part is placed on the software development methodology for mobile Android applications, e.g., the technical quality regarding the testability and maintainability of the software. The practical work takes place in teams of 5-8 students, whereas no devices can be made available by the university, and therefore participants need to bring their own hardware.','https://online.tugraz.at/tug_online/pl/ui/$ctx/wbLv.wbShowLVDetail?pStpSpNr=235434&pSpracheNr=2');
INSERT INTO lecture_information_lecturer VALUES(2,'Slany, Wolfgang');
INSERT INTO lecture_date (id, duration, "TIME") VALUES (3, 7200000000000, '2021-06-07 14:24:53.993996');
INSERT INTO lecture_date (id, duration, "TIME") VALUES (4, 7200000000000, '2021-06-08 11:24:53.993996');
INSERT INTO lecture_date (id, duration, "TIME") VALUES (5, 7200000000000, '2021-06-08 19:24:53.993996');
INSERT INTO lecture_date (id, duration, "TIME") VALUES (6, 7200000000000, '2021-06-08 21:24:53.993996');
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (2, 3);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (2, 4);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (2, 5);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (2, 6);
INSERT INTO lecture_information (id, tag, title, number, semester, ects, content, link) VALUES (7, 'st','Software Technology', 'INP.33172UF', 'Summer semester 2021',5,'This course covers modern techniques and methods in the software development process. In particular, established techniques are presented that help students in a practical way to overcome the complexity of software development. The course emphasises agile methods, in particular extreme programming, and their relation to informal techniques in requirements analysis, design, implementation, and validation of software.\n\nThe focus of the exercise part is placed on the software development methodology for mobile Android applications, e.g., the technical quality regarding the testability and maintainability of the software. The practical work takes place in teams of 5-8 students. No devices can be made available by the university, and therefore participants need to bring their own hardware.','https://online.tugraz.at/tug_online/pl/ui/$ctx/wbLv.wbShowLVDetail?pStpSpNr=236781&pSpracheNr=2');
INSERT INTO lecture_information_lecturer VALUES(7,'Slany, Wolfgang');
INSERT INTO lecture_information_lecturer VALUES(7,'Kutschera, Stefan');
INSERT INTO lecture_date (id, duration, "TIME") VALUES (8, 7200000000000, '2021-06-07 14:24:53.993996');
INSERT INTO lecture_date (id, duration, "TIME") VALUES (9, 7200000000000, '2021-06-08 11:24:53.993996');
INSERT INTO lecture_date (id, duration, "TIME") VALUES (10, 7200000000000, '2021-06-08 19:24:53.993996');
INSERT INTO lecture_date (id, duration, "TIME") VALUES (11, 7200000000000, '2021-06-08 21:24:53.993996');
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (7, 8);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (7, 9);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (7, 10);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (7, 11);
INSERT INTO lecture_information (id, tag, title, number, semester, ects, content, link) VALUES (12, 'ma','Mobile Applications', 'INP.32600UF', 'Summer semester 2021',5,'The aim of the course is the study of mobile application development for Android smartphones.\n\nThe practical work takes place in teams consisting of 5-8 students. No devices can be made available by the university, and therefore participants need to bring their own hardware.\n\nThe focus of the exercise part is placed on the software development methodology for mobile applications, e.g., the technical quality regarding the testability and maintainability of the software, but also the usability of the applications.','https://online.tugraz.at/tug_online/pl/ui/$ctx/wbLv.wbShowLVDetail?pStpSpNr=238227&pSpracheNr=2');
INSERT INTO lecture_information_lecturer VALUES(12,'Slany, Wolfgang');
INSERT INTO lecture_information_lecturer VALUES(12,'Krnjic, Vesna');
INSERT INTO lecture_date (id, duration, "TIME") VALUES (13, 7200000000000, '2021-06-07 14:24:53.993996');
INSERT INTO lecture_date (id, duration, "TIME") VALUES (14, 7200000000000, '2021-06-08 11:24:53.993996');
INSERT INTO lecture_date (id, duration, "TIME") VALUES (15, 7200000000000, '2021-06-08 19:24:53.993996');
INSERT INTO lecture_date (id, duration, "TIME") VALUES (16, 7200000000000, '2021-06-08 21:24:53.993996');
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (12, 13);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (12, 14);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (12, 15);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (12,16);
