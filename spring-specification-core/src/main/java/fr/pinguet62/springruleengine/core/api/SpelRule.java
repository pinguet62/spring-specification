package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.builder.database.parameter.RuleParameter;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * Special {@link Rule} based on <b>Spring Expression Language</b>.
 *
 * @see ExpressionParser
 */
@Component
@Scope(SCOPE_PROTOTYPE)
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
