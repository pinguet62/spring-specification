insert into RULE_ENTITY (ID, KEY) values (1, 'and');
    insert into RULE_ENTITY (ID, KEY, PARENT) values (11, 'hasColorRule', 1);
           insert into PARAMETER_ENTITY (ID, RULE_ID, KEY, VALUE, TYPE) values (111, 11, 'color', 'black', 'java.lang.String');
    insert into RULE_ENTITY (ID, KEY, PARENT) values (12, 'or', 1);
		insert into RULE_ENTITY (ID, KEY, PARENT) values (121, 'onlyWeekendRule', 12);
        insert into RULE_ENTITY (ID, KEY, PARENT) values (122, 'priceGreaterThanRule', 12);
                insert into PARAMETER_ENTITY (ID, RULE_ID, KEY, VALUE, TYPE) values (1221, 122, 'amount', '100.00', 'java.lang.Double');