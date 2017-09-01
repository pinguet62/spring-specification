package fr.pinguet62.springruleengine.core.builder.database.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Define a business {@link Rule}, used into application.
 */
@Data
@Entity
public class BusinessRuleEntity {

    /**
     * Used by {@link RuleBuilder}.
     */
    @Id
    private String id;

    /**
     * Argument type of {@link Rule#test(Object)} method.
     * <p>
     * Used to filter assocoable {@link Rule} to this.
     *
     * @see Class#name
     */
    private String argumentType;

    /**
     * Root element of {@link Rule} tree.
     */
    @OneToOne
    private RuleComponentEntity rootRuleComponent;

    /**
     * User's notes.
     */
    private String title;

}