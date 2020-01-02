package toto;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Alk 
{

    public static void main(String[] args)
    {
        //Adatok beolvasása a fileból egy listába.
        ArrayList<String> adatok = new ArrayList<String>();
        try 
        {
            File beolvasando = new File("toto.csv");
            Scanner beolvaso = new Scanner(beolvasando);
            while (beolvaso.hasNextLine()) 
            {
                String adat = beolvaso.nextLine();
                adatok.add(adat);
            }
            beolvaso.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("Hiba történt a beolvasáskor, a file nem található!");
        }
        //Szétválasztom az egy sorban lévő adatokat.
        String[] sor;
        ArrayList<String> osszegek = new ArrayList<String>();
        double xcount = 0, onecount = 0, twocount = 0;//Ezek a statisztikához kellenek, ezekkel számolom meg hányszor nyert melyik csapat és lett döntetlen.
        for (int i = 0; i < adatok.size(); i++) 
        {
         sor = adatok.get(i).split(";");
         //A nyerési összegeket összegyűjtöm egy osszegek listába, hogy később ebből kiszedhessem a legnagyobbat.
         osszegek.add(sor[5]);
         osszegek.add(sor[7]);
         osszegek.add(sor[9]);
         osszegek.add(sor[11]);
         osszegek.add(sor[13]);
         //Megszámolom meg hányszor nyertek a csapatok és lett döntetlen.
            for (int j = 14; j < 28; j++) 
            {
                if(sor[j].equals("x") || sor[j].equals("X") || sor[j].equals("+x") || sor[j].equals("+X"))
                {
                    xcount++;
                }else if(sor[j].equals("1") || sor[j].equals("+1"))
                {
                    onecount++;
                }else if(sor[j].equals("2") || sor[j].equals("+2"))
                {
                    twocount++;
                }
            }
        }
        //Kiszedem az összegek listából a legnagyobbat.
        String osszeg = "";
        int ossz;
        int maxosszeg = 0;//Ez lesz intben a legnagyobb összeg.
        String maxossz = "";//Ez meg stringben, ugyanolyan formában ahogy az excel file-ban szerepel.
        for (int i = 0; i < osszegek.size(); i++) 
        {
            osszeg = osszegek.get(i).replace(" ", "").replace("Ft", "");
            ossz = Integer.parseInt(osszeg);
            if(ossz>maxosszeg)
            {
                maxosszeg = ossz;
                maxossz = osszegek.get(i);
            }
            
        }
        //Kiiratom a legnagyobb nyereményt amit valaha rögzítettek.
        System.out.println("A legnagyobb nyeremeny amit rogzitettek: " + maxossz.replaceFirst(" ", ",").replaceFirst((" "), (",")));
        //Kimatematikázom hányszor %-ban lett döntetlen, első csapat bagy második csapat győzelme.
        double x = xcount + onecount + twocount;
        double xszazalek = xcount / x * 100;
        double oneszazalek = onecount / x * 100;
        double twoszazalek = twocount / x * 100;
        //Itt pedig kiírom 2 tizedesjegyre kerekítve az eredményt.
        System.out.println("Statisztika: #1 csapat nyert: " + String.format("%.2f", oneszazalek) + " %, #2 csapat nyert: " + String.format("%.2f", twoszazalek) + " %, dontetlen: " + String.format("%.2f", xszazalek) + " %");
        //Bekérem a felhasználó tippjeit.
        Scanner szkennelo = new Scanner(System.in);
        System.out.print("Kerem adjon meg egy datumot[YYYY.MM.DD.]: ");
        String datum = szkennelo.next();
        System.out.print("Kerem adjon meg egy tippet[12121212121212]: ");
        String tipp = szkennelo.next();
        ArrayList<String> eredmenyek = new ArrayList<String>();
        ArrayList<String> nyeremenyek = new ArrayList<String>();
        //Végig megyek újra az adatok listán, megkeresem az egyező dátumot és ellenőrzöm jók e a tippek, jó adatokat adott e meg a felhasználó.
        Boolean hiba = true;
        for (int i = 0; i < adatok.size(); i++) 
        {
            sor = adatok.get(i).split(";");
            if(sor[3].equals(datum))
            {
                hiba = false;
                for (int j = 14; j < 28; j++) 
                {
                    eredmenyek.add(sor[j]);
                    nyeremenyek.add(sor[5]); //0. elem: 14-es találat
                    nyeremenyek.add(sor[7]); //1. elem: 13-as találat
                    nyeremenyek.add(sor[9]); //2. elem: 12-es találat
                    nyeremenyek.add(sor[11]); //3. elem: 11-es találat
                    nyeremenyek.add(sor[13]); //4. elem: 10-es találat
                }
            }
        }
        if(hiba.equals(true))
        {
            System.out.println("Rossz dátumot adtál meg!");
        }
        String[] tippek = tipp.split("");
        int talalatok = 0;
        for (int i = 0; i < 14; i++) 
        {
            if(eredmenyek.get(i).equals(tippek[i]))
            {
                talalatok++;
            }
            if(eredmenyek.get(i).equals("+1") && tippek[i].equals("1"))
            {
                talalatok++;
            }
            if(eredmenyek.get(i).equals("+2") && tippek[i].equals("2"))
            {
                talalatok++;
            }
            if(eredmenyek.get(i).equals("X") && tippek[i].equals("X"))
            {
                talalatok++;
            }
            if(eredmenyek.get(i).equals("+X") && tippek[i].equals("X"))
            {
                talalatok++;
            }
        }
        String nyeremeny = "";
        if(talalatok == 14)
        {
            nyeremeny = nyeremenyek.get(0);
        }else if(talalatok == 13)
        {
            nyeremeny = nyeremenyek.get(1);
        }else if(talalatok == 12)
        {
            nyeremeny = nyeremenyek.get(2);
        }else if(talalatok == 11)
        {
            nyeremeny = nyeremenyek.get(3);
        }else if(talalatok == 10)
        {
            nyeremeny = nyeremenyek.get(4);
        }else if(talalatok < 10)
        {
            nyeremeny = "0 Ft";
        }
        //Kiírja a végeredményt.
        System.out.println("Eredmeny: talalat: " + talalatok + ", nyeremeny: " + nyeremeny.replace(" ", ",").replace(",Ft", " Ft"));
    }
    
}
