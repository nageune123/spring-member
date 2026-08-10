package com.example.spring_member.dto;
import com.example.spring_member.entity.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberResponseDto {
    private final Long id;
    private final String name;

    public static MemberResponseDto from(Member member){

    return new MemberResponseDto(
        
        member.getId(), member.getName());

}

}
