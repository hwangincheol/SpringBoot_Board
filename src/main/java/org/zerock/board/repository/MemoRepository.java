package org.zerock.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Memo;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    // JPA 레포지토리는 인터페이스 자체이고 JpaRepository 인터페이스를 상속하는 것 만드로 모든 작없이 끝남.
    // extends JpaRepository<엔티티, pk타입>

    // insert 작업 : save(엔티티 객체)
    // select 작업 : findById(키 타입), getOne(키 타입)
    // update 작업 : save(엔티티 객체)
    // delete 작업 : deleteById(키 타입),  delete(엔티티 객체)
    
    // 쿼리 메서드는 메서드명 자체가 쿼리문으로 동작한다.
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
    // List<Memo> 리턴타입 -> 리스트타입에 객체는 memo
    // 매개값으로 받은 from ~ to 까지 select 진행하여 리스트로 리턴하는 쿼리메서드

    Page<Memo> findByMnoBetween (Long from, Long to, Pageable pageable);
    // Page<Memo> 리턴타입 -> 페이징 타입에 객체는 memo
    // 매개값으로 받은 from ~ to 까지 select 진행하여 페이징 타입으로 리턴하는 쿼리 메서드


    // 예를 들어 10보다 작은 데이터를 삭제한다.
    void deleteMemoByMnoLessThan(Long num);


    // @Query는 순수한 sql 쿼리문으로 작성한다. 단. 테이블 명이 아니라 엔티티 명으로 사용함.
    @Query("SELECT m FROM Memo m ORDER BY m.mno DESC")
    List<Memo> getListDese(); // 내가 만든 메서드 명


    // 매개값이 있는 @Query문 :값 (타입으로 받음)
    @Query("UPDATE Memo m SET m.memoText = :memoText WHERE m.mno = :mno")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

    // 매개값이 객체(빈)으로 들어올 경우

//    @Query("UPDATE Memo m SET m.memoText = : #{memoBean.memoText} WHERE m.mno= : #{memoBean.mno}" )
//    int updateMemoBean(@Param("memoBean") Memo memo) ;


    // @Query 메서드로 페이징 처리 해보기 -> 리턴 타입이 Page<Memo>
    @Query(value = "SELECT m FROM Memo m WHERE m.mno > :mno ",
           countQuery = "SELECT count(m) FROM Memo m WHERE m.mno > : mno ")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);

    // db에 존재 하지 않는 값 처리 해보기 : 예를 들어 날짜

    @Query(value = "SELECT m.mno, m.memoText, CURRENT_DATE FROM Memo m WHERE m.mno > : mno",
           countQuery = " SELECT count(m) FROM Memo m WHERE m.mno > : mno ")
    Page<Object[]> getlistWithQueryObject(Long mno, Pageable pageable);

    // Native Sql 처리 : Db용 쿼리로 사용하는 기법 -> nativeQuery = true
    // 엔티티 대신에 테이블명으로 사용함.
    @Query(value = "SELECT * FROM memo WHERE mno > 0", nativeQuery = true)
    List<Object[]> getNativeResult();
}
