/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionarchive.entity;
import com.mycompany.missionarchive.entity.CurseEntity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author march
 */
@Entity
@Table(name = "missions")
public class MissionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String missionId;
    private String date;
    private String location;
    private String outcome;
    private long damageCost;
    private String comment;
    
    @OneToOne(cascade = CascadeType.ALL)
    private CurseEntity curse;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<SorcererEntity> sorcerers = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<TechniqueEntity> techniques = new ArrayList<>();
    
    public MissionEntity() {}
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getMissionId() {
        return missionId;
    }
    
    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getOutcome() {
        return outcome;
    }
    
    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
    
    public long getDamageCost() {
        return damageCost;
    }
    
    public void setDamageCost(long damageCost) {
        this.damageCost = damageCost;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public CurseEntity getCurse() {
        return curse;
    }
    
    public void setCurse(CurseEntity curse) {
        this.curse = curse;
    }
    
    public List<SorcererEntity> getSorcerers() {
        return sorcerers;
    }
    
    public void setSorcerers(List<SorcererEntity> sorcerers) {
        this.sorcerers = sorcerers;
    }
    
    public List<TechniqueEntity> getTechniques() {
        return techniques;
    }
    
    public void setTechniques(List<TechniqueEntity> techniques) {
        this.techniques = techniques;
    }
    
    @ElementCollection
    @CollectionTable(name = "mission_extensions", 
        joinColumns = @JoinColumn(name = "mission_id"))
    @MapKeyColumn(name = "ext_key")
    @Column(name = "ext_value", length = 2000)
    private Map<String, String> extensions = new HashMap<>();


    public Map<String, String> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, String> extensions) {
        this.extensions = extensions;
    }
}
