/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionarchive.dto;

/**
 *
 * @author march
 */
public class MissionSummaryDto {
    
    private Long id;
    private String missionId;
    private String date;
    private String location;
    private String outcome;
    private long damageCost;
    private String comment;
    
    public MissionSummaryDto() {}
    
    public Long getId() {
        return id;
    }
    
    public String getMissionId() {
        return missionId;
    }
    
    public String getDate() {
        return date;
    }
    
    public String getLocation() {
        return location;
    }
    
    public String getOutcome() {
        return outcome;
    }
    
    public long getDamageCost() {
        return damageCost;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setOutcome(String outcome) {
        this.outcome = outcome;
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
}