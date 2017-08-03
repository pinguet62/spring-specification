package fr.pinguet62.springruleengine.core.builder.database.model;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "rule_id", "key" }) })
public class ParameterEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = LAZY)
    private RuleEntity rule;

    private String key;

    private String value;

    /**
     * Value: Class#getName()<br>
     * Doesn't support primitive type ({@code Class.forName("int")} throws an {@link ClassNotFoundException}).
     */
    private String type;

}