/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionarchive.mapper;

import com.mycompany.missionarchive.dto.MissionSummaryDto;
import com.mycompany.missionarchive.entity.CurseEntity;
import com.mycompany.missionarchive.entity.MissionEntity;
import com.mycompany.missionarchive.entity.SorcererEntity;
import com.mycompany.missionarchive.entity.TechniqueEntity;
import com.mycompany.missionarchive.model.Curse;
import com.mycompany.missionarchive.model.Mission;
import com.mycompany.missionarchive.model.Sorcerer;
import com.mycompany.missionarchive.model.Technique;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author march
 */
@Component
public class MissionMapper {
    public MissionSummaryDto toDto(MissionEntity entity) {
        if (entity == null) return null;
        MissionSummaryDto dto = new MissionSummaryDto();
        dto.setId(entity.getId());
        dto.setMissionId(entity.getMissionId());
        dto.setDate(entity.getDate());
        dto.setLocation(entity.getLocation());
        dto.setOutcome(entity.getOutcome());
        dto.setDamageCost(entity.getDamageCost());
        dto.setComment(entity.getComment());
        return dto;
    }
    
    public List<MissionSummaryDto> toDtoList(List<MissionEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        
        List<MissionSummaryDto> dtos = new ArrayList<>();
        for (MissionEntity entity : entities) {
            dtos.add(toDto(entity));
        }
        return dtos;
    }
    
    public MissionEntity toEntity(Mission mission) {
        if (mission == null) {
            return null;
        }
        
        MissionEntity entity = new MissionEntity();
        entity.setMissionId(mission.getMissionId());
        entity.setDate(mission.getDate());
        entity.setLocation(mission.getLocation());
        entity.setOutcome(mission.getOutcome());
        entity.setDamageCost(mission.getDamageCost());
        entity.setComment(mission.getComment());
        
        if (mission.getCurse() != null) {
            CurseEntity curseEntity = new CurseEntity();
            curseEntity.setName(mission.getCurse().getName());
            curseEntity.setThreatLevel(mission.getCurse().getThreatLevel());
            entity.setCurse(curseEntity);
        }
        
        if (mission.getSorcerers() != null) {
            List<SorcererEntity> sorcererEntities = new ArrayList<>();
            for (Sorcerer s : mission.getSorcerers()) {
                SorcererEntity se = new SorcererEntity();
                se.setName(s.getName());
                se.setRank(s.getRank());
                se.setAge(s.getAge());
                sorcererEntities.add(se);
            }
            entity.setSorcerers(sorcererEntities);
        }
        
        if (mission.getTechniques() != null) {
            List<TechniqueEntity> techniqueEntities = new ArrayList<>();
            for (Technique t : mission.getTechniques()) {
                TechniqueEntity te = new TechniqueEntity();
                te.setName(t.getName());
                te.setType(t.getType());
                te.setOwner(t.getOwner());
                te.setDamage(t.getDamage());
                techniqueEntities.add(te);
            }
            entity.setTechniques(techniqueEntities);
        }
        
        if (mission.getExtensions() != null && !mission.getExtensions().isEmpty()) {
            Map<String, String> stringExtensions = new HashMap<>();
            for (Entry<String, Object> entry : mission.getExtensions().entrySet()) {
                stringExtensions.put(entry.getKey(), 
                    entry.getValue() == null ? "null" : entry.getValue().toString());
            }
            entity.setExtensions(stringExtensions);
        }
        
        return entity;
    }
    
    public Mission toDomain(MissionEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Mission mission = new Mission();
        mission.setMissionId(entity.getMissionId());
        mission.setDate(entity.getDate());
        mission.setLocation(entity.getLocation());
        mission.setOutcome(entity.getOutcome());
        mission.setDamageCost(entity.getDamageCost());
        mission.setComment(entity.getComment());
        
        if (entity.getCurse() != null) {
            Curse curse = new Curse(
                    entity.getCurse().getName(),
                    entity.getCurse().getThreatLevel()
                );
            mission.setCurse(curse);
        }
        
        if (entity.getSorcerers() != null) {
            List<Sorcerer> sorcerers = new ArrayList<>();
            for (SorcererEntity se : entity.getSorcerers()) {
                Sorcerer sorcerer = new Sorcerer(
                        se.getName(), se.getRank(), se.getAge()
                    );
                sorcerers.add(sorcerer);
            }
            mission.setSorcerers(sorcerers);
        }

        if (entity.getTechniques() != null) {
            List<Technique> techniques = new ArrayList<>();
            for (TechniqueEntity te : entity.getTechniques()) {
                Technique technique = new Technique(
                        te.getName(), te.getType(), te.getOwner(), te.getDamage()
                    );
                techniques.add(technique);
            }
            mission.setTechniques(techniques);
        }

        if (entity.getExtensions() != null && !entity.getExtensions().isEmpty()) {
            Map<String, Object> objExtensions = new HashMap<>();
            for (Entry<String, String> entry : entity.getExtensions().entrySet()) {
                objExtensions.put(entry.getKey(), entry.getValue());
            }
            mission.setExtensions(objExtensions);
        }
        
        return mission;
    }
}
