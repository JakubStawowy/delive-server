package com.example.rentiaserver.data.dao;

import com.example.rentiaserver.data.po.FeedbackPo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends CrudRepository<FeedbackPo, Long> {
    List<FeedbackPo> getFeedbacksByUserId(Long id);
}
