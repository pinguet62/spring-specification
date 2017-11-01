package fr.pinguet62.springspecification.core.builder.database.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import static javax.persistence.CascadeType.REMOVE;

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
     * Root element of {@link Rule} tree.
     */
    @OneToOne(cascade = REMOVE)
    private RuleComponentEntity rootRuleComponent;

    /**
     * Argument type of {@link Rule#test(Object)} method.
     * <p>
     * Used to filter assocoable {@link Rule} to this.
     *
     * @see Class#name
     */
    private String argumentType;

    /**
     * User's notes.
     */
    private String title;

}