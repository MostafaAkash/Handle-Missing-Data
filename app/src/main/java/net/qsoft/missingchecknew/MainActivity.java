package net.qsoft.missingchecknew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseAccess databaseAccess;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseAccess =DatabaseAccess.getInstance(this);
        databaseAccess.open();
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
        monitorNo=2;
        //callFirstSection(eventId,sectionId,monitorNo);
        sectionId=2;
        callSecondSection(eventId,sectionId);

        databaseAccess.close();


    }

    private void callSecondSection(int eventId, int sectionId) {
      ArrayList<OrgMem>orgMems = databaseAccess.getMemberFromOrganization(eventId,sectionId);
      String data= "";
      for(int i=0;i<orgMems.size();i++)
      {
          data = data +orgMems.get(i).getOrgNumber()+"    "+orgMems.get(i).getOrgMemberNumber()+"\n";
      }
      createDialog(data);
    }

    private void callFirstSection(int eventId,int sectionId,int monitorNo) {
         ArrayList<SurveyQuestion> surveyQuestions = databaseAccess.getSurveyQuestionData(eventId,sectionId,monitorNo);
         showSurveyQuestionData(surveyQuestions);

         ArrayList<ActualQuestion>actualQuestions = databaseAccess.getActualQuestionData(sectionId);
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
