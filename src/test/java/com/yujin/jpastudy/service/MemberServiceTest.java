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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    // @Rollback(false) => 커밋 => insert 확인 가능
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("kim");

        // when
        Long saveId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    /**
     * [ERROR]org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is org.hibernate.service.spi.ServiceException: Unable to create requested service [org.hibernate.engine.jdbc.env.spi.JdbcEnvironment]
     * 에러해결 => h2 실행시키는것 잊지않기
     * @Transactional <- 사용하면 기본 롤백
     * persist를 한다고해서 insert문이 나가지 않는다. 트랜잭션이 커밋하는 순간에 insert
     */

    @Test
    public void 중복_회원_예외() throws Exception {
        // given

        // when

        // then
    }



}