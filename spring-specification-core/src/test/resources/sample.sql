    insert into RULE_COMPONENT_ENTITY (id, key) values (1, 'fr.pinguet62.springruleengine.core.api.AndRule');
        insert into RULE_COMPONENT_ENTITY (id, key, parent_id) values (11, 'fr.pinguet62.springruleengine.core.api.NotRule', 1);
            insert into RULE_COMPONENT_ENTITY (id, key, parent_id) values (111, 'fr.pinguet62.springruleengine.core.builder.database.DatabaseRuleBuilderTest$TestRules$FirstCustomRule', 11);
                    insert into PARAMETER_ENTITY (id, rule_id, key, value) values (1111, 111, '111_k1', '111_v1');
                    insert into PARAMETER_ENTITY (id, rule_id, key, value) values (1112, 111, '111_k2', '111_v2');
        insert into RULE_COMPONENT_ENTITY (id, key, parent_id) values (12, 'fr.pinguet62.springruleengine.core.api.OrRule', 1);
            insert into RULE_COMPONENT_ENTITY (id, key, parent_id) values (121, 'fr.pinguet62.springruleengine.core.builder.database.DatabaseRuleBuilderTest$TestRules$SecondCustomRule', 12);
            insert into RULE_COMPONENT_ENTITY (id, key, parent_id) values (122, 'fr.pinguet62.springruleengine.core.builder.database.DatabaseRuleBuilderTest$TestRules$ThirdCustomRule', 12);
                    insert into PARAMETER_ENTITY (id, rule_id, key, value) values (1221, 122, '122_k1', '122_v1');
insert into BUSINESS_RULE_ENTITY (id, argument_type, root_rule_component_id, title) values ('test', 'fr.pinguet62.springruleengine.sample.model.Product', 1, null);