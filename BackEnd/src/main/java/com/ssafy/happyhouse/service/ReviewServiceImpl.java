package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.ReviewDto;
import com.ssafy.happyhouse.model.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {
	
    @Autowired
	private ReviewMapper ReviewMapper;

    @Override
	public List<ReviewDto> retrieveReview() {
		return ReviewMapper.selectReview();
	}
    
  	@Override
	public boolean writeReview(ReviewDto review) {
		return ReviewMapper.insertReview(review) == 1;
	}

	@Override
	public ReviewDto detailReview(int articleno) {
		return ReviewMapper.selectReviewByNo(articleno);
	}

	@Override
	@Transactional
	public boolean updateReview(ReviewDto review) {
		return ReviewMapper.updateReview(review) == 1;
	}

	@Override
	@Transactional
	public boolean deleteReview(int articleno) {
		return ReviewMapper.deleteReview(articleno) == 1;
	}
}