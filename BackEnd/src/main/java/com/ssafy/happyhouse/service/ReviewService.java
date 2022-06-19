package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.model.ReviewDto;

public interface ReviewService {
	public List<ReviewDto> retrieveReview();
	public ReviewDto detailReview(int articleno);
	public boolean writeReview(ReviewDto review);
	public boolean updateReview(ReviewDto review);
	public boolean deleteReview(int articleno);
}
