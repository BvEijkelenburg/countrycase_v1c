package nl.hu.bep.countrycase.webservices;

import nl.hu.bep.countrycase.model.Country;
import nl.hu.bep.countrycase.model.World;

import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.StringReader;
import java.util.List;

@Path("/countries")
public class WorldResource {

    @POST
    @Path("/demo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postMessage(String jsonBody) {
        StringReader strReader = new StringReader(jsonBody);
        JsonReader jsonReader = Json.createReader(strReader);

        JsonArray students = jsonReader.readArray();

        for (JsonValue student : students) {
            var naam = ((JsonObject)student).getString("voornaam");
            var leeftijd = ((JsonObject)student).getJsonNumber("leeftijd").intValue();

            System.out.println(naam);
            System.out.println(leeftijd);
        }

        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("result", "Message received!");
        return job.build().toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postCountry(String jsonBody) {
        StringReader strReader = new StringReader(jsonBody);
        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject newCountry = jsonReader.readObject();

        String code = newCountry.getString("code");
        String iso3 = newCountry.getString("iso3");
        String name = newCountry.getString("name");
        String capital = newCountry.getString("capital");
        String continent = newCountry.getString("continent");
        String region = newCountry.getString("region");
        double surface = Double.parseDouble(newCountry.getString("surface"));
        int population = Integer.parseInt(newCountry.getString("population"));
        String government = newCountry.getString("government");
        double latitude = Double.parseDouble(newCountry.getString("latitude"));
        double longitude = Double.parseDouble(newCountry.getString("longitude"));

        List<Country> countries = World.getWorld().getAllCountries();
        for (Country country : countries) {
            if (country.getCode().equals(code)) {
                JsonObjectBuilder job = Json.createObjectBuilder();
                job.add("result", "Country already exists!");
                return job.build().toString();
            }
        }

        Country c = new Country(code, iso3, name, capital, continent, region, surface, population, government, latitude, longitude);
        countries.add(c);

        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("result", "Country added!");
        return job.build().toString();
    }

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
