package com.company;

import java.io.*;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.io.Serializable;
import java.util.function.Consumer;

public class Doctor extends Human implements Serializable{
    private String specDoc; // Профиль врача
    private String login;
    private String pass;

    public static final  String PATH_List = "D://Tatiana//JAVA//20.06.2022//DOC//RECORD//";

    public Doctor(String name, String dateG, String polisOMS, String address,String specDoc, String login, String pass) {
        super(name, dateG, polisOMS, address);
        this.specDoc = specDoc;
        this.login = login;
        this.pass = pass;
    }

    public Doctor() {

    }

    public String getSpecDoc() {
        return specDoc;
    }

    private void setSpecDoc(String specDoc) {
        this.specDoc = specDoc;
    }

// авторизация врача по данным

    public Doctor avtoriz (ArrayList<Doctor> arrDoc,String login, String pass) throws IOException {
       // ArrayList <Doctor> arrDoc = listDoctorsOutFile();

        for (int i = 0; i<arrDoc.size(); i++) {
       //   arrDoc.get(i).infoHuman();
            if (verif(arrDoc.get(i), login, pass)) {
              return arrDoc.get(i);
            }
        }
      return null;
    }
    // проверка авторизационных данных

    private boolean verif (Doctor dr,String login, String pass){
        return dr.login.equals(login) && dr.pass.equals(pass);
    }
// создание списка объектов-врачей поликлиники

    public ArrayList listDoctorsOutFile() throws IOException {
        ArrayList <Doctor> arrDoc = new ArrayList<>();
        String pathFile = Main.PATH_List+"DOCTORS//ACCOUNTS//doctors.dat";
        BufferedReader br = new BufferedReader(new FileReader(pathFile));

        String[] str;
        String s;
        while ((s = br.readLine())!= null) {
            str = s.split(" ");   //13 Пухова 04.04.1985 15210464 Surova 8-35 терапевт puhova p123
                                        //14 Лаптев 03.08.1978 1038461 Карбышева 15-10 ЛОР laptev l123
            arrDoc.add(new Doctor(str[1], str[2], str[3], str[4] + str[5], str[6], str[7], str[8]));
        }
        return arrDoc;
    }


    public void priemPatient(Patient patient, String date) throws IOException {
          patient.setPriem(true);

          recElProtocol(patient,  date);

        System.out.println("Прием пациента окончен");

    }

    // формирование и запись протокола в файл с датой, в каталог эл. карты пациента (в "папку" для каждого врача)

    public void recElProtocol(Patient patient, String date) throws IOException {
        System.out.println("Начата запись в электронный протокол осмотра");

        String pathDir = patient.getDirEMC().getAbsoluteFile()+ "//" + this.specDoc+"//";
        File newListDoc = new File(pathDir); //объект для каталога (для врача-специалиста) для хранения протокола осмотра
        if (!newListDoc.isDirectory()) {
            newListDoc.mkdir();
            System.out.println("Создан " + newListDoc.getAbsolutePath());
        }

        String fileNameElProt = pathDir + date + ".txt";
        File newEPO = new File(fileNameElProt);
        newEPO.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileNameElProt));

        String fileNameTemplate = Main.PATH_List+"DOCTORS//TEMPLATES//protocol.txt";
        BufferedReader brTemplate = new BufferedReader(new FileReader(fileNameTemplate));
        Scanner sc = new Scanner(System.in);

        String s = "Прием врача-специалиста " + this.specDoc + " " + this.getName()+ " "+   date+"\n";
        int numPunktTempl = 1;
        bw.write(s);
        String str = "";
        String codDiagnoz = "";
        String strTemp = "";
        boolean f = false;
        while (!s.equals("1")) {
            try {
                while((str = brTemplate.readLine())!=null) {
                    System.out.print(numPunktTempl +" "+ str + " ");

                    if (numPunktTempl != 6) {
                        strTemp = sc.nextLine();
                    } else {
                        do {
                            System.out.print("    Выбрать из предложенного? (y/n):  ");
                            String q= sc.nextLine();

                            if (q.equals("y")) {
                                strTemp = MedLek.caseLekarstvo(codDiagnoz);
                                System.out.println(strTemp);
                                f = true;
                            } else {
                                if (q.equals("n")) {
                                    strTemp = MedLek.freeLekarstvo(sc);
                                    System.out.println(strTemp);
                                    f = true;
                                } else {
                                    System.out.println("Укажите, пожалуйста какое будете назначать лекарства");
                                }
                            }
                        } while (!f);
                    }
                    if (numPunktTempl == 4) {
                        codDiagnoz = strTemp;
                    }

                    if (numPunktTempl == 7) {
                        int isMl = patient.isOpenMedical();
                        if ( isMl== -1) {
                            System.out.print("    Открыть больничный? (y/n):  ");
                            String q = sc.nextLine();
                            if (q.equals("y")) {
                                strTemp = openMedical(patient, date, codDiagnoz);
                            }
                        } else {
                            System.out.print("    Закрыть больничный? (y/n):  ");
                            String q = sc.nextLine();
                            if (q.equals("y")) {
                                System.out.print("Введите дату окончания ");
                                String dE = sc.nextLine();
                                patient.medicals.get(isMl).closeMedical(dE);
                                strTemp = "Закрыт больничный лист " + patient.medicals.get(isMl).getNumber();
                            } else {
                                System.out.print("Введите новую дату окончания ");
                                String dE = sc.nextLine();
                                patient.medicals.get(isMl).setDataEnd(dE);
                            }
                        }
                    }
                    str = str + " " + strTemp;
                    bw.write(str+"\n");
                    numPunktTempl++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.print("Сохранить протокол осмотра? (1/0)");
            s = sc.nextLine();
            if (s.equals("0")) {
                System.out.println("Пожалуй, все-таки сохраним протокол осмотра.");
                s = "1";
            }
        }
        bw.flush();
        bw.close();
    }

    // формирование и запись протокола в файл с датой, в каталог эл. карты пациента (в "папку" для каждого врача)

    public String openMedical(Patient patient, String date,String dz) throws IOException {
        System.out.println("Открытие больничного листа");

        String dE;
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите дату предположительного окончания ");
        dE = sc.nextLine();

        Medical ml = new Medical(date,dE,getName(),dz);

        patient.addMedical(ml);
        patient.infoMedical(0);

     return ml.getNumber();
    }
}
