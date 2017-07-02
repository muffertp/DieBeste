package programm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kevin on 07.06.2017.
 * Ein programm.Block ist eine Liste von X Fragen, die zusammen gehören. (Z.B. nur Mathematik additionsaufgaben)
 */

public class Block {

    private ArrayList<Question> questions = null;

    private int blockId;
    private String blockName="";

    private int currentQuestion=0;

    public String getQuestion(){
        if(questions.size()>0){
            int randomNum = (int)(Math.random() * questions.size());
            currentQuestion=randomNum;
            return questions.get(currentQuestion).getQuestionText();
        }
        return "-1";
    }

    public boolean checkAnswer(String userAnswer){
        if(!userAnswer.trim().equals("")) {
            double answer = Double.parseDouble(userAnswer);
            boolean correct = questions.get(currentQuestion).checkQuestion(answer);
            if (correct){
                questions.remove(currentQuestion);
            }
            return correct;
        }else{
            return false;
        }

    }

    public Block(int num,String n){

        questions = new ArrayList<Question>();
        blockId = num;
        blockName=n;
        loadBlock();

    }
    public String getBlockName(){return blockName;}
    private void loadBlock(){
        Loader loader = new Loader(this);
        String url = Lib.baseURL+"getMathBlock?block="+blockId;
        loader.execute("block",url);
    }

    public void loadResponse(String result) {
        stringToJSON(result);
        System.out.println("Done-Loading: "+result);

    }

    private void stringToJSON(String jsonStr){
        System.out.println(jsonStr);

        if (jsonStr != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                System.out.println(jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.has("error")){
                        Orchestrator.getOrchestrator().logout();
                    }else if(jsonObject.has("questionText")) {
                        questions.add(new Question(
                                jsonObject.getString("questionText"),
                                jsonObject.getString("questionType"),
                                jsonObject.getInt("answerCount"),
                                jsonObject.getDouble("answerMax"),
                                jsonObject.getDouble("answerMin")
                        ));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println(toString());

    }

    public ArrayList<Question> getQuestions(){return questions;}

    public String toString(){
        String result = "";
        for (int i =0;i<questions.size();i++){
            result += questions.get(i).toString();
        }
        return result;
    }
}
