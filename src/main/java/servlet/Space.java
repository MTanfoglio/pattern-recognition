package servlet;

import bean.Response;
import com.google.gson.Gson;
import service.SpaceStore;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@WebServlet("/space")
public class Space extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final SpaceStore spaceStore = SpaceStore.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        //TODO: handle Exception - log messages
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.print(new Gson().toJson(spaceStore.getPoints()));
        out.flush();
    }

    @Override
    protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp) throws IOException{
        spaceStore.deleteAllPoints();
        Response responseMessage = new Response ("success", "all points were deleted");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.print(new Gson().toJson(responseMessage, Response.class));
        out.flush();
    }





}
