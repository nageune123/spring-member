package com.example.spring_member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
    


}
