package nl.hu.bep.persistence;

import nl.hu.bep.countrycase.model.World;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class PersistenceManager {
    public static void loadWorldFromAzure() {
        try {
            InputStream is = Files.newInputStream(Path.of("/home/world.obj"));
            ObjectInputStream ois = new ObjectInputStream(is);
            World loadedWorld = (World) ois.readObject();
            ois.close();

            World.setWorld(loadedWorld);
        } catch (Exception e) {
            System.out.println("Failed to load the world...");
            e.printStackTrace();
        }
    }

    public static void saveWorldToAzure() {
        World worldToSave = World.getWorld();

        try {
            OutputStream os = Files.newOutputStream(Path.of("/home/world.obj"));
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(worldToSave);
            oos.close();
        } catch (Exception e) {
            System.out.println("Failed to save the world...");
            e.printStackTrace();
        }
    }
}
