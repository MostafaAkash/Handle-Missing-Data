package net.qsoft.missingchecknew;

public class OrgMem {
    private int id;
    private String orgNumber;
    private String orgMemberNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrgMem(String orgNumber, String orgMemberNumber) {
        this.orgNumber = orgNumber;
        this.orgMemberNumber = orgMemberNumber;
    }

    public OrgMem(int id, String orgNumber, String orgMemberNumber) {
        this.orgNumber = orgNumber;
        this.orgMemberNumber = orgMemberNumber;
        this.id = id;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getOrgMemberNumber() {
        return orgMemberNumber;
    }

    public void setOrgMemberNumber(String orgMemberNumber) {
        this.orgMemberNumber = orgMemberNumber;
    }
}
