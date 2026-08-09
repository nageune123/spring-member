package com.example.spring_member.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.spring_member.dto.MemberDto;
import com.example.spring_member.dto.MemberResponseDto;
import com.example.spring_member.entity.Member;
import com.example.spring_member.exception.MemberNotFoundException;
import com.example.spring_member.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class MemberService {

        private final MemberRepository memberRepository;

        public MemberService(MemberRepository memberRepository){
            this.memberRepository = memberRepository;
        }

        public MemberResponseDto join(Member member){
            Member savedMember =memberRepository.save(member);
            return new MemberResponseDto(savedMember.getId(), savedMember.getName());
        }



        public List<MemberResponseDto> findAll(){
            
            List<Member> members= memberRepository.findAll();

            return members.stream().map( member ->
                 new MemberResponseDto(member.getId(), member.getName()) )
                 .toList();

        }
        public MemberResponseDto findById(Long id){
            Member member  =
             memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);

            
            return new MemberResponseDto(member.getId(),member.getName() );
           
           
        }


        @Transactional
        public void update(Long id, MemberDto dto){
             Member member = memberRepository.findById(id)
            .orElseThrow(MemberNotFoundException::new);

            member.setName(dto.getName());
        }
        @Transactional
        public void delete(Long id){
               Member member = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);

                    memberRepository.delete(member);
        }
}