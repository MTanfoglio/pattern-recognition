package service;


import bean.LinesResult;
import bean.Points;
import servlet.Point;

import java.util.*;
import java.util.stream.Collectors;

public class RecognitionService {

    final LinesResult segments = new LinesResult();

    /*
    * Di base il metodo ritorna una lista di segmenti (Points).
    * Questi sono composti dai punti identificati dalla retta comune che li interseca.
    *
    * Viene sfruttato il principio per cui partendo da un punto di origine, i restanti punti del segmento
    * sono quelli che rispetto ad esso hanno ugual angolazione.
    *
    * */
    public LinesResult retrieveCollinearPoints(final Points space, final int threshold){

        List<Point> pointsInSpace = space.getPoints();

        //Ciclo su ogni punto presente nello spazio
        for(int i=0; i < pointsInSpace.size(); i++){

            Point pTest = pointsInSpace.get(i);

            //Mappa che conterrà il raggruppamento dei punti per angolazione
            Map<Double, List<Point>> res = new HashMap<>();

            //Ciclo per il confronto dell'angolazione
            for(int j=0; j < pointsInSpace.size(); j++){

                Double slp = pTest.angle(pointsInSpace.get(j));

                if (res.containsKey(slp)){
                    List<Point> sameAngle = res.get(slp);
                    sameAngle.add(pointsInSpace.get(j));
                }else{
                    List<Point> newAngle = new LinkedList<>();
                    newAngle.add(pointsInSpace.get(j));
                    newAngle.add(pointsInSpace.get(i));
                    res.put(slp, newAngle);
                }
            }
            verifyResult(res, threshold);
        }

        return segments;

    }

    //Verifica che i segmenti trovati abbiano un numero di punti >= ad N
    private void verifyResult(Map<Double, List<Point>> res, int threshold){

        List<List<Point>> test = res.entrySet().stream()
                .filter(entry -> entry.getValue().size() >= threshold)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        segments.addSegments(test);

    }
}
