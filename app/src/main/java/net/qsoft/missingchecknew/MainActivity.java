package net.qsoft.missingchecknew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private DatabaseAccess databaseAccess;
    private AlertDialog dialog;
    private ArrayList<ActualQuestion> actualQuestionArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseAccess =DatabaseAccess.getInstance(this);
        databaseAccess.open();
        loadAllActualQuestion();
       // String data = databaseAccess.getDataFromExternalDatabase();
        //Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        //createDialog(data);
        //String qData = databaseAccess.getSurveyQuestionData();
       // createDialog(qData);

        //String qaData = databaseAccess.getActualQuestionData();
        //createDialog(qaData);

       // showMissingMandatoryQuestionUsingEvent();
        //()
        int eventId,sectionId,monitorNo;
        eventId=1;
        sectionId=1;
       // monitorNo=2;
       // callFirstSection(eventId,sectionId,monitorNo);

        callSecondSection(eventId,sectionId);
       // String data = databaseAccess.getServayOrgMem();
        //createDialog(data);


        databaseAccess.close();


    }

    private void loadAllActualQuestion() {
        actualQuestionArrayList = databaseAccess.getActualQuestionData();
    }

    private ArrayList<ActualQuestion> getActualQuestionDataUsingSection(int sectionId)
    {
        ArrayList<ActualQuestion>actQuesSecWise = new ArrayList<>();
        for(ActualQuestion actualQuestion: actualQuestionArrayList)
        {
            if(actualQuestion.getSectionId()==sectionId)
            {
                actQuesSecWise.add(actualQuestion);
            }
        }
        return actQuesSecWise;
    }

    private void callSecondSection(int eventId, int sectionId) {
      ArrayList<OrgMem>orgMems = databaseAccess.getMemberFromRespondents(eventId,sectionId);

      StringBuffer firstParam = new StringBuffer();
      StringBuffer secondParam = new StringBuffer();

      String data= "";
      for(int i=0;i<orgMems.size();i++)
      {
          data = data +orgMems.get(i).getOrgNumber()+"    "+orgMems.get(i).getOrgMemberNumber()+"\n";
          if(i==(orgMems.size()-1))
          {
              firstParam.append("'"+orgMems.get(i).getOrgNumber()+"'");
              secondParam.append("'"+orgMems.get(i).getOrgMemberNumber()+"'");
          }
          else
          {
              firstParam.append("'"+orgMems.get(i).getOrgNumber()+"'"+",");
              secondParam.append("'"+orgMems.get(i).getOrgMemberNumber()+"'"+",");
          }


      }
      createDialog(data);

      ArrayList<ActualQuestion>actualQuestions = getActualQuestionDataUsingSection(sectionId);
      showActualQuestionData(actualQuestions);



        firstParam.insert(0,"(");
        secondParam.insert(0,"(");
        firstParam.insert(firstParam.length(),")");
        secondParam.insert(secondParam.length(),")");
        createDialog(firstParam.toString());
        createDialog(secondParam.toString());
        String st1 = firstParam.toString();
        String st2 = secondParam.toString();


        ArrayList<SurveyQuestion> surveyQuestions = databaseAccess.getSurveyQuestionDataForRespondentsMember(eventId,sectionId,st1,st2);
        showSurveyQuestionData(surveyQuestions);

        //showMissingMandatoryQuestionUsingSection(surveyQuestions,actualQuestions);

    }

    private void callFirstSection(int eventId,int sectionId,int monitorNo) {
         ArrayList<SurveyQuestion> surveyQuestions = databaseAccess.getSurveyQuestionData(eventId,sectionId,monitorNo);
         showSurveyQuestionData(surveyQuestions);

         ArrayList<ActualQuestion>actualQuestions = getActualQuestionDataUsingSection(sectionId);
         showActualQuestionData(actualQuestions);

         showMissingMandatoryQuestionUsingSection(surveyQuestions,actualQuestions);

    }

    private void showMissingMandatoryQuestionUsingSection(ArrayList<SurveyQuestion>surveyQuestions,ArrayList<ActualQuestion>actualQuestions)   {
        ArrayList<Integer>mandMissQuesId = new ArrayList<>();
        for(int i=0;i<actualQuestions.size();i++)
        {
            if(actualQuestions.get(i).getIsMandatory()==1)
            {
                boolean flag = false;
                for(int j=0;j<surveyQuestions.size();j++)
                {
                    if((actualQuestions.get(i).getSectionId()==surveyQuestions.get(j).getSectionId())
                            &&(actualQuestions.get(i).getSubSectionId()==Integer.valueOf(surveyQuestions.get(j).getSubSectionId()))
                            && (actualQuestions.get(i).getQuestionNo()==surveyQuestions.get(j).getQuestionNo()) )
                    {
                        flag = true;
                        break;
                    }
                }
                if(flag==false)
                {
                    mandMissQuesId.add(i);
                }
            }
        }

        String data="";
        for(int i=0;i<mandMissQuesId.size();i++)
        {
            int index = mandMissQuesId.get(i);
            data = data + actualQuestions.get(index).getSectionId()
                    +"   "+actualQuestions.get(index).getSubSectionId()
                    +"   "+actualQuestions.get(index).getQuestionNo()
                    +"\n\n";
        }
        createDialog(data);

    }

    public void showSurveyQuestionData(ArrayList<SurveyQuestion>surveyQuestions)
    {
        String data = "";
        for(SurveyQuestion surveyQuestion:surveyQuestions)
        {
            data = data + surveyQuestion.getEventId()+" "+
                    surveyQuestion.getSectionId()+" "+
                    surveyQuestion.getSubSectionId()+" "+
                    surveyQuestion.getQuestionNo()+" "+
                    surveyQuestion.getMonitorNo()+"\n\n";

        }
        createDialog(data);
    }

    public void showActualQuestionData(ArrayList<ActualQuestion>actualQuestions)
    {
        String data = "";
        data = "";
        for(ActualQuestion actualQuestion:actualQuestions)
        {
            data = data + actualQuestion.getSectionId()+"   "+
                    actualQuestion.getSubSectionId()+"   "+
                    actualQuestion.getQuestionNo()+"\n\n";
        }
        createDialog(data);
    }

    private void showMissingMandatoryQuestionUsingEvent() {
       // ArrayList<SurveyQuestion> surveyQuestions = databaseAccess.getSurveyQuestionData(1);
       // ArrayList<ActualQuestion>actualQuestions = databaseAccess.getActualQuestionData();
/*
        String data = "";
        for(SurveyQuestion surveyQuestion:surveyQuestions)
        {
            data = data + surveyQuestion.getEventId()+" "+
                    surveyQuestion.getSectionId()+" "+
                    surveyQuestion.getSubSectionId()+" "+
                    surveyQuestion.getQuestionNo()+" "+
                    surveyQuestion.getMonitorNo()+"\n\n";

        }
        createDialog(data);
        data = "";
        for(ActualQuestion actualQuestion:actualQuestions)
        {
            data = data + actualQuestion.getSectionId()+"   "+
                    actualQuestion.getSubSectionId()+"   "+
                    actualQuestion.getQuestionNo()+"\n\n";
        }
        createDialog(data);*/

/*

        ArrayList<Integer>mandMissQuesId = new ArrayList<>();
        for(int i=0;i<actualQuestions.size();i++)
        {
            if(actualQuestions.get(i).getIsMandatory()==1)
            {
                boolean flag = false;
                for(int j=0;j<surveyQuestions.size();j++)
                {
                    if((actualQuestions.get(i).getSectionId()==surveyQuestions.get(j).getSectionId())
                      &&(actualQuestions.get(i).getSubSectionId()==Integer.valueOf(surveyQuestions.get(j).getSubSectionId()))
                        && (actualQuestions.get(i).getQuestionNo()==surveyQuestions.get(j).getQuestionNo()) )
                    {
                        flag = true;
                        break;
                    }
                }
                if(flag==false)
                {
                    mandMissQuesId.add(i);
                }
            }
        }

        String data="";
        for(int i=0;i<mandMissQuesId.size();i++)
        {
            int index = mandMissQuesId.get(i);
            data = data + actualQuestions.get(index).getSectionId()
                    +"   "+actualQuestions.get(index).getSubSectionId()
                    +"   "+actualQuestions.get(index).getQuestionNo()
                    +"\n\n";
        }
        createDialog(data);
*/

    }

    public void createDialog(String data)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(data);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        builder.create();
        dialog = builder.show();
    }
}
