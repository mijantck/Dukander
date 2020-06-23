package com.mrsoftit.dukander.modle;

public class ReviewComentNote {
    private String reviewID;
    private String reviewCustomerID;
    private String reviewCustomerName;
    private String reviewComment;
    private int dateAndTime;

    public ReviewComentNote(){}

    public ReviewComentNote(String reviewID, String reviewCustomerID, String reviewCustomerName, String reviewComment, int dateAndTime) {
        this.reviewID = reviewID;
        this.reviewCustomerID = reviewCustomerID;
        this.reviewCustomerName = reviewCustomerName;
        this.reviewComment = reviewComment;
        this.dateAndTime = dateAndTime;
    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getReviewCustomerID() {
        return reviewCustomerID;
    }

    public void setReviewCustomerID(String reviewCustomerID) {
        this.reviewCustomerID = reviewCustomerID;
    }

    public String getReviewCustomerName() {
        return reviewCustomerName;
    }

    public void setReviewCustomerName(String reviewCustomerName) {
        this.reviewCustomerName = reviewCustomerName;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public int getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(int dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
