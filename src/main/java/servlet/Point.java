package servlet;

import bean.Points;
import bean.Response;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.SpaceStore;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@NoArgsConstructor
@WebServlet("/point")
public class Point extends HttpServlet implements Comparable<Point> {
    private static final long serialVersionUID = 1L;

    private final transient SpaceStore space = SpaceStore.getInstance();

    @Getter
    @Setter
    private int x;

    @Getter
    @Setter
    private int y;

    @Override
    public String toString() {
        return "(x: " + this.x + ", y: " + this.y + ")";
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        Response responseMessage;
        BufferedReader reader = req.getReader();

        Points currentPts = space.getPoints();
        Point toAdd = new Gson().fromJson(reader, Point.class);

        if(currentPts.isPresent(toAdd)){
            responseMessage = new Response("error", String.format("Sorry, this point (x: %o, y: %o) is just in the space", toAdd.getX(), toAdd.getY()));
        }else{
            space.addPointToSpace(toAdd);
            responseMessage = new Response("success", "New point: " + toAdd.toString());
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.print(new Gson().toJson(responseMessage, Response.class));
        out.flush();

    }

    @Override
    public int compareTo(final Point toCompare) {

        if (this.y == toCompare.getY() && this.x == toCompare.getX())
            return 0;

        if (this.y == toCompare.getY())
            return Integer.compare(this.x, toCompare.getX());
        else
            return Integer.compare(this.y, toCompare.getY());

    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    //Calcola angolazione di un angolo rispetto all'attuale
    public double angle(final Point that) {

        if (this.y == that.y)
        {
            if (this.x == that.x)
            {
                return Double.NEGATIVE_INFINITY;
            }
            else
            {
                return 0.0;
            }
        }
        else {
            if (this.x == that.x)
            {
                return Double.POSITIVE_INFINITY;
            }
            else
            {
                return 1.0 * (that.y - this.y) / (that.x - this.x);
            }
        }

    }
}
