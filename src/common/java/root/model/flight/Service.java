package common.java.root.model.flight;

import javax.persistence.*;

@Entity
@Table

public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true)
    private Long id;
    private int cateringRating;
    private int comfortRating;
    private String comments;

    @OneToOne(mappedBy = "service")
    private PlaneSeat planeSeat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCateringRating() {
        return cateringRating;
    }

    public void setCateringRating(int cateringRating) {
        this.cateringRating = cateringRating;
    }

    public int getComfortRating() {
        return comfortRating;
    }

    public void setComfortRating(int comfortRating) {
        this.comfortRating = comfortRating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public PlaneSeat getPlaneSeat() {
        return planeSeat;
    }

    public void setPlaneSeat(PlaneSeat planeSeat) {
        this.planeSeat = planeSeat;
    }
}
