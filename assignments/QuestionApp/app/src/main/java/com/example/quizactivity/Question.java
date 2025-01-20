package com.example.quizactivity;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }
    public int getTextResId(){return mTextResId;}
    public void setTextResId(int mTextResID){this.mTextResId=mTextResID;}
    public boolean isAnswerTrue(){return mAnswerTrue;}
    public void setAnswerTrue(boolean mAnswerTrue){this.mAnswerTrue=mAnswerTrue;}

    }
