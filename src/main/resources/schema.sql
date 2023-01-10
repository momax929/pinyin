--DROP TABLE word;
CREATE TABLE if not exists word(
    word_id VARCHAR(255) PRIMARY KEY NOT NULL,
    question VARCHAR(255),
    answer VARCHAR(255),
    lession VARCHAR(255),
    flag INT,
    printed INT

);

--DROP TABLE jour;
CREATE TABLE if not exists jour(
    page_id INT,
    word_id VARCHAR(255),
    flag INT,
    line_no INT,
    order_no INT
);

--DROP TABLE jour_his;
CREATE TABLE if not exists jour_his(
    page_id INT,
    word_id VARCHAR(255),
    flag INT,
    line_no INT,
    order_no INT
);

--DROP TABLE page;
CREATE TABLE if not exists page(
    page_id INT,
    count INT,
    count_error INT,
    count_new INT,
    print_date Datetime,
    answer_date Datetime,
    answer_right INT,
    answer_error INT
);

--DROP TABLE page_his;
CREATE TABLE if not exists page_his(
    page_id INT,
    count INT,
    count_error INT,
    count_new INT,
    print_date Datetime,
    answer_date Datetime,
    answer_right INT,
    answer_error INT
);