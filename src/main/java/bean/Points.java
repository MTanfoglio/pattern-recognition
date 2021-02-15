package bean;

import lombok.Data;
import servlet.Point;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Points {
    List<Point> points;

    public Points(List<Point> pts){
        points = pts;
    }

    public void addPoint(final Point toAdd){
        this.points.add(toAdd);
    }

    public void removeAllPoints(){
        this.points.clear();
    }

    public boolean isPresent(final Point p){

        List<Point> result = points.stream()
                .filter((x) -> x.getX() == p.getX() && x.getY() == p.getY())
                .collect(Collectors.toList());

        return !result.isEmpty();
    }

}
