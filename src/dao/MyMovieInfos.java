package dao;

import javax.persistence.*;

@Entity
@Table(name="MovieInfos")
public class MyMovieInfos {

    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="code" , unique = true, nullable = false)
    private Integer code;

    @Column(name="title")
    private String title;

    @Column(name="productionYear")
    private Integer productionYear;

    @Column(name="poster")
    private String poster;

    @Lob
    @Column(name="synopsisShort")
    private String synopsisShort;

    public MyMovieInfos(Integer code, String title, Integer productionYear, String poster, String synopsisShort) {
        this.code = code;
        this.title = title;
        this.productionYear = productionYear;
        this.poster = poster;
        this.synopsisShort = synopsisShort;
    }

    public MyMovieInfos() {

    }

    public Integer getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public String getPoster() {
        return poster;
    }

    public String getSynopsisShort() {
        return synopsisShort;
    }

    /*private Integer code;
    private String[] directors;
    private String distributor;
    private String[] festivalAwards;
    private String[] genres;
    private String[] nationalities;
    private String originalTitle;
    private String productionYear;
    private String releaseDate;
    private String Runtime;
    private String Synopsis;
    private String Title;
    private String UserRating;
    private String Actors;
    private String Certification;
    private String Writers;
    private String Art;
    private String Camera;
    private String Movie;
    private String Posters;
    private String PressRating;
    private String Producers;
    private String ReleaseCountry;
    private String SynopsisShort;*/
}
