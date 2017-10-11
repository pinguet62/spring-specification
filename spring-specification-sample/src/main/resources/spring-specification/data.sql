    insert into RULE_COMPONENT_ENTITY (ID, PARENT_ID, INDEX, KEY, DESCRIPTION) values (-1, null, 0, 'fr.pinguet62.springruleengine.core.api.AndRule', null);
        insert into RULE_COMPONENT_ENTITY (ID, PARENT_ID, INDEX, KEY, DESCRIPTION) values (-11, -1, 0, 'fr.pinguet62.springruleengine.core.api.NotRule', 'must not be injured');
            insert into RULE_COMPONENT_ENTITY (ID, PARENT_ID, INDEX, KEY, DESCRIPTION) values (-111, -11, 0, 'fr.pinguet62.springruleengine.sample.rule.DangerousProductRule', null);
        insert into RULE_COMPONENT_ENTITY (ID, PARENT_ID, INDEX, KEY, DESCRIPTION) values (-12, -1, 1, 'fr.pinguet62.springruleengine.core.api.OrRule', null);
            insert into RULE_COMPONENT_ENTITY (ID, PARENT_ID, INDEX, KEY, DESCRIPTION) values (-121, -12, 0, 'fr.pinguet62.springruleengine.sample.rule.TypeProductRule', null);
                    insert into PARAMETER_ENTITY (ID, RULE_ID, KEY, VALUE) values (-1211, -121, 'type', 'toy');
            insert into RULE_COMPONENT_ENTITY (ID, PARENT_ID, INDEX, KEY, DESCRIPTION) values (-122, -12, 1, 'fr.pinguet62.springruleengine.sample.rule.LessThanPriceProductRule', 'pocket money');
                    insert into PARAMETER_ENTITY (ID, RULE_ID, KEY, VALUE) values (-1221, -122, 'amount', '10.00');
insert into BUSINESS_RULE_ENTITY (ID, ARGUMENT_TYPE, ROOT_RULE_COMPONENT_ID, TITLE) values ('CanSellToMinor', 'fr.pinguet62.springruleengine.sample.model.Product', -1, 'Rule to test if the "Product" can be sold to a minor.');

    insert into RULE_COMPONENT_ENTITY (ID, PARENT_ID, INDEX, KEY, DESCRIPTION) values (-2, null, 0, 'fr.pinguet62.springruleengine.core.api.AndRule', null);
        insert into RULE_COMPONENT_ENTITY (ID, PARENT_ID, INDEX, KEY, DESCRIPTION) values (-21, -2, 0, 'fr.pinguet62.springruleengine.sample.rule.LessThanPriceProductRule', null);
                insert into PARAMETER_ENTITY (ID, RULE_ID, KEY, VALUE) values (-211, -21, 'amount', '19.00');
        insert into RULE_COMPONENT_ENTITY (ID, PARENT_ID, INDEX, KEY, DESCRIPTION) values (-22, -2, 1, 'fr.pinguet62.springruleengine.sample.rule.TypeProductRule', null);
                insert into PARAMETER_ENTITY (ID, RULE_ID, KEY, VALUE) values (-221, -22, 'type', 'food');
insert into BUSINESS_RULE_ENTITY (ID, ARGUMENT_TYPE, ROOT_RULE_COMPONENT_ID, TITLE) values ('CanUseLuncheonVoucher', 'fr.pinguet62.springruleengine.sample.model.Product', -2, 'Rule to test if the "Product" can be paid using "luncheon voucher".');
