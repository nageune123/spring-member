package com.example.spring_member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_member.dto.MemberDto;
import com.example.spring_member.entity.Member;
import com.example.spring_member.service.MemberService;

@RestController
public class MemberController {
    private final MemberService memberService;  
    public MemberController(MemberService memberService){

        this.memberService = memberService;
    }
    @PostMapping("/members")
        public void join(@RequestBody MemberDto dto) {
            Member member = new Member();
            member.setName(dto.getName());
            memberService.join(member);
            
}   
}