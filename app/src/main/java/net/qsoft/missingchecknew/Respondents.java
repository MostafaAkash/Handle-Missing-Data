package net.qsoft.missingchecknew;

import java.util.ArrayList;

public class Respondents {
    private int subSectionNo;
    private int numOfMandRespondents;
    private ArrayList<OrgMem>respondentMembers;

    public Respondents(int subSectionNo, int numOfMandRespondents, ArrayList<OrgMem> respondentMembers) {
        this.subSectionNo = subSectionNo;
        this.numOfMandRespondents = numOfMandRespondents;
        this.respondentMembers = respondentMembers;
    }

    public int getSubSectionNo() {
        return subSectionNo;
    }

    public void setSubSectionNo(int subSectionNo) {
        this.subSectionNo = subSectionNo;
    }

    public int getNumOfMandRespondents() {
        return numOfMandRespondents;
    }

    public void setNumOfMandRespondents(int numOfMandRespondents) {
        this.numOfMandRespondents = numOfMandRespondents;
    }

    public ArrayList<OrgMem> getRespondentMembers() {
        return respondentMembers;
    }

    public void setRespondentMembers(ArrayList<OrgMem> respondentMembers) {
        this.respondentMembers = respondentMembers;
    }
}
