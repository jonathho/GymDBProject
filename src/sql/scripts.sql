CREATE TABLE Guest
(
    c#                INTEGER,
    phone_num         INTEGER,
    free_classes_left INTEGER,
    PRIMARY KEY c#
);

INSERT INTO Guest
VALUES (10, 6044523454, 1);
INSERT INTO Guest
VALUES (20, 6041119830, 2);
INSERT INTO Guest
VALUES (30, 7783408871, 3);
INSERT INTO Guest
VALUES (40, 4164591804, 4);
INSERT INTO Guest
VALUES (50, 7785479093, 5);

CREATE TABLE Member
(
    c#        INTEGER,
    phone_num INTEGER,
    mem_start DATE,
    mem_end   DATE,
    mem_level INTEGER,
    PRIMARY KEY c#,
    FOREIGN KEY mem_level references Membership ON DELETE CASCADE
);

INSERT INTO Member
VALUES (15, 7782450731, 2022 - 02 - 26, 2022 - 03 - 25, 1);
INSERT INTO Member
VALUES (25, 6045134954, 2022 - 01 - 11, 2022 - 03 - 10, 3);
INSERT INTO Member
VALUES (35, 2161389022, 2022 - 02 - 09, 2022 - 04 - 08, 2);
INSERT INTO Member
VALUES (45, 7785103871, 2022 - 02 - 17, 2022 - 03 - 16, 1);
INSERT INTO Member
VALUES (55, 6045147890, 2022 - 02 - 27, 2022 - 04 - 26, 2);

CREATE TABLE Membership
(
    mem_level INTEGER,
    length    INTEGER,
    PRIMARY KEY mem_level
);

INSERT INTO Memberships
VALUES (1, 1);
INSERT INTO Memberships
VALUES (2, 2);
INSERT INTO Memberships
VALUES (3, 3);
INSERT INTO Memberships
VALUES (4, 4);
INSERT INTO Memberships
VALUES (5, 5);

CREATE TABLE SignsUp
(
    c#           INTEGER,
    class_code   INTEGER,
    confirmation INTEGER
        PRIMARY KEY (c#, class_code),
    FOREIGN KEY c# references Customer ON DELETE CASCADE,
    FOREIGN KEY class_code references ClassSession ON DELETE CASCADE
);

INSERT INTO SignsUp
VALUES (40, 441, 664311);
INSERT INTO SignsUp
VALUES (45, 313, 331265);
INSERT INTO SignsUp
VALUES (15, 317, 333153);
INSERT INTO SignsUp
VALUES (55, 304, 887432);
INSERT INTO SignsUp
VALUES (20, 121, 754473);

CREATE TABLE ClassSession
(
    class_code INTEGER,
    address    VARCHAR(100) NOT NULL,
    SIN        INTEGER      NOT NULL,
    start_time DATETIME,
    category   VARCHAR(100),
    duration   INTEGER,
    size       INTEGER,
    PRIMARY KEY class_code,
    FOREIGN KEY address references Location ON DELETE CASCADE,
    FOREIGN KEY SIN references Employee ON DELETE CASCADE
);

INSERT INTO ClassSession
VALUES (441, '2891 Laurel St', 862245315, 2022 - 10 - 03 16:30:00, 'Yoga', 45, 10);
INSERT INTO ClassSession
VALUES (313, '5728 University Blvd #103', 964327815, 2022 - 03 - 03 8:30:00, 'Private', 90, 1);
INSERT INTO ClassSession
VALUES (317, '2637 Enterprise Way', 247556899, 2022 - 03 - 02 8:30:00, 'Cycling', 90, 45);
INSERT INTO ClassSession
VALUES (304, '5728 University Blvd #103', 124789643, 2022 - 03 - 06 17:00:00, 'Cycling', 60, 25);
INSERT INTO ClassSession
VALUES (121, '5728 University Blvd #103', 359846244, 2022 - 03 - 06 18:00:00, 'Cycling', 60, 25);

CREATE TABLE Location
(
    address   VARCHAR(100),
    g#        INTEGER NOT NULL,
    capacity  INTEGER,
    phone_num INTEGER,
    PRIMARY KEY address,
    FOREIGN KEY g# references GymFranchise ON DELETE CASCADE
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