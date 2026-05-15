/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionarchive.controller;

import com.mycompany.missionarchive.entity.MissionEntity;
import com.mycompany.missionarchive.service.MissionService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author march
 */
@RestController
@RequestMapping("/api/missions")
public class MissionController {
    
    @Autowired
    private MissionService missionService;
    
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MissionEntity upload(
        @Parameter(description = "Файл миссии в формате JSON, XML, TXT, YAML или без расширения", 
                   content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                      schema = @Schema(type = "string", format = "binary")))
        @RequestParam("file") MultipartFile file) throws Exception {
        return missionService.upload(file);
    }
    
    @GetMapping
    public List<MissionEntity> list() {
        return missionService.getAll();
    }
    
    @GetMapping("/{id}")
    public MissionEntity getById(@PathVariable Long id) {
        return missionService.getById(id);
    }
    
    @GetMapping("/{id}/report")
    public String getReport(
            @PathVariable Long id,
            @RequestParam(defaultValue = "simple") ReportType type) {
        return missionService.generateReport(id, type.toString());
    }
    
    enum ReportType {
        simple,
        detailed
    }
}