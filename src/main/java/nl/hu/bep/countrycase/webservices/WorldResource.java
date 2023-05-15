package nl.hu.bep.countrycase.webservices;

import nl.hu.bep.countrycase.model.Country;
import nl.hu.bep.countrycase.model.World;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/countries")
public class WorldResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCountries() {
        World world = World.getWorld();
        List<Country> allCountries = world.getAllCountries();

        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Country country : allCountries) {
            jab.add(convertCountryToJSON(country));
        }

        return jab.build().toString();
    }

    @GET
    @Path("/largestsurfaces")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLargestCountries() {
        World world = World.getWorld();
        List<Country> allCountries = world.get10LargestSurfaces();

        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Country country : allCountries) {
            jab.add(convertCountryToJSON(country));
        }

        return jab.build().toString();
    }

    @GET
    @Path("/largestpopulations")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLargestPopulations() {
        World world = World.getWorld();
        List<Country> allCountries = world.get10LargestPopulations();

        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Country country : allCountries) {
            jab.add(convertCountryToJSON(country));
        }

        return jab.build().toString();
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCountryByCode(@PathParam("code") String code) {

        Country country = World.getWorld().getCountryByCode(code);

        return convertCountryToJSON(country).build().toString();
    }


    private JsonObjectBuilder convertCountryToJSON(Country country) {
        JsonObjectBuilder job = Json.createObjectBuilder();

        job.add("code", country.getCode());
        job.add("iso3", country.getIso3());
        job.add("name", country.getName());

        return job;
    }
}
