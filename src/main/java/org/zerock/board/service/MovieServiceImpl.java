package org.zerock.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.MovieDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Movie;
import org.zerock.board.entity.MovieImage;
import org.zerock.board.repository.MovieImageRepository;
import org.zerock.board.repository.MovieRepository;
import org.zerock.board.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;  //final

    private final MovieImageRepository movieImageRepository; //final

    private final ReviewRepository reviewRepository;

    @Override
    public void modify(MovieDTO movieDTO) {

        Map<String , Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = movieRepository.getOne(movieDTO.getMno());
        List<MovieImage> movieImageList= (List<MovieImage>) entityMap.get("imgList");

        movie.changeTitle(movieDTO.getTitle());

        movieRepository.save(movie);

        if (movieImageList != null && !movieImageList.isEmpty()) {
            movieImageList.forEach(movieImage -> {
                movieImageRepository.save(movieImage);

            });
        }
    }

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String , Object> entityMap = dtoToEntity(movieDTO);
        Movie movie =(Movie) entityMap.get("movie");
        List<MovieImage> movieImageList= (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);

        if (movieImageList != null && !movieImageList.isEmpty()) {
            movieImageList.forEach(movieImage -> {
                movieImageRepository.save(movieImage);

            });
        }
        return movie.getMno();
    }

    @Override
    public MovieDTO getMovie(Long mno) {
        List<Object[]> result = movieRepository.getMovieWithAll(mno);

        Movie movie = (Movie) result.get(0)[0]; //Movie 엔티티는 가장 앞에 존재 - 모든 Row가 동일한 값

        List<MovieImage> movieImageList = new ArrayList<>();    //영화의 이미지 개수만큼 MovieImage객체 필요

        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage)arr[1];
            movieImageList.add(movieImage);
        });

        Double avg = (Double) result.get(0)[2]; //평균 평점 - 모든 Row가 동일한 값
        Long reviewCnt = (Long) result.get(0)[3];   //리뷰 개수 - 모든 Row가 동일한 값

        return entitiesToDTO(movie, movieImageList, avg, reviewCnt);
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getListPage(pageable);

        log.info("==============================================");
        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });

        Function<Object[], MovieDTO> fn = (arr -> entitiesToDTO(
                (Movie)arr[0] ,
                (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
                (Double) arr[2],
                (Long)arr[3])
        );

        return new PageResultDTO<>(result, fn);
    }

    @Transactional
    @Override
    public void removeWithReview(Long mno) {   //삭제 기능 구현, 트랜잭션 추가

        //리뷰 삭제
        reviewRepository.deleteByMno(mno);
        //이미지 삭제
        movieImageRepository.deleteByMno(mno);
        //movie 글 삭제
        movieRepository.deleteById(mno);

    }



}
