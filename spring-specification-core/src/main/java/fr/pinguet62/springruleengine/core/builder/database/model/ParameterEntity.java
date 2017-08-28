package fr.pinguet62.springruleengine.core.builder.database.model;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"rule_id", "key"})})
public class ParameterEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = LAZY)
    private RuleComponentEntity rule;

    private String key;

    private String value;

}