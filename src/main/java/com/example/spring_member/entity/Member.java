package com.example.spring_member.entity;

import com.example.spring_member.dto.MemberDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.Getter;


@Getter
@Entity
@NoArgsConstructor
public class Member {
        
        @Id
        @GeneratedValue
        private Long id;
        
        private String name;

      
        public void setName(String name){
                this.name = name ; 
        }
        public static Member from(MemberDto dto){
                Member member = new Member();
                member.setName(dto.getName());
                return member;
        }
}
