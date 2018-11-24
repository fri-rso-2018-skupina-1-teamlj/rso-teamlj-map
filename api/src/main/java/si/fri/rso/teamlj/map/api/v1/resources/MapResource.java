package si.fri.rso.teamlj.map.api.v1.resources;

import si.fri.rso.teamlj.map.entities.MapEntity;
import si.fri.rso.teamlj.map.services.beans.MapBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("/map")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MapResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private MapBean mapBean;

    @GET
    public Response getMaps() {

        List<MapEntity> map = mapBean.getMaps();

        return Response.ok(map).build();
    }

    @GET
    @Path("/{mapId}")
    public Response getMap(@PathParam("mapId") Integer mapId) {

        MapEntity map = mapBean.getMap(mapId);

        if (map == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(map).build();
    }

    @POST
    public Response createMap(MapEntity mapEntity) {

        mapEntity = mapBean.createMap(mapEntity);

        if (mapEntity.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(mapEntity).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(mapEntity).build();
        }
    }

    @PUT
    @Path("/{mapId}")
    public Response putMap(@PathParam("mapId") Integer mapId, MapEntity mapEntity) {

        mapEntity = mapBean.putMap(mapId, mapEntity);

        if (mapEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (mapEntity.getId() != null)
                return Response.status(Response.Status.OK).entity(mapEntity).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("/{mapId}")
    public Response deleteMap(@PathParam("mapId") Integer mapId) {

        boolean deleted = mapBean.deleteMap(mapId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
