package programm;

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
    private ArrayList<Integer> blockList;
    private Block currentBlock;
    private Block nextBlock;
    private int currentBlockNum=0;
    private int nextBlockNum=1;
    private Question currentQuestion;

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
            nextBlock=new Block(nextBlockNum);

        }
    }

    public Question getCurrentQuestion(){return currentQuestion;}

    private static Orchestrator instance;

    private Orchestrator(){

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
                blockList.add(obj.getInt("blockID"));
            }
        }catch (JSONException e) {
            e.printStackTrace();

        }
    }

    public void login(String userName, String userPass){

        Loader loader = new Loader(this);
        loader.execute("login");

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
