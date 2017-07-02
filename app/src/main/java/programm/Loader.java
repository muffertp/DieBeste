package programm;

import android.content.Intent;
import android.os.AsyncTask;

import com.example.pierrelaptop.diebeste.DisaplyMessageActivity;
import com.example.pierrelaptop.diebeste.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kevin on 22.06.2017.
 */

public class Loader extends AsyncTask<String,Integer,String> {


    private Object object;

    private String[] parameter;

    public Loader(Object obj){
        object=obj;
    }

    @Override
    protected String doInBackground(String... strings) {
        parameter=strings;
        String result="";

        try {
            if(strings.length>0) {
                if (object.getClass()==Block.class&&strings[0].equals("block")){
                    result=Lib.readUrl(strings[1]);
                }else if (object.getClass()==Orchestrator.class&&strings[0].equals("getBlockList")){
                    result=Lib.readUrl(Lib.baseURL+"userBlockList");
                }else if (object.getClass()==MainActivity.class&&strings[0].equals("login")){
                    result=Lib.readUrl(strings[1]);
                }

            }
        } catch ( Exception anyError) {
            result= "error";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        System.out.println("S :::: "+s);
        if ( s.equals("error")||s.equals("") ) {
            System.out.println("onPostError");
            Orchestrator.getOrchestrator().goHome();
        } else {
            if (parameter.length > 0) {
                if (object.getClass() == Block.class && parameter[0].equals("block")) {
                    Block b = (Block) object;
                    b.loadResponse(s);
                } else if (object.getClass() == Orchestrator.class && parameter[0].equals("getBlockList")) {
                    Orchestrator.getOrchestrator().setUserBlockList(s);
                } else if (object.getClass() == MainActivity.class && parameter[0].equals("login")) {
                    MainActivity activity = (MainActivity) object;
                    Orchestrator orchestrator = Orchestrator.getOrchestrator();
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        JSONObject obj = jsonArray.getJSONObject(0);
                        if (obj.getInt("loginState") == 1) {
                            orchestrator.getBlockList();
                            activity.loginSucsess();
                            orchestrator.setLogin(true);
                        } else {
                            orchestrator.logout();
                            activity.loginFail();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
