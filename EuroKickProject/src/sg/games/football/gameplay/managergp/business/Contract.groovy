/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.games.football.gameplay.managergp.business

import groovy.text.SimpleTemplateEngine
/**
 *
 * @author cuong.nguyenmanh2
 */
public class Contract {
    def type;
    def from;
    def to;
    def title;
    def description;
    def status;
    def cost;
    def signatures;
        
    def text="""
"""
    def engine = new SimpleTemplateEngine()
    def binding = ["firstname":"Sam", "lastname":"Pullara", "city":"San Francisco", "month":"December", "signed":"Groovy-Dev"]    
    
    def makeContract(){
        
    }
    def withPlayer(){
        text = loadTemplate("contractForms/FootballPlayerContract.tpl");
        
        def template = engine.createTemplate(text).make(binding)
        return template.toString();
    }
    
    def loadTemplate(path){
        return getClass().getResource(path).text
    }






}

