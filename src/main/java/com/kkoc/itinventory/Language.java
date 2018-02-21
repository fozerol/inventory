/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

/**
 *
 * @author fatih
 */
public class Language {
    
        private int id;
        private String name;
        private String territory;
        private String language;

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    
    
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Language(int id, String name) {
            this.id = id;
            this.name = name;
        }

    public Language(int id, String name,  String language,String territory) {
        this.id = id;
        this.name = name;
        this.territory = territory;
        this.language = language;
    }
        
        
}
