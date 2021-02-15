package servlet;

import bean.LinesResult;
import bean.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import service.RecognitionService;
import service.SpaceStore;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/lines/*")
public class Lines extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final SpaceStore space = SpaceStore.getInstance();

    private final RecognitionService recognitionService = new RecognitionService();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            //Recupero soglia N
            String pathInfo = req.getPathInfo();

            if (pathInfo != null){

                int n = Integer.parseInt(pathInfo.split("/")[1]);

                if (space.isEmpty()) {
                    sendResponse(resp, new Response("error", "Space is empty!"));
                } else {
                    sendResponse(resp, recognitionService.retrieveCollinearPoints(space.getPoints(), n));
                }

            }else{
                sendResponse(resp, new Response("error", "Empty path parameter N!"));
            }

        }catch(IOException | NumberFormatException nfe){
            System.out.println("Log exception properly");
        }
    }

    private <T> void sendResponse(final HttpServletResponse resp, final T responseBean) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.print(new Gson().toJson(responseBean, new TypeToken<T>(){}.getType()));
        out.flush();
    }
}
