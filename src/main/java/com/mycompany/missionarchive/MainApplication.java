/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.missionarchive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author march
 */
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
        
        System.out.println("Сервер запущен!");
        System.out.println("Веб-интерфейс: http://localhost:8080");
        System.out.println("Swagger: http://localhost:8080/swagger-ui/index.html");
    }
}
