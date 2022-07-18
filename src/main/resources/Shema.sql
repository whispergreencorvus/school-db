DROP SCHEMA IF EXISTS school CASCADE ;
DROP ROLE IF EXISTS userDB;
CREATE ROLE userDB WITH LOGIN ENCRYPTED PASSWORD '123';
CREATE SCHEMA school;

CREATE TABLE school.students(
    STUDENT_ID smallint GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    GROUP_ID smallint,
    FIRST_NAME char(11),
    LAST_NAME char(11)
    );

CREATE TABLE school.courses(
    COURSE_ID smallint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    COURSE_NAME char(20),
    COURSE_DESCRIPTION TEXT
    );
    
CREATE TABLE school.groups(
    GROUP_ID smallint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    GROUP_NAME char(5)
    );
  
CREATE TABLE school.students_courses (  
    ID smallint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,    
    STUDENT_ID smallint references school.students(STUDENT_ID) ON DELETE CASCADE,
    COURSE_ID smallint references school.courses(COURSE_ID) ON DELETE CASCADE
    );  
    
