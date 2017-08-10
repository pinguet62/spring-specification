package fr.pinguet62.springruleengine.core.builder.database.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;

/**
 * Usage of a {@link Rule}.
 */
@Data
@Entity
public class RuleEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer index;

    /**
     * {@link Rule} class.
     *
     * @see Class#getName()
     */
    private String key;

    /**
     * User's notes.
     */
    private String description;

    /**
     * {@code null} when root {@link Rule}.
     */
    @ManyToOne
    private RuleEntity parent;

    @OneToMany(mappedBy = "parent", cascade = REMOVE)
    @OrderBy("index")
    private List<RuleEntity> components = new ArrayList<>();

    @OneToMany(mappedBy = "rule", fetch = EAGER, cascade = REMOVE)
    private List<ParameterEntity> parameters = new ArrayList<>();

}