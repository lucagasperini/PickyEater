package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.Review;
import com.pickyeaters.logic.utils.QueryProcedure;

public class ReviewDAO {

    public void add(Review review) throws DAOException {
        switch (review.getGrade()) {
            case POOR -> addReviewPoor(review.getUser().getID(), review.getDish().getID());
            case FAIR -> addReviewFair(review.getUser().getID(), review.getDish().getID());
            case AVERAGE -> addReviewAverage(review.getUser().getID(), review.getDish().getID());
            case GOOD -> addReviewGood(review.getUser().getID(), review.getDish().getID());
            case EXCELLENT -> addReviewExcellent(review.getUser().getID(), review.getDish().getID());
        }
    }

    private void addReview(String userID, String dishID, String procedure) throws DAOException {
        try {
            QueryProcedure query = DatabaseController.getInstance().queryProcedure(
                    procedure
            );
            query.setString(userID);
            query.setString(dishID);

            query.execute();
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    private void addReviewPoor(String userID, String dishID) throws DAOException {
        addReview(userID, dishID, "CALL add_review_poor(?, ?)");
    }

    private void addReviewFair(String userID, String dishID) throws DAOException {
        addReview(userID, dishID,"CALL add_review_fair(?, ?)");
    }

    private void addReviewAverage(String userID, String dishID) throws DAOException {
        addReview(userID, dishID,"CALL add_review_average(?, ?)");
    }

    private void addReviewGood(String userID, String dishID) throws DAOException {
        addReview(userID, dishID,"CALL add_review_good(?, ?)");
    }

    private void addReviewExcellent(String userID, String dishID) throws DAOException {
        addReview(userID, dishID,"CALL add_review_excellent(?, ?)");
    }
}
