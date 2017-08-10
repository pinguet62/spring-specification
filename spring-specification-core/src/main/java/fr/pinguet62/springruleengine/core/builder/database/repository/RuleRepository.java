package fr.pinguet62.springruleengine.core.builder.database.repository;

import fr.pinguet62.springruleengine.core.builder.database.model.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<RuleEntity, Integer> {

    List<RuleEntity> findByParentIsNull();

}