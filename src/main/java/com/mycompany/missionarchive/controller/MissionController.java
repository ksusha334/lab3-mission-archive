/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionarchive.controller;

import com.mycompany.missionarchive.dto.MissionSummaryDto;
import com.mycompany.missionarchive.entity.MissionEntity;
import com.mycompany.missionarchive.mapper.MissionMapper;
import com.mycompany.missionarchive.service.MissionService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 *
 * @author march
 */
@RestController
@RequestMapping("/api/missions")
public class MissionController {
    
    @Autowired
    private MissionService missionService;
    
    @Autowired
    private MissionMapper missionMapper;
    
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MissionSummaryDto upload(
            @Parameter(description = "Файл миссии", 
                       content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                          schema = @Schema(type = "string", format = "binary")))
            @RequestParam("file") MultipartFile file) throws Exception {
        MissionEntity saved = missionService.upload(file);
        return missionMapper.toDto(saved);
    }
    
    @GetMapping
    public List<MissionSummaryDto> list() {
        List<MissionEntity> missions = missionService.getAll();
        return missionMapper.toDtoList(missions);
    }
    
    @GetMapping("/{id}")
    public MissionSummaryDto getById(@Parameter(description = "ID миссии") @PathVariable Long id) {
        MissionEntity mission = missionService.getById(id);
        return missionMapper.toDto(mission);
    }
    
    @GetMapping("/{id}/report")
    public String getReport(
            @Parameter(description = "ID миссии") @PathVariable Long id,
            @Parameter(description = "Тип отчёта: simple или detailed")
            @RequestParam(defaultValue = "simple") String type) {
        return missionService.generateReport(id, type);
    }
}