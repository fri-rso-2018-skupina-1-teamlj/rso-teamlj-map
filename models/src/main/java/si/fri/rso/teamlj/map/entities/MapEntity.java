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

    @Column(name = "longitude")
    @Getter
    @Setter
    private float longitude;

    @Column(name = "latitude")
    @Getter
    @Setter
    private float latitude;

    @Column(name = "location")
    @Getter
    @Setter
    private String location;

//    @Column(name = "bike_id")
//    @Getter
//    @Setter
//    private Integer bikeId;

    @Column(name = "freeBikes")
    @Getter
    @Setter
    private int freeBikes;

//    @Transient
//    @Getter
//    @Setter
//    private List<Map> maps;

}
