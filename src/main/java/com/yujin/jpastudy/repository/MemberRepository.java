package com.yujin.jpastudy.repository;

import com.yujin.jpastudy.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // @PersistenceContext // 이 어노테이션이 있으면 jpa의 엔티티 매니저 -> spring이 생성한 엔티티 매니저에 주입
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    /**
     * SQL vs JPQL
     * SQL: 테이블 대상으로 수행
     * JPQL: 엔티티(객체)를 대상으로 수행
     * */

    public List<Member> findByName(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }
}
