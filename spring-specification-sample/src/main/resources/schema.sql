create table RULE_ENTITY (
    ID integer identity primary key,
    INDEX integer /*NOT NULL*/,
    KEY varchar(99),
    DESCRIPTION varchar(99),
    PARENT_ID integer references RULE_ENTITY(ID)
);
create table PARAMETER_ENTITY (
    ID integer identity primary key,
    RULE_ID integer not null references RULE_ENTITY(ID),
    KEY varchar(99),
    VALUE varchar(99),
    TYPE varchar(99),
    unique(RULE_ID, KEY)
);