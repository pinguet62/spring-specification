insert into RULE_ENTITY (id, key) values (1, 'andRule');
    insert into RULE_ENTITY (id, key, parent_id) values (11, 'notRule', 1);
        insert into RULE_ENTITY (id, key, parent_id) values (111, 'firstCustomRule', 11);
                insert into PARAMETER_ENTITY (id, rule_id, key, value, type) values (1111, 111, '111_k1', '111_v1', 'java.lang.String');
                insert into PARAMETER_ENTITY (id, rule_id, key, value, type) values (1112, 111, '111_k2', '111_v2', 'java.lang.String');
    insert into RULE_ENTITY (id, key, parent_id) values (12, 'orRule', 1);
        insert into RULE_ENTITY (id, key, parent_id) values (121, 'secondCustomRule', 12);
        insert into RULE_ENTITY (id, key, parent_id) values (122, 'thirdCustomRule', 12);
                insert into PARAMETER_ENTITY (id, rule_id, key, value, type) values (1221, 122, '122_k1', '122_v1', 'java.lang.String');