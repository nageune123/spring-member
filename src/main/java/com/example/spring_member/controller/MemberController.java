package com.example.spring_member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_member.dto.MemberDto;
import com.example.spring_member.dto.MemberResponseDto;
import com.example.spring_member.entity.Member;
import com.example.spring_member.service.MemberService;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor    
@RestController
public class MemberController {
    private final MemberService memberService;  
   
    @PostMapping("/members")
        public ResponseEntity<MemberResponseDto> join(@Valid @RequestBody MemberDto dto) {
           
            MemberResponseDto responseDto =  memberService.join(dto);
            return ResponseEntity.status(201).body(responseDto);
            
}   
    @GetMapping("/members")
    public List<MemberResponseDto> findAll(){

        return memberService.findAll();

    }
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id){
            MemberResponseDto responseDto = memberService.findById(id);
        return ResponseEntity.ok(responseDto);

    }

 @PutMapping("/members/{id}")
public ResponseEntity<MemberResponseDto> update(
        @PathVariable Long id,
        @Valid @RequestBody MemberDto dto) {

    MemberResponseDto responseDto =
           memberService.update(id, dto);
    return ResponseEntity.ok(responseDto);
}
    
    
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        memberService.delete(id);
        return ResponseEntity.noContent().build();
        
    }
@GetMapping("/docker-test")
public String dockerTest() {
    return "Docker Build Test v2!";
}



 }
    
    
