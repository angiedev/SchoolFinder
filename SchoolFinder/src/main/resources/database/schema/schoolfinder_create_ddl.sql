# DB Schema Creation Script for School Finder Web Service

CREATE TABLE District( district_id BIGINT(20) NOT NULL auto_increment,
	lea_id VARCHAR(7) NOT NULL,
	name VARCHAR(120) NOT NULL,
	PRIMARY KEY (district_id),
	UNIQUE INDEX (lea_id)
) ENGINE=INNODB;

CREATE TABLE School ( school_id BIGINT(20) NOT NULL auto_increment, 
    nces_id VARCHAR(12) NOT NULL,
	name VARCHAR(200) NOT NULL,
	district_id BIGINT(20) NOT NULL,
	street_address VARCHAR(100) NOT NULL,
	city VARCHAR(40) NOT NULL,
	state VARCHAR(2) NOT NULL,
	zip VARCHAR(12) NOT NULL,
	status int NOT NULL,
	low_grade VARCHAR(2) NOT NULL,
	high_grade VARCHAR(2) NOT NULL,
	longitude FLOAT(10,6),
	latitude FLOAT(10,6),
	PRIMARY KEY (school_id),
	FOREIGN KEY (district_id) REFERENCES District(district_id),
	UNIQUE INDEX (nces_id)
) ENGINE=INNODB;
