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


        float totalPrice[] = totalPrice(shoppinglist);

        System.out.println("Prisen uden rabbat er : " + totalPrice[0] + " og prisen med rabatter er : " + totalPrice[1]);

        printReceipt(shoppinglist);
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

    public static float [] totalPrice(VareData[] array) {

        float totalPriceNoDiscount = 0;
        float totalPriceDiscount = 0;

        for (VareData items : array) {

            totalPriceNoDiscount += totalItemPrice(items)[0];

            totalPriceDiscount += totalItemPrice(items)[1];


        }

        return new float[] {totalPriceNoDiscount, totalPriceDiscount};

    }




    public static float[] totalItemPrice (VareData item) {

        float priceNoDiscount = item.getAntal() * item.getStykPris();

        if (item.getAntal() > 10) {
            float priceDiscount = (float) ((item.getAntal() * item.getStykPris()) * 0.85);

            return new float[]{priceNoDiscount, priceDiscount};
        } else {
            return new float[] {priceNoDiscount, priceNoDiscount};
        }

    }

    public static void printReceipt (VareData[] items) throws IOException {

        File receipt = new File ("faktura.txt");

        if (receipt.createNewFile()) {
            System.out.println("File created " + receipt.getName());
        } else {
            System.out.println("File already exists");
        }

        FileWriter fileWriter = new FileWriter(receipt);

        fileWriter.write("Fakta \n");
        fileWriter.write("\nYou have bought the following items \n");

        for (VareData item : items) {

            fileWriter.write("\n" + item.getAntal() + "\t" + item.getVareNavn() + "\t\t\t\t" + totalItemPrice(item)[0] + " kr,-\n");

            if (totalItemPrice(item)[1] != totalItemPrice(item)[0]) {
                fileWriter.write("\tDiscount:\t\t\t-" + (totalItemPrice(item)[0] - totalItemPrice(item)[1]) + " kr,-\n");
                fileWriter.write("Price with added discount: " + totalItemPrice(item)[1] + "\n\n");
            }

        }

        fileWriter.write("\nYour total price is: " + totalPrice(items)[0] + "\n" );
        fileWriter.write("\nYour total discount is: " + (totalPrice(items)[0] - totalPrice(items)[1]) + "\n" );
        fileWriter.write("\nYour total discounted price is: " + totalPrice(items)[1]);

        fileWriter.close();

        BufferedReader in = new BufferedReader(new FileReader("faktura.txt"));

        String line = in.readLine();
        while (line != null){
            System.out.println(line);
            line = in.readLine();
        }
        in.close();
    }

}