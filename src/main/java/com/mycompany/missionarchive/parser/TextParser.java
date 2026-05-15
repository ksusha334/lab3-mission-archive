/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionarchive.parser;

import com.mycompany.missionarchive.builder.MissionBuilder;
import com.mycompany.missionarchive.builder.MissionBuilderImpl;
import com.mycompany.missionarchive.model.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author march
 */
public class TextParser extends BaseParser {
    
    @Override
    public Mission doParse(File file) throws Exception {
        String firstLine = getFirstNonEmptyLine(file);
        
        if (firstLine != null && firstLine.startsWith("[")) {
            return parseSectionFormat(file);
        } else {
            return parseKeyValueFormat(file);
        }
    }
    
    private String getFirstNonEmptyLine(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    return line.trim();
                }
            }
        }
        return null;
    }
    
    private Mission parseKeyValueFormat(File file) throws Exception {
        Mission mission = new Mission();
        List<Sorcerer> sorcerers = new ArrayList<>();
        List<Technique> techniques = new ArrayList<>();
        Curse curse = new Curse();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(":", 2);
                if (parts.length < 2) continue;
                
                String key = parts[0].trim();
                String value = parts[1].trim();
                
                if (key.equals("missionId")) {
                    mission.setMissionId(value);
                } else if (key.equals("date")) {
                    mission.setDate(value);
                } else if (key.equals("location")) {
                    mission.setLocation(value);
                } else if (key.equals("outcome")) {
                    mission.setOutcome(value);
                } else if (key.equals("damageCost")) {
                    try {
                        mission.setDamageCost(Long.parseLong(value));
                    } catch (NumberFormatException e) {
                        mission.setDamageCost(0);
                    }
                }
                else if (key.equals("curse.name")) {
                    curse.setName(value);
                } else if (key.equals("curse.threatLevel")) {
                    curse.setThreatLevel(value);
                }
                else if (key.equals("note") || key.equals("comment")) {
                    mission.setComment(value);
                }
                else if (key.startsWith("sorcerer[") && key.endsWith(".name")) {
                    int index = Integer.parseInt(key.substring(9, key.indexOf("]")));
                    while (sorcerers.size() <= index) sorcerers.add(new Sorcerer());
                    sorcerers.get(index).setName(value);
                }
                else if (key.startsWith("sorcerer[") && key.endsWith(".rank")) {
                    int index = Integer.parseInt(key.substring(9, key.indexOf("]")));
                    while (sorcerers.size() <= index) sorcerers.add(new Sorcerer());
                    sorcerers.get(index).setRank(value);
                }
                else if (key.startsWith("sorcerer[") && key.endsWith(".age")) {
                    int index = Integer.parseInt(key.substring(9, key.indexOf("]")));
                    while (sorcerers.size() <= index) sorcerers.add(new Sorcerer());
                    try {
                        sorcerers.get(index).setAge(Integer.parseInt(value));
                    } catch (NumberFormatException e) {
                        sorcerers.get(index).setAge(0);
                    }
                }
                else if (key.startsWith("technique[") && key.endsWith(".name")) {
                    int index = Integer.parseInt(key.substring(10, key.indexOf("]")));
                    while (techniques.size() <= index) techniques.add(new Technique());
                    techniques.get(index).setName(value);
                }
                else if (key.startsWith("technique[") && key.endsWith(".type")) {
                    int index = Integer.parseInt(key.substring(10, key.indexOf("]")));
                    while (techniques.size() <= index) techniques.add(new Technique());
                    techniques.get(index).setType(value);
                }
                else if (key.startsWith("technique[") && key.endsWith(".owner")) {
                    int index = Integer.parseInt(key.substring(10, key.indexOf("]")));
                    while (techniques.size() <= index) techniques.add(new Technique());
                    techniques.get(index).setOwner(value);
                }
                else if (key.startsWith("technique[") && key.endsWith(".damage")) {
                    int index = Integer.parseInt(key.substring(10, key.indexOf("]")));
                    while (techniques.size() <= index) techniques.add(new Technique());
                    try {
                        techniques.get(index).setDamage(Long.parseLong(value));
                    } catch (NumberFormatException e) {
                        techniques.get(index).setDamage(0);
                    }
                }
                else {
                    mission.addExtension(key, value);
                }
            }
        }
        
        mission.setCurse(curse);
        mission.setSorcerers(sorcerers);
        mission.setTechniques(techniques);
        
        return mission;
    }
    private Mission parseSectionFormat(File file) throws Exception {
        MissionBuilder builder = new MissionBuilderImpl();
        
        List<Sorcerer> sorcerers = new ArrayList<Sorcerer>();
        List<Technique> techniques = new ArrayList<Technique>();
        
        String currentSection = null;
        List<String> sectionLines = new ArrayList<String>();
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            
            if (line.startsWith("[") && line.endsWith("]")) {
                if (currentSection != null) {
                    processSection(currentSection, sectionLines, builder, sorcerers, techniques);
                }
                currentSection = line.substring(1, line.length() - 1);
                sectionLines.clear();
            }
            else if (currentSection == null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    applyMainField(
                            parts[0].trim(), 
                            parts[1].trim(), 
                            builder);
                }
            }
            else {
                sectionLines.add(line);
            }
        }
        
        if (currentSection != null) {
            processSection(currentSection, sectionLines, builder, sorcerers, techniques);
        }
        
        reader.close();
        
        for (Sorcerer s : sorcerers) {
            builder.addSorcerer(s);
        }
        for (Technique t : techniques) {
            builder.addTechnique(t);
        }
        
        return builder.build();
    }
    
    private void processSection(String sectionName, List<String> lines, MissionBuilder builder,List<Sorcerer> sorcerers, List<Technique> techniques) {
        if (sectionName.equals("MISSION")) {
            for (String line : lines) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    applyMainField(key, value, builder);
                }
            }
        }
        else if (sectionName.equals("CURSE")) {
            String name = null;
            String threatLevel = null;
            for (String line : lines) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    if (key.equals("name")) {
                        name = value;
                    } else if (key.equals("threatLevel")) {
                        threatLevel = value;
                    } else {
                        builder.addExtension("curse." + key, value);
                    }
                }
            }
            if (name != null || threatLevel != null) {
                Curse curse = new Curse();
                curse.setName(name);
                curse.setThreatLevel(threatLevel);
                builder.setCurse(curse);
            }
        }
        else if (sectionName.equals("SORCERER")) {
            Sorcerer sorcerer = new Sorcerer();
            for (String line : lines) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    if (key.equals("name")) {
                        sorcerer.setName(value);
                    } else if (key.equals("rank")) {
                        sorcerer.setRank(value);
                    } else {
                        int currentIndex = sorcerers.size();
                        builder.addExtension("sorcerer_" + currentIndex + "." + key, value);
                    }
                }
            }
            sorcerers.add(sorcerer);
        }
        else if (sectionName.equals("TECHNIQUE")) {
            Technique technique = new Technique();
            for (String line : lines) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    if (key.equals("name")) {
                        technique.setName(value);
                    } else if (key.equals("type")) {
                        technique.setType(value);
                    } else if (key.equals("owner")) {
                        technique.setOwner(value);
                    } else if (key.equals("damage")) {
                        try {
                            technique.setDamage(Long.parseLong(value));
                        } catch (NumberFormatException e) {
                        }
                    } else {
                        int currentIndex = techniques.size();
                        builder.addExtension("technique_" + currentIndex + "." + key, value);
                    }
                }
            }
            techniques.add(technique);
        }
        else {
            StringBuilder sectionContent = new StringBuilder();
            for (String line : lines) {
                sectionContent.append(line).append("\n");
            }
            builder.addExtension("section_" + sectionName, sectionContent.toString());
        }
    }
    
    private void applyMainField(String key, String value, MissionBuilder builder) {
        if (key.equals("missionId")) {
            builder.setMissionId(value);
        } else if (key.equals("date")) {
            builder.setDate(value);
        } else if (key.equals("location")) {
            builder.setLocation(value);
        } else if (key.equals("outcome")) {
            builder.setOutcome(value);
        } else if (key.equals("damageCost")) {
            try {
                builder.setDamageCost(Long.parseLong(value));
            } catch (NumberFormatException e) {
            }
        } else if (key.equals("comment") || key.equals("note")) {
            builder.setComment(value);
        } else {
            builder.addExtension(key, value);
        }
    }
    
    @Override
    public boolean canParse(String fileName) {
        return fileName.toLowerCase().endsWith(".txt");
    }
}