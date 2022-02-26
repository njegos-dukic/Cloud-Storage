package org.unibl.etf.sni.dms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.sni.dms.models.entities.LogEntity;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Integer> {

    List<LogEntity> getAllByType(String type);
}