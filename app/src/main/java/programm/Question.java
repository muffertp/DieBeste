package programm;

/**
 * Created by kevin on 08.06.2017.
 */

public class Question {

    private String questionText = null;
    private String questionType = null;
    private Integer answerCount = null;
    private Double answerMax = null;
    private Double answerMin = null;

    public Question(String qt, String qtyoe, Integer ac, Double aMax, Double aMin){
        questionText = qt;
        questionType = qtyoe;
        answerCount = ac;
        answerMax = aMax;
        answerMin = aMin;
    }



    public String getQuestionText(){return questionText;}
    public String getQuestionType(){return questionType;}
    public Integer getAnswerCount(){return answerCount;}
    public Double getAnswerMax(){return answerMax;}
    public Double getAnswerMin(){return answerMin;}
    public String toString(){return "questionText: "+questionText+" :: "+
            "questionType: "+questionType+" :: "+
            "answerCount: "+answerCount+" :: "+
            "answerMax: "+answerMax+" :: "+
            "answerMin: "+answerMin+" :: ";}
}
