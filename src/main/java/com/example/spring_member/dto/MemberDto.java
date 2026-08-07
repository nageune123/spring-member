package com.example.spring_member.dto;
import jakarta.validation.constraints.NotBlank;

public class MemberDto {
    
    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    public MemberDto(){

    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name ;
    

    }
}
