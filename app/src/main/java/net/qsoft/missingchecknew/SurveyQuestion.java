package net.qsoft.missingchecknew;

public class SurveyQuestion {
    private int eventId;
    private int sectionId;
    private String subSectionId;
    private String orgNo;
    private String orgMemNumber;
    private String monitorNo;
    private int questionNo;


    public SurveyQuestion(int eventId, int sectionId, String subSectionId, String orgNo, String monitorNo, int questionNo, String orgMemNumber) {
        this.eventId = eventId;
        this.sectionId = sectionId;
        this.subSectionId = subSectionId;
        this.orgNo = orgNo;
        this.monitorNo = monitorNo;
        this.questionNo = questionNo;
        this.orgMemNumber = orgMemNumber;
    }

    public SurveyQuestion(int eventId, int sectionId, String subSectionId, String orgNo, String monitorNo, int questionNo) {
        this.eventId = eventId;
        this.sectionId = sectionId;
        this.subSectionId = subSectionId;
        this.orgNo = orgNo;
        this.monitorNo = monitorNo;
        this.questionNo = questionNo;
    }

    public String getOrgMemNumber() {
        return orgMemNumber;
    }

    public void setOrgMemNumber(String orgMemNumber) {
        this.orgMemNumber = orgMemNumber;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getSubSectionId() {
        return subSectionId;
    }

    public void setSubSectionId(String subSectionId) {
        this.subSectionId = subSectionId;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getMonitorNo() {
        return monitorNo;
    }

    public void setMonitorNo(String monitorNo) {
        this.monitorNo = monitorNo;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }
}
