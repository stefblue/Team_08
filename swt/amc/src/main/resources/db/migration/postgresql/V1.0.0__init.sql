CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE lecture_date (
    id bigint NOT NULL,
    duration bigint,
    "time" timestamp without time zone
);

CREATE TABLE lecture_information (
    id bigint NOT NULL,
    content text NOT NULL,
    ects integer NOT NULL,
    link character varying(255) NOT NULL,
    number character varying(255) NOT NULL,
    semester character varying(255) NOT NULL,
    tag character varying(255) NOT NULL,
    title character varying(255) NOT NULL
);

CREATE TABLE lecture_information_dates (
    lecture_information_id bigint NOT NULL,
    dates_id bigint NOT NULL
);

CREATE TABLE lecture_information_lecturer (
    lecture_information_id bigint NOT NULL,
    lecturer character varying(255) NOT NULL
);

CREATE TABLE user_information (
    id bigint NOT NULL,
    password character varying(255),
    username character varying(255),
    given_name character varying(255),
    last_name character varying(255)
);

ALTER TABLE ONLY lecture_date
    ADD CONSTRAINT lecture_date_pkey PRIMARY KEY (id);

ALTER TABLE ONLY lecture_information_lecturer
    ADD CONSTRAINT lecture_information_lecturer_pkey PRIMARY KEY (lecture_information_id, lecturer);

ALTER TABLE ONLY lecture_information
    ADD CONSTRAINT lecture_information_pkey PRIMARY KEY (id);

ALTER TABLE ONLY lecture_information
    ADD CONSTRAINT uk_7hoajvbiuwja2i4icmq3il5mw UNIQUE (number);

ALTER TABLE ONLY lecture_information
    ADD CONSTRAINT uk_a3h6qt3lt9i99hc4s642x69vd UNIQUE (tag);

ALTER TABLE ONLY lecture_information
    ADD CONSTRAINT uk_eccb5w6et33ivm75tea6n2yac UNIQUE (title);

ALTER TABLE ONLY lecture_information
    ADD CONSTRAINT uk_ih5aq7wag1skk4tpyv5i3n0vm UNIQUE (link);

ALTER TABLE ONLY lecture_information_dates
    ADD CONSTRAINT uk_ls36tews23p23telitrugk8k0 UNIQUE (dates_id);

ALTER TABLE ONLY user_information
    ADD CONSTRAINT user_information_pkey PRIMARY KEY (id);

ALTER TABLE ONLY lecture_information_lecturer
    ADD CONSTRAINT fk7xs9k4qte4ae7hkioxsvnphkp FOREIGN KEY (lecture_information_id) REFERENCES lecture_information(id);

ALTER TABLE ONLY lecture_information_dates
    ADD CONSTRAINT fka716dnqhu3kf7fp3tlaknhvnh FOREIGN KEY (dates_id) REFERENCES lecture_date(id);

ALTER TABLE ONLY lecture_information_dates
    ADD CONSTRAINT fkeow08lj5tesu2u3i3gfpgw54s FOREIGN KEY (lecture_information_id) REFERENCES lecture_information(id);
    
INSERT INTO user_information (id, username, password, given_name, last_name) VALUES (1, 'floebl','password', 'Florian Patrick', 'Löbl');
INSERT INTO lecture_information (id, tag, title, number, semester, ects, content, link) VALUES (2, 'ags','Agile Software Development', 'INH.04062UF', 'Summer semester 2021',5,'Successful organization of software development projects with special consideration of knowledge management aspects. The course emphasises agile methods, in particular extreme programming, and their relation to informal techniques in requirements analysis, design, implementation and validation of software.\n\nThe focus of the exercise part is placed on the software development methodology for mobile Android applications, e.g., the technical quality regarding the testability and maintainability of the software. The practical work takes place in teams of 5-8 students, whereas no devices can be made available by the university, and therefore participants need to bring their own hardware.','https://online.tugraz.at/tug_online/pl/ui/$ctx/wbLv.wbShowLVDetail?pStpSpNr=235434&pSpracheNr=2');
INSERT INTO lecture_information_lecturer VALUES(2,'Slany, Wolfgang');
INSERT INTO lecture_date (id, duration, "time") VALUES (3, 7200000000000, '2021-06-07 14:24:53.993996');
INSERT INTO lecture_date (id, duration, "time") VALUES (4, 7200000000000, '2021-06-08 11:24:53.993996');
INSERT INTO lecture_date (id, duration, "time") VALUES (5, 7200000000000, '2021-06-08 19:24:53.993996');
INSERT INTO lecture_date (id, duration, "time") VALUES (6, 7200000000000, '2021-06-08 21:24:53.993996');
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (2, 3);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (2, 4);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (2, 5);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (2, 6);
INSERT INTO lecture_information (id, tag, title, number, semester, ects, content, link) VALUES (7, 'st','Software Technology', 'INP.33172UF', 'Summer semester 2021',5,'This course covers modern techniques and methods in the software development process. In particular, established techniques are presented that help students in a practical way to overcome the complexity of software development. The course emphasises agile methods, in particular extreme programming, and their relation to informal techniques in requirements analysis, design, implementation, and validation of software.\n\nThe focus of the exercise part is placed on the software development methodology for mobile Android applications, e.g., the technical quality regarding the testability and maintainability of the software. The practical work takes place in teams of 5-8 students. No devices can be made available by the university, and therefore participants need to bring their own hardware.','https://online.tugraz.at/tug_online/pl/ui/$ctx/wbLv.wbShowLVDetail?pStpSpNr=236781&pSpracheNr=2');
INSERT INTO lecture_information_lecturer VALUES(7,'Slany, Wolfgang');
INSERT INTO lecture_information_lecturer VALUES(7,'Kutschera, Stefan');
INSERT INTO lecture_date (id, duration, "time") VALUES (8, 7200000000000, '2021-06-07 14:24:53.993996');
INSERT INTO lecture_date (id, duration, "time") VALUES (9, 7200000000000, '2021-06-08 11:24:53.993996');
INSERT INTO lecture_date (id, duration, "time") VALUES (10, 7200000000000, '2021-06-08 19:24:53.993996');
INSERT INTO lecture_date (id, duration, "time") VALUES (11, 7200000000000, '2021-06-08 21:24:53.993996');
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (7, 8);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (7, 9);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (7, 10);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (7, 11);
INSERT INTO lecture_information (id, tag, title, number, semester, ects, content, link) VALUES (12, 'ma','Mobile Applications', 'INP.32600UF', 'Summer semester 2021',5,'The aim of the course is the study of mobile application development for Android smartphones.\n\nThe practical work takes place in teams consisting of 5-8 students. No devices can be made available by the university, and therefore participants need to bring their own hardware.\n\nThe focus of the exercise part is placed on the software development methodology for mobile applications, e.g., the technical quality regarding the testability and maintainability of the software, but also the usability of the applications.','https://online.tugraz.at/tug_online/pl/ui/$ctx/wbLv.wbShowLVDetail?pStpSpNr=238227&pSpracheNr=2');
INSERT INTO lecture_information_lecturer VALUES(12,'Slany, Wolfgang');
INSERT INTO lecture_information_lecturer VALUES(12,'Krnjic, Vesna');
INSERT INTO lecture_date (id, duration, "time") VALUES (13, 7200000000000, '2021-06-07 14:24:53.993996');
INSERT INTO lecture_date (id, duration, "time") VALUES (14, 7200000000000, '2021-06-08 11:24:53.993996');
INSERT INTO lecture_date (id, duration, "time") VALUES (15, 7200000000000, '2021-06-08 19:24:53.993996');
INSERT INTO lecture_date (id, duration, "time") VALUES (16, 7200000000000, '2021-06-08 21:24:53.993996');
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (12, 13);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (12, 14);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (12, 15);
INSERT INTO lecture_information_dates (lecture_information_id, dates_id) VALUES (12,16);
SELECT pg_catalog.setval('hibernate_sequence', 17, true);