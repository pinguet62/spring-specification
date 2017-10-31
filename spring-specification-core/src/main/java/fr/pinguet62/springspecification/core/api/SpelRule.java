package fr.pinguet62.springspecification.core.api;

import fr.pinguet62.springspecification.core.RuleDescription;
import fr.pinguet62.springspecification.core.RuleName;
import fr.pinguet62.springspecification.core.SpringRule;
import fr.pinguet62.springspecification.core.builder.database.parameter.RuleParameter;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Special {@link Rule} based on <b>Spring Expression Language</b>.
 *
 * @see ExpressionParser
 */
@SpringRule
@RuleName(value = "Spring Expression Language")
@RuleDescription("SpEL variables \"#value\" is the input test() parameter")
public class SpelRule implements Rule<Object> {

    @Autowired
    private BeanFactory beanFactory;

    @Setter
    @RuleParameter("expression")
    private String spel;

    @Override
    public boolean test(Object value) {
        ExpressionParser parser = new SpelExpressionParser();

        // variables
        StandardEvaluationContext context = new StandardEvaluationContext(value);
        if (beanFactory != null) // manual instantiation
            context.setBeanResolver(new BeanFactoryResolver(beanFactory));
        context.setVariable("value", value);

        // execute
        Expression exp = parser.parseExpression(spel);
        return exp.getValue(context, Boolean.class);
    }

}
