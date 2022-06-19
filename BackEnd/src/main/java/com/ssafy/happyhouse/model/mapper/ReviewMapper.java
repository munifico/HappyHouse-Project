package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.ReviewDto;
@Mapper
public interface ReviewMapper {
	public List<ReviewDto> selectReview();
	public ReviewDto selectReviewByNo(int articleno);
	public int insertReview(ReviewDto review);
	public int updateReview(ReviewDto review);
	public int deleteReview(int articleno);
}