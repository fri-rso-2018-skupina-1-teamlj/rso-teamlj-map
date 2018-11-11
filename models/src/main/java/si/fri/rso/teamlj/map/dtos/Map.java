package si.fri.rso.teamlj.map.dtos;

import lombok.Getter;
import lombok.Setter;

public class Map {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private float longitude;

    @Getter
    @Setter
    private float latitude;

    @Getter
    @Setter
    private String location;

    @Getter
    @Setter
    private int freeBikes;

}