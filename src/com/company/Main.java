package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main implements java.io.Serializable {

    private static int antalVare = 5;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        VareData[] shoppinglist = new VareData[5];

        shoppinglist = textTilArray();

        printArray(shoppinglist);

        lavDataFile(shoppinglist);

        File file = new File("varer.set");

        shoppinglist = dataFilTilArray(file);

        printArray(shoppinglist);
    }


    public static VareData[] textTilArray() throws FileNotFoundException {

        File bestilling = new File("src/com/company/bestilling");
        Scanner input = new Scanner(bestilling);
        VareData[] shoppingList = new VareData[5];

        for (int i = 0; i < antalVare; i++) {
            shoppingList[i] = new VareData(input.nextInt(), input.next(), input.nextFloat());
        }
        input.close();

        return shoppingList;
    }

    public static void printArray(VareData[] array) {

        for (VareData data : array) {
            System.out.println(data.getAntal() + " stk. " + data.getVareNavn() + " til en stykpris på " + data.getStykPris());
        }

    }

    public static void lavDataFile(VareData[] shoppingList) throws IOException {

        File varer = new File("varer.set");

        if (varer.createNewFile()) {
            System.out.println("File created " + varer.getName());
        } else {
            System.out.println("File already exists");
        }

        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(varer));

        for (VareData list : shoppingList) {
            output.writeObject(list);
        }
        output.close();

    }

    public static VareData[] dataFilTilArray(File data) throws IOException, ClassNotFoundException {

        ObjectInputStream input = new ObjectInputStream(new FileInputStream(data));
        List<VareData> list = new ArrayList<>();

        try {
            for (; ; ) {
                list.add((VareData) input.readObject());
            }
        } catch(EOFException exc){
            // end of stream
        }

            input.close();

            VareData[] vareData = new VareData[list.size()];
            for (int i = 0; i < list.size(); i++) {
                vareData[i] = list.get(i);
            }

            return vareData;


    }


}