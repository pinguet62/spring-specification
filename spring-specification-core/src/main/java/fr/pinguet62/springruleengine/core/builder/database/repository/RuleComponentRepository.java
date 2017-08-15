package fr.pinguet62.springruleengine.core.builder.database.repository;

import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleComponentRepository extends JpaRepository<RuleComponentEntity, Integer> {
}
