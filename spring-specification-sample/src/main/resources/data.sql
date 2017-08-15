    insert into RULE_COMPONENT_ENTITY (ID, INDEX, KEY, DESCRIPTION, PARENT_ID) values (1, 0, 'fr.pinguet62.springruleengine.core.api.AndRule', null, null);
        insert into RULE_COMPONENT_ENTITY (ID, INDEX, KEY, DESCRIPTION, PARENT_ID) values (11, 0, 'fr.pinguet62.springruleengine.sample.rule.HasColorRule', null, 1);
               insert into PARAMETER_ENTITY (ID, RULE_ID, KEY, VALUE, TYPE) values (111, 11, 'color', 'black', 'java.lang.String');
               insert into PARAMETER_ENTITY (ID, RULE_ID, KEY, VALUE, TYPE) values (112, 11, 'a_key', 'a_value', 'java.lang.Integer');
               insert into PARAMETER_ENTITY (ID, RULE_ID, KEY, VALUE, TYPE) values (113, 11, 'b_key', 'b_value', 'java.lang.Integer');
               insert into PARAMETER_ENTITY (ID, RULE_ID, KEY, VALUE, TYPE) values (114, 11, 'c_key', 'c_value', 'java.lang.Integer');
        insert into RULE_COMPONENT_ENTITY (ID, INDEX, KEY, DESCRIPTION, PARENT_ID) values (12, 1, 'fr.pinguet62.springruleengine.core.api.OrRule', '', 1);
            insert into RULE_COMPONENT_ENTITY (ID, INDEX, KEY, DESCRIPTION, PARENT_ID) values (121, 0, 'fr.pinguet62.springruleengine.sample.rule.OnlyWeekendRule', '.', 12);
            insert into RULE_COMPONENT_ENTITY (ID, INDEX, KEY, DESCRIPTION, PARENT_ID) values (122, 1, 'fr.pinguet62.springruleengine.sample.rule.PriceGreaterThanRule', '.', 12);
            insert into RULE_COMPONENT_ENTITY (ID, INDEX, KEY, DESCRIPTION, PARENT_ID) values (123, 2, 'fr.pinguet62.springruleengine.core.api.AndRule', null, 12);
            insert into RULE_COMPONENT_ENTITY (ID, INDEX, KEY, DESCRIPTION, PARENT_ID) values (124, 3, 'fr.pinguet62.springruleengine.sample.rule.OnlyWeekendRule', 'The product can only be proposed the weekend.', 12);
            insert into RULE_COMPONENT_ENTITY (ID, INDEX, KEY, DESCRIPTION, PARENT_ID) values (125, 4, 'fr.pinguet62.springruleengine.sample.rule.PriceGreaterThanRule', 'It''s necessary to have good profict.', 12);
                    insert into PARAMETER_ENTITY (ID, RULE_ID, KEY, VALUE, TYPE) values (1221, 125, 'amount', '100.00', 'java.lang.Double');
insert into BUSINESS_RULE_ENTITY (id, title, ROOT_RULE_COMPONENT_ID) values ('first', 'The best sample', 1);

    insert into RULE_COMPONENT_ENTITY (ID, INDEX, KEY, DESCRIPTION, PARENT_ID) values (2, 0, 'fr.pinguet62.springruleengine.core.api.NotRule', null, null);
        insert into RULE_COMPONENT_ENTITY (ID, INDEX, KEY, DESCRIPTION, PARENT_ID) values (21, 0, 'fr.pinguet62.springruleengine.sample.rule.OnlyWeekendRule', null, 2);
insert into BUSINESS_RULE_ENTITY (id, title, ROOT_RULE_COMPONENT_ID) values ('second', 'Another sample', 2);

    insert into RULE_COMPONENT_ENTITY (ID, INDEX, KEY, DESCRIPTION, PARENT_ID) values (3, 0, 'fr.pinguet62.springruleengine.sample.rule.OnlyWeekendRule', null, null);
insert into BUSINESS_RULE_ENTITY (id, title, ROOT_RULE_COMPONENT_ID) values ('third', 'The last sample', 3);
