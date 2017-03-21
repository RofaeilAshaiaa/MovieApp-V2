package idea.rofaeil.ashaiaa.myapplication.Objects;

/**
 * Created by Matrix on 3/12/2017.
 */

public class Review {

    private String Author ;
    private String ReviewContent ;

    public Review() {
    }

    public Review(String author, String reviewContent) {
        Author = author;
        ReviewContent = reviewContent;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getReviewContent() {
        return ReviewContent;
    }

    public void setReviewContent(String reviewContent) {
        ReviewContent = reviewContent;
    }
}
