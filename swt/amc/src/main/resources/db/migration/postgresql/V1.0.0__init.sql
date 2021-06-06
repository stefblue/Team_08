CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.lecture_information (
    id bigint PRIMARY KEY NOT NULL,
    content text NOT NULL,
    ects integer NOT NULL,
    link character varying(255) UNIQUE NOT NULL,
    number character varying(255) UNIQUE NOT NULL,
    semester character varying(255) NOT NULL,
    tag character varying(255) UNIQUE NOT NULL,
    title character varying(255) UNIQUE NOT NULL
);

CREATE TABLE public.lecture_information_lecturer (
    lecture_information_id bigint NOT NULL,
    lecturer character varying(255) NOT NULL
);

CREATE TABLE public.user_information (
    id bigint PRIMARY KEY NOT NULL,
    given_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) UNIQUE NOT NULL
);


INSERT INTO user_information (id, username, password, given_name, last_name) VALUES (nextval('hibernate_sequence'), 'floebl','password', 'Florian Patrick', 'Löbl');
INSERT INTO lecture_information (id, tag, title, number, semester, ects, content, link) VALUES (nextval('hibernate_sequence'), 'ags','Agile Software Development', 'INH.04062UF', 'Summer semester 2021',5,'Successful organization of software development projects with special consideration of knowledge management aspects. The course emphasises agile methods, in particular extreme programming, and their relation to informal techniques in requirements analysis, design, implementation and validation of software.\n\nThe focus of the exercise part is placed on the software development methodology for mobile Android applications, e.g., the technical quality regarding the testability and maintainability of the software. The practical work takes place in teams of 5-8 students, whereas no devices can be made available by the university, and therefore participants need to bring their own hardware.','https://online.tugraz.at/tug_online/pl/ui/$ctx/wbLv.wbShowLVDetail?pStpSpNr=235434&pSpracheNr=2');
INSERT INTO lecture_information_lecturer VALUES((SELECT id from lecture_information where tag = 'ags'),'Slany, Wolfgang');
INSERT INTO lecture_information (id, tag, title, number, semester, ects, content, link) VALUES (nextval('hibernate_sequence'), 'st','Software Technology', 'INP.33172UF', 'Summer semester 2021',5,'This course covers modern techniques and methods in the software development process. In particular, established techniques are presented that help students in a practical way to overcome the complexity of software development. The course emphasises agile methods, in particular extreme programming, and their relation to informal techniques in requirements analysis, design, implementation, and validation of software.\n\nThe focus of the exercise part is placed on the software development methodology for mobile Android applications, e.g., the technical quality regarding the testability and maintainability of the software. The practical work takes place in teams of 5-8 students. No devices can be made available by the university, and therefore participants need to bring their own hardware.','https://online.tugraz.at/tug_online/pl/ui/$ctx/wbLv.wbShowLVDetail?pStpSpNr=236781&pSpracheNr=2');
INSERT INTO lecture_information_lecturer VALUES((SELECT id from lecture_information where tag = 'st'),'Slany, Wolfgang');
INSERT INTO lecture_information_lecturer VALUES((SELECT id from lecture_information where tag = 'st'),'Kutschera, Stefan');
INSERT INTO lecture_information (id, tag, title, number, semester, ects, content, link) VALUES (nextval('hibernate_sequence'), 'ma','Mobile Applications', 'INP.32600UF', 'Summer semester 2021',5,'The aim of the course is the study of mobile application development for Android smartphones.\n\nThe practical work takes place in teams consisting of 5-8 students. No devices can be made available by the university, and therefore participants need to bring their own hardware.\n\nThe focus of the exercise part is placed on the software development methodology for mobile applications, e.g., the technical quality regarding the testability and maintainability of the software, but also the usability of the applications.','https://online.tugraz.at/tug_online/pl/ui/$ctx/wbLv.wbShowLVDetail?pStpSpNr=238227&pSpracheNr=2');
INSERT INTO lecture_information_lecturer VALUES((SELECT id from lecture_information where tag = 'ma'),'Slany, Wolfgang');
INSERT INTO lecture_information_lecturer VALUES((SELECT id from lecture_information where tag = 'ma'),'Krnjic, Vesna');
