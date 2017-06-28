package programm;

import android.os.AsyncTask;

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
        if(strings.length>0) {
            if (strings[0].equals("login")) {
                Lib.login();
            }else if (object.getClass()==Block.class&&strings[0].equals("block")){
                Lib.readUrl(strings[1]);
            }else if (object.getClass()==Orchestrator.class&&strings[0].equals("getBlockList")){
                Lib.readUrl(Lib.baseURL+"userBlockList");
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (parameter.length>0){
            if (object.getClass()==Block.class && parameter[0].equals("block")) {
                Block b = (Block) object;
                b.loadResponse(s);
            }else if (object.getClass()==Orchestrator.class && parameter[0].equals("getBlockList")){

            }
        }
    }
}
