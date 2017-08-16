create table RULE_COMPONENT_ENTITY (
    ID integer identity primary key,
    PARENT_ID integer references RULE_COMPONENT_ENTITY(ID),
    INDEX integer /*NOT NULL*/,
    KEY varchar(99),
    DESCRIPTION varchar(99)
);
create table PARAMETER_ENTITY (
    ID integer identity primary key,
    RULE_ID integer not null references RULE_COMPONENT_ENTITY(ID),
    KEY varchar(99),
    VALUE varchar(99),
    TYPE varchar(99),
    unique(RULE_ID, KEY)
);
create table BUSINESS_RULE_ENTITY (
    ID varchar(99) primary key,
    ROOT_RULE_COMPONENT_ID integer references RULE_COMPONENT_ENTITY(ID),
    TITLE varchar(99)
);