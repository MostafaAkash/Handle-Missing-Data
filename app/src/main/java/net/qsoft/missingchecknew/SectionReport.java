package net.qsoft.missingchecknew;

public class SectionReport {
    private int totalManMissQuestion;
    private String reportData;

    public SectionReport(int totalManMissQuestion, String reportData) {
        this.totalManMissQuestion = totalManMissQuestion;
        this.reportData = reportData;
    }

    public int getTotalManMissQuestion() {
        return totalManMissQuestion;
    }

    public void setTotalManMissQuestion(int totalManMissQuestion) {
        this.totalManMissQuestion = totalManMissQuestion;
    }

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }
}
