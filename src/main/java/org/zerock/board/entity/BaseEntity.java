package org.zerock.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass   //직접 테이블용은 아님을 명시
@Getter
//세터 대신 감시용 코드(데이터 변경을 감지하여 적용 -> Main메서드에 추가코드 필수
@EntityListeners(value = {AuditingEntityListener.class})
abstract class BaseEntity { //abstract 상속간에 추상클래스 동작
    //테이블이 공통되는 부분을 상속해줄 클래스

    @CreatedDate    //게시물 생성할 때 동작
    @Column(name = "regdate", updatable = false)  //db 테이블에 필드명 지정, 업데이트 안됨
    private LocalDateTime regDate;  //게시물 등록 시간
    
    @LastModifiedDate   //게시물 수정시 동작
    @Column(name = "moddate")   //db 테이블에 필ㄹ드명 지정
    private LocalDateTime modDate;  //게시물 수정 시간






}

