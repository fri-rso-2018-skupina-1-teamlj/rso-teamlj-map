package si.fri.rso.teamlj.map.services.beans;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.teamlj.map.services.configuration.AppProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
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
        //baseUrl = "http://localhost:8082"; // map
    }

    public List<si.fri.rso.teamlj.map.entities.MapEntity> getMap(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, si.fri.rso.teamlj.map.entities.MapEntity.class, queryParameters);

    }

    public si.fri.rso.teamlj.map.entities.MapEntity getMap(Integer mapId) {

        si.fri.rso.teamlj.map.entities.MapEntity map = em.find(si.fri.rso.teamlj.map.entities.MapEntity.class, mapId);

        if (map == null) {
            throw new NotFoundException();
        }

        return map;
    }

    public List<si.fri.rso.teamlj.map.entities.MapEntity> getMapFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, si.fri.rso.teamlj.map.entities.MapEntity.class, queryParameters);
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
