package programm;

import com.example.pierrelaptop.diebeste.DisaplyMessageActivity;

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
    private Block currentBlock;
    private Block nextBlock;
    private int currentBlockNum=0;
    private int nextBlockNum=1;
    private Question currentQuestion;
    private DisaplyMessageActivity disaplyMessageActivity;

    public void setDisaplyMessageActivity(DisaplyMessageActivity d){
        disaplyMessageActivity = d ;
    }

    public Question getNextQuestion(){

        if (currentBlock.getQuestions().size()>0){
            int randomNum = (int)(Math.random() * currentBlock.getQuestions().size());
            currentQuestion = currentBlock.getQuestions().get(randomNum);
        }else{

        }
        return getCurrentQuestion();
    }

    public void changeBlock(int newBlcok){
        if(newBlcok==-1){
            currentBlock=nextBlock;
            currentBlockNum = nextBlockNum;
           // nextBlock=new Block(nextBlockNum);

        }
    }

    public Question getCurrentQuestion(){return currentQuestion;}
    public ArrayList<Block> getBlockList(){return blockList;}
    private static Orchestrator instance;

    private Orchestrator(){
        blockList = new ArrayList<Block>();
        //this.execute();
        //currentBlock = new Block(currentBlockNum);
        //nextBlock = new Block(nextBlockNum);
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

    public boolean getLoginStatus(){return login;}
}
