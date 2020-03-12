package net.qsoft.missingchecknew;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.CredentialProtectedWhileLockedViolation;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    //private DatabaseEntry databaseEntry;
    Cursor cursor = null;

    private DatabaseAccess(Context context)
    {
        this.openHelper = new DatabaseOpenHelper(context);
        //databaseEntry = new DatabaseEntry();

    }

    public static DatabaseAccess getInstance(Context context)
    {
        if(instance==null)
        {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open()
    {
        this.database = openHelper.getWritableDatabase();
    }
    public void close()
    {
        if(database!=null)
        {
            this.database.close();
        }
    }

    public String getDataFromExternalDatabase()
    {
        cursor = null;
        cursor = database.rawQuery("select "+DatabaseEntry.RESPONDENTS_SECTION_ID+","+DatabaseEntry.RESPONDENTS_SUBSECTION_ID+" from "+DatabaseEntry.TABLE_RESPONDENTS,null);
        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext())
        {
            String secId= cursor.getString(0);
            String subSecId = cursor.getString(1);


            buffer.append(secId+"   "+subSecId+"\n");
        }

        return buffer.toString();
    }

    public ArrayList<SurveyQuestion> getSurveyQuestionDataBySectionAndSubSectoin(int eventId,int sectionId,int subSection)
    {
        cursor = null;
        cursor = database.rawQuery(DatabaseEntry.SURVEY_QUESTION_INFO,new String[]{String.valueOf(eventId),String.valueOf(sectionId),String.valueOf(subSection)});

        int indexOne = cursor.getColumnIndex(DatabaseEntry.SURVEY_EVENT_ID);
        int indexTwo = cursor.getColumnIndex(DatabaseEntry.SURVEY_SECTION_ID);
        int indexThree = cursor.getColumnIndex(DatabaseEntry.SURVEY_SUBSECTION_ID);
        int indexFour = cursor.getColumnIndex(DatabaseEntry.SURVEY_ID_QUESTION);
        int indexFive = cursor.getColumnIndex(DatabaseEntry.SURVEY_ORG_NO);
        int indexSix = cursor.getColumnIndex(DatabaseEntry.SURVEY_MONITOR_NO);

        //StringBuffer buffer = new StringBuffer();

        ArrayList<SurveyQuestion>surveyQuestions = new ArrayList<>();

        while (cursor.moveToNext())
        {
            int eventIdExtra = cursor.getInt(indexOne);
            int sectionIdExtra = cursor.getInt(indexTwo);
            String subSectionId = cursor.getString(indexThree);
            int quesId = cursor.getInt(indexFour);
            String orgNo = cursor.getString(indexFive);
            String monitorNoExtra = cursor.getString(indexSix);

          //  buffer.append(eventId+"  "+sectionId+"  "+subSectionId+"  "+orgNo+"\n");
            surveyQuestions.add(new SurveyQuestion(eventIdExtra,sectionIdExtra,subSectionId,orgNo,monitorNoExtra,quesId));

        }

        //return buffer.toString();
        return surveyQuestions;
    }
    public String getServayOrgMem()
    {
        cursor = null;
        cursor = database.rawQuery("select "+DatabaseEntry.SURVEY_ORG_NO+" from "+DatabaseEntry.TABLE_SURVEY+" where "+ DatabaseEntry.RESPONDENTS_ORGMEM_NO+ " IN"+"('000394','000083')",null);

        String data = "";
        while (cursor.moveToNext())
        {
            String s = cursor.getString(0);
            if(s!=null)
            data = data + s+"   "+s.length()+"\n";
        }
        return data;
    }

    public ArrayList<SurveyQuestion> getSurveyQuestionDataForRespondentsMember(int eventId,int sectionId,String first,String second)
    {
        // +DatabaseEntry.SURVEY_ORG_NO +" IN "+first+" AND "
        cursor = null;
        cursor = database.rawQuery("select "+DatabaseEntry.SURVEY_EVENT_ID
                +","+DatabaseEntry.SURVEY_SECTION_ID
                +","+DatabaseEntry.SURVEY_SUBSECTION_ID
                +","+DatabaseEntry.SURVEY_ID_QUESTION
                +","+DatabaseEntry.SURVEY_ORG_NO
                +","+DatabaseEntry.SURVEY_ORG_MEM_NO
                +","+DatabaseEntry.SURVEY_MONITOR_NO +" from "+DatabaseEntry.TABLE_SURVEY+" where ("+DatabaseEntry.SURVEY_EVENT_ID+"=? AND "+
                DatabaseEntry.SURVEY_SECTION_ID+"=? AND "
                        +DatabaseEntry.SURVEY_ORG_NO +" IN "+first+" AND "
                +DatabaseEntry.SURVEY_ORG_MEM_NO+" IN "+second+")",
                new String[]{String.valueOf(eventId),String.valueOf(sectionId)});

        int indexOne = cursor.getColumnIndex(DatabaseEntry.SURVEY_EVENT_ID);
        int indexTwo = cursor.getColumnIndex(DatabaseEntry.SURVEY_SECTION_ID);
        int indexThree = cursor.getColumnIndex(DatabaseEntry.SURVEY_SUBSECTION_ID);
        int indexFour = cursor.getColumnIndex(DatabaseEntry.SURVEY_ID_QUESTION);
        int indexFive = cursor.getColumnIndex(DatabaseEntry.SURVEY_ORG_NO);
        int indexSix = cursor.getColumnIndex(DatabaseEntry.SURVEY_MONITOR_NO);
        int indexSeven = cursor.getColumnIndex(DatabaseEntry.SURVEY_ORG_MEM_NO);

        //StringBuffer buffer = new StringBuffer();

        ArrayList<SurveyQuestion>surveyQuestions = new ArrayList<>();

        while (cursor.moveToNext())
        {
            int eventIdExtra = cursor.getInt(indexOne);
            int sectionIdExtra = cursor.getInt(indexTwo);
            String subSectionId = cursor.getString(indexThree);
            int quesId = cursor.getInt(indexFour);
            String orgNo = cursor.getString(indexFive);
            String monitorNoExtra = cursor.getString(indexSix);
            String orgMemNumber = cursor.getString(indexSeven);

            //  buffer.append(eventId+"  "+sectionId+"  "+subSectionId+"  "+orgNo+"\n");
            surveyQuestions.add(new SurveyQuestion(eventIdExtra,sectionIdExtra,subSectionId,orgNo,monitorNoExtra,quesId,orgMemNumber));

        }

        //return buffer.toString();
        return surveyQuestions;
    }



    public ArrayList<ActualQuestion>  getActualQuestionData()
    {
        cursor = null;
        cursor = database.rawQuery(DatabaseEntry.ACTUAL_QUESTION_INFO,null);

        int indexOne = cursor.getColumnIndex(DatabaseEntry.QUESTION_SECTION_NO);
        int indexTwo = cursor.getColumnIndex(DatabaseEntry.QUESTION_SUBSECTION_ID);
        int indexThree = cursor.getColumnIndex(DatabaseEntry.QUESTION_QUESTION_NO);
        int indexFour = cursor.getColumnIndex(DatabaseEntry.QUESTION_CHECK_FIELD);

        ArrayList<ActualQuestion> actualQuestions = new ArrayList<>();
        //StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext())
        {
            int secNo = cursor.getInt(indexOne);
            int subSecNo = cursor.getInt(indexTwo);
            int quesNo = cursor.getInt(indexThree);
            int checkField = cursor.getInt(indexFour);
            actualQuestions.add(new ActualQuestion(secNo,subSecNo,quesNo,checkField));
            //buffer.append(secNo+" "+subSecNo+" "+quesNo+"\n");
        }

        return  actualQuestions;
    }

    public ArrayList<OrgMem> getMemberFromOrganization(int eventId,int sectionId)
    {
        cursor = null;
        cursor = database.rawQuery(DatabaseEntry.FIND_UNIQUE_MEMBER_FROM_SECTION_TWO,new String[]{String.valueOf(eventId),String.valueOf(sectionId)});
       // int indexOne = cursor.getColumnIndex(DatabaseEntry.SURVEY_ID);
        int indexTwo = cursor.getColumnIndex(DatabaseEntry.SURVEY_ORG_NO);
        int indexThree = cursor.getColumnIndex(DatabaseEntry.SURVEY_ORG_MEM_NO);
        ArrayList<OrgMem> orgMems = new ArrayList<>();
        while (cursor.moveToNext())
        {
            //int id = cursor.getInt(indexOne);
            String orgNO = cursor.getString(indexTwo);
            String memNo = cursor.getString(indexThree);
            orgMems.add(new OrgMem(orgNO,memNo));
        }

        return orgMems;

    }

    public ArrayList<OrgMem> getMemberFromRespondents(int eventId,int sectionId)
    {
        cursor = null;
        cursor = database.rawQuery(DatabaseEntry.FIND_UNIQUE_MEMBER_FROM_RESPONDENTS_USING_SECTION,new String[]{String.valueOf(eventId),String.valueOf(sectionId)});
        // int indexOne = cursor.getColumnIndex(DatabaseEntry.SURVEY_ID);
        int indexTwo = cursor.getColumnIndex(DatabaseEntry.SURVEY_ORG_NO);
        int indexThree = cursor.getColumnIndex(DatabaseEntry.SURVEY_ORG_MEM_NO);
        ArrayList<OrgMem> orgMems = new ArrayList<>();
        while (cursor.moveToNext())
        {
            //int id = cursor.getInt(indexOne);
            String orgNO = cursor.getString(indexTwo);
            String memNo = cursor.getString(indexThree);
            orgMems.add(new OrgMem(orgNO,memNo));
        }

        return orgMems;

    }

    public ArrayList<OrgMem> getMemberFromRespondentsUsingSectionAndSubSection(int eventId,int sectionId,int subSectionId)
    {
        cursor = null;
        cursor = database.rawQuery(DatabaseEntry.FIND_UNIQUE_MEMBER_FROM_RESPONDENTS_USING_SECTION_SUB_SECTION,new String[]{String.valueOf(eventId),String.valueOf(sectionId),String.valueOf(subSectionId)});
        // int indexOne = cursor.getColumnIndex(DatabaseEntry.SURVEY_ID);
        int indexTwo = cursor.getColumnIndex(DatabaseEntry.SURVEY_ORG_NO);
        int indexThree = cursor.getColumnIndex(DatabaseEntry.SURVEY_ORG_MEM_NO);
        ArrayList<OrgMem> orgMems = new ArrayList<>();
        while (cursor.moveToNext())
        {
            //int id = cursor.getInt(indexOne);
            String orgNO = cursor.getString(indexTwo);
            String memNo = cursor.getString(indexThree);
            orgMems.add(new OrgMem(orgNO,memNo));
        }

        return orgMems;

    }

    public ArrayList<String> getUniqueVONumbersFromRespondents(int eventId,int sectionId)
    {
        cursor = null;
        cursor = database.rawQuery(DatabaseEntry.FIND_UNIQUE_ORGANIZATION_VO,new String[]{String.valueOf(eventId),String.valueOf(sectionId)});
        int indexOne = cursor.getColumnIndex(DatabaseEntry.RESPONDENTS_ORG_NO);
        ArrayList<String> orgNumbers = new ArrayList<>();

        while (cursor.moveToNext())
        {
            String orgNo = cursor.getString(indexOne);
            orgNumbers.add(orgNo);
        }

        return orgNumbers;

    }



    private static class DatabaseEntry
    {
        private static final String TABLE_RESPONDENTS = "Respondents";
        private static final String TABLE_SURVEY = "SurveyData";
        private static final String TABLE_QUESTION = "Def_Question";

        private static final String RESPONDENTS_ID = "id";
        private static final String RESPONDENTS_EVENT_ID = "EventId";
        private static final String RESPONDENTS_SECTION_ID = "SectionId";
        private static final String RESPONDENTS_SUBSECTION_ID = "SubdectionId";
        private static final String RESPONDENTS_ORG_NO = "OrgNo";
        private static final String RESPONDENTS_ORGMEM_NO = "OrgMemNo";
        private static final String RESPONDENTS_MEMBER_NAME = "MemberName";
        private static final String RESPONDENTS_ADMISSION_DATE = "AdmissionDate";
        private static final String RESPONDENTS_LOAN_NO = "LoanNo";
        private static final String RESPONDENTS_DISB_DATE = "DisbDate";
        private static final String RESPONDENTS_AMOUNT = "Amount";
        private static final String RESPONDENTS_CO_NO = "CONo";
        private static final String RESPONDENTS_CO_NAME = "COName";
        private static final String RESPONDENTS_MONITOR_NO = "MonitorNo";
        private static final String RESPONDENTS_STATUS = "Status";
        private static final String RESPONDENTS_UPDATED_DATE = "Updated_At";

        private static final String SURVEY_ID = "Id";
        private static final String SURVEY_EVENT_ID = "EventId";
        private static final String SURVEY_SECTION_ID = "SectionId";
        private static final String SURVEY_SUBSECTION_ID = "SubSectionId";
        private static final String SURVEY_ORG_NO = "OrgNo";
        private static final String SURVEY_ORG_MEM_NO = "OrgMemNo";
        private static final String SURVEY_ID_QUESTION = "Question";
        private static final String SURVEY_ANSWER = "Answer";
        private static final String SURVEY_SCORE = "Score";
        private static final String SURVEY_REMARKS = "Remarks";
        private static final String SURVEY_MONITOR_NO = "MonitorNo";
        private static final String SURVEY_STATUS = "Status";
        private static final String SURVEY_UPDATED_DATE = "Updated_At";
        private static final String SURVEY_LONGI = "Longi";
        private static final String SURVEY_LATI = "Lati";
        private static final String SURVEY_VERIFIED = "Verified";


        private static final String QUESTION_ID = "Id";
        private static final String QUESTION_SECTION_NO = "SectionNo";
        private static final String QUESTION_SUBSECTION_ID = "SubSectionId";
        private static final String QUESTION_QUESTION_NO = "QuestionNo";
        private static final String QUESTION_QUESTION_DES = "QuestionDes";
        private static final String QUESTION_CHECK_FIELD = "CheckField";
        private static final String QUESTION_TIME_STAMP = "Timestamp";


        private static final String SURVEY_QUESTION_INFO = "select "+SURVEY_EVENT_ID
                +","+SURVEY_SECTION_ID
                +","+SURVEY_SUBSECTION_ID
                +","+SURVEY_ID_QUESTION
                +","+SURVEY_ORG_NO
                +","+SURVEY_MONITOR_NO +" from "+TABLE_SURVEY+" where "+SURVEY_EVENT_ID+"=? AND "+SURVEY_SECTION_ID+"=? AND "+SURVEY_MONITOR_NO+"=?";

        private static final String ACTUAL_QUESTION_INFO = "select "+QUESTION_SECTION_NO
                +","+QUESTION_SUBSECTION_ID
                +","+QUESTION_QUESTION_NO
                +","+QUESTION_CHECK_FIELD+" from "+TABLE_QUESTION;

        private static final String FIND_UNIQUE_MEMBER_FROM_SECTION_TWO = "SELECT DISTINCT "+
                SURVEY_ORG_NO
                +","+ SURVEY_ORG_MEM_NO
                +" FROM "+TABLE_SURVEY +" WHERE "+SURVEY_EVENT_ID+"=? AND "+SURVEY_SECTION_ID+"=? AND "+SURVEY_ID_QUESTION+" IS NOT NULL";

        private static final String FIND_UNIQUE_MEMBER_FROM_RESPONDENTS_USING_SECTION = "SELECT DISTINCT "+
                RESPONDENTS_ORG_NO
                +","+RESPONDENTS_ORGMEM_NO
                +" FROM "+TABLE_RESPONDENTS +" WHERE "+RESPONDENTS_EVENT_ID+"=? AND "+RESPONDENTS_SECTION_ID+"=? AND ("+RESPONDENTS_ORGMEM_NO+" IS NOT NULL AND LENGTH(TRIM("
                +RESPONDENTS_ORGMEM_NO+"))>0)";
        private static final String FIND_UNIQUE_MEMBER_FROM_RESPONDENTS_USING_SECTION_SUB_SECTION= "SELECT DISTINCT "+
                RESPONDENTS_ORG_NO
                +","+RESPONDENTS_ORGMEM_NO
                +" FROM "+TABLE_RESPONDENTS
                + " WHERE "+RESPONDENTS_EVENT_ID+"=? AND "+RESPONDENTS_SECTION_ID
                +"=? AND "+RESPONDENTS_SUBSECTION_ID+"=?" +" AND ("+RESPONDENTS_ORGMEM_NO+" IS NOT NULL AND LENGTH(TRIM("
                +RESPONDENTS_ORGMEM_NO+"))>0)";

        private static final String SURVEY_QUESTION_INFO_USING_RESPONDENTS_MEMBER = "select "+SURVEY_EVENT_ID
                +","+SURVEY_SECTION_ID
                +","+SURVEY_SUBSECTION_ID
                +","+SURVEY_ID_QUESTION
                +","+SURVEY_ORG_NO
                +","+SURVEY_MONITOR_NO +" from "+TABLE_SURVEY+" where "+SURVEY_EVENT_ID+"=? AND "+
                SURVEY_SECTION_ID+"=? AND "+SURVEY_ORG_NO+" IN (?)";
        private static final String FIND_UNIQUE_ORGANIZATION_VO = "SELECT DISTINCT "+
                RESPONDENTS_ORG_NO
                +" FROM "+TABLE_RESPONDENTS +" WHERE "+RESPONDENTS_EVENT_ID+"=? AND "+RESPONDENTS_SECTION_ID+"=? AND ("+RESPONDENTS_ORG_NO+" IS NOT NULL AND LENGTH(TRIM("
                +RESPONDENTS_ORG_NO+"))>0)";


    }


}
