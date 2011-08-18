--postgresql locale "Chinese_People's Republic of China.936"

CREATE TABLE Users
(
  id bigint NOT NULL,
  userLogin CHARACTER VARYING(50) NOT NULL,
  userName CHARACTER VARYING(100) NOT NULL,
  password CHARACTER VARYING(100) NOT NULL,
  creationDate TIMESTAMP WITH TIME ZONE NOT NULL,
  version bigint NOT NULL,
  CONSTRAINT Users_pkey PRIMARY KEY (id),
  CONSTRAINT unique_userlogin UNIQUE (userlogin)
);

CREATE TABLE Roles
(
  id bigint NOT NULL,
  name CHARACTER VARYING(100) NOT NULL,
  description CHARACTER VARYING(1000) NULL,
  creationDate TIMESTAMP WITH TIME ZONE NOT NULL,
  version bigint NOT NULL,
  CONSTRAINT Roles_pkey PRIMARY KEY (id),
  CONSTRAINT unique_rolename UNIQUE (name)
);

CREATE TABLE user_role
(
  userid bigint,
  roleid bigint,
  CONSTRAINT user_fkey FOREIGN KEY (userid)
      REFERENCES Users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT role_fkey FOREIGN KEY (roleid)
      REFERENCES Roles (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE Purpose
(
  id bigint NOT NULL,
  name CHARACTER VARYING(200),
  description CHARACTER VARYING(1000),
  active BOOLEAN NOT NULL DEFAULT FALSE,
  version bigint NOT NULL,
  creationDate TIMESTAMP WITH TIME ZONE NOT NULL,
  CONSTRAINT Purpose_pkey PRIMARY KEY(id)
);

CREATE TABLE ExpenseReport
(
  id bigint NOT NULL,
  title CHARACTER VARYING(200) NOT NULL,
  amount DOUBLE PRECISION,
  creationDate TIMESTAMP WITH TIME ZONE NOT NULL,
  version bigint NOT NULL,
  status SMALLINT NOT NULL,
  purposeId bigint,
  userId bigint,
  CONSTRAINT ExpenseReport_pkey PRIMARY KEY (id),
  CONSTRAINT ExpenseReport_fkey1 FOREIGN KEY (purposeId)
      REFERENCES Purpose (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ExpenseReport_fkey2 FOREIGN KEY (userId)
      REFERENCES Users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE LineItem
(
  id bigint NOT NULL,
  amount INTEGER,
  description CHARACTER VARYING(1000),
  CONSTRAINT LineItem_pkey PRIMARY KEY (id)
);

CREATE TABLE ExpenseType
(
  id bigint NOT NULL,
  name CHARACTER VARYING(100),
  description CHARACTER VARYING(1000),
  CONSTRAINT ExpenseType_pkey PRIMARY KEY(id)
);

CREATE TABLE Department
(
  id bigint NOT NULL,
  parentId bigint,
  name CHARACTER VARYING(100) NOT NULL,
  code CHARACTER VARYING(20) NOT NULL,
  description CHARACTER VARYING(1000),
  managerId bigint,
  version bigint NOT NULL,
  creationDate TIMESTAMP WITH TIME ZONE NOT NULL,
  CONSTRAINT Department_pkey PRIMARY KEY(id),
  CONSTRAINT Department_parentId FOREIGN KEY(parentId)
      REFERENCES Department (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT Department_managerId FOREIGN KEY(managerId)
      REFERENCES Users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE Department_User
(
  departmentid bigint NOT NULL,
  userid bigint NOT NULL
);

INSERT INTO Users(id, userLogin, userName, password, creationDate, version) VALUES(-1, 'admin', 'Adm', 'E44E172A573F39F4323695FF0651B342333B8461', @#CURRENT_TIMESTAMP#@, 0);
INSERT INTO Roles(id,           name,    description, creationDate, version) 
           VALUES(-2, 'admin', '系统管理', @#CURRENT_TIMESTAMP#@, 0);
INSERT INTO user_role(userid, roleid) VALUES((SELECT id FROM Users WHERE userLogin='admin'), (SELECT id FROM Roles WHERE name='admin'));
