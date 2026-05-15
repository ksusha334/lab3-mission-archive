/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionarchive.parser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.missionarchive.model.Mission;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mycompany.missionarchive.builder.MissionBuilderImpl;
import java.io.File;

/**
 *
 * @author march
 */
public class XmlParser extends BaseParser{
    
    private ObjectMapper objectMapper = new XmlMapper();
    
    @Override
    protected Mission doParse(File file) throws Exception {
        Mission tempMission = objectMapper.readValue(file, Mission.class);
        return MissionBuilderImpl.fromMission(tempMission).build();
    }
    
    @Override
    public boolean canParse(String fileName) {
        return fileName.toLowerCase().endsWith(".xml");
    }
}