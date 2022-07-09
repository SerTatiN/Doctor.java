package com.company;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;

public class Patient extends Human implements Serializable {
    private Integer idPatient;
    private boolean isPriem;
    public ArrayList<Medical> medicals;

    public Patient(){
        }


    public Patient(String name, String dateG, String polisOMS, String address, Integer idPatient) {
        super(name, dateG, polisOMS, address);
        this.idPatient = idPatient;
        this.isPriem = false;

        if (!getDirEMC().isDirectory()) {
            getDirEMC().mkdir();
            System.out.println("EMC has been created " + getDirEMC().getAbsolutePath());
        }
        this.medicals = new ArrayList<>();
    }


    public File getDirEMC() {
         File dirEMC= new File(Main.PATH_List + "EMCard//"+ idPatient.toString()); //объект для каталога для хранения электр.мед.карты
        return dirEMC;
    }

    public boolean isPriem() {
        return isPriem;
    }

    public Integer getIdPatient() {
        return idPatient;
    }

    public Patient getPatientId(Integer id) {
        if (id == this.idPatient) {
            return this;
        } else {
            return null;
        }
    }

    public void setPriem(boolean priem) {
        isPriem = priem;
    }

    public void addMedical(Medical ml){
        medicals.add(ml);
    }


    public void infoMedical(int i){
        System.out.println("Больничный лист выдан " + getName());
        medicals.get(i).showML();

    }

    public void showInfoPat() {
        System.out.print(idPatient.toString() + " | ");
        infoHuman();
        if (!medicals.isEmpty()) {
            for (int i=0;i< medicals.size();i++) {
                infoMedical(i);
            }
        }
        System.out.println();
    }

    public  int isOpenMedical() {
        if (!this.medicals.isEmpty()) {
            for (int j = 0; j < this.medicals.size(); j++) {
                if (!this.medicals.get(j).isStatus()) {
                    return j;
                }
            }

        }
        return -1;
    }

}
