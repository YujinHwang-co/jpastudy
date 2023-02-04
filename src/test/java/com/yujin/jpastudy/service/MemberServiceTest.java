package com.yujin.jpastudy.service;

import com.yujin.jpastudy.domain.Member;
import com.yujin.jpastudy.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // JUnit 실행시 스프링이랑 같이 실행
@SpringBootTest // 스프링 부트 띄운 상태로 테스트시 반드시 있어야 함 => 없으면 @Autowired 전체실패
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    // @Rollback(false) => 커밋 => insert 확인 가능
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("kim");

        // when
        Long saveId = memberService.join(member);

        // then
        // em.flush(); --> 영속성 컨텍스트에 있는 변경이나 등록 내용들을 DB에 반영 -> 쿼리 날리는것
        assertEquals(member, memberRepository.findOne(saveId));
    }

    /**
     * [ERROR]org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is org.hibernate.service.spi.ServiceException: Unable to create requested service [org.hibernate.engine.jdbc.env.spi.JdbcEnvironment]
     * 에러해결 => h2 실행시키는것 잊지않기
     * @Transactional <- 사용하면 기본 롤백
     * persist를 한다고해서 insert문이 나가지 않는다. 트랜잭션이 커밋하는 순간에 insert
     * 08:10..
     */

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setUsername("kim");

        Member member2 = new Member();
        member2.setUsername("kim");

        // when
        memberService.join(member1);
        //try { -> 복잡한 try-catch 대신 test 어노테이션 옆에 illegal~ 사용시 예외처리 가능
        memberService.join(member2); // 같은 이름의 중복을 체크했으므로.. 예외가 발생해야함!
        //} catch (IllegalStateException e) {
        //    return;
        //}
        // then
        fail("예외발생");
    }


}