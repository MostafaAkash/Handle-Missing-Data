package net.qsoft.missingchecknew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;
/*package net.qsoft.missingchecknew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;*/

public class MainActivity extends AppCompatActivity {
    private DatabaseAccess databaseAccess;
    private AlertDialog dialog;
    private ArrayList<ActualQuestion> actualQuestionArrayList;
    private ArrayList<SurveyQuestion> sectionWiseSurveyQuesList;
    private ArrayList<SectionReport> sectionReports;
    private SectionWiseRespondents[] sectionWiseRespondents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionWiseSurveyQuesList = new ArrayList<>();
        sectionWiseRespondents = new SectionWiseRespondents[5];
        init();
        sectionReports = new ArrayList<>();

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
        goSection(eventId,sectionId);

        //callSection(eventId,sectionId);

        //eventId=1;
        //sectionId=3;

        //callSection(eventId,sectionId);






        databaseAccess.close();


    }

    private void init() {
        for(int i=0;i<5;i++)
        {
            sectionWiseRespondents[i] = new SectionWiseRespondents();
        }
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

    private ArrayList<ActualQuestion> getActualQuestionDataUsingSectionAndSubSection(int sectionId,int subSectionId)
    {
        ArrayList<ActualQuestion>actQuesSecWise = new ArrayList<>();
        for(ActualQuestion actualQuestion: actualQuestionArrayList)
        {
            if(actualQuestion.getSectionId()==sectionId && actualQuestion.getSubSectionId()==subSectionId)
            {
                actQuesSecWise.add(actualQuestion);
            }
        }
        return actQuesSecWise;
    }
    private void goSection(int eventId,int sectionId)
    {
        ArrayList<OrgMem>orgMembers;
        ArrayList<Respondents>subRespondents;
        String[] orgWithMembers;
        switch (sectionId)
        {

            case 1:
                orgMembers = databaseAccess.getMemberFromRespondents(eventId,sectionId);
                subRespondents = new ArrayList<>();
                subRespondents.add(new Respondents(1,5,orgMembers));
                sectionWiseRespondents[sectionId-1].setSubRespondentList(subRespondents);
                orgWithMembers = getMembersAndOrgInStringFormat(orgMembers);
                loadSurveyQuestionBySection(eventId,sectionId,orgWithMembers[0],orgWithMembers[1]);
                break;

            case 2:
                ArrayList<OrgMem>orgMemSectionOne = databaseAccess.getMemberFromRespondents(eventId,1);
                ArrayList<OrgMem>orgMemSecFourSubTen = databaseAccess.getMemberFromRespondentsUsingSectionAndSubSection(eventId,4,10);
                ArrayList<OrgMem>orgMemSectionTwo = databaseAccess.getMemberFromRespondents(eventId,sectionId);
                subRespondents = new ArrayList<>();
                subRespondents.add(new Respondents(1,6,orgMemSectionTwo));
                subRespondents.add(new Respondents(2,6,orgMemSectionTwo));
                ArrayList<OrgMem>allMembers = new ArrayList<>();
                allMembers.addAll(orgMemSectionOne);
                allMembers.addAll(orgMemSectionTwo);
                allMembers.addAll(orgMemSecFourSubTen);
                subRespondents.add(new Respondents(3,16,allMembers));
                subRespondents.add(new Respondents(4,16,allMembers));
                subRespondents.add(new Respondents(5,16,allMembers));

                orgWithMembers = getMembersAndOrgInStringFormat(allMembers);
                loadSurveyQuestionBySection(eventId,sectionId,orgWithMembers[0],orgWithMembers[1]);

                sectionWiseRespondents[sectionId-1].setSubRespondentList(subRespondents);

                break;

            case 3:

                break;
            case 4:
                break;
            case 5:
                break;
        }

        //ArrayList<ActualQuestion>actualQuestions = getActualQuestionDataUsingSection(sectionId);

        ArrayList<Respondents>subSecRespondents =sectionWiseRespondents[sectionId-1].getSubRespondentList();

        String data = "";
        for(int i=0;i<subSecRespondents.size();i++)
        {
            Respondents respondents = subSecRespondents.get(i);

            data = data + "SectionId: "+ sectionId +"     SubsectionId: "+respondents.getSubSectionNo()+"\n";
            data = data + "OrgName"+"   "+"RespondentList"+"   NoOfMissQues"+"\n";

            ArrayList<OrgMem>orgSubMemList = respondents.getRespondentMembers();

            ArrayList<ActualQuestion>subSecActQues = getActualQuestionDataUsingSectionAndSubSection(sectionId,respondents.getSubSectionNo());

            int countMisQuestion=0;
            for(OrgMem orgMem:orgSubMemList)
            {
                //data = data + orgMem.getOrgNumber()+"    "+orgMem.getOrgMemberNumber()+"\n";
                ArrayList<SurveyQuestion> surQuesMemWise = getSurveyQuestionOfSpecificMember(orgMem);
                ArrayList<ActualQuestion> missQuesList = getMissingMandatoryQuestionUsingSection(surQuesMemWise,subSecActQues);

                //showSurveyQuestionData(surQuesMemWise);
               // showActualQuestionData(missQuesList);
                if(missQuesList!=null)
                {
                    data = data + orgMem.getOrgNumber()+"    "+orgMem.getOrgMemberNumber()+"     "+missQuesList.size()+"\n";
                    for(ActualQuestion misQuestion: missQuesList)
                    {
                        data = data + misQuestion.getSectionId()+"     "+misQuestion.getSubSectionId()+"     "+misQuestion.getQuestionNo()+"\n\n";
                    }
                    countMisQuestion = countMisQuestion + missQuesList.size();
                }


            }
            data = data +"\n";





        }

        createDialog(data);




    }

    private void callSection(int eventId, int sectionId) {
        ArrayList<OrgMem>orgMems = new ArrayList<>();
        switch (sectionId)
        {
            case 1:
                orgMems = databaseAccess.getMemberFromRespondents(eventId,sectionId);
                break;
            case 2:
                ArrayList<OrgMem>orgMemSectionOne = databaseAccess.getMemberFromRespondents(eventId,1);
                ArrayList<OrgMem>orgMemSecFourSubTen = databaseAccess.getMemberFromRespondentsUsingSectionAndSubSection(eventId,4,10);
                ArrayList<OrgMem>orgMemSectionTwo = databaseAccess.getMemberFromRespondents(eventId,sectionId);
                orgMems.addAll(orgMemSectionOne);
                orgMems.addAll(orgMemSecFourSubTen);
                orgMems.addAll(orgMemSectionTwo);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                break;
        }

        String data= "";
        if(orgMems!=null)
        {
            data = data + "Number of members: "+orgMems.size()+"\n";
        }
        else
        {
            createDialog("Something went wrong");
            return;
        }


        //data = data +"\n";
        //createDialog(data);

        ArrayList<ActualQuestion>actualQuestions = getActualQuestionDataUsingSection(sectionId);
        //showActualQuestionData(actualQuestions);




        String[] orgWithMembers = getMembersAndOrgInStringFormat(orgMems);
        loadSurveyQuestionBySection(eventId,sectionId,orgWithMembers[0],orgWithMembers[1]);
        //showSurveyQuestionData(surveyQuestions);

        //showMissingMandatoryQuestionUsingSection(surveyQuestions,actualQuestions);
        //data="";
        data = data +orgWithMembers[2]+"\n";

        int countMisQuestion = 0;
        for(OrgMem orgMem:orgMems)
        {
            ArrayList<SurveyQuestion> surQuesMemWise = getSurveyQuestionOfSpecificMember(orgMem);

            ArrayList<ActualQuestion> missQuesList = getMissingMandatoryQuestionUsingSection(surQuesMemWise,actualQuestions);

            showSurveyQuestionData(surQuesMemWise);
            showActualQuestionData(missQuesList);

            if(missQuesList!=null)
            {
                data = data + orgMem.getOrgNumber()+"    "+orgMem.getOrgMemberNumber()+"     "+missQuesList.size()+"\n";
                for(ActualQuestion misQuestion: missQuesList)
                {
                    data = data + misQuestion.getSectionId()+"     "+misQuestion.getSubSectionId()+"     "+misQuestion.getQuestionNo()+"\n\n";
                }
                countMisQuestion = countMisQuestion + missQuesList.size();
            }


        }
        createDialog(data);


    }

    private String[] getMembersAndOrgInStringFormat(ArrayList<OrgMem>orgMemberList)
    {
        StringBuffer firstParam = new StringBuffer();
        StringBuffer secondParam = new StringBuffer();
        String data="";
        for(int i=0;i<orgMemberList.size();i++)
        {
            data = data + orgMemberList.get(i).getOrgNumber()+"    "+orgMemberList.get(i).getOrgMemberNumber()+"\n";
            if(i==(orgMemberList.size()-1))
            {
                firstParam.append("'"+orgMemberList.get(i).getOrgNumber()+"'");
                secondParam.append("'"+orgMemberList.get(i).getOrgMemberNumber()+"'");
            }
            else
            {
                firstParam.append("'"+orgMemberList.get(i).getOrgNumber()+"'"+",");
                secondParam.append("'"+orgMemberList.get(i).getOrgMemberNumber()+"'"+",");
            }
        }
        firstParam.insert(0,"(");
        secondParam.insert(0,"(");
        firstParam.insert(firstParam.length(),")");
        secondParam.insert(secondParam.length(),")");
        createDialog(firstParam.toString());
        createDialog(secondParam.toString());
        String mOrgNumbers = firstParam.toString();
        String mOrgMemNumbers= secondParam.toString();
        return new String[]{mOrgNumbers,mOrgMemNumbers,data};
    }

    private void loadSurveyQuestionBySection(int eventId, int sectionId, String mOrgNumbers, String mOrgMemNumbers) {
        sectionWiseSurveyQuesList.clear();
        sectionWiseSurveyQuesList = databaseAccess.getSurveyQuestionDataForRespondentsMember(eventId,sectionId,mOrgNumbers,mOrgMemNumbers);
        //showSurveyQuestionData(sectionWiseSurveyQuesList);
    }


    private ArrayList<SurveyQuestion> getSurveyQuestionOfSpecificMember(OrgMem orgMem)
    {
        ArrayList<SurveyQuestion> surQuesMemWise  = new ArrayList<>();
        for(SurveyQuestion surveyQuestion:sectionWiseSurveyQuesList)
        {
            if(surveyQuestion.getOrgNo().equals(orgMem.getOrgNumber()) && surveyQuestion.getOrgMemNumber().equals(orgMem.getOrgMemberNumber()))
            {
                surQuesMemWise.add(surveyQuestion);
            }
        }
        return surQuesMemWise;

    }

    private void callFirstSection(int eventId,int sectionId,int monitorNo) {
        ArrayList<SurveyQuestion> surveyQuestions = databaseAccess.getSurveyQuestionData(eventId,sectionId,monitorNo);
        showSurveyQuestionData(surveyQuestions);

        ArrayList<ActualQuestion>actualQuestions = getActualQuestionDataUsingSection(sectionId);
        showActualQuestionData(actualQuestions);

        //showMissingMandatoryQuestionUsingSection(surveyQuestions,actualQuestions);

    }

    private ArrayList<ActualQuestion> getMissingMandatoryQuestionUsingSection(ArrayList<SurveyQuestion>surveyQuestions,ArrayList<ActualQuestion>actualQuestions)   {
        ArrayList<ActualQuestion>mandMissQuesList= new ArrayList<>();
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
                    // mandMissQuesId.add(i);
                    mandMissQuesList.add(actualQuestions.get(i));
                }
            }
        }
/*
        String data="";
        for(int i=0;i<mandMissQuesId.size();i++)
        {
            int index = mandMissQuesId.get(i);
            data = data + actualQuestions.get(index).getSectionId()
                    +"   "+actualQuestions.get(index).getSubSectionId()
                    +"   "+actualQuestions.get(index).getQuestionNo()
                    +"\n\n";
        }
        createDialog(data);*/
        return mandMissQuesList;

    }

    public void showSurveyQuestionData(ArrayList<SurveyQuestion>surveyQuestions)
    {
        String data = "Survey ";
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
        String data = "Actual ";
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
    private class SectionWiseRespondents
    {
        private ArrayList<Respondents> subRespondentList;

        public ArrayList<Respondents> getSubRespondentList() {
            return subRespondentList;
        }

        public void setSubRespondentList(ArrayList<Respondents> subRespondentList) {
            this.subRespondentList = subRespondentList;
        }
    }
}
