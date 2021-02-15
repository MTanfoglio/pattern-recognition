package bean;

import lombok.Data;
import servlet.Point;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Data
public class LinesResult {

    private List<Points> segments;

    public LinesResult(){
        this.segments = new LinkedList<>();
    }

    public void addSegments(List<List<Point>> segments){
        for(List<Point> toAdd : segments){
            this.addSegment(toAdd);
        }
    }

    private void addSegment(List<Point> toAdd){
        Collections.sort(toAdd);

        if(!checkDuplicate(toAdd)){
            Points pts = new Points(toAdd);
            segments.add(pts);
        }
    }

    /*
    * La verifica di un duplicato si basa sull'ordinare il segmento e verificarne gli estremi.
    * Se gli estremi coincidono si può desumere che si tratti dello stesso segmento,
    * quindi lo stesso non verrà inserito nuovamente tra i risultati.
    */
    private boolean checkDuplicate(final List<Point> toAdd) {
        for(Points current : segments){

            List<Point> currentListOfPoints = current.getPoints();
            Collections.sort(currentListOfPoints);

            Point toAddStartingPoint = toAdd.get(0);
            Point toAddEndingPoint = toAdd.get(toAdd.size() -1 );

            if(toAddStartingPoint.compareTo(currentListOfPoints.get(0)) == 0 &&
                toAddEndingPoint.compareTo(currentListOfPoints.get(currentListOfPoints.size()-1)) == 0

            ) {
                return true;
            }
        }
        return false;
    }
}
