package com.example.spring_member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_member.dto.MemberDto;
import com.example.spring_member.entity.Member;
import com.example.spring_member.service.MemberService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;

@RestController
public class MemberController {
    private final MemberService memberService;  
    public MemberController(MemberService memberService){

        this.memberService = memberService;
    }
    @PostMapping("/members")
        public void join(@Valid @RequestBody MemberDto dto) {
            Member member = new Member();
            member.setName(dto.getName());
            memberService.join(member);
            
}   
    @GetMapping("/members")
    public List<Member> findAll(){
        return memberService.findAll();

    }
    @GetMapping("/members/{id}")
    public ResponseEntity<Member> findByID(@PathVariable Long id){
        Member member = memberService.findById(id);
        return ResponseEntity.ok(member);

    }
    @PutMapping("/members/{id}")
    public Member update(@PathVariable Long id, @Valid @RequestBody MemberDto dto){
         memberService.update(id, dto);
         return memberService.findById(id);
    }
    @DeleteMapping("/members/{id}")
    public void delete(@PathVariable Long id){
        memberService.delete(id);
        
    }

 }
    
    
