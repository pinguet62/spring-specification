package fr.pinguet62.springruleengine.core.builder.database.model;

import static javax.persistence.FetchType.EAGER;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.Data;

@Data
@Entity
public class RuleEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer index;

    /** Key used by factory to find corresponding implementation. */
    private String key;

    /** User's notes. */
    private String description;

    @ManyToOne
    private RuleEntity parent;

    @OneToMany
    @JoinColumn(name = "parent_id")
    @OrderBy("index")
    private List<RuleEntity> components = new ArrayList<>();

    @OneToMany(mappedBy = "rule", fetch = EAGER)
    private List<ParameterEntity> parameters = new ArrayList<>();

}