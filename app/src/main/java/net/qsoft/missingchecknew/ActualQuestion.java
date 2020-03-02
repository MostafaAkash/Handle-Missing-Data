package net.qsoft.missingchecknew;

public class ActualQuestion {

    private int sectionId;
    private int subSectionId;
    private int questionNo;

    public int getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(int isMandatory) {
        this.isMandatory = isMandatory;
    }

    private int isMandatory;

    public ActualQuestion(int sectionId, int subSectionId, int questionNo,int isMAndatory) {
        this.sectionId = sectionId;
        this.subSectionId = subSectionId;
        this.questionNo = questionNo;
        this.isMandatory = isMAndatory;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getSubSectionId() {
        return subSectionId;
    }

    public void setSubSectionId(int subSectionId) {
        this.subSectionId = subSectionId;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }
}
