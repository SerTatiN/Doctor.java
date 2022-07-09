package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final String PATH_List = "D://Tatiana//JAVA//20.06.2022//DOC//";


    public static Doctor avtorization() throws IOException {
        Doctor dtr = new Doctor();
        String sLogin;
        String sPass;
        boolean f = false;
        Scanner sc = new Scanner(System.in);
        ArrayList<Doctor> arrDoc = dtr.listDoctorsOutFile();
        while (!f) {
            System.out.print("   Введите login: ");
            sLogin = sc.nextLine();
            System.out.print("   Введите password: ");
            sPass = sc.nextLine();
            dtr = dtr.avtoriz(arrDoc, sLogin, sPass);

            if (dtr != null) {
                f = true;
            } else {
                System.out.println("Вы не авторизованы!");
                dtr = new Doctor();
            }

        }
        return dtr;
    }

    public static void menu21(Doctor dtr, Scanner sc, ArrayList<Patient> arrayList, String date) throws IOException {
        shouMenu21();

        int n;
        Patient patient = null;
        boolean casePacient = false;
        ElecMedRec recPat = new ElecMedRec();
        boolean f = false;
        while (!f) {
            System.out.print("Выберете дальнейшее действие:");
            n = sc.nextInt();
            switch (n) {
                case (1):
                    //patient = dtr.casePatient(arrayList,sc);
                    patient = recPat.casePatient2(arrayList, sc);
                    System.out.println("Выбран пациент ");
                    System.out.println(patient.getName());
                    casePacient = true;
                    break;
                case (2):
                    System.out.println("Просмотр информации о пациенте"); // если выбран пациент
                    if (casePacient) {
                        patient.showInfoPat();
                    } else {
                        System.out.println("Пациент не выбран");
                    }
                    break;
                case (3):
                    if (casePacient) {
                        System.out.println("Прием пациента"); // если выбран пациент
                        if (patient.isPriem()) {
                            System.out.println("Пациент уже был принят!");
                        } else {
                            dtr.priemPatient(patient, date);
                        }
                        shouMenu21();
                    } else {
                        System.out.println("Пациент не выбран");
                    }
                    break;
                case (4):
                    shouMenuGL();
                    f = true;
                    break;
                default:
                    System.out.println("Попробуйте еще раз выбрать действие!");
                    break;
            }
        }
    }

    public static void menu24(Doctor dtr, ArrayList<Patient> arrPat, Scanner sc) {
        System.out.println("Меню отчетов: ");
        System.out.println("1. Отчет по принятым пациентам на сегодня");
        System.out.println("2. Отчет по открытым больничным");
        System.out.println("3. Выход");

        int n;
        boolean f = false;
        while (!f) {
            System.out.print("Выберете дальнейшее действие: ");
            n = sc.nextInt();
            switch (n) {
                case (1):
                    System.out.println("Отчет по принятым пациентам на сегодня:");
                    System.out.println("Записано пациентов к : " + dtr.getSpecDoc() + " " + dtr.getName() + " " + arrPat.size());

                    int num = 0;
                    for (int j = 0; j < arrPat.size(); j++) {
                        if (arrPat.get(j).isPriem()) {
                          //  arrPat.get(j).infoHuman();
                            num++;
                        }
                    }
                    System.out.println("Принято:" + num);
                    break;
                case (2):
                    System.out.println("Отчет по открытым больничным:");
                    num = 0;
                    for (int i = 0; i < arrPat.size(); i++) {
                        if (!arrPat.get(i).medicals.isEmpty()) {
                            for (int j = 0; j < arrPat.get(i).medicals.size(); j++) {
                                if (!arrPat.get(i).medicals.get(j).isStatus()) {
                                    num++;
                                   // arrPat.get(i).infoMedical(j);
                                }
                            }
                        }
                    }
                    System.out.println("Открыто больничных: " + num);
                    break;
                case (3):
                    System.out.println("Выход");
                    System.out.print("Вы уверены, что хотите выйти? (0-нет/1-да) ");
                    n = sc.nextInt();
                    if (n == 1) f = true;
                    break;
                default:
                    System.out.println("Попробуйте еще раз выбрать действие!");
                    f = true;
                    break;
            }
        }
    }

    public static void menu2(Doctor dtr, Scanner sc, String filePathList, String date) throws IOException { //главное меню
        shouMenuGL();

        ArrayList<Patient> arrayList = null;

        Patient patient = null;
        ElecMedRec recPat = new ElecMedRec();
        boolean casePacient = false;
        int n;
        boolean f = false;
        while (!f) {
            System.out.print("Выберете дальнейшее действие: ");
            n = sc.nextInt();
            switch (n) {
                case (1):
                    System.out.println("Записанные пациенты на сегодня:");

                    arrayList = recPat.showListPatientFile2(filePathList);
                    menu21(dtr, sc, arrayList, date);
                    break;
                case (2):
                    System.out.println("Выбор пациента:");
                    if (arrayList == null) {
                        System.out.println("Записанные пациенты на сегодня:");

                        arrayList = recPat.showListPatientFile2(filePathList);
                    }

                    patient = recPat.casePatient2(arrayList, sc);
                    System.out.println("Выбран пациент ");
                    System.out.println(patient.getName());
                    casePacient = true;
                    break;
                case (3):
                    System.out.println("Начат прием");
                    if (arrayList == null) {
                        System.out.println("Записанные пациенты на сегодня " + date + " :");

                        arrayList = recPat.showListPatientFile2(filePathList);
                    }
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (!casePacient) {

                            patient = recPat.casePatient2(arrayList, sc);
                        }
                        if (patient.isPriem()) {
                            System.out.println("Пациент уже был принят!");

                            patient = recPat.casePatient2(arrayList, sc);
                        }
                        dtr.priemPatient(patient, date);
                        System.out.println("принят- " + patient.getName() +" " + patient.isPriem());
                        System.out.print("Остановить прием? (0/1)");
                        n = sc.nextInt();
                        if (n == 1) {
                            System.out.println("Остались непринятые пациенты :");
                            for (int j = 0; j < arrayList.size(); j++) {
                                if (!arrayList.get(j).isPriem()) {
                                    arrayList.get(j).infoHuman();
                                }
                            }
                            System.out.print("Вы уверены, что хотите остановить прием? (0/1)");
                            n = sc.nextInt();
                            if (n == 1) {
                                break;
                            }
                        }
                    }
                    shouMenuGL();
                    break;
                case (4):
                    System.out.println("Формирование отчетов");
                    menu24(dtr, arrayList, sc);
                    shouMenuGL();
                    break;
                case (5):
                    System.out.print("Вы уверены, что хотите выйти? (0-нет/1-да) ");
                    n = sc.nextInt();
                    System.out.print("До свидания!");
                    if (n == 1) f = true;
                    break;
                default:
                    System.out.println("Попробуйте еще раз выбрать действие!");
                    break;
            }
        }
    }

    public static void shouMenuGL() {
        System.out.println("Главное меню ");
        System.out.println("1. Посмотреть записанных пациентов на сегодня");
        System.out.println("2. Выбрать пациента");
        System.out.println("3. Начать прием");
        System.out.println("4. Работа с документами (отчеты)");
        System.out.println("5. Выход");
    }

    public static void shouMenu21() {
        System.out.println("Подменю \" Записанные пациенты\":");
        System.out.println("1. Выбрать пациента");
        System.out.println("2. Посмотреть информацию о пациенте");
        System.out.println("3. Принять пациента");
        System.out.println("4. Вернуться в главное меню");
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Doctor doctor;
        ElecMedRec recPat = new ElecMedRec();

        System.out.println("Вас приветствует система АРМ поликлиники! ");
        System.out.println("1. Авторизация");
        System.out.println("2. О программе");
        System.out.println("3. Выход");
        System.out.println("Для начала работы необходимо авторизоваться ");
        int n;
        String date = "09.07.2022";
        boolean f = false;
        String filepath;
        String filepath1;
        while (!f) {
            System.out.print("Выберете действие: ");
            n = sc.nextInt();
            switch (n) {
                case (1):
                    doctor = avtorization();
                    String fileDir = PATH_List + "RECORD//" + doctor.getSpecDoc();
                    filepath = recPat.createListFile2(date, fileDir);

                    if (doctor != null) {
                        menu2(doctor, sc, filepath, date);
                        // f = true;
                    }
                    System.out.print("\n Сменить пользователя или завершить работу? (0/1) ");
                    n = sc.nextInt();
                    if (n == 1) {
                        f = true;
                    } else {
                        System.out.println("1. Авторизация");
                        System.out.println("2. О программе");
                        System.out.println("3. Выход");
                        System.out.println("Для начала работы необходимо авторизоваться ");
                    }
                    break;
                case (2):
                    System.out.println("АРМ врача поликлиники \n Выполнен в рамках курсовой работы \n Преподаватель: Пунченко Евгений  \n " +
                            "Студент: Сергеева Татьяна \n Группа: JAVA221\n");
                    break;
                case (3):
                    System.out.println("До скорой встречи!");
                    f = true;
                    break;
                default:
                    System.out.println("Попробуйте авторизоваться!");
                    break;
            }
        }
    }
}