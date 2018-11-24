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

    @Column(name = "latitude")
    @Getter
    @Setter
    private float latitude;

    @Column(name = "longitude")
    @Getter
    @Setter
    private float longitude;

    @Column(name = "locationString")
    @Getter
    @Setter
    private String locationString;

    @Column(name = "locationName")
    @Getter
    @Setter
    private String locationName;

    @Column(name = "numberOfAvailableBikes")
    @Getter
    @Setter
    private int numberOfAvailableBikes;

}
