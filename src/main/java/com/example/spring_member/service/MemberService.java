package com.example.spring_member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.spring_member.dto.MemberDto;
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

        public void join(Member member){
            memberRepository.save(member);
        }
        public List<Member> findAll(){
            return memberRepository.findAll();

        }
        public Member findById(Long id){
           return memberRepository.findById(id)
           .orElseThrow(MemberNotFoundException::new);
           
        }
        @Transactional
        public void update(Long id, MemberDto dto){
            Member member = findById(id);
            member.setName(dto.getName());

        }
        @Transactional
        public void delete(Long id){
            Member member = findById(id);
            memberRepository.delete(member);
        }
}