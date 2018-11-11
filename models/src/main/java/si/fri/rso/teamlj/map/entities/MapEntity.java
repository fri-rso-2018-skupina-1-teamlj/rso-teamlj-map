package si.fri.rso.teamlj.map.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity(name = "map")
@NamedQueries(value =
        {
                @NamedQuery(name = "MapEntity.getAll", query = "SELECT r FROM map r")
        })
public class MapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private Instant numberOfBikes;

    @Column(name = "bike_id")
    @Getter
    @Setter
    private Integer bikeId;

    @Transient
    @Getter
    @Setter
    private List<si.fri.rso.teamlj.map.dtos.Map> maps;

}
