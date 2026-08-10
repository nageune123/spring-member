package com.example.spring_member.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    
    @NotBlank(message = "이름은 필수입니다.")
    private String name;
  
}
