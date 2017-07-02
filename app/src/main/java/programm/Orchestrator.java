package programm;

import android.content.Intent;
import android.widget.Toast;

import com.example.pierrelaptop.diebeste.DisaplyMessageActivity;
import com.example.pierrelaptop.diebeste.MainActivity;
import com.example.pierrelaptop.diebeste.Uebungen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kevin on 19.06.2017.
 * Es handelt sich hierbei um einen Singelton.
 */

public class Orchestrator{

    private String sessionID;

    private boolean login = false;
    private ArrayList<Block> blockList;
    private int currentBlockNum=0;
    private DisaplyMessageActivity disaplyMessageActivity;
    private Uebungen uebungen;
    private MainActivity mainActivity;
    private static Orchestrator instance;

    private Orchestrator(){
        blockList = new ArrayList<Block>();
    }

    /* Die Klasse kann nicht direckt instanziiert werden, sondern muss über die get Insatance Methode
     * aufgerufen werden. Dadurch kann es in dem gesammtet Programm nur eine Instans der Klasse geben.
     * um sicherzustellen, das dies nicht durch Multithreads ausgehebelt wird muss die getOrchestrator
     * Methode synchronized durchgeführt werden, damit es keinen Parallelen Thread gibt und es nicht
     * zwei instansen geben kann. (kann bei häufigen getOrchestrator aufrufen zu einem Performanceproblem
     * führen.)
    */
    public static synchronized Orchestrator getOrchestrator(){
        if (instance==null){
            instance = new Orchestrator();
        }
        return instance;
    }

    public void displayNextQuestion(){
        if(blockList.get(currentBlockNum).getQuestions().size()>0) {
            Block block = blockList.get(currentBlockNum);
            uebungen.setQuestion(block.getQuestion());
        }else {
            goDisplay();
        }
    }

    public void goDisplay(){
        Intent intent = new Intent(disaplyMessageActivity.getApplicationContext(), DisaplyMessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        disaplyMessageActivity.startActivity(intent);
        Toast.makeText(disaplyMessageActivity, "Es gibt keine Fragen mehr in diesem Block",
                Toast.LENGTH_LONG).show();
        Loader loader = new Loader(this);
        loader.execute("update",Integer.toString(blockList.get(currentBlockNum).getBlockId()));
    }

    public void checkUserAnswer(String userAnswer){
        boolean answerCorrect = blockList.get(currentBlockNum).checkAnswer(userAnswer);
        if(answerCorrect){
            displayNextQuestion();
        }else{
            Toast.makeText(uebungen, "Deine Antwort ist Falsch!",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void goHome(){
        Intent intent = new Intent(mainActivity.getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainActivity.startActivity(intent);
        Toast.makeText(mainActivity, "Es besteht aktuell keine Interntverbidung.", Toast.LENGTH_LONG).show();

    }

    public void logout(){
        sessionID = "";
        login = false;
    }

    public void loadUserBlockList(){
        if (login){

            Loader loader = new Loader(this);
            loader.execute("getBlockList");
        }
    }

    public void setUserBlockList (String s){
        blockList.clear();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                blockList.add(new Block(obj.getInt("blockID"),obj.getString("blockName")));
            }
        }catch (JSONException e) {
            e.printStackTrace();

        }
        disaplyMessageActivity.setButton();
    }

    public void setLogin(boolean state){
        login = state;
        if (state){
            loadUserBlockList();
        }
    }

    public void setCurrentBlock(int blockNum){
        currentBlockNum = blockNum;
    }
    public void setMainActivity(MainActivity m ){
        mainActivity = m;
    }
    public MainActivity getMainActivity(){return mainActivity;}
    public void setUebungen(Uebungen u ){
        uebungen = u;
    }
    public Uebungen getUebungen(){return uebungen;}
    public ArrayList<Block> getBlockList(){return blockList;}
    public void setDisaplyMessageActivity(DisaplyMessageActivity d){
        disaplyMessageActivity = d ;
    }
    public DisaplyMessageActivity getDisaplyMessageActivity(){return disaplyMessageActivity;}
}
