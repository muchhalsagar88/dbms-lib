--------------------------------------------------------------------------
--SELECT TABLE_NAME||'('||RTRIM (XMLAGG (XMLELEMENT (e, COLUMN_NAME|| ' ' || DATA_TYPE || ',')).EXTRACT ('//text()'),',')||')' FROM ALL_TAB_COLUMNS WHERE OWNER = 'CSC540' GROUP BY TABLE_NAME;
--------------------------------------------------------------------------


ASSET(ASSET_ID VARCHAR2,LIBRARY_ID NUMBER,ASSET_TYPE NUMBER)
ASSET_CHECKOUT(ID NUMBER,PATRON_ID VARCHAR2,ASSET_ASSET_ID VARCHAR2,RETURN_DATE TIMESTAMP(6),ISSUE_DATE TIMESTAMP(6),FINE NUMBER,DUE_DATE TIMESTAMP(6),ASSET_SECONDARY_ID VARCHAR2)
ASSET_CHECKOUT_CONSTRAINT(ASSET_SECONDARY_ID VARCHAR2,ASSETCHECKOUT_ID NUMBER,PATRON_ID VARCHAR2)
ASSET_PATRON_CONSTRAINT(DURATION NUMBER,ASSET_TYPE_ID NUMBER,PATRON_TYPE CHAR,FINE_DURATION NUMBER,FINE NUMBER)
ASSET_TYPE(ASSETTYPEID NUMBER,SUB_CATEGORY VARCHAR2,CATEGORY VARCHAR2)
AUTHOR(ID VARCHAR2,NAME VARCHAR2)
BOOK(BOOK_ID VARCHAR2,ISBN_NUMBER VARCHAR2)
BOOK_AUTHOR(AUTHOR_ID VARCHAR2,BOOK_ID VARCHAR2)
BOOK_DETAIL(ISBN_NUMBER VARCHAR2,PUBLISHER_ID NUMBER,TITLE VARCHAR2,PUBLICATIONYEAR NUMBER,EDITION VARCHAR2)
CAMERA(CAMERA_ID VARCHAR2,CAMERA_DETAIL_ID VARCHAR2)
CAMERA_DETAIL(CAMERA_DETAIL_ID VARCHAR2,MODEL VARCHAR2,MEMORYAVAILABLE NUMBER,MAKER VARCHAR2,LENSDETAIL VARCHAR2)
CAMERA_RESERVATION(RESERVE_DATE TIMESTAMP(6),CHECKOUT_ID NUMBER,PATRON_ID VARCHAR2,CAMERA_ID VARCHAR2,ISSUE_DATE TIMESTAMP(6),RESERVATION_STATUS VARCHAR2)
CLASSIFICATION(ID NUMBER,NAME VARCHAR2)
CONFERENCE_PROCEEDING_DETAIL(CONF_NUM VARCHAR2,PUB_YEAR NUMBER,TITLE VARCHAR2,CONFERENCENAME VARCHAR2)
CONFERENCE_ROOM(CONF_ROOM_ID VARCHAR2)
CONF_PROCEEDING(CONF_PROC_ID VARCHAR2,CONF_NUM VARCHAR2)
CONF_PROC_AUTHOR(AUTHOR_ID VARCHAR2,CONF_NUM VARCHAR2)
COURSE(ID NUMBER,COURSE_NAME VARCHAR2)
DEGREE_PROGRAM(ID NUMBER,CLASSIFICATION_ID NUMBER,NAME VARCHAR2)
DEGREE_YEAR(DEGREE_YEAR_ID NUMBER,YEAR_ID NUMBER,DEGREE_PROGRAM_ID NUMBER)
DEPARTMENT(ID NUMBER,DEPT_NAME VARCHAR2)
DUE_IN_NEXT_24_HRS(ASSET_ID VARCHAR2,FIRST_NAME VARCHAR2,ASSET_NAME VARCHAR2,PATRON_TYPE CHAR,DUE_DATE TIMESTAMP(6),EMAIL_ADDRESS VARCHAR2,TODAY DATE,LAST_NAME VARCHAR2,ISSUE_DATE TIMESTAMP(6),PATRON_ID VARCHAR2,CHECKOUT_ID NUMBER)
DUE_IN_NEXT_3_DAYS(PATRON_TYPE CHAR,ASSET_NAME VARCHAR2,PATRON_ID VARCHAR2,LAST_NAME VARCHAR2,CHECKOUT_ID NUMBER,DUE_DATE TIMESTAMP(6),ISSUE_DATE TIMESTAMP(6),ASSET_ID VARCHAR2,FIRST_NAME VARCHAR2,TODAY DATE,EMAIL_ADDRESS VARCHAR2)
ENROLL(STUDENT_ID VARCHAR2,COURSE_ID NUMBER)
FACULTY(FACULTY_ID VARCHAR2,CATEGORY_ID NUMBER)
FACULTY_CATEGORY(ID NUMBER,NAME VARCHAR2)
FINE_SNAPSHOT(MAX_ALLOWED_DURATION NUMBER,PATRON_ID VARCHAR2,TODAY DATE,EMAIL_ADDRESS VARCHAR2,CHECKOUT_ID NUMBER,FINE_PER_TIME_Q NUMBER,FINE_AMOUNT NUMBER,OVER_DUE_HRS NUMBER,ISSUE_DATE TIMESTAMP(6),FIRST_NAME VARCHAR2,TIME_Q_HRS NUMBER,DUE_DATE TIMESTAMP(6),LAST_NAME VARCHAR2,PATRON_TYPE CHAR,OVER_DUE_TIME_Q NUMBER)
JOURNAL(JOURNAL_ID VARCHAR2,ISSN_NUMBER VARCHAR2)
JOURNAL_AUTHOR(AUTHOR_ID VARCHAR2,JOURNAL_ID VARCHAR2)
JOURNAL_DETAIL(ISSN_NUMBER VARCHAR2,TITLE VARCHAR2,PUBLICATIONYEAR NUMBER)
LIBRARY(LIBRARY_ID NUMBER,PINCODE NUMBER,CITYNAME VARCHAR2,ADDRESSLINETWO VARCHAR2,ADDRESSLINEONE VARCHAR2,LIBRARY_NAME VARCHAR2)
LOGIN_DETAILS(USERNAME VARCHAR2,PATRON_ID VARCHAR2,PASSWORD VARCHAR2)
MIN_RESERVATION_VIEW(CAMERA_ID VARCHAR2,MIN_RESERVE_DATE TIMESTAMP(6),ISSUE_DATE TIMESTAMP(6))
ONGOING_CHECKOUTS(ID NUMBER,ASSET_ID VARCHAR2,PATRON_ID VARCHAR2,RETURN_DATE TIMESTAMP(6),ISSUE_DATE TIMESTAMP(6),DUE_DATE TIMESTAMP(6))
PATRON(PATRON_ID VARCHAR2,DEPARTMENT_ID NUMBER,NATIONALITY VARCHAR2,LAST_NAME VARCHAR2,HOLD CHAR,FIRST_NAME VARCHAR2,EMAIL_ADDRESS VARCHAR2,PATRON_TYPE CHAR)
PATRON_TYPE(PATRON_TYPE_ID CHAR,DESCRIPTION VARCHAR2)
PUBLICATION(PUBLICATION_ID VARCHAR2,PUBLICATIONFORMAT VARCHAR2)
PUBLICATION_WAITLIST(PUBSECONDARYID VARCHAR2,REQUEST_DATE TIMESTAMP(6),IS_STUDENT NUMBER,PATRONID VARCHAR2)
PUBLISHER(ID NUMBER,NAME VARCHAR2)
RESERVE_BOOK(FROM_DATE DATE,FACULTY_ID VARCHAR2,COURSE_ID NUMBER,BOOK_ISBN VARCHAR2,TODATE DATE)
ROOM(ROOM_ID VARCHAR2,ROOMNO VARCHAR2,FLOORLEVEL NUMBER,CAPACITY NUMBER)
ROOM_RESERVATION(RESERVATION_ID NUMBER,CHECKOUT_ID NUMBER,ROOM_ASSET_ID VARCHAR2,PATRON_ID VARCHAR2,START_TIME TIMESTAMP(6),RESERVE_TIME TIMESTAMP(6),END_TIME TIMESTAMP(6))
SEQUENCE(SEQ_NAME VARCHAR2,SEQ_COUNT NUMBER)
STUDENT(STUDENT_ID VARCHAR2,DEGREEYEAR_DEGREE_YEAR_ID NUMBER,PINCODE NUMBER,CITYNAME VARCHAR2,ADDRESSLINETWO VARCHAR2,ADDRESSLINEONE VARCHAR2,SEX CHAR,PHONE_NO VARCHAR2,DOB DATE,ALT_PHONE_NO VARCHAR2)
STUDY_ROOM(STUDY_ROOM_ID VARCHAR2)
TEACH(FACULTY_ID VARCHAR2,COURSE_ID NUMBER)
YEAR(ID NUMBER,NAME VARCHAR2)



















--------------------------------------------------------
--  File created - Monday-November-02-2015   
--------------------------------------------------------
DROP TABLE "CSC540"."ASSET";
DROP TABLE "CSC540"."ASSET_CHECKOUT";
DROP TABLE "CSC540"."ASSET_CHECKOUT_CONSTRAINT";
DROP TABLE "CSC540"."ASSET_PATRON_CONSTRAINT";
DROP TABLE "CSC540"."ASSET_TYPE";
DROP TABLE "CSC540"."AUTHOR";
DROP TABLE "CSC540"."BOOK";
DROP TABLE "CSC540"."BOOK_AUTHOR";
DROP TABLE "CSC540"."BOOK_DETAIL";
DROP TABLE "CSC540"."CAMERA";
DROP TABLE "CSC540"."CAMERA_DETAIL";
DROP TABLE "CSC540"."CAMERA_RESERVATION";
DROP TABLE "CSC540"."CLASSIFICATION";
DROP TABLE "CSC540"."CONFERENCE_PROCEEDING_DETAIL";
DROP TABLE "CSC540"."CONFERENCE_ROOM";
DROP TABLE "CSC540"."CONF_PROCEEDING";
DROP TABLE "CSC540"."CONF_PROC_AUTHOR";
DROP TABLE "CSC540"."COURSE";
DROP TABLE "CSC540"."DEGREE_PROGRAM";
DROP TABLE "CSC540"."DEGREE_YEAR";
DROP TABLE "CSC540"."DEPARTMENT";
DROP TABLE "CSC540"."ENROLL";
DROP TABLE "CSC540"."FACULTY";
DROP TABLE "CSC540"."FACULTY_CATEGORY";
DROP TABLE "CSC540"."JOURNAL";
DROP TABLE "CSC540"."JOURNAL_AUTHOR";
DROP TABLE "CSC540"."JOURNAL_DETAIL";
DROP TABLE "CSC540"."LIBRARY";
DROP TABLE "CSC540"."LOGIN_DETAILS";
DROP TABLE "CSC540"."PATRON";
DROP TABLE "CSC540"."PATRON_TYPE";
DROP TABLE "CSC540"."PUBLICATION";
DROP TABLE "CSC540"."PUBLICATION_WAITLIST";
DROP TABLE "CSC540"."PUBLISHER";
DROP TABLE "CSC540"."RESERVE_BOOK";
DROP TABLE "CSC540"."ROOM";
DROP TABLE "CSC540"."ROOM_RESERVATION";
DROP TABLE "CSC540"."SEQUENCE";
DROP TABLE "CSC540"."STUDENT";
DROP TABLE "CSC540"."STUDY_ROOM";
DROP TABLE "CSC540"."TEACH";
DROP TABLE "CSC540"."YEAR";
DROP SEQUENCE "CSC540"."SEQ_GEN_IDENTITY";
DROP SEQUENCE "CSC540"."SEQ_GEN_SEQUENCE";
DROP VIEW "CSC540"."DUE_IN_NEXT_24_HRS";
DROP VIEW "CSC540"."DUE_IN_NEXT_3_DAYS";
DROP VIEW "CSC540"."FINE_SNAPSHOT";
DROP VIEW "CSC540"."MIN_RESERVATION_VIEW";
DROP VIEW "CSC540"."ONGOING_CHECKOUTS";
DROP FUNCTION "CSC540"."FINE_FOR_PATRON";
DROP FUNCTION "CSC540"."MY_HASH";
DROP FUNCTION "CSC540"."TS_DIFF_IN_HRS";
--------------------------------------------------------
--  DDL for Sequence SEQ_GEN_IDENTITY
--------------------------------------------------------

   CREATE SEQUENCE  "CSC540"."SEQ_GEN_IDENTITY"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 26 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_GEN_SEQUENCE
--------------------------------------------------------

   CREATE SEQUENCE  "CSC540"."SEQ_GEN_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 50 START WITH 350 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table ASSET
--------------------------------------------------------

  CREATE TABLE "CSC540"."ASSET" 
   (	"ASSET_ID" VARCHAR2(255), 
	"ASSET_TYPE" NUMBER(31,0), 
	"LIBRARY_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table ASSET_CHECKOUT
--------------------------------------------------------

  CREATE TABLE "CSC540"."ASSET_CHECKOUT" 
   (	"ID" NUMBER(19,0), 
	"ASSET_SECONDARY_ID" VARCHAR2(255), 
	"DUE_DATE" TIMESTAMP (6), 
	"FINE" NUMBER(19,4), 
	"ISSUE_DATE" TIMESTAMP (6), 
	"RETURN_DATE" TIMESTAMP (6), 
	"ASSET_ASSET_ID" VARCHAR2(255), 
	"PATRON_ID" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table ASSET_CHECKOUT_CONSTRAINT
--------------------------------------------------------

  CREATE TABLE "CSC540"."ASSET_CHECKOUT_CONSTRAINT" 
   (	"ASSET_SECONDARY_ID" VARCHAR2(255), 
	"PATRON_ID" VARCHAR2(255), 
	"ASSETCHECKOUT_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table ASSET_PATRON_CONSTRAINT
--------------------------------------------------------

  CREATE TABLE "CSC540"."ASSET_PATRON_CONSTRAINT" 
   (	"DURATION" NUMBER(10,0), 
	"FINE" NUMBER(19,4), 
	"FINE_DURATION" NUMBER(10,0), 
	"PATRON_TYPE" CHAR(1), 
	"ASSET_TYPE_ID" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table ASSET_TYPE
--------------------------------------------------------

  CREATE TABLE "CSC540"."ASSET_TYPE" 
   (	"ASSETTYPEID" NUMBER(10,0), 
	"CATEGORY" VARCHAR2(255), 
	"SUB_CATEGORY" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table AUTHOR
--------------------------------------------------------

  CREATE TABLE "CSC540"."AUTHOR" 
   (	"ID" VARCHAR2(255), 
	"NAME" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table BOOK
--------------------------------------------------------

  CREATE TABLE "CSC540"."BOOK" 
   (	"BOOK_ID" VARCHAR2(255), 
	"ISBN_NUMBER" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table BOOK_AUTHOR
--------------------------------------------------------

  CREATE TABLE "CSC540"."BOOK_AUTHOR" 
   (	"AUTHOR_ID" VARCHAR2(255), 
	"BOOK_ID" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table BOOK_DETAIL
--------------------------------------------------------

  CREATE TABLE "CSC540"."BOOK_DETAIL" 
   (	"ISBN_NUMBER" VARCHAR2(255), 
	"EDITION" VARCHAR2(255), 
	"PUBLICATIONYEAR" NUMBER(10,0), 
	"TITLE" VARCHAR2(255), 
	"PUBLISHER_ID" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table CAMERA
--------------------------------------------------------

  CREATE TABLE "CSC540"."CAMERA" 
   (	"CAMERA_ID" VARCHAR2(255), 
	"CAMERA_DETAIL_ID" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table CAMERA_DETAIL
--------------------------------------------------------

  CREATE TABLE "CSC540"."CAMERA_DETAIL" 
   (	"CAMERA_DETAIL_ID" VARCHAR2(255), 
	"LENSDETAIL" VARCHAR2(255), 
	"MAKER" VARCHAR2(255), 
	"MEMORYAVAILABLE" NUMBER(10,0), 
	"MODEL" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table CAMERA_RESERVATION
--------------------------------------------------------

  CREATE TABLE "CSC540"."CAMERA_RESERVATION" 
   (	"RESERVE_DATE" TIMESTAMP (6), 
	"RESERVATION_STATUS" VARCHAR2(20) DEFAULT 'ACTIVE', 
	"ISSUE_DATE" TIMESTAMP (6), 
	"CAMERA_ID" VARCHAR2(255), 
	"PATRON_ID" VARCHAR2(255), 
	"CHECKOUT_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table CLASSIFICATION
--------------------------------------------------------

  CREATE TABLE "CSC540"."CLASSIFICATION" 
   (	"ID" NUMBER(19,0), 
	"NAME" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table CONFERENCE_PROCEEDING_DETAIL
--------------------------------------------------------

  CREATE TABLE "CSC540"."CONFERENCE_PROCEEDING_DETAIL" 
   (	"CONF_NUM" VARCHAR2(255), 
	"CONFERENCENAME" VARCHAR2(255), 
	"TITLE" VARCHAR2(255), 
	"PUB_YEAR" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table CONFERENCE_ROOM
--------------------------------------------------------

  CREATE TABLE "CSC540"."CONFERENCE_ROOM" 
   (	"CONF_ROOM_ID" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table CONF_PROCEEDING
--------------------------------------------------------

  CREATE TABLE "CSC540"."CONF_PROCEEDING" 
   (	"CONF_PROC_ID" VARCHAR2(255), 
	"CONF_NUM" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table CONF_PROC_AUTHOR
--------------------------------------------------------

  CREATE TABLE "CSC540"."CONF_PROC_AUTHOR" 
   (	"AUTHOR_ID" VARCHAR2(255), 
	"CONF_NUM" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table COURSE
--------------------------------------------------------

  CREATE TABLE "CSC540"."COURSE" 
   (	"ID" NUMBER(19,0), 
	"COURSE_NAME" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table DEGREE_PROGRAM
--------------------------------------------------------

  CREATE TABLE "CSC540"."DEGREE_PROGRAM" 
   (	"ID" NUMBER(19,0), 
	"NAME" VARCHAR2(255), 
	"CLASSIFICATION_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table DEGREE_YEAR
--------------------------------------------------------

  CREATE TABLE "CSC540"."DEGREE_YEAR" 
   (	"DEGREE_YEAR_ID" NUMBER(19,0), 
	"DEGREE_PROGRAM_ID" NUMBER(19,0), 
	"YEAR_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table DEPARTMENT
--------------------------------------------------------

  CREATE TABLE "CSC540"."DEPARTMENT" 
   (	"ID" NUMBER(19,0), 
	"DEPT_NAME" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table ENROLL
--------------------------------------------------------

  CREATE TABLE "CSC540"."ENROLL" 
   (	"STUDENT_ID" VARCHAR2(255), 
	"COURSE_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table FACULTY
--------------------------------------------------------

  CREATE TABLE "CSC540"."FACULTY" 
   (	"FACULTY_ID" VARCHAR2(255), 
	"CATEGORY_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table FACULTY_CATEGORY
--------------------------------------------------------

  CREATE TABLE "CSC540"."FACULTY_CATEGORY" 
   (	"ID" NUMBER(19,0), 
	"NAME" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table JOURNAL
--------------------------------------------------------

  CREATE TABLE "CSC540"."JOURNAL" 
   (	"JOURNAL_ID" VARCHAR2(255), 
	"ISSN_NUMBER" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table JOURNAL_AUTHOR
--------------------------------------------------------

  CREATE TABLE "CSC540"."JOURNAL_AUTHOR" 
   (	"AUTHOR_ID" VARCHAR2(255), 
	"JOURNAL_ID" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table JOURNAL_DETAIL
--------------------------------------------------------

  CREATE TABLE "CSC540"."JOURNAL_DETAIL" 
   (	"ISSN_NUMBER" VARCHAR2(255), 
	"PUBLICATIONYEAR" NUMBER(10,0), 
	"TITLE" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table LIBRARY
--------------------------------------------------------

  CREATE TABLE "CSC540"."LIBRARY" 
   (	"LIBRARY_ID" NUMBER(19,0), 
	"LIBRARY_NAME" VARCHAR2(255), 
	"ADDRESSLINEONE" VARCHAR2(255), 
	"ADDRESSLINETWO" VARCHAR2(255), 
	"CITYNAME" VARCHAR2(255), 
	"PINCODE" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LOGIN_DETAILS
--------------------------------------------------------

  CREATE TABLE "CSC540"."LOGIN_DETAILS" 
   (	"USERNAME" VARCHAR2(255), 
	"PASSWORD" VARCHAR2(255), 
	"PATRON_ID" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table PATRON
--------------------------------------------------------

  CREATE TABLE "CSC540"."PATRON" 
   (	"PATRON_ID" VARCHAR2(255), 
	"PATRON_TYPE" CHAR(1), 
	"EMAIL_ADDRESS" VARCHAR2(255), 
	"FIRST_NAME" VARCHAR2(255), 
	"HOLD" CHAR(1) DEFAULT 'N', 
	"LAST_NAME" VARCHAR2(255), 
	"NATIONALITY" VARCHAR2(255), 
	"DEPARTMENT_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table PATRON_TYPE
--------------------------------------------------------

  CREATE TABLE "CSC540"."PATRON_TYPE" 
   (	"PATRON_TYPE_ID" CHAR(1), 
	"DESCRIPTION" VARCHAR2(50)
   ) ;
--------------------------------------------------------
--  DDL for Table PUBLICATION
--------------------------------------------------------

  CREATE TABLE "CSC540"."PUBLICATION" 
   (	"PUBLICATION_ID" VARCHAR2(255), 
	"PUBLICATIONFORMAT" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table PUBLICATION_WAITLIST
--------------------------------------------------------

  CREATE TABLE "CSC540"."PUBLICATION_WAITLIST" 
   (	"PUBSECONDARYID" VARCHAR2(255), 
	"PATRONID" VARCHAR2(255), 
	"IS_STUDENT" NUMBER(10,0), 
	"REQUEST_DATE" TIMESTAMP (6)
   ) ;
--------------------------------------------------------
--  DDL for Table PUBLISHER
--------------------------------------------------------

  CREATE TABLE "CSC540"."PUBLISHER" 
   (	"ID" NUMBER(10,0), 
	"NAME" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table RESERVE_BOOK
--------------------------------------------------------

  CREATE TABLE "CSC540"."RESERVE_BOOK" 
   (	"FROM_DATE" DATE, 
	"TODATE" DATE, 
	"BOOK_ISBN" VARCHAR2(255), 
	"COURSE_ID" NUMBER(19,0), 
	"FACULTY_ID" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table ROOM
--------------------------------------------------------

  CREATE TABLE "CSC540"."ROOM" 
   (	"ROOM_ID" VARCHAR2(255), 
	"CAPACITY" NUMBER(10,0), 
	"FLOORLEVEL" NUMBER(10,0), 
	"ROOMNO" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table ROOM_RESERVATION
--------------------------------------------------------

  CREATE TABLE "CSC540"."ROOM_RESERVATION" 
   (	"RESERVATION_ID" NUMBER(19,0), 
	"END_TIME" TIMESTAMP (6), 
	"RESERVE_TIME" TIMESTAMP (6), 
	"START_TIME" TIMESTAMP (6), 
	"PATRON_ID" VARCHAR2(255), 
	"ROOM_ASSET_ID" VARCHAR2(255), 
	"CHECKOUT_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table SEQUENCE
--------------------------------------------------------

  CREATE TABLE "CSC540"."SEQUENCE" 
   (	"SEQ_NAME" VARCHAR2(50), 
	"SEQ_COUNT" NUMBER(38,0)
   ) ;
--------------------------------------------------------
--  DDL for Table STUDENT
--------------------------------------------------------

  CREATE TABLE "CSC540"."STUDENT" 
   (	"STUDENT_ID" VARCHAR2(255), 
	"ALT_PHONE_NO" VARCHAR2(255), 
	"DOB" DATE, 
	"PHONE_NO" VARCHAR2(255), 
	"SEX" CHAR(1), 
	"ADDRESSLINEONE" VARCHAR2(255), 
	"ADDRESSLINETWO" VARCHAR2(255), 
	"CITYNAME" VARCHAR2(255), 
	"PINCODE" NUMBER(10,0), 
	"DEGREEYEAR_DEGREE_YEAR_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table STUDY_ROOM
--------------------------------------------------------

  CREATE TABLE "CSC540"."STUDY_ROOM" 
   (	"STUDY_ROOM_ID" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table TEACH
--------------------------------------------------------

  CREATE TABLE "CSC540"."TEACH" 
   (	"FACULTY_ID" VARCHAR2(255), 
	"COURSE_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table YEAR
--------------------------------------------------------

  CREATE TABLE "CSC540"."YEAR" 
   (	"ID" NUMBER(19,0), 
	"NAME" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for View DUE_IN_NEXT_24_HRS
--------------------------------------------------------

  CREATE OR REPLACE VIEW "CSC540"."DUE_IN_NEXT_24_HRS" ("FIRST_NAME", "LAST_NAME", "EMAIL_ADDRESS", "ISSUE_DATE", "DUE_DATE", "TODAY", "PATRON_ID", "PATRON_TYPE", "CHECKOUT_ID", "ASSET_ID", "ASSET_NAME") AS 
  SELECT P.FIRST_NAME,
       P.LAST_NAME,
       P.EMAIL_ADDRESS,
       ISSUE_DATE,
       DUE_DATE,
       SYSDATE TODAY,
       P.PATRON_ID,
       P.PATRON_TYPE,
       AC.ID CHECKOUT_ID,
       A.ASSET_ID,
       (SELECT 'Camera: "' || MAKER || ' ' || MODEL || '"'  FROM
            CAMERA C,
            CAMERA_DETAIL CD
        WHERE (C.CAMERA_ID = A.ASSET_ID AND C.CAMERA_DETAIL_ID = CD.CAMERA_DETAIL_ID)
        UNION
        SELECT 'Journal: "' || TITLE || '"'  FROM
            JOURNAL J,
            JOURNAL_DETAIL JD
        WHERE (J.ISSN_NUMBER = JD.ISSN_NUMBER AND J.JOURNAL_ID = A.ASSET_ID)
        UNION
        SELECT 'Conference Proceeding: "' || TITLE || '"'  FROM
            CONF_PROCEEDING CP,
            CONFERENCE_PROCEEDING_DETAIL CPD
        WHERE (CP.CONF_NUM = CPD.CONF_NUM AND CP.CONF_PROC_ID = A.ASSET_ID)
        UNION
        SELECT 'Book: "' || TITLE || '"' FROM
            BOOK B,
            BOOK_DETAIL BD
        WHERE (B.ISBN_NUMBER = BD.ISBN_NUMBER AND B.BOOK_ID = A.ASSET_ID)) ASSET_NAME
FROM
    ASSET A,
    ASSET_CHECKOUT AC,
    PATRON P
WHERE
    A.ASSET_ID = AC.ASSET_ASSET_ID
    AND P.PATRON_ID = AC.PATRON_ID
    AND AC.RETURN_DATE IS NULL
    AND A.ASSET_TYPE NOT IN (SELECT ASSETTYPEID FROM ASSET_TYPE AT WHERE AT.CATEGORY = 'Room')
    AND DUE_DATE < SYSDATE+1
    AND DUE_DATE > SYSDATE;
--------------------------------------------------------
--  DDL for View DUE_IN_NEXT_3_DAYS
--------------------------------------------------------

  CREATE OR REPLACE VIEW "CSC540"."DUE_IN_NEXT_3_DAYS" ("FIRST_NAME", "LAST_NAME", "EMAIL_ADDRESS", "ISSUE_DATE", "DUE_DATE", "TODAY", "PATRON_ID", "PATRON_TYPE", "CHECKOUT_ID", "ASSET_ID", "ASSET_NAME") AS 
  SELECT P.FIRST_NAME,
       P.LAST_NAME,
       P.EMAIL_ADDRESS,
       ISSUE_DATE,
       DUE_DATE,
       SYSDATE TODAY,
       P.PATRON_ID,
       P.PATRON_TYPE,
       AC.ID CHECKOUT_ID,
       A.ASSET_ID,
       (SELECT 'Camera: "' || MAKER || ' ' || MODEL || '"'  FROM
            CAMERA C,
            CAMERA_DETAIL CD
        WHERE (C.CAMERA_ID = A.ASSET_ID AND C.CAMERA_DETAIL_ID = CD.CAMERA_DETAIL_ID)
        UNION
        SELECT 'Journal: "' || TITLE || '"'  FROM
            JOURNAL J,
            JOURNAL_DETAIL JD
        WHERE (J.ISSN_NUMBER = JD.ISSN_NUMBER AND J.JOURNAL_ID = A.ASSET_ID)
        UNION
        SELECT 'Conference Proceeding: "' || TITLE || '"'  FROM
            CONF_PROCEEDING CP,
            CONFERENCE_PROCEEDING_DETAIL CPD
        WHERE (CP.CONF_NUM = CPD.CONF_NUM AND CP.CONF_PROC_ID = A.ASSET_ID)
        UNION
        SELECT 'Book: "' || TITLE || '"' FROM
            BOOK B,
            BOOK_DETAIL BD
        WHERE (B.ISBN_NUMBER = BD.ISBN_NUMBER AND B.BOOK_ID = A.ASSET_ID)) ASSET_NAME
FROM
    ASSET A,
    ASSET_CHECKOUT AC,
    PATRON P
WHERE
    A.ASSET_ID = AC.ASSET_ASSET_ID
    AND P.PATRON_ID = AC.PATRON_ID
    AND AC.RETURN_DATE IS NULL
    AND A.ASSET_TYPE NOT IN (SELECT ASSETTYPEID FROM ASSET_TYPE AT WHERE AT.CATEGORY = 'Room')
    AND DUE_DATE < SYSDATE+3
    AND DUE_DATE > SYSDATE+2;
--------------------------------------------------------
--  DDL for View FINE_SNAPSHOT
--------------------------------------------------------

  CREATE OR REPLACE VIEW "CSC540"."FINE_SNAPSHOT" ("ISSUE_DATE", "DUE_DATE", "PATRON_ID", "PATRON_TYPE", "MAX_ALLOWED_DURATION", "TODAY", "OVER_DUE_HRS", "OVER_DUE_TIME_Q", "FINE_AMOUNT", "FINE_PER_TIME_Q", "TIME_Q_HRS", "CHECKOUT_ID", "EMAIL_ADDRESS", "FIRST_NAME", "LAST_NAME") AS 
  SELECT ISSUE_DATE,
       DUE_DATE,
       P.PATRON_ID,
       P.PATRON_TYPE,
       DURATION MAX_ALLOWED_DURATION,
       SYSDATE TODAY,
       CASE WHEN TS_DIFF_IN_HRS(NVL(RETURN_DATE,SYSDATE), DUE_DATE, 1) < 0 THEN 0 ELSE TS_DIFF_IN_HRS(NVL(RETURN_DATE,SYSDATE), DUE_DATE, 1) END OVER_DUE_HRS,
       CASE WHEN TS_DIFF_IN_HRS(NVL(RETURN_DATE,SYSDATE), DUE_DATE, 1) < 0 THEN 0 ELSE CEIL(TS_DIFF_IN_HRS(NVL(RETURN_DATE,SYSDATE), DUE_DATE, 1)/FINE_DURATION) END OVER_DUE_TIME_Q,
       CASE WHEN TS_DIFF_IN_HRS(NVL(RETURN_DATE,SYSDATE), DUE_DATE, 1) < 0 THEN 0 ELSE APC.FINE*CEIL(TS_DIFF_IN_HRS(NVL(RETURN_DATE,SYSDATE), DUE_DATE, 1)/FINE_DURATION) END FINE_AMOUNT,
       APC.FINE FINE_PER_TIME_Q,
       FINE_DURATION TIME_Q_HRS,
       AC.ID CHECKOUT_ID,
       P.EMAIL_ADDRESS,
       P.FIRST_NAME,
       P.LAST_NAME
FROM
    ASSET A,
    ASSET_PATRON_CONSTRAINT APC,
    ASSET_CHECKOUT AC,
    PATRON P
WHERE
    A.ASSET_TYPE = APC.ASSET_TYPE_ID
    AND A.ASSET_ID = AC.ASSET_ASSET_ID
    AND P.PATRON_ID = AC.PATRON_ID
    AND P.PATRON_TYPE = APC.PATRON_TYPE
    AND APC.FINE <> 0;
--------------------------------------------------------
--  DDL for View MIN_RESERVATION_VIEW
--------------------------------------------------------

  CREATE OR REPLACE VIEW "CSC540"."MIN_RESERVATION_VIEW" ("CAMERA_ID", "ISSUE_DATE", "MIN_RESERVE_DATE") AS 
  SELECT c.camera_Id as id, c.issue_Date, MIN(c.reserve_Date) as res_date
				FROM Camera_Reservation c
				WHERE c.reservation_status='ACTIVE'
        GROUP BY c.camera_Id, c.issue_date;
--------------------------------------------------------
--  DDL for View ONGOING_CHECKOUTS
--------------------------------------------------------

  CREATE OR REPLACE VIEW "CSC540"."ONGOING_CHECKOUTS" ("ID", "DUE_DATE", "ISSUE_DATE", "RETURN_DATE", "ASSET_ID", "PATRON_ID") AS 
  SELECT c.ID, c.DUE_DATE, c.ISSUE_DATE, c.RETURN_DATE, c.ASSET_ASSET_ID, c.PATRON_ID
  FROM Asset_Checkout c
  WHERE c.Due_Date < SYSDATE
    AND c.Return_Date IS NULL;
--------------------------------------------------------
--  Constraints for Table ASSET
--------------------------------------------------------

  ALTER TABLE "CSC540"."ASSET" ADD PRIMARY KEY ("ASSET_ID") ENABLE;
  ALTER TABLE "CSC540"."ASSET" MODIFY ("LIBRARY_ID" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."ASSET" MODIFY ("ASSET_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ASSET_CHECKOUT
--------------------------------------------------------

  ALTER TABLE "CSC540"."ASSET_CHECKOUT" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CSC540"."ASSET_CHECKOUT" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ASSET_CHECKOUT_CONSTRAINT
--------------------------------------------------------

  ALTER TABLE "CSC540"."ASSET_CHECKOUT_CONSTRAINT" ADD PRIMARY KEY ("ASSET_SECONDARY_ID", "PATRON_ID") ENABLE;
  ALTER TABLE "CSC540"."ASSET_CHECKOUT_CONSTRAINT" MODIFY ("PATRON_ID" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."ASSET_CHECKOUT_CONSTRAINT" MODIFY ("ASSET_SECONDARY_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ASSET_PATRON_CONSTRAINT
--------------------------------------------------------

  ALTER TABLE "CSC540"."ASSET_PATRON_CONSTRAINT" ADD PRIMARY KEY ("PATRON_TYPE", "ASSET_TYPE_ID") ENABLE;
  ALTER TABLE "CSC540"."ASSET_PATRON_CONSTRAINT" MODIFY ("ASSET_TYPE_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ASSET_TYPE
--------------------------------------------------------

  ALTER TABLE "CSC540"."ASSET_TYPE" ADD PRIMARY KEY ("ASSETTYPEID") ENABLE;
  ALTER TABLE "CSC540"."ASSET_TYPE" MODIFY ("ASSETTYPEID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table AUTHOR
--------------------------------------------------------

  ALTER TABLE "CSC540"."AUTHOR" ADD CONSTRAINT "AUTHOR_C01" CHECK (NAME IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."AUTHOR" ADD CONSTRAINT "AUTHOR_U01" UNIQUE ("NAME") ENABLE;
  ALTER TABLE "CSC540"."AUTHOR" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CSC540"."AUTHOR" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table BOOK
--------------------------------------------------------

  ALTER TABLE "CSC540"."BOOK" ADD PRIMARY KEY ("BOOK_ID") ENABLE;
  ALTER TABLE "CSC540"."BOOK" MODIFY ("BOOK_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table BOOK_AUTHOR
--------------------------------------------------------

  ALTER TABLE "CSC540"."BOOK_AUTHOR" ADD PRIMARY KEY ("AUTHOR_ID", "BOOK_ID") ENABLE;
  ALTER TABLE "CSC540"."BOOK_AUTHOR" MODIFY ("BOOK_ID" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."BOOK_AUTHOR" MODIFY ("AUTHOR_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table BOOK_DETAIL
--------------------------------------------------------

  ALTER TABLE "CSC540"."BOOK_DETAIL" ADD PRIMARY KEY ("ISBN_NUMBER") ENABLE;
  ALTER TABLE "CSC540"."BOOK_DETAIL" MODIFY ("ISBN_NUMBER" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CAMERA
--------------------------------------------------------

  ALTER TABLE "CSC540"."CAMERA" ADD PRIMARY KEY ("CAMERA_ID") ENABLE;
  ALTER TABLE "CSC540"."CAMERA" MODIFY ("CAMERA_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CAMERA_DETAIL
--------------------------------------------------------

  ALTER TABLE "CSC540"."CAMERA_DETAIL" ADD CONSTRAINT "CAMERA_DETAIL_U01" UNIQUE ("MODEL", "MEMORYAVAILABLE", "MAKER", "LENSDETAIL") ENABLE;
  ALTER TABLE "CSC540"."CAMERA_DETAIL" ADD PRIMARY KEY ("CAMERA_DETAIL_ID") ENABLE;
  ALTER TABLE "CSC540"."CAMERA_DETAIL" MODIFY ("CAMERA_DETAIL_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CAMERA_RESERVATION
--------------------------------------------------------

  ALTER TABLE "CSC540"."CAMERA_RESERVATION" ADD PRIMARY KEY ("ISSUE_DATE", "CAMERA_ID", "PATRON_ID") ENABLE;
  ALTER TABLE "CSC540"."CAMERA_RESERVATION" MODIFY ("PATRON_ID" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."CAMERA_RESERVATION" MODIFY ("CAMERA_ID" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."CAMERA_RESERVATION" MODIFY ("ISSUE_DATE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CLASSIFICATION
--------------------------------------------------------

  ALTER TABLE "CSC540"."CLASSIFICATION" ADD CONSTRAINT "CLASSIFICATION_C01" CHECK (NAME IS NOT NULL AND LOWER(NAME) IN ('undergraduate','postgraduate','graduate')) ENABLE;
  ALTER TABLE "CSC540"."CLASSIFICATION" ADD CONSTRAINT "CLASSIFICATION_U01" UNIQUE ("NAME") ENABLE;
  ALTER TABLE "CSC540"."CLASSIFICATION" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CSC540"."CLASSIFICATION" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CONFERENCE_PROCEEDING_DETAIL
--------------------------------------------------------

  ALTER TABLE "CSC540"."CONFERENCE_PROCEEDING_DETAIL" ADD PRIMARY KEY ("CONF_NUM") ENABLE;
  ALTER TABLE "CSC540"."CONFERENCE_PROCEEDING_DETAIL" MODIFY ("CONF_NUM" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CONFERENCE_ROOM
--------------------------------------------------------

  ALTER TABLE "CSC540"."CONFERENCE_ROOM" ADD PRIMARY KEY ("CONF_ROOM_ID") ENABLE;
  ALTER TABLE "CSC540"."CONFERENCE_ROOM" MODIFY ("CONF_ROOM_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CONF_PROCEEDING
--------------------------------------------------------

  ALTER TABLE "CSC540"."CONF_PROCEEDING" ADD PRIMARY KEY ("CONF_PROC_ID") ENABLE;
  ALTER TABLE "CSC540"."CONF_PROCEEDING" MODIFY ("CONF_PROC_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CONF_PROC_AUTHOR
--------------------------------------------------------

  ALTER TABLE "CSC540"."CONF_PROC_AUTHOR" ADD PRIMARY KEY ("AUTHOR_ID", "CONF_NUM") ENABLE;
  ALTER TABLE "CSC540"."CONF_PROC_AUTHOR" MODIFY ("CONF_NUM" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."CONF_PROC_AUTHOR" MODIFY ("AUTHOR_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table COURSE
--------------------------------------------------------

  ALTER TABLE "CSC540"."COURSE" ADD CONSTRAINT "COURSE_C01" CHECK (COURSE_NAME IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."COURSE" ADD CONSTRAINT "COURSE_U01" UNIQUE ("COURSE_NAME") ENABLE;
  ALTER TABLE "CSC540"."COURSE" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CSC540"."COURSE" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DEGREE_PROGRAM
--------------------------------------------------------

  ALTER TABLE "CSC540"."DEGREE_PROGRAM" ADD CONSTRAINT "DEGREE_PROGRAM_C01" CHECK (NAME IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."DEGREE_PROGRAM" ADD CONSTRAINT "DEGREE_PROGRAM_U01" UNIQUE ("NAME") ENABLE;
  ALTER TABLE "CSC540"."DEGREE_PROGRAM" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CSC540"."DEGREE_PROGRAM" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DEGREE_YEAR
--------------------------------------------------------

  ALTER TABLE "CSC540"."DEGREE_YEAR" ADD CONSTRAINT "UNQ_DEGREE_YEAR_0" UNIQUE ("DEGREE_PROGRAM_ID", "YEAR_ID") ENABLE;
  ALTER TABLE "CSC540"."DEGREE_YEAR" ADD PRIMARY KEY ("DEGREE_YEAR_ID") ENABLE;
  ALTER TABLE "CSC540"."DEGREE_YEAR" MODIFY ("DEGREE_YEAR_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DEPARTMENT
--------------------------------------------------------

  ALTER TABLE "CSC540"."DEPARTMENT" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CSC540"."DEPARTMENT" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ENROLL
--------------------------------------------------------

  ALTER TABLE "CSC540"."ENROLL" ADD PRIMARY KEY ("STUDENT_ID", "COURSE_ID") ENABLE;
  ALTER TABLE "CSC540"."ENROLL" MODIFY ("COURSE_ID" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."ENROLL" MODIFY ("STUDENT_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table FACULTY
--------------------------------------------------------

  ALTER TABLE "CSC540"."FACULTY" ADD PRIMARY KEY ("FACULTY_ID") ENABLE;
  ALTER TABLE "CSC540"."FACULTY" MODIFY ("FACULTY_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table FACULTY_CATEGORY
--------------------------------------------------------

  ALTER TABLE "CSC540"."FACULTY_CATEGORY" ADD CONSTRAINT "FACULTY_CATEGORY_C01" CHECK (NAME IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."FACULTY_CATEGORY" ADD CONSTRAINT "FACULTY_CATEGORY_U01" UNIQUE ("NAME") ENABLE;
  ALTER TABLE "CSC540"."FACULTY_CATEGORY" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CSC540"."FACULTY_CATEGORY" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table JOURNAL
--------------------------------------------------------

  ALTER TABLE "CSC540"."JOURNAL" ADD PRIMARY KEY ("JOURNAL_ID") ENABLE;
  ALTER TABLE "CSC540"."JOURNAL" MODIFY ("JOURNAL_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table JOURNAL_AUTHOR
--------------------------------------------------------

  ALTER TABLE "CSC540"."JOURNAL_AUTHOR" ADD PRIMARY KEY ("AUTHOR_ID", "JOURNAL_ID") ENABLE;
  ALTER TABLE "CSC540"."JOURNAL_AUTHOR" MODIFY ("JOURNAL_ID" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."JOURNAL_AUTHOR" MODIFY ("AUTHOR_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table JOURNAL_DETAIL
--------------------------------------------------------

  ALTER TABLE "CSC540"."JOURNAL_DETAIL" ADD PRIMARY KEY ("ISSN_NUMBER") ENABLE;
  ALTER TABLE "CSC540"."JOURNAL_DETAIL" MODIFY ("ISSN_NUMBER" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LIBRARY
--------------------------------------------------------

  ALTER TABLE "CSC540"."LIBRARY" ADD CONSTRAINT "LIBRARY_C01" CHECK (LIBRARY_NAME IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."LIBRARY" ADD CONSTRAINT "LIBRARY_U01" UNIQUE ("LIBRARY_NAME") ENABLE;
  ALTER TABLE "CSC540"."LIBRARY" ADD PRIMARY KEY ("LIBRARY_ID") ENABLE;
  ALTER TABLE "CSC540"."LIBRARY" MODIFY ("LIBRARY_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOGIN_DETAILS
--------------------------------------------------------

  ALTER TABLE "CSC540"."LOGIN_DETAILS" ADD CONSTRAINT "LOGIN_DETAILS_C02" CHECK (PASSWORD IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."LOGIN_DETAILS" ADD CONSTRAINT "LOGIN_DETAILS_C01" CHECK (PATRON_ID IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."LOGIN_DETAILS" ADD CONSTRAINT "LOGIN_DETAILS_U01" UNIQUE ("PATRON_ID") ENABLE;
  ALTER TABLE "CSC540"."LOGIN_DETAILS" ADD PRIMARY KEY ("USERNAME") ENABLE;
  ALTER TABLE "CSC540"."LOGIN_DETAILS" MODIFY ("PATRON_ID" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."LOGIN_DETAILS" MODIFY ("USERNAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PATRON
--------------------------------------------------------

  ALTER TABLE "CSC540"."PATRON" ADD CONSTRAINT "PATRON_C06" CHECK (HOLD IN ('Y','N')) ENABLE;
  ALTER TABLE "CSC540"."PATRON" ADD CONSTRAINT "PATRON_C05" CHECK (DEPARTMENT_ID IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."PATRON" ADD CONSTRAINT "PATRON_C04" CHECK (NATIONALITY IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."PATRON" ADD CONSTRAINT "PATRON_C03" CHECK (FIRST_NAME IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."PATRON" ADD CONSTRAINT "PATRON_U01" UNIQUE ("EMAIL_ADDRESS") ENABLE;
  ALTER TABLE "CSC540"."PATRON" ADD CONSTRAINT "PATRON_C02" CHECK (EMAIL_ADDRESS IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."PATRON" ADD CONSTRAINT "PATRON_C01" CHECK (PATRON_TYPE IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."PATRON" ADD PRIMARY KEY ("PATRON_ID") ENABLE;
  ALTER TABLE "CSC540"."PATRON" MODIFY ("PATRON_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PATRON_TYPE
--------------------------------------------------------

  ALTER TABLE "CSC540"."PATRON_TYPE" ADD CONSTRAINT "PATRON_TYPE_PK" PRIMARY KEY ("PATRON_TYPE_ID") ENABLE;
  ALTER TABLE "CSC540"."PATRON_TYPE" MODIFY ("DESCRIPTION" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."PATRON_TYPE" MODIFY ("PATRON_TYPE_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PUBLICATION
--------------------------------------------------------

  ALTER TABLE "CSC540"."PUBLICATION" ADD CONSTRAINT "PUBLICATION_C01" CHECK (PUBLICATIONFORMAT IN ('Physical copy','Electronic copy')) ENABLE;
  ALTER TABLE "CSC540"."PUBLICATION" ADD PRIMARY KEY ("PUBLICATION_ID") ENABLE;
  ALTER TABLE "CSC540"."PUBLICATION" MODIFY ("PUBLICATION_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PUBLICATION_WAITLIST
--------------------------------------------------------

  ALTER TABLE "CSC540"."PUBLICATION_WAITLIST" ADD PRIMARY KEY ("PUBSECONDARYID", "PATRONID") ENABLE;
  ALTER TABLE "CSC540"."PUBLICATION_WAITLIST" MODIFY ("PATRONID" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."PUBLICATION_WAITLIST" MODIFY ("PUBSECONDARYID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PUBLISHER
--------------------------------------------------------

  ALTER TABLE "CSC540"."PUBLISHER" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CSC540"."PUBLISHER" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table RESERVE_BOOK
--------------------------------------------------------

  ALTER TABLE "CSC540"."RESERVE_BOOK" ADD PRIMARY KEY ("BOOK_ISBN", "COURSE_ID", "FACULTY_ID") ENABLE;
  ALTER TABLE "CSC540"."RESERVE_BOOK" MODIFY ("FACULTY_ID" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."RESERVE_BOOK" MODIFY ("COURSE_ID" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."RESERVE_BOOK" MODIFY ("BOOK_ISBN" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ROOM
--------------------------------------------------------

  ALTER TABLE "CSC540"."ROOM" ADD CONSTRAINT "ROOM_C05" CHECK (CAPACITY BETWEEN 1 AND 25) ENABLE;
  ALTER TABLE "CSC540"."ROOM" ADD CONSTRAINT "ROOM_C04" CHECK (CAPACITY IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."ROOM" ADD CONSTRAINT "ROOM_U01" UNIQUE ("ROOMNO") ENABLE;
  ALTER TABLE "CSC540"."ROOM" ADD CONSTRAINT "ROOM_C03" CHECK (ROOMNO IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."ROOM" ADD CONSTRAINT "ROOM_C02" CHECK (FLOORLEVEL BETWEEN 0 AND 100) ENABLE;
  ALTER TABLE "CSC540"."ROOM" ADD CONSTRAINT "ROOM_C01" CHECK (FLOORLEVEL IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."ROOM" ADD PRIMARY KEY ("ROOM_ID") ENABLE;
  ALTER TABLE "CSC540"."ROOM" MODIFY ("ROOM_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ROOM_RESERVATION
--------------------------------------------------------

  ALTER TABLE "CSC540"."ROOM_RESERVATION" ADD PRIMARY KEY ("RESERVATION_ID") ENABLE;
  ALTER TABLE "CSC540"."ROOM_RESERVATION" MODIFY ("RESERVATION_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SEQUENCE
--------------------------------------------------------

  ALTER TABLE "CSC540"."SEQUENCE" ADD PRIMARY KEY ("SEQ_NAME") ENABLE;
  ALTER TABLE "CSC540"."SEQUENCE" MODIFY ("SEQ_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table STUDENT
--------------------------------------------------------

  ALTER TABLE "CSC540"."STUDENT" ADD CONSTRAINT "STUDENT_C08" CHECK (DEGREEYEAR_DEGREE_YEAR_ID IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."STUDENT" ADD CONSTRAINT "STUDENT_C07" CHECK (PINCODE BETWEEN 1000 AND 999999) ENABLE;
  ALTER TABLE "CSC540"."STUDENT" ADD CONSTRAINT "STUDENT_C06" CHECK (PINCODE IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."STUDENT" ADD CONSTRAINT "STUDENT_C05" CHECK (CITYNAME IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."STUDENT" ADD CONSTRAINT "STUDENT_C04" CHECK (ADDRESSLINEONE IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."STUDENT" ADD CONSTRAINT "STUDENT_C03" CHECK (SEX IN ('M','F')) ENABLE;
  ALTER TABLE "CSC540"."STUDENT" ADD CONSTRAINT "STUDENT_C_SEX" CHECK (SEX IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."STUDENT" ADD CONSTRAINT "STUDENT_C_PHNO" CHECK (PHONE_NO IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."STUDENT" ADD CONSTRAINT "STUDENT_C01" CHECK (DOB IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."STUDENT" ADD PRIMARY KEY ("STUDENT_ID") ENABLE;
  ALTER TABLE "CSC540"."STUDENT" MODIFY ("PHONE_NO" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."STUDENT" MODIFY ("STUDENT_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table STUDY_ROOM
--------------------------------------------------------

  ALTER TABLE "CSC540"."STUDY_ROOM" ADD PRIMARY KEY ("STUDY_ROOM_ID") ENABLE;
  ALTER TABLE "CSC540"."STUDY_ROOM" MODIFY ("STUDY_ROOM_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TEACH
--------------------------------------------------------

  ALTER TABLE "CSC540"."TEACH" ADD PRIMARY KEY ("FACULTY_ID", "COURSE_ID") ENABLE;
  ALTER TABLE "CSC540"."TEACH" MODIFY ("COURSE_ID" NOT NULL ENABLE);
  ALTER TABLE "CSC540"."TEACH" MODIFY ("FACULTY_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table YEAR
--------------------------------------------------------

  ALTER TABLE "CSC540"."YEAR" ADD CONSTRAINT "YEAR_C02" CHECK (LOWER(NAME) IN ('first year','second year','third year','fourth year','fifth year','sixth year')) ENABLE;
  ALTER TABLE "CSC540"."YEAR" ADD CONSTRAINT "YEAR_U01" UNIQUE ("NAME") ENABLE;
  ALTER TABLE "CSC540"."YEAR" ADD CONSTRAINT "YEAR_C01" CHECK (NAME IS NOT NULL) ENABLE;
  ALTER TABLE "CSC540"."YEAR" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CSC540"."YEAR" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ASSET
--------------------------------------------------------

  ALTER TABLE "CSC540"."ASSET" ADD CONSTRAINT "FK_ASSET_LIBRARY_ID" FOREIGN KEY ("LIBRARY_ID")
	  REFERENCES "CSC540"."LIBRARY" ("LIBRARY_ID") ENABLE;
  ALTER TABLE "CSC540"."ASSET" ADD CONSTRAINT "FK_ASSET_TYPE" FOREIGN KEY ("ASSET_TYPE")
	  REFERENCES "CSC540"."ASSET_TYPE" ("ASSETTYPEID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ASSET_CHECKOUT
--------------------------------------------------------

  ALTER TABLE "CSC540"."ASSET_CHECKOUT" ADD CONSTRAINT "ASSET_CHECKOUT_ASSET_ASSET_ID" FOREIGN KEY ("ASSET_ASSET_ID")
	  REFERENCES "CSC540"."ASSET" ("ASSET_ID") ENABLE;
  ALTER TABLE "CSC540"."ASSET_CHECKOUT" ADD CONSTRAINT "FK_ASSET_CHECKOUT_PATRON_ID" FOREIGN KEY ("PATRON_ID")
	  REFERENCES "CSC540"."PATRON" ("PATRON_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ASSET_CHECKOUT_CONSTRAINT
--------------------------------------------------------

  ALTER TABLE "CSC540"."ASSET_CHECKOUT_CONSTRAINT" ADD CONSTRAINT "SSETCHECKOUTCONSTRAINTPATRONID" FOREIGN KEY ("PATRON_ID")
	  REFERENCES "CSC540"."PATRON" ("PATRON_ID") ENABLE;
  ALTER TABLE "CSC540"."ASSET_CHECKOUT_CONSTRAINT" ADD CONSTRAINT "SSTCHCKUTCONSTRAINTSSTCHCKUTID" FOREIGN KEY ("ASSETCHECKOUT_ID")
	  REFERENCES "CSC540"."ASSET_CHECKOUT" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ASSET_PATRON_CONSTRAINT
--------------------------------------------------------

  ALTER TABLE "CSC540"."ASSET_PATRON_CONSTRAINT" ADD CONSTRAINT "ASSET_PATRON_CONSTRAINT_P_TYPE" FOREIGN KEY ("PATRON_TYPE")
	  REFERENCES "CSC540"."PATRON_TYPE" ("PATRON_TYPE_ID") ENABLE;
  ALTER TABLE "CSC540"."ASSET_PATRON_CONSTRAINT" ADD CONSTRAINT "FK_ASSET_P_C_ASSET_TYPE" FOREIGN KEY ("ASSET_TYPE_ID")
	  REFERENCES "CSC540"."ASSET_TYPE" ("ASSETTYPEID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table BOOK
--------------------------------------------------------

  ALTER TABLE "CSC540"."BOOK" ADD CONSTRAINT "FK_BOOK_BOOK_ID" FOREIGN KEY ("BOOK_ID")
	  REFERENCES "CSC540"."ASSET" ("ASSET_ID") ENABLE;
  ALTER TABLE "CSC540"."BOOK" ADD CONSTRAINT "FK_BOOK_ISBN_NUMBER" FOREIGN KEY ("ISBN_NUMBER")
	  REFERENCES "CSC540"."BOOK_DETAIL" ("ISBN_NUMBER") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table BOOK_AUTHOR
--------------------------------------------------------

  ALTER TABLE "CSC540"."BOOK_AUTHOR" ADD CONSTRAINT "FK_BOOK_AUTHOR_AUTHOR_ID" FOREIGN KEY ("AUTHOR_ID")
	  REFERENCES "CSC540"."AUTHOR" ("ID") ENABLE;
  ALTER TABLE "CSC540"."BOOK_AUTHOR" ADD CONSTRAINT "FK_BOOK_AUTHOR_BOOK_ID" FOREIGN KEY ("BOOK_ID")
	  REFERENCES "CSC540"."BOOK_DETAIL" ("ISBN_NUMBER") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table BOOK_DETAIL
--------------------------------------------------------

  ALTER TABLE "CSC540"."BOOK_DETAIL" ADD CONSTRAINT "FK_BOOK_DETAIL_PUBLISHER_ID" FOREIGN KEY ("PUBLISHER_ID")
	  REFERENCES "CSC540"."PUBLISHER" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CAMERA
--------------------------------------------------------

  ALTER TABLE "CSC540"."CAMERA" ADD CONSTRAINT "FK_CAMERA_CAMERA_DETAIL_ID" FOREIGN KEY ("CAMERA_DETAIL_ID")
	  REFERENCES "CSC540"."CAMERA_DETAIL" ("CAMERA_DETAIL_ID") ENABLE;
  ALTER TABLE "CSC540"."CAMERA" ADD CONSTRAINT "FK_CAMERA_CAMERA_ID" FOREIGN KEY ("CAMERA_ID")
	  REFERENCES "CSC540"."ASSET" ("ASSET_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CAMERA_RESERVATION
--------------------------------------------------------

  ALTER TABLE "CSC540"."CAMERA_RESERVATION" ADD CONSTRAINT "CAMERA_RESERVATION_CAMERA_ID" FOREIGN KEY ("CAMERA_ID")
	  REFERENCES "CSC540"."ASSET" ("ASSET_ID") ENABLE;
  ALTER TABLE "CSC540"."CAMERA_RESERVATION" ADD CONSTRAINT "CAMERA_RESERVATION_CHECKOUT_ID" FOREIGN KEY ("CHECKOUT_ID")
	  REFERENCES "CSC540"."ASSET_CHECKOUT" ("ID") ENABLE;
  ALTER TABLE "CSC540"."CAMERA_RESERVATION" ADD CONSTRAINT "CAMERA_RESERVATION_PATRON_ID" FOREIGN KEY ("PATRON_ID")
	  REFERENCES "CSC540"."PATRON" ("PATRON_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CONFERENCE_ROOM
--------------------------------------------------------

  ALTER TABLE "CSC540"."CONFERENCE_ROOM" ADD CONSTRAINT "CONFERENCE_ROOM_CONF_ROOM_ID" FOREIGN KEY ("CONF_ROOM_ID")
	  REFERENCES "CSC540"."ASSET" ("ASSET_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CONF_PROCEEDING
--------------------------------------------------------

  ALTER TABLE "CSC540"."CONF_PROCEEDING" ADD CONSTRAINT "CONF_PROCEEDING_CONF_PROC_ID" FOREIGN KEY ("CONF_PROC_ID")
	  REFERENCES "CSC540"."ASSET" ("ASSET_ID") ENABLE;
  ALTER TABLE "CSC540"."CONF_PROCEEDING" ADD CONSTRAINT "FK_CONF_PROCEEDING_CONF_NUM" FOREIGN KEY ("CONF_NUM")
	  REFERENCES "CSC540"."CONFERENCE_PROCEEDING_DETAIL" ("CONF_NUM") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CONF_PROC_AUTHOR
--------------------------------------------------------

  ALTER TABLE "CSC540"."CONF_PROC_AUTHOR" ADD CONSTRAINT "FK_CONF_PROC_AUTHOR_AUTHOR_ID" FOREIGN KEY ("AUTHOR_ID")
	  REFERENCES "CSC540"."AUTHOR" ("ID") ENABLE;
  ALTER TABLE "CSC540"."CONF_PROC_AUTHOR" ADD CONSTRAINT "FK_CONF_PROC_AUTHOR_CONF_NUM" FOREIGN KEY ("CONF_NUM")
	  REFERENCES "CSC540"."CONFERENCE_PROCEEDING_DETAIL" ("CONF_NUM") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table DEGREE_PROGRAM
--------------------------------------------------------

  ALTER TABLE "CSC540"."DEGREE_PROGRAM" ADD CONSTRAINT "DEGREEPROGRAMCLASSIFICATION_ID" FOREIGN KEY ("CLASSIFICATION_ID")
	  REFERENCES "CSC540"."CLASSIFICATION" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table DEGREE_YEAR
--------------------------------------------------------

  ALTER TABLE "CSC540"."DEGREE_YEAR" ADD CONSTRAINT "DEGREE_YEAR_DEGREE_PROGRAM_ID" FOREIGN KEY ("DEGREE_PROGRAM_ID")
	  REFERENCES "CSC540"."DEGREE_PROGRAM" ("ID") ENABLE;
  ALTER TABLE "CSC540"."DEGREE_YEAR" ADD CONSTRAINT "FK_DEGREE_YEAR_YEAR_ID" FOREIGN KEY ("YEAR_ID")
	  REFERENCES "CSC540"."YEAR" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ENROLL
--------------------------------------------------------

  ALTER TABLE "CSC540"."ENROLL" ADD CONSTRAINT "FK_ENROLL_COURSE_ID" FOREIGN KEY ("COURSE_ID")
	  REFERENCES "CSC540"."COURSE" ("ID") ENABLE;
  ALTER TABLE "CSC540"."ENROLL" ADD CONSTRAINT "FK_ENROLL_STUDENT_ID" FOREIGN KEY ("STUDENT_ID")
	  REFERENCES "CSC540"."STUDENT" ("STUDENT_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table FACULTY
--------------------------------------------------------

  ALTER TABLE "CSC540"."FACULTY" ADD CONSTRAINT "FK_FACULTY_CATEGORY_ID" FOREIGN KEY ("CATEGORY_ID")
	  REFERENCES "CSC540"."FACULTY_CATEGORY" ("ID") ENABLE;
  ALTER TABLE "CSC540"."FACULTY" ADD CONSTRAINT "FK_FACULTY_FACULTY_ID" FOREIGN KEY ("FACULTY_ID")
	  REFERENCES "CSC540"."PATRON" ("PATRON_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table JOURNAL
--------------------------------------------------------

  ALTER TABLE "CSC540"."JOURNAL" ADD CONSTRAINT "FK_JOURNAL_ISSN_NUMBER" FOREIGN KEY ("ISSN_NUMBER")
	  REFERENCES "CSC540"."JOURNAL_DETAIL" ("ISSN_NUMBER") ENABLE;
  ALTER TABLE "CSC540"."JOURNAL" ADD CONSTRAINT "FK_JOURNAL_JOURNAL_ID" FOREIGN KEY ("JOURNAL_ID")
	  REFERENCES "CSC540"."ASSET" ("ASSET_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table JOURNAL_AUTHOR
--------------------------------------------------------

  ALTER TABLE "CSC540"."JOURNAL_AUTHOR" ADD CONSTRAINT "FK_JOURNAL_AUTHOR_AUTHOR_ID" FOREIGN KEY ("AUTHOR_ID")
	  REFERENCES "CSC540"."AUTHOR" ("ID") ENABLE;
  ALTER TABLE "CSC540"."JOURNAL_AUTHOR" ADD CONSTRAINT "FK_JOURNAL_AUTHOR_JOURNAL_ID" FOREIGN KEY ("JOURNAL_ID")
	  REFERENCES "CSC540"."JOURNAL_DETAIL" ("ISSN_NUMBER") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOGIN_DETAILS
--------------------------------------------------------

  ALTER TABLE "CSC540"."LOGIN_DETAILS" ADD CONSTRAINT "FK_LOGIN_DETAILS_PATRON_ID" FOREIGN KEY ("PATRON_ID")
	  REFERENCES "CSC540"."PATRON" ("PATRON_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PATRON
--------------------------------------------------------

  ALTER TABLE "CSC540"."PATRON" ADD CONSTRAINT "FK_PATRON_DEPARTMENT_ID" FOREIGN KEY ("DEPARTMENT_ID")
	  REFERENCES "CSC540"."DEPARTMENT" ("ID") ENABLE;
  ALTER TABLE "CSC540"."PATRON" ADD CONSTRAINT "FK_PATRON_TYPE" FOREIGN KEY ("PATRON_TYPE")
	  REFERENCES "CSC540"."PATRON_TYPE" ("PATRON_TYPE_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PUBLICATION
--------------------------------------------------------

  ALTER TABLE "CSC540"."PUBLICATION" ADD CONSTRAINT "FK_PUBLICATION_PUBLICATION_ID" FOREIGN KEY ("PUBLICATION_ID")
	  REFERENCES "CSC540"."ASSET" ("ASSET_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PUBLICATION_WAITLIST
--------------------------------------------------------

  ALTER TABLE "CSC540"."PUBLICATION_WAITLIST" ADD CONSTRAINT "FK_PW_PATRON_ID" FOREIGN KEY ("PATRONID")
	  REFERENCES "CSC540"."PATRON" ("PATRON_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RESERVE_BOOK
--------------------------------------------------------

  ALTER TABLE "CSC540"."RESERVE_BOOK" ADD CONSTRAINT "FK_RESERVE_BOOK_COURSE_ID" FOREIGN KEY ("COURSE_ID")
	  REFERENCES "CSC540"."COURSE" ("ID") ENABLE;
  ALTER TABLE "CSC540"."RESERVE_BOOK" ADD CONSTRAINT "FK_RESERVE_BOOK_FACULTY_ID" FOREIGN KEY ("FACULTY_ID")
	  REFERENCES "CSC540"."PATRON" ("PATRON_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ROOM
--------------------------------------------------------

  ALTER TABLE "CSC540"."ROOM" ADD CONSTRAINT "FK_ROOM_ROOM_ID" FOREIGN KEY ("ROOM_ID")
	  REFERENCES "CSC540"."ASSET" ("ASSET_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ROOM_RESERVATION
--------------------------------------------------------

  ALTER TABLE "CSC540"."ROOM_RESERVATION" ADD CONSTRAINT "FK_ROOM_RESERVATION_PATRON_ID" FOREIGN KEY ("PATRON_ID")
	  REFERENCES "CSC540"."PATRON" ("PATRON_ID") ENABLE;
  ALTER TABLE "CSC540"."ROOM_RESERVATION" ADD CONSTRAINT "ROOM_RESERVATION_CHECKOUT_ID" FOREIGN KEY ("CHECKOUT_ID")
	  REFERENCES "CSC540"."ASSET_CHECKOUT" ("ID") ENABLE;
  ALTER TABLE "CSC540"."ROOM_RESERVATION" ADD CONSTRAINT "ROOM_RESERVATION_ROOM_ASSET_ID" FOREIGN KEY ("ROOM_ASSET_ID")
	  REFERENCES "CSC540"."ASSET" ("ASSET_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table STUDENT
--------------------------------------------------------

  ALTER TABLE "CSC540"."STUDENT" ADD CONSTRAINT "FK_STUDENT_STUDENT_ID" FOREIGN KEY ("STUDENT_ID")
	  REFERENCES "CSC540"."PATRON" ("PATRON_ID") ENABLE;
  ALTER TABLE "CSC540"."STUDENT" ADD CONSTRAINT "STUDENTDEGREEYEARDEGREEYEAR_ID" FOREIGN KEY ("DEGREEYEAR_DEGREE_YEAR_ID")
	  REFERENCES "CSC540"."DEGREE_YEAR" ("DEGREE_YEAR_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table STUDY_ROOM
--------------------------------------------------------

  ALTER TABLE "CSC540"."STUDY_ROOM" ADD CONSTRAINT "FK_STUDY_ROOM_STUDY_ROOM_ID" FOREIGN KEY ("STUDY_ROOM_ID")
	  REFERENCES "CSC540"."ASSET" ("ASSET_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table TEACH
--------------------------------------------------------

  ALTER TABLE "CSC540"."TEACH" ADD CONSTRAINT "FK_TEACH_COURSE_ID" FOREIGN KEY ("COURSE_ID")
	  REFERENCES "CSC540"."COURSE" ("ID") ENABLE;
  ALTER TABLE "CSC540"."TEACH" ADD CONSTRAINT "FK_TEACH_FACULTY_ID" FOREIGN KEY ("FACULTY_ID")
	  REFERENCES "CSC540"."FACULTY" ("FACULTY_ID") ENABLE;
--------------------------------------------------------
--  DDL for Trigger ASSET_DUE_DATE
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."ASSET_DUE_DATE" 
BEFORE INSERT OR UPDATE
ON ASSET_CHECKOUT
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
cnt INTEGER;
dueDate TIMESTAMP;
atype INTEGER;
BEGIN
    SELECT DURATION,ASSET_TYPE_ID INTO cnt,atype FROM ASSET A, ASSET_PATRON_CONSTRAINT APC, PATRON P
    WHERE A.ASSET_TYPE = APC.ASSET_TYPE_ID AND APC.PATRON_TYPE = P.PATRON_TYPE AND P.PATRON_ID = :new.PATRON_ID
    AND A.ASSET_ID = :new.ASSET_ASSET_ID;
    IF atype = 1 OR atype = 2 OR atype = 3 OR atype = 4 THEN
        dueDate := :new.ISSUE_DATE + (cnt/24);
    ELSIF atype = 5 OR atype = 6 THEN
        IF EXTRACT(HOUR FROM :new.ISSUE_DATE) >= 21 THEN
            dueDate := TO_DATE(TO_CHAR(:new.ISSUE_DATE,'MM/DD/YYYY'),'MM/DD/YYYY')+1;
        ELSE
            SELECT MAX(END_TIME) into dueDate FROM ROOM_RESERVATION WHERE ROOM_ASSET_ID = :new.ASSET_ASSET_ID AND PATRON_ID = :new.PATRON_ID AND START_TIME = :new.ISSUE_DATE;
        END IF;
    ELSIF atype = 7 THEN
           dueDate := NEXT_DAY (TO_DATE(TO_CHAR(:new.ISSUE_DATE,'MM/DD/YYYY'),'MM/DD/YYYY'), 'THU')+(18/24);
    END IF;
    :new.DUE_DATE := dueDate;
END;
/
ALTER TRIGGER "CSC540"."ASSET_DUE_DATE" ENABLE;
--------------------------------------------------------
--  DDL for Trigger DISJOINT_ASSET_01
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."DISJOINT_ASSET_01" 
BEFORE INSERT OR UPDATE
ON BOOK
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
study INTEGER;
confR INTEGER;
books INTEGER;
journals INTEGER;
cps INTEGER;
cameras INTEGER;
cnt INTEGER;
atid INTEGER;
BEGIN
        books:=0;
        SELECT COUNT(*) INTO cameras FROM CAMERA WHERE CAMERA_ID = :new.BOOK_ID;
        SELECT COUNT(*) INTO journals FROM JOURNAL WHERE JOURNAL_ID = :new.BOOK_ID;
        SELECT COUNT(*) INTO cps FROM CONF_PROCEEDING WHERE CONF_PROC_ID = :new.BOOK_ID;
        SELECT COUNT(*) INTO study FROM STUDY_ROOM WHERE STUDY_ROOM_ID = :new.BOOK_ID;
        SELECT COUNT(*) INTO confR FROM CONFERENCE_ROOM WHERE CONF_ROOM_ID = :new.BOOK_ID;
        cnt := study + confR + books + journals + cps + cameras;
        IF cnt > 0 THEN
            RAISE_APPLICATION_ERROR(-20015, 'ASSET CAN NOT FALL IN MULTIPLE CATEGORY');
        ELSE
            SELECT ASSETTYPEID INTO atid FROM ASSET_TYPE WHERE UPPER(SUB_CATEGORY) = 'BOOK';
            UPDATE ASSET SET ASSET_TYPE = atid WHERE ASSET_ID = :new.BOOK_ID;
        END IF;
END;
/
ALTER TRIGGER "CSC540"."DISJOINT_ASSET_01" ENABLE;
--------------------------------------------------------
--  DDL for Trigger DISJOINT_ASSET_02
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."DISJOINT_ASSET_02" 
BEFORE INSERT OR UPDATE
ON CAMERA
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
study INTEGER;
confR INTEGER;
books INTEGER;
journals INTEGER;
cps INTEGER;
cameras INTEGER;
cnt INTEGER;
atid INTEGER;
BEGIN
        SELECT COUNT(*) INTO books FROM BOOK WHERE BOOK_ID = :new.CAMERA_ID;
        cameras :=0;
        SELECT COUNT(*) INTO journals FROM JOURNAL WHERE JOURNAL_ID = :new.CAMERA_ID;
        SELECT COUNT(*) INTO cps FROM CONF_PROCEEDING WHERE CONF_PROC_ID = :new.CAMERA_ID;
        SELECT COUNT(*) INTO study FROM STUDY_ROOM WHERE STUDY_ROOM_ID = :new.CAMERA_ID;
        SELECT COUNT(*) INTO confR FROM CONFERENCE_ROOM WHERE CONF_ROOM_ID = :new.CAMERA_ID;
        cnt := study + confR + books + journals + cps + cameras;
        IF cnt > 0 THEN
            RAISE_APPLICATION_ERROR(-20015, 'ASSET CAN NOT FALL IN MULTIPLE CATEGORY');
        ELSE
            SELECT ASSETTYPEID INTO atid FROM ASSET_TYPE WHERE UPPER(SUB_CATEGORY) = 'CAMERA';
            UPDATE ASSET SET ASSET_TYPE = atid WHERE ASSET_ID = :new.CAMERA_ID;
        END IF;
END;
/
ALTER TRIGGER "CSC540"."DISJOINT_ASSET_02" ENABLE;
--------------------------------------------------------
--  DDL for Trigger DISJOINT_ASSET_03
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."DISJOINT_ASSET_03" 
BEFORE INSERT OR UPDATE
ON JOURNAL
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
study INTEGER;
confR INTEGER;
books INTEGER;
journals INTEGER;
cps INTEGER;
cameras INTEGER;
cnt INTEGER;
atid INTEGER;
BEGIN
        SELECT COUNT(*) INTO books FROM BOOK WHERE BOOK_ID = :new.JOURNAL_ID;
        SELECT COUNT(*) INTO cameras FROM CAMERA WHERE CAMERA_ID = :new.JOURNAL_ID;
        journals:=0;
        SELECT COUNT(*) INTO cps FROM CONF_PROCEEDING WHERE CONF_PROC_ID = :new.JOURNAL_ID;
        SELECT COUNT(*) INTO study FROM STUDY_ROOM WHERE STUDY_ROOM_ID = :new.JOURNAL_ID;
        SELECT COUNT(*) INTO confR FROM CONFERENCE_ROOM WHERE CONF_ROOM_ID = :new.JOURNAL_ID;
        cnt := study + confR + books + journals + cps + cameras;
        IF cnt > 0 THEN
            RAISE_APPLICATION_ERROR(-20015, 'ASSET CAN NOT FALL IN MULTIPLE CATEGORY');
        ELSE
            SELECT ASSETTYPEID INTO atid FROM ASSET_TYPE WHERE UPPER(SUB_CATEGORY) = 'JOURNAL';
            UPDATE ASSET SET ASSET_TYPE = atid WHERE ASSET_ID = :new.JOURNAL_ID;
        END IF;
END;
/
ALTER TRIGGER "CSC540"."DISJOINT_ASSET_03" ENABLE;
--------------------------------------------------------
--  DDL for Trigger DISJOINT_ASSET_04
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."DISJOINT_ASSET_04" 
BEFORE INSERT OR UPDATE
ON CONF_PROCEEDING
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
study INTEGER;
confR INTEGER;
books INTEGER;
journals INTEGER;
cps INTEGER;
cameras INTEGER;
cnt INTEGER;
atid INTEGER;
BEGIN
        SELECT COUNT(*) INTO books FROM BOOK WHERE BOOK_ID = :new.CONF_PROC_ID;
        SELECT COUNT(*) INTO cameras FROM CAMERA WHERE CAMERA_ID = :new.CONF_PROC_ID;
        SELECT COUNT(*) INTO journals FROM JOURNAL WHERE JOURNAL_ID = :new.CONF_PROC_ID;
        cps:=0;
        SELECT COUNT(*) INTO study FROM STUDY_ROOM WHERE STUDY_ROOM_ID = :new.CONF_PROC_ID;
        SELECT COUNT(*) INTO confR FROM CONFERENCE_ROOM WHERE CONF_ROOM_ID = :new.CONF_PROC_ID;
        cnt := study + confR + books + journals + cps + cameras;
        IF cnt > 0 THEN
            RAISE_APPLICATION_ERROR(-20015, 'ASSET CAN NOT FALL IN MULTIPLE CATEGORY');
        ELSE
            SELECT ASSETTYPEID INTO atid FROM ASSET_TYPE WHERE UPPER(SUB_CATEGORY) = 'CONFERENCEPROCEEDING';
            UPDATE ASSET SET ASSET_TYPE = atid WHERE ASSET_ID = :new.CONF_PROC_ID;
        END IF;
END;
/
ALTER TRIGGER "CSC540"."DISJOINT_ASSET_04" ENABLE;
--------------------------------------------------------
--  DDL for Trigger DISJOINT_ASSET_05
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."DISJOINT_ASSET_05" 
BEFORE INSERT OR UPDATE
ON STUDY_ROOM
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
study INTEGER;
confR INTEGER;
books INTEGER;
journals INTEGER;
cps INTEGER;
cameras INTEGER;
cnt INTEGER;
atid INTEGER;
BEGIN
        SELECT COUNT(*) INTO books FROM BOOK WHERE BOOK_ID = :new.STUDY_ROOM_ID;
        SELECT COUNT(*) INTO cameras FROM CAMERA WHERE CAMERA_ID = :new.STUDY_ROOM_ID;
        SELECT COUNT(*) INTO journals FROM JOURNAL WHERE JOURNAL_ID = :new.STUDY_ROOM_ID;
        SELECT COUNT(*) INTO cps FROM CONF_PROCEEDING WHERE CONF_PROC_ID = :new.STUDY_ROOM_ID;
        study:=0;
        SELECT COUNT(*) INTO confR FROM CONFERENCE_ROOM WHERE CONF_ROOM_ID = :new.STUDY_ROOM_ID;
        cnt := study + confR + books + journals + cps + cameras;
        IF cnt > 0 THEN
            RAISE_APPLICATION_ERROR(-20015, 'ASSET CAN NOT FALL IN MULTIPLE CATEGORY');
        ELSE
            SELECT ASSETTYPEID INTO atid FROM ASSET_TYPE WHERE UPPER(SUB_CATEGORY) = 'STUDY ROOM';
            UPDATE ASSET SET ASSET_TYPE = atid WHERE ASSET_ID = :new.STUDY_ROOM_ID;
        END IF;
END;
/
ALTER TRIGGER "CSC540"."DISJOINT_ASSET_05" ENABLE;
--------------------------------------------------------
--  DDL for Trigger DISJOINT_ASSET_06
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."DISJOINT_ASSET_06" 
BEFORE INSERT OR UPDATE
ON CONFERENCE_ROOM
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
study INTEGER;
confR INTEGER;
books INTEGER;
journals INTEGER;
cps INTEGER;
cameras INTEGER;
cnt INTEGER;
atid INTEGER;
BEGIN
        SELECT COUNT(*) INTO books FROM BOOK WHERE BOOK_ID = :new.CONF_ROOM_ID;
        SELECT COUNT(*) INTO cameras FROM CAMERA WHERE CAMERA_ID = :new.CONF_ROOM_ID;
        SELECT COUNT(*) INTO journals FROM JOURNAL WHERE JOURNAL_ID = :new.CONF_ROOM_ID;
        SELECT COUNT(*) INTO cps FROM CONF_PROCEEDING WHERE CONF_PROC_ID = :new.CONF_ROOM_ID;
        SELECT COUNT(*) INTO study FROM STUDY_ROOM WHERE STUDY_ROOM_ID = :new.CONF_ROOM_ID;
        confR:=0;
        cnt := study + confR + books + journals + cps + cameras;
        IF cnt > 0 THEN
            RAISE_APPLICATION_ERROR(-20015, 'ASSET CAN NOT FALL IN MULTIPLE CATEGORY');
        ELSE
            SELECT ASSETTYPEID INTO atid FROM ASSET_TYPE WHERE UPPER(SUB_CATEGORY) = 'CONFERENCE ROOM';
            UPDATE ASSET SET ASSET_TYPE = atid WHERE ASSET_ID = :new.CONF_ROOM_ID;
        END IF;
END;
/
ALTER TRIGGER "CSC540"."DISJOINT_ASSET_06" ENABLE;
--------------------------------------------------------
--  DDL for Trigger LOGIN_PASSWORD
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."LOGIN_PASSWORD" 
BEFORE INSERT OR UPDATE
OF PASSWORD
ON LOGIN_DETAILS
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN
  :new.PASSWORD := MY_HASH(:new.PASSWORD, 100000, 57643);
END;
/
ALTER TRIGGER "CSC540"."LOGIN_PASSWORD" ENABLE;
--------------------------------------------------------
--  DDL for Trigger NO_CONF_ROOM_HILL
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."NO_CONF_ROOM_HILL" 
BEFORE INSERT OR UPDATE
ON ASSET
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
cnt INTEGER;
BEGIN
        SELECT COUNT(*) INTO cnt FROM LIBRARY LIB, ASSET_TYPE AST WHERE AST.ASSETTYPEID = :new.ASSET_TYPE AND AST.SUB_CATEGORY = 'Conference Room' AND UPPER(LIB.LIBRARY_NAME) LIKE '%D%H%HILL%' AND LIB.LIBRARY_ID = :new.LIBRARY_ID;
        IF cnt > 0 THEN
            RAISE_APPLICATION_ERROR(-20013, 'NO_CNF_ROOM_IN_HILL');
        END IF;
END;
/
ALTER TRIGGER "CSC540"."NO_CONF_ROOM_HILL" ENABLE;
--------------------------------------------------------
--  DDL for Trigger NO_CONF_ROOM_STUDENT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."NO_CONF_ROOM_STUDENT" 
BEFORE INSERT OR UPDATE
ON ROOM_RESERVATION
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
cnt INTEGER;
BEGIN
        SELECT COUNT(*) INTO cnt FROM PATRON PT, ASSET_TYPE AST, ASSET ASS, PATRON_TYPE PTT WHERE ASS.ASSET_ID = :new.ROOM_ASSET_ID AND AST.ASSETTYPEID = ASS.ASSET_TYPE AND AST.SUB_CATEGORY = 'Conference Room' AND PT.PATRON_ID = :new.PATRON_ID AND PT.PATRON_TYPE = PTT.PATRON_TYPE_ID AND PTT.DESCRIPTION = 'Student' ;
        IF cnt > 0 THEN
            RAISE_APPLICATION_ERROR(-20013, 'NO_CONF_ROOM_STUDENT');
        END IF;
END;
/
ALTER TRIGGER "CSC540"."NO_CONF_ROOM_STUDENT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger NO_FACULTY_STUDENT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."NO_FACULTY_STUDENT" 
BEFORE INSERT OR UPDATE
ON FACULTY
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
cnt INTEGER;
BEGIN
        SELECT COUNT(*) INTO cnt FROM STUDENT WHERE STUDENT_ID = :new.FACULTY_ID;
        IF cnt > 0 THEN
            RAISE_APPLICATION_ERROR(-20015, 'STUDENT_AND_FACULTY_ARE_DISJOINT');
        END IF;
END;
/
ALTER TRIGGER "CSC540"."NO_FACULTY_STUDENT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger NO_RES_BOOK_FACULTY
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."NO_RES_BOOK_FACULTY" 
BEFORE INSERT OR UPDATE
ON ASSET_CHECKOUT
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
cnt INTEGER;
BEGIN
        SELECT COUNT(*) INTO cnt FROM PATRON PT, ASSET_TYPE AST, ASSET ASS, PATRON_TYPE PTT WHERE ASS.ASSET_ID = :new.ASSET_ASSET_ID AND AST.ASSETTYPEID = ASS.ASSET_TYPE AND AST.SUB_CATEGORY = 'Reserve Book' AND PT.PATRON_ID = :new.PATRON_ID AND PT.PATRON_TYPE = PTT.PATRON_TYPE_ID AND PTT.DESCRIPTION = 'Faculty' ;
        IF cnt > 0 THEN
            RAISE_APPLICATION_ERROR(-20013, 'NO_RESERVE_BOOK_FACULTY');
        END IF;
END;
/
ALTER TRIGGER "CSC540"."NO_RES_BOOK_FACULTY" ENABLE;
--------------------------------------------------------
--  DDL for Trigger NO_STUDENT_FACULTY
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."NO_STUDENT_FACULTY" 
BEFORE INSERT OR UPDATE
ON STUDENT
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
cnt INTEGER;
BEGIN
        SELECT COUNT(*) INTO cnt FROM FACULTY WHERE FACULTY_ID = :new.STUDENT_ID;
        IF cnt > 0 THEN
            RAISE_APPLICATION_ERROR(-20014, 'STUDENT_AND_FACULTY_ARE_DISJOINT');
        END IF;
END;
/
ALTER TRIGGER "CSC540"."NO_STUDENT_FACULTY" ENABLE;
--------------------------------------------------------
--  DDL for Trigger T_RESERVER_BOOK
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."T_RESERVER_BOOK" 
AFTER INSERT
ON  RESERVE_BOOK
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
atid INTEGER;
BEGIN
    SELECT ASSETTYPEID INTO atid FROM ASSET_TYPE WHERE UPPER(SUB_CATEGORY) = 'RESERVED BOOK';
    UPDATE ASSET set ASSET_TYPE = atid WHERE ASSET_ID IN (SELECT BOOK_ID FROM BOOK WHERE ISBN_NUMBER = :new.BOOK_ISBN);
END;
/
ALTER TRIGGER "CSC540"."T_RESERVER_BOOK" ENABLE;
--------------------------------------------------------
--  DDL for Trigger UPD_ASSET_CK_CSTRN
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CSC540"."UPD_ASSET_CK_CSTRN" 
AFTER INSERT OR UPDATE
OF RETURN_DATE
ON ASSET_CHECKOUT
REFERENCING NEW AS new OLD AS old
FOR EACH ROW
DECLARE
BEGIN
      IF UPDATING THEN
        IF :new.RETURN_DATE IS NOT NULL THEN
            DELETE FROM ASSET_CHECKOUT_CONSTRAINT ACC WHERE ACC.ASSETCHECKOUT_ID = :new.ID;
        END IF;
      ELSIF INSERTING THEN
        IF :new.RETURN_DATE IS NULL AND :new.ASSET_SECONDARY_ID IS NOT NULL THEN
            INSERT INTO ASSET_CHECKOUT_CONSTRAINT(ASSETCHECKOUT_ID, PATRON_ID, ASSET_SECONDARY_ID) VALUES (:new.ID, :new.PATRON_ID, :new.ASSET_SECONDARY_ID);
        END IF;
      END IF;
END;
/
ALTER TRIGGER "CSC540"."UPD_ASSET_CK_CSTRN" ENABLE;
--------------------------------------------------------
--  DDL for Function FINE_FOR_PATRON
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "CSC540"."FINE_FOR_PATRON" (P_ID IN VARCHAR2) RETURN NUMBER IS
  RETVAL NUMBER;
BEGIN
  SELECT NVL(SUM(FINE_AMOUNT),0) INTO RETVAL FROM FINE_SNAPSHOT WHERE PATRON_ID = P_ID;
  RETURN RETVAL;
END;

/
--------------------------------------------------------
--  DDL for Function MY_HASH
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "CSC540"."MY_HASH" (X IN VARCHAR2, y IN NUMBER, z IN NUMBER) RETURN NUMBER IS
  RETVAL NUMBER;
BEGIN
  SELECT ORA_HASH(X,y,z) INTO RETVAL FROM DUAL;
  RETURN RETVAL;
END;

/
--------------------------------------------------------
--  DDL for Function TS_DIFF_IN_HRS
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "CSC540"."TS_DIFF_IN_HRS" (END_TIME IN TIMESTAMP, START_TIME IN TIMESTAMP, DO_ROUND IN INTEGER) RETURN NUMBER IS
  RETVAL NUMBER;
BEGIN
  IF DO_ROUND < 0 THEN
  SELECT FLOOR(EXTRACT (DAY FROM (END_TIME-START_TIME))*24 + EXTRACT (HOUR FROM (END_TIME-START_TIME))) DELTA INTO RETVAL FROM DUAL;
  ELSIF DO_ROUND > 0 THEN
  SELECT CEIL(EXTRACT (DAY FROM (END_TIME-START_TIME))*24 + EXTRACT (HOUR FROM (END_TIME-START_TIME))) DELTA INTO RETVAL FROM DUAL;
  ELSE
  SELECT EXTRACT (DAY    FROM (END_TIME-START_TIME))*24  + EXTRACT (HOUR FROM (END_TIME-START_TIME)) DELTA INTO RETVAL FROM DUAL;
  END IF;
  RETURN RETVAL;
END;

/
