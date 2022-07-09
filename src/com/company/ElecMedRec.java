package com.company;

import java.io.*;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class ElecMedRec implements Serializable {
    // private String dataPriema;
    private String timePriema;
    private Patient patient;

    public ElecMedRec() {
    }

    public ElecMedRec(String timePriema, Patient patient) {
        //this.dataPriema = dataPriema;
        this.timePriema = timePriema;
        this.patient = patient;
    }

    public void infoRecPat() {
        System.out.print(timePriema + " | ");
        this.patient.showInfoPat();
    }


    // Создание листа записи пациентов

    public String createListFile2(String dataPriema, String pathDir) throws IOException {

        File newListDoc = new File(pathDir); //объект для каталога для хранения списка очереди к врачу
        if (!newListDoc.isDirectory()) {
            newListDoc.mkdir();
            System.out.println("EMC has been created " + newListDoc.getAbsolutePath());
        }
        String pathFile = pathDir + "//" + dataPriema + ".dat2";
        File newL = new File(pathFile);
        ArrayList<ElecMedRec> aListRecPat = new ArrayList<>();

        if (!newL.exists()) {
            newL.createNewFile();
        //    System.out.println(newL.getAbsolutePath());
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathFile));

            String pathfH = Main.PATH_List + "//HUMAN//patients.txt";
            BufferedReader br = new BufferedReader(new FileReader(pathfH));

            String s;
            String[] str;
            String[] time = new String[]{"08:00 ", "08:20 ", "08:40 ", "09:00 ", "09:20 "};
            int i = 0;
            while ((s = br.readLine()) != null && i < 5) {
                str = s.split(" ");
                Patient pt1 = new Patient(str[1], str[2], str[3], str[4] + str[5], Integer.parseInt(str[6]));
//                ElecMedRec recP = new ElecMedRec(time[i], pt1); //для сериализации
//                aListRecPat.add(recP);
                bw.write(time[i] + pt1.getName() + " " + pt1.getDateG() + " " + pt1.getPolisOMS() + " " + pt1.getAddress() + " " + pt1.getIdPatient().toString() + "\n");
               // System.out.println(time[i] + " " + pt1.getName());
                i++;
            }
            bw.close();
            System.out.println("Список создан");
        } else {
            System.out.println("Список существует");
        }
        return pathFile;
    }

    // Вывод на консоль список записанных пациентов (и создание списка пациентов)

    public ArrayList<Patient> showListPatientFile2(String pathFile) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(pathFile));
        //System.out.println(pathFile);
        ArrayList<Patient> aListRecPat = new ArrayList<>();
        String s;
        String[] str;
        int i = 1;
        while ((s = br.readLine()) != null) {
            str = s.split(" ");
            aListRecPat.add(new Patient(str[1], str[2], str[3], str[4], Integer.valueOf(str[5])));
            System.out.println(i + " " + s);
            i++;
        }
        return aListRecPat;
    }

    // Выбор пациента из списка очереди
    public Patient casePatient2(ArrayList<Patient> arrList, Scanner sc) throws IOException {

       int n;

        while (true) {
            System.out.print("Выберите номер пациента:");
            n = sc.nextInt();
            if (n > 0 && n <= arrList.size()) {
                return arrList.get(n - 1);
            } else {
                System.out.println("Запись не существует");
            }
        }
    }

    //для сериализации
    public String createListFileSer(String dataPriema, String pathDir) throws IOException {

        File newListDoc = new File(pathDir); //объект для каталога для хранения списка очереди к врачу
        if (!newListDoc.isDirectory()) {
            newListDoc.mkdir();
            System.out.println("EMC has been created " + newListDoc.getAbsolutePath());
        }
        String pathFile = pathDir + "//" + dataPriema + "Ser.dat";
        File newL = new File(pathFile);

        ArrayList<ElecMedRec> aListRecPat = new ArrayList<>();

        if (!newL.exists()) {
            newL.createNewFile();
            System.out.println(newL.getAbsolutePath());
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathFile));

            String pathfH = pathDir + "//HUMAN//human.txt";
            BufferedReader br = new BufferedReader(new FileReader(pathfH));

            String s;
            String[] str;
            String[] time = new String[]{"08:00", "08:20", "08:40", "09:00", "09:20"};
            int i = 0;
            while ((s = br.readLine()) != null && i < 5) {
                str = s.split(" ");
                Patient pt1 = new Patient(str[1], str[2], str[3], str[4] + str[5], i + 1);
                ElecMedRec recP = new ElecMedRec(time[i], pt1);
                aListRecPat.add(recP);
//                bw.write(time[i] + pt1.getName() + " " + pt1.getDateG() + " " + pt1.getPolisOMS() + " " + pt1.getAddress() + " " + pt1.getIdPatient().toString() + "\n");
//                System.out.println(time[i] + " " + pt1.getName());
                i++;
            }

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(pathFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(aListRecPat);
                oos.close();
                fos.close();
                System.out.println("File has been written");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            System.out.println("Список создан");
        } else {
            System.out.println("Список существует");
        }

        return pathFile;
    }
}

