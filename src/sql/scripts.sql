CREATE TABLE Customer
(
    cid       INTEGER,
    phone_num INTEGER,
    PRIMARY KEY (cid)
);

INSERT INTO Customer
VALUES (10, 6044523454);
INSERT INTO Customer
VALUES (20, 6041119830);
INSERT INTO Customer
VALUES (30, 7783408871);
INSERT INTO Customer
VALUES (40, 4164591804);
INSERT INTO Customer
VALUES (50, 7785479093);

CREATE TABLE Employee
(
    SIN         INTEGER,
    phone_num   INTEGER,
    hourly_wage INTEGER,
    name        VARCHAR(100),
    PRIMARY KEY (SIN)
);

INSERT INTO Employee
VALUES (247556899, 7786634973, 15, 'Carlos M. Carr');

INSERT INTO Employee
VALUES (124789643, 7783433429, 15, 'John R. Young');

INSERT INTO Employee
VALUES (359846244, 6045533944, 16, 'Elise M. McKinney');

INSERT INTO Employee
VALUES (862245315, 6045531986, 20, 'Michelle C. Drake');

INSERT INTO Employee
VALUES (964327815, 6047896542, 15, 'Gerald E. Carter');

CREATE TABLE GymFranchise
(
    gid  INTEGER,
    name VARCHAR(100),
    PRIMARY KEY (gid)
);

INSERT INTO GymFranchise
VALUES (2, 'Fold''s Gym');
INSERT INTO GymFranchise
VALUES (14, 'Chicken Coop');
INSERT INTO GymFranchise
VALUES (1, 'Flat Earth Fitness');
INSERT INTO GymFranchise
VALUES (3, 'Sometimes Fitness');
INSERT INTO GymFranchise
VALUES (6, 'NWT Gyms');

CREATE TABLE Location
(
    address  VARCHAR(100),
    g#       INTEGER NOT NULL,
    capacity INTEGER,
    phone_num INTEGER,
    PRIMARY KEY (address),
    FOREIGN KEY (g#) references GymFranchise ON DELETE CASCADE
);

INSERT INTO Location
VALUES ('2891 Laurel St', 2, 100, 7781239483);
INSERT INTO Location
VALUES ('2637 Enterprise Way', 14, 150, 6041351942);
INSERT INTO Location
VALUES ('1900 Douglas St', 3, 100, 7780998387);
INSERT INTO Location
VALUES ('32555 London Ave', 1, 500, 7783386729);
INSERT INTO Location
VALUES ('5728 University Blvd #103', 14, 200, 6041804872);

CREATE TABLE ClassSession
(
    class_code INTEGER,
    address    VARCHAR(100) NOT NULL,
    SIN        INTEGER      NOT NULL,
    start_time timestamp,
    category   VARCHAR(100),
    duration   INTEGER,
    capacity       INTEGER,
    PRIMARY KEY (class_code),
    FOREIGN KEY (address) references Location ON DELETE CASCADE,
    FOREIGN KEY (SIN) references Employee ON DELETE CASCADE
);

INSERT INTO ClassSession
VALUES (441, '2891 Laurel St', 862245315, TO_TIMESTAMP('2022-10-03 16:30:00'), 'Yoga', 45, 10);
INSERT INTO ClassSession
VALUES (313, '5728 University Blvd #103', 964327815, TO_TIMESTAMP('2022-03-03 8:30:00'), 'Private', 90, 1);
INSERT INTO ClassSession
VALUES (317, '2637 Enterprise Way', 247556899, TO_TIMESTAMP('2022-03-02 8:30:00'), 'Cycling', 90, 45);
INSERT INTO ClassSession
VALUES (304, '5728 University Blvd #103', 124789643, TO_TIMESTAMP('2022-03-06 17:00:00'), 'Cycling', 60, 25);
INSERT INTO ClassSession
VALUES (121, '5728 University Blvd #103', 359846244, TO_TIMESTAMP('2022-03-06 18:00:00'), 'Cycling', 60, 25);
INSERT INTO ClassSession
VALUES (110, '5728 University Blvd #103', 124789643, TO_TIMESTAMP('2022-03-06 19:00:00'), 'Yoga', 60, 35);
INSERT INTO ClassSession
VALUES (210, '5728 University Blvd #103', 359846244, TO_TIMESTAMP('2022-03-06 20:00:00'), 'Private', 60, 1);

CREATE TABLE SignsUp
(
    cid          INTEGER,
    class_code   INTEGER,
    confirmation INTEGER,
    PRIMARY KEY (cid, class_code),
    FOREIGN KEY (cid) references Customer ON DELETE CASCADE,
    FOREIGN KEY (class_code) references ClassSession ON DELETE CASCADE
);

INSERT INTO SignsUp
VALUES (10, 441, 664311);
INSERT INTO SignsUp
VALUES (20, 313, 331265);
INSERT INTO SignsUp
VALUES (30, 317, 333153);
INSERT INTO SignsUp
VALUES (40, 304, 887432);
INSERT INTO SignsUp
VALUES (50, 121, 754473);
INSERT INTO SignsUp
VALUES (20, 121, 754473);