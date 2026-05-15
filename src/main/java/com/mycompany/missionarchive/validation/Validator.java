/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.missionarchive.validation;

import com.mycompany.missionarchive.model.Mission;
import java.util.List;

/**
 *
 * @author march
 */
public interface Validator {
    List<String> validate(Mission mission);
}
