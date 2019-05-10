CREATE SCHEMA IF NOT EXISTS scantron;

CREATE TABLE IF NOT EXISTS scantron.instructor (
    instructor_id VARCHAR(200) NOT NULL,
    inst_first_name VARCHAR(200),
    inst_last_name VARCHAR(200),
    inst_email VARCHAR(500) NOT NULL,
    PRIMARY KEY (instructor_id)
);

CREATE TABLE IF NOT EXISTS scantron.answerkey (
    exam_id VARCHAR(8) NOT NULL,
    instructor_id VARCHAR(200) NOT NULL,
    answers VARCHAR(5000),
    updated_answewrs VARCHAR(5000),
    grade_scale VARCHAR(45),
    exam_name VARCHAR(200),
    answer_key_length VARCHAR(50) NOT NULL,
    FOREIGN KEY (instructor_id)
        REFERENCES instructor (instructor_id),
    PRIMARY KEY (exam_id)
);

CREATE TABLE IF NOT EXISTS scantron.course (
    course_crn VARCHAR(5) NOT NULL,
    course_name VARCHAR(250),
    section_num VARCHAR(10),
    semester VARCHAR(12) NOT NULL,
    instructor_id VARCHAR(200) NOT NULL,
    FOREIGN KEY (instructor_id)
        REFERENCES instructor (instructor_id),
    PRIMARY KEY (course_crn)
);

CREATE TABLE IF NOT EXISTS scantron.exam (
    first_name VARCHAR(200) NOT NULL,
    last_name VARCHAR(200) NOT NULL,
    student_id VARCHAR(10) NOT NULL,
    semester VARCHAR(12) NOT NULL,
    gender VARCHAR(1),
    birth_date VARCHAR(15),
    course_crn VARCHAR(5) NOT NULL,
    instructor_id VARCHAR(200) NOT NULL,
    exam_id VARCHAR(5) NOT NULL,
    answers VARCHAR(5000) NOT NULL,
    FOREIGN KEY (instructor_id)
        REFERENCES instructor (instructor_id),
    FOREIGN KEY (exam_id)
        REFERENCES answerkey (exam_id),
    FOREIGN KEY (course_crn)
        REFERENCES course (course_crn),
    PRIMARY KEY (student_id)
);
