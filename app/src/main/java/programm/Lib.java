package programm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kevin on 07.06.2017.
 * In der programm.Lib stehen hilfsmethoden, die von mehrern Instanzen verwendet werden. Diese Mehtoden müssen nich nicht instanziiert werden.
 */

public class Lib {

    private static String sessionID="";
    public static final String baseURL = "http://thebest.sysgame.de/run.cgi/";

    public static String getSessionID(){return sessionID;}

    public static String readUrl(String urlString) {
        String result="";
        URL url;
        try {
            url = new URL(urlString);
            String sCookie ="";
            if (!sessionID.equals("")) {
                sCookie = "dancer.session=" + sessionID + ";";
            }
            HttpURLConnection httpconn  = (HttpURLConnection) url.openConnection();
            if(sCookie!=null && sCookie.length()>0) {
                httpconn.setRequestProperty("Cookie", sCookie);
            }
            InputStreamReader reader = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(reader);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result+=inputLine;
            }
            if (httpconn.getHeaderFields().get("Set-Cookie")!=null) {
                String mydata = httpconn.getHeaderFields().get("Set-Cookie").get(0);
                Pattern pattern = Pattern.compile("dancer.session=(.*?);");
                Matcher matcher = pattern.matcher(mydata);
                if (matcher.find()) {
                    sessionID = matcher.group(1);
                }
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
