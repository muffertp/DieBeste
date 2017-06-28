package programm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kevin on 07.06.2017.
 * In der programm.Lib stehen hilfsmethoden, die von mehrern Instanzen verwendet werden. Diese Mehtoden müssen nich nicht instanziiert werden.
 */

public class Lib {

    private static String sessionID="";

    public static final String baseURL = "http://thebest.sysgame.de/run.cgi/";

    public static void login(){
        readUrl(baseURL+"userData?email=kprause@gmx.net&password=pass");
    }

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

            if(sCookie!=null && sCookie.length()>0){
                httpconn.setRequestProperty("Cookie", sCookie);
            }
            System.out.println(url.toString());
            //URLConnection urlCon = url.openConnection();
            System.out.println("Alive!");
            InputStreamReader reader = new InputStreamReader(httpconn.getInputStream());
            System.out.println("Still alive");
            BufferedReader in = new BufferedReader(reader);
            System.out.println("STILL ALIVE!");
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                result+=inputLine;
            }

            String headerName = "";


            Map<String, List<String>> header = httpconn.getHeaderFields();

            List<String> cookies = httpconn.getHeaderFields().get("Set-Cookie");

            String mydata = httpconn.getHeaderFields().get("Set-Cookie").get(0);
            Pattern pattern = Pattern.compile("dancer.session=(.*?);");
            Matcher matcher = pattern.matcher(mydata);
            if (matcher.find())
            {
                sessionID = matcher.group(1);
            }
            //System.out.println(urlCon.ge);
            /*
            for (int i = 0; (headerName = urlCon.getHeaderFieldKey(i)) != null; i++)
            {

                System.out.println("Cookie - "+urlCon.getHeaderFieldKey(i)+" :: "+urlCon.getHeaderField(i));

                if(headerName.equals("Set-Cookie"))
                {
                    //cookieValue = urlCon.getHeaderField(i);
                }
            }
            */

            in.close();
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        System.out.println(result);
        return result;
    }

}
