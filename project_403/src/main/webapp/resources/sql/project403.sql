use wjdwoals222;

CREATE TABLE Member (
    mem_id VARCHAR(100) UNIQUE KEY NOT NULL,
    mem_pw VARCHAR(15),
    mem_nickName VARCHAR(10),
    mem_point INT DEFAULT 0,
    mem_exp INT DEFAULT 0,
    mem_admin TINYINT(1) DEFAULT 0,
    mem_email TEXT,
    mem_confirmed TINYINT(1) DEFAULT 0,
    mem_serial INT PRIMARY KEY AUTO_INCREMENT,
    mem_profile_name VARCHAR(100),
    mem_date DATE,
    mem_alarm TEXT NULL
);
ALTER TABLE Member AUTO_INCREMENT = 10000;

CREATE TABLE Question (
    question_num INT AUTO_INCREMENT PRIMARY KEY,
    question_content TEXT,
    question_ans TEXT,
    question_img_name VARCHAR(60),
    question_level INT,
    question_count INT,
    sub_code_sum VARCHAR(20),
    mem_serial INT,
    question_serial VARCHAR(20) UNIQUE,
    question_id VARCHAR(25),
    question_visible TINYINT(1) DEFAULT 1,
    FOREIGN KEY (mem_serial) REFERENCES Member(mem_serial)
);


CREATE TABLE QnA (
    comment_num INT AUTO_INCREMENT PRIMARY KEY,
    mem_id VARCHAR(100),
    question_serial VARCHAR(20),
    comment_root INT,
    comment_parent INT,
    comment_child INT,
    comment_title VARCHAR(25),
    comment_content TEXT,
    comment_date TIMESTAMP,
    comment_hit INT,
    FOREIGN KEY (mem_id) REFERENCES Member(mem_id),
    FOREIGN KEY (question_serial) REFERENCES Question(question_serial)
);

CREATE TABLE Member_Item (
    mem_itemA TINYINT(1) DEFAULT 0,
    mem_itemB TINYINT(1) DEFAULT 0,
    mem_color VARCHAR(15) NULL DEFAULT '',
    mem_id VARCHAR(100) NOT NULL,
    FOREIGN KEY (mem_id) REFERENCES Member(mem_id)
);

CREATE TABLE `Subject` (
    sub_num INT PRIMARY KEY AUTO_INCREMENT,
    sub_name_code INT,
    sub_chap_code INT,
    sub_name VARCHAR(20),
    sub_chap VARCHAR(20)
);

CREATE TABLE Test (
    test_num INT AUTO_INCREMENT PRIMARY KEY,
    mem_id VARCHAR(100) NOT NULL,
    test_name VARCHAR(30),
    test_pw VARCHAR(10),
    test_openYN VARCHAR(2),
    sub_name VARCHAR(20),
    sub_chap VARCHAR(20),
    test_hit INT DEFAULT 0,
    serial TEXT,
    visible TINYINT(1) DEFAULT 1,
    FOREIGN KEY (mem_id) REFERENCES Member(mem_id)
);

CREATE TABLE Fnote (
    mem_id VARCHAR(100),
    test_num INT,
    note TEXT NULL,
    FOREIGN KEY (mem_id) REFERENCES Member(mem_id),
    FOREIGN KEY (test_num) REFERENCES Test(test_num)
);

CREATE TABLE Favorite (
    comment_num INT,         #FK
    mem_id VARCHAR(100),     #FK
    comment_good TINYINT(1), #좋아요
    comment_bad TINYINT(1),  #싫어요
    FOREIGN KEY (comment_num) REFERENCES QnA(comment_num),
    FOREIGN KEY (mem_id) REFERENCES Member(mem_id)
);

select * from Member;
delete from Member where mem_nickName='정재민';
select * from Subject;
select * from Question;
select * from QnA;
delete from Question where question_num=1;
update Member set mem_admin=true;
select * from Fnote;