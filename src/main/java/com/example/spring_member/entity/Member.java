package com.example.spring_member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Member {
        
        @Id
        @GeneratedValue
        private Long id;
        private String name;


        public Member() {

}
        public Long getId(){
                return id;
        }
        public String getName(){
                return name;
        }
        public void setName(String name){
                this.name = name ; 
        }
}
