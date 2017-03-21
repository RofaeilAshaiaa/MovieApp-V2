package idea.rofaeil.ashaiaa.myapplication.Objects;

/**
 * Created by Matrix on 3/21/2017.
 */

public class Trailer {
    private String VideoThumbURL;
    private String VideoURL ;

    public Trailer(String VideoThumbURL, String videoURL) {

        this.VideoThumbURL = VideoThumbURL;
        VideoURL = videoURL;
    }

    public Trailer() {

    }

    public String getVideoThumbURL() {
        return VideoThumbURL;
    }

    public void setVideoThumbURL(String videoThumbURL) {
        this.VideoThumbURL = videoThumbURL;
    }

    public String getVideoURL() {
        return VideoURL;
    }

    public void setVideoURL(String videoURL) {
        VideoURL = videoURL;
    }
}
