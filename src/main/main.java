package main;

import java.io.*;
import java.util.ArrayList;

import Character.*;

public class main {

    public static void main(String[] args) {

        //Container for all heroes
        ArrayList<Hero> allHeroes = new ArrayList();

        //Morale data file
        String dataFile= "src/morale.csv";

        //Initialize the hero container with data
        allHeroes = readHeroData(dataFile);

        //Get Choices
        String choiceFile = "src/Choices.txt";
        ArrayList<String> choices = getChoices(choiceFile);

        //Cross reference choices with hero data and retrieve data
        ArrayList<Hero> selection = pickHeroes(allHeroes, choices);

        //Create All Unique Teams following the create conditions
        ArrayList<Team> teams = createTeams(selection);

        //Sort and filter the teams
        ArrayList<Team> out = sortTeams(teams);

       out = filterTeams(out);

        //Write Teams and Morale to output file
        writeFile(out);
    }

    public static ArrayList<Hero> readHeroData(String fileName) {

        //Initialize line and hero list to return
        String line = null;
        ArrayList all = new ArrayList();

        //initialize morale value holder
        int[] morale = new int[22];

        try {
            //Read in the given file name
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            Hero toAdd;
            //Go past first line
            line= bufferedReader.readLine();

            //Iterate through file
            while ((line = bufferedReader.readLine()) != null) {
                String[] temp = line.split(",");

                //Create Hero(hero name)
                toAdd = new Hero(temp[2]);

                //Initialize Speeches
                toAdd.setSpeech(temp[0], temp[1]);

                //Initialize Morale values
                int i=0;
                for(i=0; i<22; i++)
                {
                    morale[i] = Integer.parseInt(temp[i+3]);
                }
                toAdd.setMorale(morale);

                //Add to list
                all.add(toAdd);
            }

            //Close the reader
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        return all;
    }

    public static ArrayList<String> getChoices(String fileName)
    {

        String line = null;

        //Initialize choice container
        ArrayList<String> choices = new ArrayList();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //Go past first line
            line= bufferedReader.readLine();
            //Iterate through file and add to choice container
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

        //Output heroes
        ArrayList<Hero> selection = new ArrayList();

        //If none selected, add all heroes to list
        if(choices.size()==0)
        {
            selection = allHeroes;
        }

        //Iterate through choices to find if data exists
        for(String hero: choices) {

            int found =0;
            for (Hero h : allHeroes) {
                //Hero exists?
                if(h.name.equals(hero)) {
                    //Yes, do we already have it?
                    if (!selection.contains(h)) {
                        //No, add it to selection
                        found = 1;
                        selection.add(h);
                    }
                    else
                    {
                        //Yes, hero is duplicate
                        found=2;
                        System.out.println("Hero " + hero + " is a duplicate");
                    }
                }
            }
            //Was the hero found?
            if(found == 0 && !hero.equals(""))
            {
                //No
                System.out.println("Hero " + hero +" not found");
            }
        }
        //Spacing for output messages
        System.out.println();
        return selection;
    }

    public static ArrayList<Team> createTeams(ArrayList<Hero> selection)
    {
        ArrayList<Team> teams = new ArrayList();

        //Include files
        ArrayList<String> includes1 = getChoices("src/Include.txt");
        ArrayList<String> includes2 = getChoices("src/Include2.txt");
        boolean result;
        Hero h1;
        Hero h2;
        Hero h3;
        Hero h4;

        //Sort Selection
        selection.sort(new SortHeroAlphabetical());
        //Create teams
        int size = selection.size();
        //Hero 1
        for(int i=0; i<size-3; i++)
        {
            //Hero 2
            for(int j=i+1; j<size-2; j++)
            {
                //Hero 3
                for(int k=j+1; k<size-1; k++)
                {
                    //Hero 4
                    for(int l=k+1; l<size; l++)
                    {
                        h1 = selection.get(i);
                        h2 = selection.get(j);
                        h3 = selection.get(k);
                        h4 = selection.get(l);
                        //Check includes
                        if(includes1.size()==0)
                        {
                            //No conditions
                            result = true;
                        }
                        else
                        {
                            //Check if one of the heroes on the team is an include
                            result = true;
                            if(includes1.contains(h1.name) || includes1.contains(h2.name) || includes1.contains(h3.name) || includes1.contains(h4.name))
                            {
                                //Second include file
                                if(includes2.size()==0)
                                {
                                    result = true;
                                }
                                else
                                {
                                    if (includes2.contains(h1.name) || includes2.contains(h2.name) || includes2.contains(h3.name) || includes2.contains(h4.name))
                                    {
                                        result = true;
                                    }
                                    else
                                    {
                                        result = false;
                                    }
                                }//End second include
                            }
                            else
                            {
                                //Does not include any, do not create
                                result = false;
                            } //End first include
                        }

                        if(result)
                        {
                            //Create Team
                            Team newTeam = new Team(h1,h2,h3,h4);
                            teams.add(newTeam);
                        }
                    }//End Hero 4
                }//End Hero 3
            }//End Hero 2
        }//End Hero 1

        return teams;
    }

    public static boolean checkIncludes(Hero h1, Hero h2, Hero h3, Hero h4)
    {
        //True creates the team
        boolean result = true;

        //Include file
        String includeFile = "src/Include.txt";

        ArrayList<String> includes = getChoices(includeFile);

        //Check if includes is empty
        if(includes.size()==0)
        {
            //No conditions
            result = true;
        }
        else
        {
            //Check if one of the heroes on the team is an include

            if(includes.contains(h1.name) || includes.contains(h2.name) || includes.contains(h3.name) || includes.contains(h4.name))
            {
                //Contains at least 1, create
                result = true;
            }
            else
            {
                //Does not include any, do not create
                result = false;
            }
        }

        if(result)
        {
            //Include file
            includeFile = "src/Include2.txt";

            includes = getChoices(includeFile);

            //Check if includes is empty
            if(includes.size()==0)
            {
                //No conditions
                result = true;
            }
            else
            {
                //Check if one of the heroes on the team is an include

                if(includes.contains(h1.name) || includes.contains(h2.name) || includes.contains(h3.name) || includes.contains(h4.name))
                {
                    //Contains at least 1, create
                    result = true;
                }
                else
                {
                    //Does not include any, do not create
                    result = false;
                }
            }
        }

        //True creates the team
        return result;
    }

    public static ArrayList<Team> sortTeams(ArrayList<Team> list)
    {
        //Sort by max morale
        list.sort(new SortTeamByMaxMorale());

        return list;
    }

    public static ArrayList<Team> filterTeams(ArrayList<Team> teams)
    {
        ArrayList<Team> output= new ArrayList<>();
        for(Team t: teams)
        {
            //Filter condition
            if(t.moraleSum>=0)
            {
                output.add(t);
            }
        }
        return output;
    }

    public static void writeFile(ArrayList<Team> teams)
    {

        //Does magic to write the teams to the output file

        String fileName = "src/Best Combinations.txt";
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


}
