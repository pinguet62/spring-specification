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
     * Root element of {@link Rule} tree.
     */
    @OneToOne
    private RuleComponentEntity rootRuleComponent;

    /**
     * User's notes.
     */
    private String title;

}