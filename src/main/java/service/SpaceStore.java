package service;

import bean.Points;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import lombok.Getter;
import servlet.Point;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SpaceStore {

    //Lo spazio viene "persistito" su un json e lo store rappresentato come singleton
    private static final String SPACE_STORE_PATH = "/space.json";

    private static final SpaceStore instance = new SpaceStore();

    @Getter
    private final Points points;

    public static SpaceStore getInstance(){
        return instance;
    }

    private SpaceStore(){
        this.points = getStoredPoints();
    }

    // Recupero dei punti presenti nello spazio.
    public static Points getStoredPoints(){

        URL url = Resources.getResource(SPACE_STORE_PATH);
        String resourceContent = null;

        try {
            resourceContent = Resources.toString(url, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Something wrong while reading resource! - we should log it");
        }

        return new Gson().fromJson(resourceContent, Points.class);
    }

    public void addPointToSpace(final Point toAdd) throws IOException{
        this.points.addPoint(toAdd);
        this.savePoints();
    }

    public void deleteAllPoints() throws IOException{
        this.points.removeAllPoints();
        this.savePoints();
    }

    public void savePoints() throws IOException {
        if (new File(SPACE_STORE_PATH).isFile()) {
            new Gson().toJson(this.points, new FileWriter(SPACE_STORE_PATH));
        }
    }

    public boolean isEmpty(){
        return this.points == null || this.points.getPoints().isEmpty();
    }

}
