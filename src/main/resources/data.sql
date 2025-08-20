
DROP SEQUENCE IF EXISTS USER_PHONE_NUMBER_SEQ;
CREATE SEQUENCE USER_PHONE_NUMBER_SEQ
START WITH 1
INCREMENT BY 1;

drop table if exists Phone_User;
CREATE TABLE Phone_User (
                        user_id INTEGER NOT NULL,
                        fullname   VARCHAR(120) NOT NULL,
                        PRIMARY KEY (user_id)
);

drop table if exists User_Phone_Number;
CREATE TABLE User_Phone_Number (
                        id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        user_id INTEGER NOT NULL,
                        number   BIGINT NOT NULL,
                        status  VARCHAR(20)
                       -- foreign key (user_id) references Phone_User(user_id)
);


insert into Phone_User (user_id,fullname) values (11,'Bob A');
insert into Phone_User (user_id,fullname) values (22,'Bob B');
insert into Phone_User (user_id,fullname) values (33,'Bob C');
insert into Phone_User (user_id,fullname) values (44,'Bob D');


-- Assumption: Phone numbers are stored in the DB in their normalised form
insert into User_Phone_Number (user_id,number,status) values (11,61411111111,'ACTIVE');
insert into User_Phone_Number (user_id,number,status) values (11,61422222222,'ACTIVE');
insert into User_Phone_Number (user_id,number,status) values (11,61433333333,'ACTIVE');
insert into User_Phone_Number (user_id,number,status) values (22,61444444444,'ACTIVE');
insert into User_Phone_Number (user_id,number,status) values (22,61455555555,'INACTIVE');
insert into User_Phone_Number (user_id,number,status) values (33,61466666666,'INACTIVE');