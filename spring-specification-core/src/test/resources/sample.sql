insert into RULE_ENTITY (id, key) values (1, 'and');
    insert into RULE_ENTITY (id, key, parent) values (11, 'hasColorRule', 1);
           insert into PARAMETER_ENTITY (id, rule_id, key, value, type) values ('11_p1', 11, 'color', 'black', 'java.lang.String');
    insert into RULE_ENTITY (id, key, parent) values (12, 'or', 1);
		insert into RULE_ENTITY (id, key, parent) values (121, 'onlyWeekendRule', 12);
        insert into RULE_ENTITY (id, key, parent) values (122, 'priceGreaterThanRule', 12);
                insert into PARAMETER_ENTITY (id, rule_id, key, value, type) values ('122_p1', 122, 'amount', '100.00', 'java.lang.Double');