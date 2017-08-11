insert into RULE_ENTITY (id, key) values (1, 'fr.pinguet62.springruleengine.core.api.AndRule');
    insert into RULE_ENTITY (id, key, parent_id) values (11, 'fr.pinguet62.springruleengine.core.api.NotRule', 1);
        insert into RULE_ENTITY (id, key, parent_id) values (111, 'fr.pinguet62.springruleengine.core.builder.database.DatabaseRuleBuilderTest$TestRules$FirstCustomRule', 11);
                insert into PARAMETER_ENTITY (id, rule_id, key, value, type) values (1111, 111, '111_k1', '111_v1', 'java.lang.String');
                insert into PARAMETER_ENTITY (id, rule_id, key, value, type) values (1112, 111, '111_k2', '111_v2', 'java.lang.String');
    insert into RULE_ENTITY (id, key, parent_id) values (12, 'fr.pinguet62.springruleengine.core.api.OrRule', 1);
        insert into RULE_ENTITY (id, key, parent_id) values (121, 'fr.pinguet62.springruleengine.core.builder.database.DatabaseRuleBuilderTest$TestRules$SecondCustomRule', 12);
        insert into RULE_ENTITY (id, key, parent_id) values (122, 'fr.pinguet62.springruleengine.core.builder.database.DatabaseRuleBuilderTest$TestRules$ThirdCustomRule', 12);
                insert into PARAMETER_ENTITY (id, rule_id, key, value, type) values (1221, 122, '122_k1', '122_v1', 'java.lang.String');