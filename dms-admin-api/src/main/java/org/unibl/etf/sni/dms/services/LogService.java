package org.unibl.etf.sni.dms.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.sni.dms.models.entities.LogEntity;
import org.unibl.etf.sni.dms.repositories.LogRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public List<LogEntity> getAllLogs() {
        return logRepository.findAll();
    }

    public LogEntity insert(LogEntity logEntity) {
        return logRepository.saveAndFlush(logEntity);
    }
}