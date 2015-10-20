package com.myapp;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/plans" })
public class PlansServlet extends HttpServlet {
    private volatile static AtomicInteger requestId = new AtomicInteger(0);
    private volatile static Random random = new Random();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Jsonb jsonb = JsonbBuilder.create();

		ServletOutputStream out = response.getOutputStream();
		out.print(jsonb.toJson(new JsonPlans()));
		out.flush();
    }

    public static class JsonPlans {
        public final Date date;
        public final Integer id;
        public final String response;

        public JsonPlans() {
            date = new Date();
            id = requestId.addAndGet(1);

            int days = getRandomDays();
            if (days < 950) {
                response = "Jason will come after you in " + days + " days.";
            } else {
                response = "Jason is not interested in you.";
            }
        }

        private Integer getRandomDays() {
            return random.nextInt(1000) + 1;
        }
    }
}
