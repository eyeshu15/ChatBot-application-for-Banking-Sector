    package text;

    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    import java.io.PrintWriter;

    import ai.api.AIConfiguration;
    import ai.api.AIDataService;
        import ai.api.model.AIRequest;
        import ai.api.model.AIResponse;



    @WebServlet(name = "Client",urlPatterns="/Client")
    public class Client extends HttpServlet {
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request, response);

        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String INPUT_PROMPT = "> ";

            final int ERROR_EXIT_CODE = 1;
            response.setContentType("text\\html");
            PrintWriter write = response.getWriter();
            String key = "b09378b51bf14044aaf32f95cbce29be";
            if (key.length() < 1) {
                showHelp("Please specify API key", ERROR_EXIT_CODE);
            }
            AIConfiguration configuration = new AIConfiguration(key);
            AIDataService dataService = new AIDataService(configuration);
            String line=request.getParameter("INPUT");
            try {
                AIRequest request1 = new AIRequest(line);

                AIResponse response1 = dataService.request(request1);
                if (response1.getStatus().getCode() == 200) {
                    write.println(response1.getResult().getFulfillment().getSpeech());
                } else {
                    write.println(response1.getStatus().getErrorDetails());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        private static void showHelp(String errorMessage, int exitCode) {
            if (errorMessage != null && errorMessage.length() > 0) {
                System.err.println(errorMessage);
                System.err.println();
            }
        }
    }
