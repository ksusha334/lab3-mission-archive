/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionarchive.service;

import com.mycompany.missionarchive.entity.MissionEntity;
import com.mycompany.missionarchive.mapper.MissionMapper;
import com.mycompany.missionarchive.model.Mission;
import com.mycompany.missionarchive.parser.JsonParser;
import com.mycompany.missionarchive.parser.MissionParser;
import com.mycompany.missionarchive.parser.NoNameParser;
import com.mycompany.missionarchive.parser.ParserRegistry;
import com.mycompany.missionarchive.parser.TextParser;
import com.mycompany.missionarchive.parser.XmlParser;
import com.mycompany.missionarchive.parser.YamlParser;
import com.mycompany.missionarchive.report.DetailedReportGenerator;
import com.mycompany.missionarchive.report.ReportGenerator;
import com.mycompany.missionarchive.report.SimpleReportGenerator;
import com.mycompany.missionarchive.repository.MissionRepository;
import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author march
 */
@Service
public class MissionService {
    
    @Autowired
    private MissionRepository repository;
    
    @Autowired
    private MissionMapper mapper;
    
    private ParserRegistry parserRegistry;
    
    public MissionService() {
        parserRegistry = new ParserRegistry();
        parserRegistry.register(new JsonParser());
        parserRegistry.register(new XmlParser());
        parserRegistry.register(new TextParser());
        parserRegistry.register(new YamlParser());
        parserRegistry.register(new NoNameParser());
    }
    
    public MissionEntity upload(MultipartFile file) throws Exception {
        File tempFile = File.createTempFile("mission", "_" + file.getOriginalFilename());
        file.transferTo(tempFile);
        
        MissionParser parser = parserRegistry.getParser(file.getOriginalFilename());
        Mission mission = parser.parse(tempFile);
        
        MissionEntity entity = mapper.toEntity(mission);
        return repository.save(entity);
    }
    
    public List<MissionEntity> getAll() {
        return repository.findAll();
    }
    
    public String generateReport(Long id, String type) {
        MissionEntity entity = repository.findById(id).orElse(null);
        if (entity == null) {
            return "Миссия не найдена";
        }
        
        Mission mission = mapper.toDomain(entity);
        
        ReportGenerator generator;
        if (type.equals("detailed")) {
            generator = new DetailedReportGenerator();
        } else {
            generator = new SimpleReportGenerator();
        }
        
        return generator.generate(mission);
    }
    
    public MissionEntity getById(Long id) {
        return repository.findById(id).orElse(null);
    }
        
        
}
