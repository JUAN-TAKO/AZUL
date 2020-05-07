package Controller.Server;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

import Controller.Controller;
import Model.GlobalBoard;


public class JavaHTTPServer implements Runnable{

    // Client Connection via Socket Class
    private Socket connect;

    public JavaHTTPServer(Socket c) {
        connect = c;
    }

    @Override
    public void run() {

        // we manage our particular client connection
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        String url = null;

        try {
            // To read required url in input stream on the socket
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            // For headers
            out = new PrintWriter(connect.getOutputStream());
            // For JSON
            dataOut = new BufferedOutputStream(connect.getOutputStream());


            // get first line of the request from the client
            String input = in.readLine();
            // we parse the request with a string tokenizer
            StringTokenizer parse = new StringTokenizer(input);
            String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
            // we get file requested
            url = parse.nextToken();

            System.out.println(method);

            // we support only GET and HEAD methods, we check
            if (!method.equals("GET")  &&  !method.equals("HEAD") && !method.equals("POST") && !method.equals("OPTIONS") ) {
                // we send HTTP Headers with data to client
//                out.println("HTTP/1.1 501 Not Implemented");
//                out.println("Server: Java HTTP Server");
//                out.println("Date: " + new Date());
//                out.println("Content-type: application/json");
//                out.println(); // blank line between headers and content, VERY IMPORTANT !
//                out.flush();
//                // file
//                dataOut.write("{\"501\":\"Not Implemented\"}".getBytes());
//                dataOut.flush();

                send501(out, dataOut);

            } else {

                if (method.equals("GET")) { // GET method so we return content

                    JSONObject jsonObject = new JSONObject("{}");
                    System.out.println(url);

                    //CREATION OF JSON IN FUNCTION OF THE URL
                    //!!! LES URL SONT TOUJOURS EN MINISCULE DU COTE SERVER MEME SI L'URL DU NAVIGATEUR EST EN MAJUSCULE Teste => teste
                    switch(url) {
                    	case "/getGameStatus":
                            send200(out);
                    		jsonObject.put("onGoing", Controller.getInstance().isOnGoing());
                    		break;
                        case "/getBoard":
                            send200(out);
                        	GlobalBoard gb = Controller.getInstance().getBoard();
                        	if(gb != null) 
                        		jsonObject.put("GlobalBoard",gb.toJSON());
                            break;
                        default:
                            send404(out);
//                            jsonString = "{\"404\":\"Demande inconnue\"}";
                            jsonObject.put("404","Demande inconnue");
                            break;
                    }

//                    // send HTTP Headers
//                    out.println("HTTP/1.1 200 OK");
//                    out.println("Server: Java HTTP Server");
//                    out.println("Date: " + new Date());
//                    out.println("Content-type: " + "application/json");
//                    out.println("Access-Control-Allow-Origin: *");
//                    out.println(); // blank line between headers and content, VERY IMPORTANT !
//                    out.flush(); // flush character output stream buffer

                    dataOut.write(jsonObject.toString().getBytes());
                    dataOut.flush();
                } else if(method.equals("POST")) {
                    int contentLength = 0;

                    while(!input.equals("")) {

                        if(input.contains("Content-Length:"))
                            contentLength = Integer.parseInt(input.replace("Content-Length: ",""));

                        input = in.readLine();
                    }

                    //VALUE OF THE POST
                    char[] cbuf = new char[contentLength+1];
                    in.read(cbuf,0,contentLength);
                    input = new String(cbuf);

                    JSONObject jsonObjectIn = new JSONObject(input);
                    JSONObject jsonObjectOut = new JSONObject("{}");

                    switch(url) {
                    	case "/startGame":
                    		int nPlayers = (int) jsonObjectIn.get("nPlayers");
                    		JSONArray jsonArray = jsonObjectIn.getJSONArray("AI");
                    		boolean[] AI = Utils.Utils.toBooleanArray(jsonArray);
                    		String[] names = Utils.Utils.toStringArray(jsonObjectIn.getJSONArray("names"));
                    		Controller.getInstance().startGame(nPlayers, names, AI);
                            send200(out);
                    		break;
                    	case "/playMove":
                    		int factory = (int) jsonObjectIn.get("factory");
                    		int color = (int) jsonObjectIn.get("color");
                    		int line = (int) jsonObjectIn.get("line");
                    		jsonObjectOut.put("value", Controller.getInstance().playMove(factory, color, line));
                            send200(out);
                    		break;
                        default :
                            send404(out);
                            break;
                    }

                    dataOut.write(jsonObjectOut.toString().getBytes());
                    dataOut.flush();
                    
                } else if(method.equals("OPTIONS")) {

                    // send HTTP Headers
                    out.println("HTTP/1.1 204 No Content");
                    out.println("Server: Java HTTP Server");
                    out.println("Date: " + new Date());
                    out.println("Content-type: text/plain, application/json");
                    out.println("Access-Control-Allow-Origin: http://localhost:8080");
                    out.println("Access-Control-Allow-Methods: POST, GET, OPTIONS");
                    out.println("Access-Control-Allow-Headers: X-PINGOTHER, Content-Type");
                    out.println("Access-Control-Max-Age: 86400");
                    out.println("Vary: Accept-Encoding, Origin");
                    out.println("Keep-Alive: timeout=2, max=100");
                    out.println("Connection: Keep-Alive");
                    out.println(); // blank line between headers and content, VERY IMPORTANT !
                    out.flush(); // flush character output stream buffer

                }
            }

        } catch (IOException | JSONException ioe) {
            System.err.println("Server error : " + ioe);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
                in.close();
                out.close();
                dataOut.close();
                connect.close(); // we close socket connection
            } catch (Exception e) {
                System.err.println("Error closing stream : " + e.getMessage());
            }
        }
    }

    public void send501(PrintWriter out, BufferedOutputStream dataOut) throws IOException {
        out.println("HTTP/1.1 501 Not Implemented");
        out.println("Server: Java HTTP Server");
        out.println("Date: " + new Date());
        out.println("Content-type: application/json");
        out.println(); // blank line between headers and content, VERY IMPORTANT !
        out.flush();
        // file
        dataOut.write("{\"501\":\"Not Implemented\"}".getBytes());
        dataOut.flush();
    }

    public void send404(PrintWriter out) throws IOException {
        out.println("HTTP/1.0 404 Not Found");
        out.println("Server: Java HTTP Server");
        out.println("Date: " + new Date());
        out.println(); // blank line between headers and content, VERY IMPORTANT !
        out.flush(); // flush character output stream buffer
    }

    public void send200(PrintWriter out) throws IOException {

        // send HTTP Headers
        out.println("HTTP/1.1 200 OK");
        out.println("Server: Java HTTP Server");
        out.println("Date: " + new Date());
        out.println("Content-type: text/plain, application/json");
        out.println("Access-Control-Allow-Origin: *");

        out.println("Vary: Accept-Encoding, Origin");
        out.println("Keep-Alive: timeout=2, max=100");
        out.println("Connection: Keep-Alive");


        out.println(); // blank line between headers and content, VERY IMPORTANT !
        out.flush(); // flush character output stream buffer
    }
}
