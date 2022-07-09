package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public  class MedLek {

    public static String caseLekarstvo(String codD) throws IOException {
        String fnLek = Main.PATH_List + "DOCTORS//TEMPLATES//lekarstva.txt";
        BufferedReader brLek = new BufferedReader(new FileReader(fnLek));
        int num;
        int dn;
        String[] str;
        String tempStr="";
        String s = "";
        boolean fD = false;
        Scanner sc = new Scanner(System.in);

        while ((s = brLek.readLine()) != null) {
            str = s.split(" ");

            if (str[0].equals(codD)) {
                System.out.println("Предлагаются следующие лекарственные средства:");
                for (int i = 1; i < str.length; i++) {
                    System.out.println("         " +i + " " + str[i]);
                }
                fD = true;
                do {
                    System.out.print("Введите номер лекарства: ");
                    num = sc.nextInt();
                    if (num > 0 && num < str.length) {
                        tempStr += str[num];
                    } else {
                        System.out.println("Под этим номером нет лекарства");
                    }
                    System.out.print("Выбрать еще лекарства: (0-нет/1-да)");

                    dn = sc.nextInt();
                    if (dn == 1) {
                        tempStr += " ";
                    } else  if (dn!=0) {
                        System.out.println("  Введите: (0 или 1)");
                    }
                } while (dn!=0);
                break;
            }
        }
        if (!fD) {
            System.out.println("Диагноз не соответствует МКБ-10 или отсутствует в файле");
            tempStr = "Больше отдыхайте!";
        }
        return tempStr;
    }
    public static String freeLekarstvo(Scanner sc) throws IOException {

        String tempStr = "";
        String s = "";
        do {
            System.out.print("Введите другое лекарство: ");
            tempStr += sc.nextLine();
            System.out.print("Добавить еще лекарства: (0/1)");
             s = sc.nextLine();
            if (s.equals("1")) {
                tempStr += " ";
            }
        } while (!s.equals("0"));

        return tempStr;
    }

}
