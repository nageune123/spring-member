package com.example.spring_member.dto;

public class MemberResponseDto {
    private final Long id;
    private final String name;
    
    public MemberResponseDto(Long id, String name){
        this.id = id;
        this.name = name;
    }
    public Long getId(){
        return  id;
    }
    public String getName(){
        return name;
    }
}
