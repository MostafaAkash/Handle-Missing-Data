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
    private SectionReport[] sectionReports;
    private SectionWiseRespondents[] sectionWiseRespondents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionReports = new SectionReport[5];
        sectionWiseSurveyQuesList = new ArrayList<>();
        sectionWiseRespondents = new SectionWiseRespondents[5];
        arrayInitialization();

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
        sectionId=4;
        goSection(eventId,sectionId);

        //callSection(eventId,sectionId);

        //eventId=1;
        //sectionId=3;

        //callSection(eventId,sectionId);



        databaseAccess.close();


    }

    private void  arrayInitialization()
    {
        for(int i=0;i<5;i++)
        {
            sectionWiseRespondents[i] = new SectionWiseRespondents();
            sectionReports[i] = new SectionReport();
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
        ArrayList<OrgMem>orgMemSecOne;
        ArrayList<Respondents>subRespondents;
        String[] orgWithMembers;
        ArrayList<SubSection>subSectionList = new ArrayList<>();
        String data = "";
        switch (sectionId)
        {

            case 1:
                orgMemSecOne = databaseAccess.getMemberFromRespondents(eventId,sectionId);
                subRespondents = new ArrayList<>();
                subRespondents.add(new Respondents(1,5,orgMemSecOne));
                sectionWiseRespondents[sectionId-1].setSubRespondentList(subRespondents);
                orgWithMembers = getMembersAndOrgInStringFormat(orgMemSecOne);
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
                ArrayList<OrgMem>orgMemSecThreeSubFour = databaseAccess.getMemberFromRespondentsUsingSectionAndSubSection(eventId,sectionId,3);
                subRespondents = new ArrayList<>();
                subRespondents.add(new Respondents(3,5,orgMemSecThreeSubFour));
                subRespondents.add(new Respondents(4,5,orgMemSecThreeSubFour));
                subRespondents.add(new Respondents(5,5,orgMemSecThreeSubFour));

                data = data + getStringDataOfMissQuestionBySectionAndSubSection(eventId,sectionId,6);
                //subSectionList.add(new SubSection(6,0,0,))

                orgMemSecOne = databaseAccess.getMemberFromRespondents(eventId,1);
                subRespondents.add(new Respondents(8,5,orgMemSecOne));

                data = data + getStringDataOfMissQuestionBySectionAndSubSection(eventId,sectionId,9);


                ArrayList<OrgMem>allMembersOfSecThree = new ArrayList<>();
                allMembersOfSecThree.addAll(orgMemSecOne);
                allMembersOfSecThree.addAll(orgMemSecThreeSubFour);

                orgWithMembers = getMembersAndOrgInStringFormat(allMembersOfSecThree);
                loadSurveyQuestionBySection(eventId,sectionId,orgWithMembers[0],orgWithMembers[1]);
                sectionWiseRespondents[sectionId-1].setSubRespondentList(subRespondents);

                break;

            case 4:
                data = data + getStringDataOfMissQuestionBySectionAndSubSection(eventId,sectionId,1);

                ArrayList<String> voNumbers = databaseAccess.getUniqueVONumbersFromRespondents(eventId,2);
                data = data + getOrgNumberInStringFormat(voNumbers);

                data  = data + getDataFromVO(eventId,sectionId,2,voNumbers)+"\n";

                data = data + "SectionId: "+sectionId+"    SubSectionId: "+3+"\n";
                data = data+"VO numbers: "+voNumbers.toString()+"\n";

                data = data +getDataFromVO(eventId,sectionId,3,voNumbers);

                ArrayList<String>restTwoVO = databaseAccess.getUniqueVONumbersFromRespondents(eventId,sectionId,4);
                data = data + "SectionId: "+sectionId+"    SubSectionId: "+4+"\n";
                data = data+"VO numbers: "+restTwoVO.toString()+"\n";

                data = data + getDataFromVO(eventId,sectionId,4,restTwoVO);

                ArrayList<String>restTwoVOA = databaseAccess.getUniqueVONumbersFromRespondents(eventId,sectionId,5);
                data = data + "SectionId: "+sectionId+"    SubSectionId: "+5+"\n";
                data = data+"VO numbers: "+restTwoVOA.toString()+"\n";
                data = data + getDataFromVO(eventId,sectionId,5,restTwoVOA);

                //subSection 7
                subRespondents = new ArrayList<>();
                ArrayList<OrgMem>allMembersSecFour = new ArrayList<>();
                ArrayList<OrgMem>orgMemSecFourSubSev = databaseAccess.getMemberFromRespondentsUsingSectionAndSubSection(eventId,sectionId,7);

                subRespondents.add(new Respondents(7,5,orgMemSecFourSubSev));

                ArrayList<OrgMem>orgMemSecFourSubNine = databaseAccess.getMemberFromRespondentsUsingSectionAndSubSection(eventId,sectionId,9);
                subRespondents.add(new Respondents(9,5,orgMemSecFourSubSev));

                ArrayList<OrgMem>orgMemsSecFourSubTen= databaseAccess.getMemberFromRespondentsUsingSectionAndSubSection(eventId,sectionId,10);
                subRespondents.add(new Respondents(10,5,orgMemsSecFourSubTen));

                ArrayList<OrgMem>orgMemsSecFourSubEle= databaseAccess.getMemberFromRespondentsUsingSectionAndSubSection(eventId,sectionId,11);
                subRespondents.add(new Respondents(11,5,orgMemsSecFourSubEle));

                allMembersSecFour.addAll(orgMemSecFourSubSev);
                allMembersSecFour.addAll(orgMemSecFourSubNine);
                allMembersSecFour.addAll(orgMemsSecFourSubTen);
                allMembersSecFour.addAll(orgMemsSecFourSubEle);

                orgWithMembers = getMembersAndOrgInStringFormat(allMembersSecFour);
                loadSurveyQuestionBySection(eventId,sectionId,orgWithMembers[0],orgWithMembers[1]);
                sectionWiseRespondents[sectionId-1].setSubRespondentList(subRespondents);

                break;
            case 5:
                subRespondents = new ArrayList<>();
                ArrayList<OrgMem>allMembersSecFive = new ArrayList<>();

                data = data + getStringDataOfMissQuestionBySectionAndSubSection(eventId,sectionId,1);
                //members from section one
                orgMemSecOne = databaseAccess.getMemberFromRespondents(eventId,1);
                subRespondents.add(new Respondents(4,5,orgMemSecOne));

                data = data + getStringDataOfMissQuestionBySectionAndSubSection(eventId,5,13);

                data = data + getStringDataOfMissQuestionBySectionAndSubSection(eventId,5,14);

                data = data + getStringDataOfMissQuestionBySectionAndSubSection(eventId,5,17);

                orgWithMembers = getMembersAndOrgInStringFormat(allMembersSecFive);
                loadSurveyQuestionBySection(eventId,sectionId,orgWithMembers[0],orgWithMembers[1]);
                sectionWiseRespondents[sectionId-1].setSubRespondentList(subRespondents);

                break;

                default:
                    break;
        }

        //ArrayList<ActualQuestion>actualQuestions = getActualQuestionDataUsingSection(sectionId);

        ArrayList<Respondents>subSecRespondents =sectionWiseRespondents[sectionId-1].getSubRespondentList();


        for(int i=0;i<subSecRespondents.size();i++)
        {

            Respondents respondents = subSecRespondents.get(i);

            data = data + "SectionId: "+ sectionId +"     SubsectionId: "+respondents.getSubSectionNo()+"\n";
            data = data + "OrgName"+"   "+"RespondentMember"+"   NoOfMissQues"+"\n";

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
                    data = data + "[Section,Subsection,QuestionNumber]"+"\n";
                    for(ActualQuestion misQuestion: missQuesList)
                    {
                        data = data + misQuestion.getSectionId()+"     "+misQuestion.getSubSectionId()+"     "+misQuestion.getQuestionNo()+"\n";
                    }
                    countMisQuestion = countMisQuestion + missQuesList.size();
                    data = data +"\n";
                }

            }

            subSectionList.add(new SubSection(respondents.getSubSectionNo(),respondents.getNumOfMandRespondents(),respondents.getNumOfMandRespondents(),countMisQuestion,"member")) ;

        }

        data = data +"\n";


        sectionReports[sectionId-1].addSubList(subSectionList);
        String dialogText = "SectionId:  "+sectionId+"    Total Miss Questions: ";
        int totalMiss = 0;
        ArrayList<SubSection>subSections = sectionReports[sectionId-1].getSubSectionList();
        for(int i=0;i<subSections.size();i++)
        {
            totalMiss = totalMiss + subSections.get(i).getNumOfMissQues();
        }
        dialogText = dialogText + String.valueOf(totalMiss)+"\n"+data;


        createDialog(dialogText);


    }

    private ArrayList<SurveyQuestion> getSpecificVOSurveyQuestion(String voNumber, ArrayList<SurveyQuestion> surveyQuestionArrayList) {
        ArrayList<SurveyQuestion> surveyQuestions = new ArrayList<>();
        for(SurveyQuestion surveyQuestion: surveyQuestionArrayList)
        {
            if(surveyQuestion.getOrgNo().equals(voNumber))
            {
                surveyQuestions.add(surveyQuestion);
            }
        }

        return surveyQuestions;
    }

    String getStringDataOfMissQuestionBySectionAndSubSection(int eventId,int sectionId,int subSectionId)
    {
        String data="";
        ArrayList<SurveyQuestion> surQues = databaseAccess.getSurveyQuestionDataBySectionAndSubSection(eventId,sectionId,subSectionId);
        ArrayList<ActualQuestion> actQues = getActualQuestionDataUsingSectionAndSubSection(sectionId,subSectionId);
        ArrayList<ActualQuestion> misActQues = getMissingMandatoryQuestionUsingSection(surQues,actQues);

        data = data + getStringDataOfMissQuestionByMonitor(sectionId,subSectionId,misActQues)+"\n";

        ArrayList<SubSection>subSections = new ArrayList<>();
        subSections.add(new SubSection(subSectionId,0,0,misActQues.size(),"Monitor"));
        sectionReports[sectionId-1].addSubList(subSections);
        return data;

    }

    String getStringDataOfMissQuestionByMonitor(int secId,int subSecId,ArrayList<ActualQuestion>misQues)
    {
        String data = "";
        data = data + "SectionId: "+secId+"    SubSectionId: "+subSecId+"\n";
        data = data + "Number of Miss Questions by Monitor: "+misQues.size()+"\n";
        data = data + "[Section,Subsection,QuestionNumber]"+"\n";

        for(ActualQuestion actualQuestion : misQues)
        {
            data = data + actualQuestion.getSectionId()+"     "+actualQuestion.getSubSectionId()+"     "+actualQuestion.getQuestionNo()+"\n";
        }

        return data;
    }


    private String getDataFromVO(int eventId,int sectionId,int subSectionId,ArrayList<String>voNumbers)
    {
        String data = "";
        ArrayList<SubSection>sectionArrayList = new ArrayList<>();

        ArrayList<SurveyQuestion> subSecSurQues = databaseAccess.getSurveyQuestionDataBySectionAndSubSection(eventId,sectionId,subSectionId);
        //showSurveyQuestionData(secFourSubTwo);

        ArrayList<ActualQuestion>actQuesSecSubSecWise = getActualQuestionDataUsingSectionAndSubSection(sectionId,subSectionId);
        data = data +"\n";
        for(int i=0;i<voNumbers.size();i++)
        {
            ArrayList<SurveyQuestion> voAnswered = getSpecificVOSurveyQuestion(voNumbers.get(i),subSecSurQues);
            //showSurveyQuestionData(voAnswered);
            ArrayList<ActualQuestion> misQues = getMissingMandatoryQuestionUsingSection(voAnswered,actQuesSecSubSecWise);

            data = data + "VONumber: "+voNumbers.get(i)+"   NumofMissQues: "+misQues.size()+"\n";
            data = data + "[Section,Subsection,QuestionNumber]"+"\n";
            for(ActualQuestion misQuestion:misQues)
            {
                data = data + misQuestion.getSectionId()+"     "+misQuestion.getSubSectionId()+"     "+misQuestion.getQuestionNo()+"\n";
            }
            sectionArrayList.add(new SubSection(subSectionId,0,0,misQues.size(),"VO"));
            data = data +"\n";

        }
        sectionReports[sectionId-1].addSubList(sectionArrayList);

        return data;
    }


    //Essential function

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
        //createDialog(firstParam.toString());
        //createDialog(secondParam.toString());
        String mOrgNumbers = firstParam.toString();
        String mOrgMemNumbers= secondParam.toString();
        return new String[]{mOrgNumbers,mOrgMemNumbers,data};
    }

    private String getOrgNumberInStringFormat(ArrayList<String>voNumbers)
    {
        StringBuffer firstParam = new StringBuffer();
        for(int i=0;i<voNumbers.size();i++)
        {
            if(i==(voNumbers.size()-1))
            {
                firstParam.append("'"+voNumbers.get(i)+"'");
            }
            else
            {
                firstParam.append("'"+voNumbers.get(i)+"'"+",");
            }
        }

        firstParam.insert(0,"(");
        firstParam.insert(firstParam.length(),")");

        return firstParam.toString();

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
/*
    private void callFirstSection(int eventId,int sectionId,int monitorNo) {
        ArrayList<SurveyQuestion> surveyQuestions = databaseAccess.getSurveyQuestionData(eventId,sectionId,monitorNo);
        showSurveyQuestionData(surveyQuestions);

        ArrayList<ActualQuestion>actualQuestions = getActualQuestionDataUsingSection(sectionId);
        showActualQuestionData(actualQuestions);

        //showMissingMandatoryQuestionUsingSection(surveyQuestions,actualQuestions);

    }*/

    //Missing check function

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

        return mandMissQuesList;

    }
// just for showing survey question data
    /*
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
    }*/
//just for showing Question table data
    /*
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
    }*/

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

    //all subSection includes in SectionWiseRespondents
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
    private class SectionReport
    {
        private ArrayList<SubSection>subSectionList;


        public SectionReport(ArrayList<SubSection> subSectionList) {
            this.subSectionList = subSectionList;
        }

        public SectionReport() {
            this.subSectionList = new ArrayList<>();
        }

        public ArrayList<SubSection> getSubSectionList() {
            return subSectionList;
        }

        public void setSubSectionList(ArrayList<SubSection> subSectionList) {
            this.subSectionList = subSectionList;
        }

        public void addSubList(ArrayList<SubSection>subSections)
        {
            subSectionList.addAll(subSections);
        }


    }
}
