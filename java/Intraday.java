package app.com.stapp;

/**
 * Created by AKASH on 1/12/2018.
 */

public class Intraday {

    public String intra_title, intra_desc, intra_image;


    public Intraday() {
    }

    public Intraday(String intra_title, String intra_desc, String intra_image) {
        this.intra_title = intra_title;
        this.intra_desc = intra_desc;
        this.intra_image = intra_image;
    }


    //required getter and setter

    public String getIntra_title() {
        return intra_title;
    }

    public void setIntra_title(String intra_title) {
        this.intra_title = intra_title;
    }

    public String getIntra_desc() {
        return intra_desc;
    }

    public void setIntra_desc(String intra_desc) {
        this.intra_desc = intra_desc;
    }

    public String getIntra_image() {
        return intra_image;
    }

    public void setIntra_image(String intra_image) {
        this.intra_image = intra_image;
    }

}
