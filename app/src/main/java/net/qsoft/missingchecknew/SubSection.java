package net.qsoft.missingchecknew;

public class SubSection {
    private int subSectionId;
    private int mandatoryMembers;
    private int selectedMembers;
    private int numOfMissQues;
    private String memberType;

    public SubSection(int subSectionId, int mandatoryMembers, int selectedMembers, int numOfMissQues, String memberType) {
        this.subSectionId = subSectionId;
        this.mandatoryMembers = mandatoryMembers;
        this.selectedMembers = selectedMembers;
        this.numOfMissQues = numOfMissQues;
        this.memberType = memberType;
    }

    public int getSubSectionId() {
        return subSectionId;
    }

    public void setSubSectionId(int subSectionId) {
        this.subSectionId = subSectionId;
    }

    public int getMandatoryMembers() {
        return mandatoryMembers;
    }

    public void setMandatoryMembers(int mandatoryMembers) {
        this.mandatoryMembers = mandatoryMembers;
    }

    public int getSelectedMembers() {
        return selectedMembers;
    }

    public void setSelectedMembers(int selectedMembers) {
        this.selectedMembers = selectedMembers;
    }

    public int getNumOfMissQues() {
        return numOfMissQues;
    }

    public void setNumOfMissQues(int numOfMissQues) {
        this.numOfMissQues = numOfMissQues;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }
}
