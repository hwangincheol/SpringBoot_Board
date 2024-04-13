package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.board.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {

    @Modifying  //update나 delete를 이용하기 위해서는 @Modifying 필요
    @Query("delete from MovieImage mi where mi.movie.mno = :mno")
    void deleteByMno(Long mno);

}
