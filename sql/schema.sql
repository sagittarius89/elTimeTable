CREATE TABLE AD_CONFIGURATION (
	"KEY" varchar(20) NOT NULL UNIQUE, 
	"VALUE" varchar(255)
);

CREATE TABLE AD_USER (
	ID  SERIAL UNIQUE NOT NULL, 
	USERNAME varchar(100) NOT NULL, 
	NAME varchar(100), 
	SURNAME varchar(100), 
	PASSWORD varchar(100) NOT NULL, 
	PRIMARY KEY (ID)
);

CREATE TABLE TT_TASK (
	ID  SERIAL UNIQUE NOT NULL, 
	NAME varchar(100) NOT NULL,
	DESCRIPTION varchar(1000),
	USER_ID int4 NOT NULL, 
	PRIOR int4,
	EXECUTION_TIME timestamp, 
	DURATION bigint,
	COLOR varchar(8),
	PROGRESS int4,
	EXP int4,
	NOTES varchar(10000),
	STATUS int4,
	CREATION_TIME timestamp,
	PRIMARY KEY (ID)
);

CREATE TABLE TT_PARENT_TASKS (
	PARENT_ID int4,
	CHILD_ID int4
);

-- 2016-03-07

ALTER TABLE TT_TASK ADD COLUMN DONE_TIME TIMESTAMP;

-- 2016-03-17

CREATE TABLE TT_TAB (
	ID  SERIAL UNIQUE NOT NULL, 
	NAME varchar(20) NOT NULL,
	TORDER int4 NOT NULL,
	OWNER int4 NOT NULL,
	IS_DEFAULT BOOLEAN DEFAULT FALSE
);

ALTER TABLE TT_TASK ADD COLUMN TAB_ID int4 DEFAULT NULL;

ALTER TABLE TT_TASK 
	ADD CONSTRAINT TT_TSK_TAB_FK 
	FOREIGN KEY (TAB_ID) 
	REFERENCES TT_TAB (ID);

ALTER TABLE TT_TASK 
	ADD CONSTRAINT TT_TSK_USER_FK 
	FOREIGN KEY (USER_ID) 
	REFERENCES AD_USER (ID);

ALTER TABLE TT_TAB 
	ADD CONSTRAINT TT_TAB_USER_FK 
	FOREIGN KEY (OWNER) 
	REFERENCES AD_USER (ID);
