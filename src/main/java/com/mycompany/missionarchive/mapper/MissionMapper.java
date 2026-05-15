/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionarchive.mapper;

import com.mycompany.missionarchive.entity.CurseEntity;
import com.mycompany.missionarchive.entity.MissionEntity;
import com.mycompany.missionarchive.entity.SorcererEntity;
import com.mycompany.missionarchive.entity.TechniqueEntity;
import com.mycompany.missionarchive.model.Curse;
import com.mycompany.missionarchive.model.Mission;
import com.mycompany.missionarchive.model.Sorcerer;
import com.mycompany.missionarchive.model.Technique;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author march
 */
@Component
public class MissionMapper {
    public MissionEntity toEntity(Mission mission) {
        MissionEntity entity = new MissionEntity();
        entity.setMissionId(mission.getMissionId());
        entity.setDate(mission.getDate());
        entity.setLocation(mission.getLocation());
        entity.setOutcome(mission.getOutcome());
        entity.setDamageCost(mission.getDamageCost());
        entity.setComment(mission.getComment());

        if (mission.getCurse() != null) {
            CurseEntity curse = new CurseEntity();
            curse.setName(mission.getCurse().getName());
            curse.setThreatLevel(mission.getCurse().getThreatLevel());
            entity.setCurse(curse);
        }
        

        if (mission.getSorcerers() != null) {
            for (Sorcerer s : mission.getSorcerers()) {
                SorcererEntity se = new SorcererEntity();
                se.setName(s.getName());
                se.setRank(s.getRank());
                se.setAge(s.getAge());
                entity.getSorcerers().add(se);
            }
        }
        

        if (mission.getTechniques() != null) {
            for (Technique t : mission.getTechniques()) {
                TechniqueEntity te = new TechniqueEntity();
                te.setName(t.getName());
                te.setType(t.getType());
                te.setOwner(t.getOwner());
                te.setDamage(t.getDamage());
                entity.getTechniques().add(te);
            }
        }
        
        if (mission.getExtensions() != null && !mission.getExtensions().isEmpty()) {
            Map<String, String> stringExtensions = new HashMap<>();
            for (Map.Entry<String, Object> entry : mission.getExtensions().entrySet()) {
                stringExtensions.put(entry.getKey(), 
                    entry.getValue() == null ? "null" : entry.getValue().toString());
            }
            entity.setExtensions(stringExtensions);
        }
        
        return entity;
    }
    public Mission toDomain(MissionEntity entity) {
        Mission mission = new Mission();
        mission.setMissionId(entity.getMissionId());
        mission.setDate(entity.getDate());
        mission.setLocation(entity.getLocation());
        mission.setOutcome(entity.getOutcome());
        mission.setDamageCost(entity.getDamageCost());
        mission.setComment(entity.getComment());
        
        

        if (entity.getCurse() != null) {
            Curse curse = new Curse();
            curse.setName(entity.getCurse().getName());
            curse.setThreatLevel(entity.getCurse().getThreatLevel());
            mission.setCurse(curse);
        }
        

        List<Sorcerer> sorcerers = new ArrayList<>();
        if (entity.getSorcerers() != null) {
            for (SorcererEntity se : entity.getSorcerers()) {
                Sorcerer sorcerer = new Sorcerer();
                sorcerer.setName(se.getName());
                sorcerer.setRank(se.getRank());
                sorcerer.setAge(se.getAge());
                sorcerers.add(sorcerer);
            }
        }
        mission.setSorcerers(sorcerers);
        

        List<Technique> techniques = new ArrayList<>();
        if (entity.getTechniques() != null) {
            for (TechniqueEntity te : entity.getTechniques()) {
                Technique technique = new Technique();
                technique.setName(te.getName());
                technique.setType(te.getType());
                technique.setOwner(te.getOwner());
                technique.setDamage(te.getDamage());
                techniques.add(technique);
            }
        }
        
        mission.setTechniques(techniques);
        if (entity.getExtensions() != null && !entity.getExtensions().isEmpty()) {
            Map<String, Object> objExtensions = new HashMap<>();
            for (Map.Entry<String, String> entry : entity.getExtensions().entrySet()) {
                objExtensions.put(entry.getKey(), entry.getValue());
            }
            mission.setExtensions(objExtensions);
        }
        
        return mission;
    }

    
}
