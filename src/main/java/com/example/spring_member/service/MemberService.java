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
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {    

        private final MemberRepository memberRepository;

        public MemberResponseDto join(MemberDto dto){
            Member member = Member.from(dto);
            
            Member savedMember =memberRepository.save(member);
            return MemberResponseDto.from(savedMember);
        }
        public List<MemberResponseDto> findAll(){
            
            List<Member> members= memberRepository.findAll();

            return members.stream().map( MemberResponseDto :: from )
                 .toList();
        }
        public MemberResponseDto findById(Long id){
            Member member  =
             memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
           return MemberResponseDto.from(member);       
        }


        @Transactional
        public MemberResponseDto update(Long id, MemberDto dto){

             Member member = memberRepository.findById(id)
            .orElseThrow(MemberNotFoundException::new);

            member.setName(dto.getName());
            return MemberResponseDto.from(member);       
        }

        @Transactional
        public void delete(Long id){
               Member member = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);

                    memberRepository.delete(member);
        }
}