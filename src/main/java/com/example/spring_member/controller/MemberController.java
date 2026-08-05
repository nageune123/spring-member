package com.example.spring_member.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.spring_member.service.MemberService;

@RestController
public class MemberController {
    private final MemberService memberService;  
    public MemberController(MemberService memberService){

        this.memberService = memberService;
    }
    
}