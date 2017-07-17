package fr.pinguet62.springruleengine.core.builder.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.pinguet62.springruleengine.core.builder.database.model.ParameterEntity;

@Repository
public interface ParameterRepository extends JpaRepository<ParameterEntity, Integer> {
}