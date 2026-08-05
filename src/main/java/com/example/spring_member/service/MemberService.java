package com.example.spring_member.service;

import org.springframework.stereotype.Service;

import com.example.spring_member.entity.Member;
import com.example.spring_member.repository.MemberRepository;

@Service
public class MemberService {

        private final MemberRepository memberRepository;

        public MemberService(MemberRepository memberRepository){
            this.memberRepository = memberRepository;
        }

        public void join(Member member){
            memberRepository.save(member);
        }
}