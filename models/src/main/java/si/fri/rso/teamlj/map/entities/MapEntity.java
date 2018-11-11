package si.fri.rso.teamlj.map.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "mapTable")
@NamedQueries(value =
        {
                @NamedQuery(name = "MapEntity.getAll", query = "SELECT m FROM mapTable m")
        })
public class MapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    @Column(name = "longitude")
    private float longitude;

    @Getter
    @Setter
    @Column(name = "longitude")
    private float latitude;

    @Getter
    @Setter
    @Column(name = "location")
    private String location;

    @Column(name = "bike_id")
    @Getter
    @Setter
    private Integer bikeId;

    @Getter
    @Setter
    @Column(name = "freeBikes")
    private int freeBikes;

//    @Transient
//    @Getter
//    @Setter
//    private List<Map> maps;

}
