package com.example.bcp.service;

import com.example.bcp.entity.PeerReviewAssignment;
import com.example.bcp.mapper.PeerReviewAssignmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PeerReviewAssignmentService {

    @Autowired
    private PeerReviewAssignmentMapper peerReviewAssignmentMapper;

    public void insertPeerReviewAssignment(String revieweeNo, String reviewerNo, String homeworkNo, LocalDateTime startTime, LocalDateTime endTime) {
        peerReviewAssignmentMapper.insertPeerReviewAssignment(revieweeNo, reviewerNo, homeworkNo, startTime, endTime);
    }

    public void updateReviewStatus(String revieweeNo, String reviewerNo, String homeworkNo, boolean reviewStatus) {
        peerReviewAssignmentMapper.updateReviewStatus(reviewStatus, revieweeNo, reviewerNo, homeworkNo);
    }

    public void updateGradeAndComment(int grade, String comment, String revieweeNo,String reviewerNo, String homeworkNo){
        peerReviewAssignmentMapper.updateGradeAndComment(grade,comment,revieweeNo,reviewerNo,homeworkNo);
    }

    public PeerReviewAssignment selectByPeerAndHomework(String revieweeNo, String reviewerNo, String homeworkNo) {
        return peerReviewAssignmentMapper.selectByPeerAndHomework(revieweeNo, reviewerNo, homeworkNo);
    }

    public List<PeerReviewAssignment> selectByRevieweeNo(String revieweeNo) {
        return peerReviewAssignmentMapper.selectByRevieweeNo(revieweeNo);
    }

    public List<PeerReviewAssignment> selectByReviewerNo(String reviewerNo) {
        return peerReviewAssignmentMapper.selectByReviewerNo(reviewerNo);
    }

    public List<PeerReviewAssignment> selectByHomeworkNo(String homeworkNo) {
        return peerReviewAssignmentMapper.selectByHomeworkNo(homeworkNo);
    }

    public List<PeerReviewAssignment> selectByRevieweeAndHomework(String revieweeNo,String homeworkNo) {
        return peerReviewAssignmentMapper.selectByRevieweeAndHomework(revieweeNo,homeworkNo);
    }


    public List<PeerReviewAssignment> selectAllAssignment() {
        return peerReviewAssignmentMapper.selectAllAssignment();
    }

    public int deletePeerAndHomework(String revieweeNo, String reviewerNo, String homeworkNo) {
        return peerReviewAssignmentMapper.deleteByPeerAndHomework(revieweeNo, reviewerNo, homeworkNo);
    }
}
