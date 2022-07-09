package com.company;

public class Medical {
    private String number;
    private String dataBeg;
    private String dataEnd;
    private String nameDoc;
    private boolean status;
    private String diagnosis;
    public static Integer numb=0;

    public Medical() {

    }

    public Medical(String dataBeg, String dataEnd, String nameDoc, String diagnosis) {
        numb++;
        this.number = "7345-" + numb.toString();
        this.dataBeg = dataBeg;
        this.dataEnd = dataEnd;
        this.nameDoc = nameDoc;
        this.diagnosis = diagnosis;
        this.status = false;
    }

    public String getNumber() {
        return number;
    }

    public String getDataBeg() {
        return dataBeg;
    }

    public void setDataBeg(String dataBeg) {
        this.dataBeg = dataBeg;
    }

    public String getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(String dataEnd) {
        this.dataEnd = dataEnd;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void closeMedical(String dE) {
        System.out.println("Закрыт больничный лист");
        setDataEnd(dE);
        setStatus(true);
    }
    public void showML(){
        System.out.println(" № " + number);
        System.out.println("Период " + dataBeg+ " " + dataEnd);
        System.out.println("Диагноз: " + diagnosis);
        System.out.println("Лечащий врач: " + nameDoc);
        if (!status) {
            System.out.println(" не закрыт!");
        }
    }

}
