package si.fri.rso.teamlj.map.services.beans;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rso.teamlj.map.entities.MapEntity;
import si.fri.rso.teamlj.map.services.configuration.AppProperties;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ManagedBean
@ApplicationScoped
@Named
public class MapBean {

    private Logger log = Logger.getLogger(MapBean.class.getName());

    private Client httpClient;

    //private String baseUrl;

    @Inject
    @DiscoverService("rso-map")
    private Optional<String> baseUrl;

    @Inject
    private EntityManager em;

    @Inject
    private AppProperties appProperties;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        //baseUrl = "http://localhost:8084"; // map
    }

    @Timed(name = "get_maps_timed")
    @Counted(name = "get_maps_counter")
    @CircuitBreaker(requestVolumeThreshold = 3)
    @Timeout(value = 5, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "getMapsFallback")
    public List<MapEntity> getMaps() {

        TypedQuery<MapEntity> query = em.createNamedQuery("MapEntity.getAll", MapEntity.class);

        return query.getResultList();

    }

    public List<MapEntity> getMapsFallback() {

        log.warning("getMapsFallback called");
        return Collections.emptyList();

    }

    @Timed(name = "get_mapsAsString_timed")
    @Counted(name = "get_mapsAsString_counter")
    @CircuitBreaker(requestVolumeThreshold = 3)
    @Timeout(value = 5, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "getMapsAsStringFallback")
    public String getMapsAsString() {

        TypedQuery<MapEntity> query = em.createNamedQuery("MapEntity.getAll", MapEntity.class);

        List<MapEntity> result = query.getResultList();

        StringBuilder stringBuilder = new StringBuilder();

        for (MapEntity mapEntity : result) {
            stringBuilder.append(mapEntity.getLocationString());
        }

        return stringBuilder.toString();
    }

    public String getMapsAsStringFallback() {

        log.warning("getMapsAsStringFallback called");
        return "";

    }

    @Timed(name = "get_map_timed")
    @Counted(name = "get_map_counter")
    @CircuitBreaker(requestVolumeThreshold = 3)
    @Timeout(value = 5, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "getMapFallback")
    public MapEntity getMap(Integer mapId) {

        MapEntity map = em.find(MapEntity.class, mapId);

        if (map == null) {
            log.warning("map does not exist/map was deleted");
            throw new NotFoundException();
        }

        return map;
    }

    public MapEntity getMapFallback(Integer mapId) {

        log.warning("getMapFallback called");
        return new MapEntity();
    }

    public MapEntity createMap(MapEntity mapEntity) {

        try {
            beginTx();
            em.persist(mapEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return mapEntity;
    }

    public MapEntity putMap(Integer mapId, MapEntity mapEntity) {

        MapEntity map = em.find(MapEntity.class, mapId);

        if (map == null) {
            return null;
        }

        try {
            beginTx();
            mapEntity.setId(map.getId());
            mapEntity = em.merge(mapEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return mapEntity;
    }

    public boolean deleteMap(Integer mapId) {

        MapEntity mapEntity = em.find(MapEntity.class, mapId);

        if (mapEntity != null) {
            try {
                beginTx();
                em.remove(mapEntity);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
