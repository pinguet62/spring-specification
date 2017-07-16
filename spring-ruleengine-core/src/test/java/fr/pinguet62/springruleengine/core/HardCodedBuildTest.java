package fr.pinguet62.springruleengine.core;

import static fr.pinguet62.springruleengine.core.sample.TestUtils.entry;
import static fr.pinguet62.springruleengine.core.sample.TestUtils.map;
import static fr.pinguet62.springruleengine.core.sample.TestUtils.sampleContext;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.pinguet62.springruleengine.core.Context;
import fr.pinguet62.springruleengine.core.rule.AndRule;
import fr.pinguet62.springruleengine.core.rule.OrRule;
import fr.pinguet62.springruleengine.core.rule.Rule;
import fr.pinguet62.springruleengine.core.sample.rule.HasColorRule;
import fr.pinguet62.springruleengine.core.sample.rule.OnlyWeekendRule;
import fr.pinguet62.springruleengine.core.sample.rule.PriceGreaterThanRule;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HardCodedBuildTest {

    @Test
    public void test() {
        // @formatter:off
        Rule rule = new AndRule(
                new HasColorRule(map(entry("color", "black"))),
                new OrRule(
                    new OnlyWeekendRule(),
                    new PriceGreaterThanRule(map(entry("amount", 100.00)))
                )
            );
        // @formatter:off

        Context context = sampleContext();
        boolean result = rule.test(context);
        assertTrue(result);
    }

}