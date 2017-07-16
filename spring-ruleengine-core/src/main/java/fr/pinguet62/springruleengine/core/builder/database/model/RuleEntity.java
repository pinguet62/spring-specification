package fr.pinguet62.springruleengine.core.builder.database.model;

import static javax.persistence.FetchType.EAGER;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class RuleEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String key;

    @OneToMany(fetch = EAGER)
    @JoinColumn(name = "parent")
    private List<RuleEntity> components = new ArrayList<>();

    @OneToMany(mappedBy = "rule", fetch = EAGER)
    private List<ParameterEntity> parameters = new ArrayList<>();

}