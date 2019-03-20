package main;

import java.io.*;
import java.util.ArrayList;

import Character.*;

public class main {

    public static void main(String[] args) {

        //Hold all Heroes
        ArrayList<Hero> all = new ArrayList();

        //read file
        String file= "src/morale.csv";

        //Read in data sheet
        all = readFile(file);

        //Get Choices
        ArrayList<String> choices = getChoices();

        //Pick Heroes
        ArrayList<Hero> selection = pickHeroes(all, choices);

        //Create Teams
        ArrayList<Team> teams = createTeams(selection);

        //Sort and filter
        ArrayList<Team> out = sortAndFilter(teams);

        //Write Teams and Morale to output file
        writeFile(out);
    }

    public static ArrayList<Hero> readFile(String fileName) {

        String line = null;
        ArrayList all = new ArrayList();
        int[] morale = new int[22];
        int i=0;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            Hero toAdd;
            line= bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] temp = line.split(",");

                //Create Hero
                toAdd = new Hero(temp[2]);

                //Initialize Speeches
                toAdd.setSpeech(temp[0], temp[1]);

                //Initialize Morale
                for(i=0; i<22; i++)
                {
                    morale[i] = Integer.parseInt(temp[i+3]);
                }
                toAdd.setMorale(morale);

                //Add to list
                all.add(toAdd);

            }

            bufferedReader.close();


        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        return all;
    }

    public static ArrayList<String> getChoices()
    {
        String fileName ="src/choices.txt";

        String line = null;
        ArrayList<String> choices = new ArrayList();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            line= bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                //Add to list

                choices.add(line);
            }

            bufferedReader.close();


        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        return choices;
    }

    public static ArrayList<Hero> pickHeroes(ArrayList<Hero> allHeroes, ArrayList<String> choices)
    {
        System.out.println();
        ArrayList<Hero> selection = new ArrayList();
        for(String hero: choices) {
            int found =0;
            for (Hero h : allHeroes) {
                if(h.name.equals(hero)) {
                    if (!selection.contains(h)) {
                        found = 1;
                        selection.add(h);
                        //System.out.println("Hero " + hero + " found");
                    }
                    else
                    {
                        found=2;
                        System.out.println("Hero " + hero + " is a duplicate");
                    }
                }
            }
            if(found == 0 && !hero.equals(""))
            {
                System.out.println("Hero " + hero +" not found");
            }
        }
        System.out.println();
        return selection;
    }

    public static ArrayList<Team> createTeams(ArrayList<Hero> selection)
    {
        ArrayList<Team> teams = new ArrayList();

        for(Hero h1: selection)
        {
            for (Hero h2: selection)
            {
                for (Hero h3: selection)
                {
                    for (Hero h4: selection)
                    {
                        if(h1.name.compareTo(h2.name)<0 && h2.name.compareTo(h3.name)<0 && h3.name.compareTo(h4.name)<0) //No repeats
                        {
                            if(createConditions(h1,h2,h3,h4))
                            {
                                teams.add(new Team(h1,h2,h3,h4));
                            }
                        }
                    }
                }
            }
        }

        return teams;
    }

    public static boolean createConditions(Hero h1, Hero h2, Hero h3, Hero h4)
    {

        //True creates the team
        boolean result = true;

        if(!h1.name.equals("No Condition"))
        {
            System.out.println(h1.name + " " + h2.name + " " + h3.name + " "+ h4.name);
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void writeFile(ArrayList<Team> teams)
    {
        String fileName = "Best Combinations.txt";
        try
        {
            int morale;
            Hero[] h;
            String output;

            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            for(Team t: teams)
            {
                h=t.getHeroes();
                output = t.moraleSum+ " " + h[0].name + " " +h[1].name + " " +h[2].name + " " +h[3].name + "\n";
                output += "     "+t.speaker1.name + " " + t.speech1 + ": " + Integer.toString(t.morale1) +"\n";
                output += "     "+t.speaker2.name + " " + t.speech2 + ": " + Integer.toString(t.morale2) +"\n";

                writer.println(output);
            }

            writer.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

    }

    public static ArrayList<Team> sortAndFilter(ArrayList<Team> list)
    {
        ArrayList<Team> out = new ArrayList();

        list.sort(new SortByMax());

        for(Team t:list)
        {
            out.add(t);
        }

        return out;
    }
}
