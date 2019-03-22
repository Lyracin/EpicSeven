package Character;

import java.util.ArrayList;
import java.util.Comparator;

public class Team {
    private Hero[] heroes = new Hero[4];

    public Hero speaker1;
    public String speech1;
    public int morale1;

    public Hero speaker2;
    public String speech2;
    public int morale2;

    public int moraleSum;


    public Team(Hero h1, Hero h2, Hero h3, Hero h4)
    {
        this.heroes[0]=h1;
        this.heroes[1]=h2;
        this.heroes[2]=h3;
        this.heroes[3]=h4;
        calculateMorale();
        moraleSum= morale1 + morale2;
    }

    public Hero[] getHeroes()
    {
        return heroes;
    }

    private void calculateMorale()
    {
        //Create a list of all the speeches
        ArrayList<String> speechList = new ArrayList();
        for(int i=0; i<4; i++)
        {
            speechList.add(heroes[i].speech1);
            speechList.add(heroes[i].speech2);
        }

        ArrayList<Integer> values = new ArrayList();

        //Add together the morale value from each person and add to values container
        for(String tempSpeech: speechList) {
            values.add(getMoraleValue((tempSpeech), 0)+getMoraleValue((tempSpeech), 1) + getMoraleValue((tempSpeech), 2) + getMoraleValue((tempSpeech), 3));
        }

        //Subtract speakers value from the total morale value
        values.set(0, values.get(0)-getMoraleValue(speechList.get(0),0));
        values.set(1, values.get(1)-getMoraleValue(speechList.get(1),0));

        values.set(2, values.get(2)-getMoraleValue(speechList.get(2),1));
        values.set(3, values.get(3)-getMoraleValue(speechList.get(3),1));

        values.set(4, values.get(4)-getMoraleValue(speechList.get(4),2));
        values.set(5, values.get(5)-getMoraleValue(speechList.get(5),2));

        values.set(6, values.get(6)-getMoraleValue(speechList.get(6),3));
        values.set(7, values.get(7)-getMoraleValue(speechList.get(7),3));


        //Compare all to get highest 2

        //initialize first 2 depending on which is greater
        if(values.get(0)>values.get(1))
        {
            //Speech 1 generates more morale than speech 2
            speaker1 = heroes[0];
            speech1 = speechList.get(0);
            morale1 = values.get(0);

            speaker2 = heroes[0];
            speech2 = speechList.get(1);
            morale2 = values.get(1);
        }
        else
        {
            //Speech 2 generates more morale than speech 1
            speaker1 = heroes[0];
            speech1 = speechList.get(1);
            morale1 = values.get(1);

            speaker2 = heroes[0];
            speech2 = speechList.get(0);
            morale2 = values.get(0);
        }

        //For the rest compare to get max
        for (int j=2; j<8; j++)
        {
            if(values.get(j)>morale1)
            {
                //Greater than largest, move to second largest
                speaker2 = speaker1;
                speech2 = speech1;
                morale2 = morale1;

                //New largest
                speaker1 = heroes[j/2];
                speech1 = speechList.get(j);
                morale1 = values.get(j);
            }
            else if(values.get(j)>morale2)
            {
                //Second largest
                speaker2 = heroes[j/2];
                speech2 = speechList.get(j);
                morale2 = values.get(j);
            }
        }
    }

    private int getMoraleValue(String speech, int who)
    {
        //Checks which speech, and returns the proper morale value for the indicated hero

        int value = 0;
        switch(speech)
        {
            case "Advice":
                value = heroes[who].getMorale(0);
                break;
            case "Belief":
                value = heroes[who].getMorale(1);
                break;
            case "Bizarre Story":
                value = heroes[who].getMorale(2);
                break;
            case "Comforting Cheer":
                value = heroes[who].getMorale(3);
                break;
            case "Complain":
                value = heroes[who].getMorale(4);
                break;
            case "Criticism":
                value = heroes[who].getMorale(5);
                break;
            case "Cute Cheer":
                value = heroes[who].getMorale(6);
                break;
            case "Dream":
                value = heroes[who].getMorale(7);
                break;
            case "Food Story":
                value = heroes[who].getMorale(8);
                break;
            case "Gossip":
                value = heroes[who].getMorale(9);
                break;
            case "Happy Memory":
                value = heroes[who].getMorale(10);
                break;
            case "Heroic Cheer":
                value = heroes[who].getMorale(11);
                break;
            case "Heroic Tale":
                value = heroes[who].getMorale(12);
                break;
            case "Horror Story":
                value = heroes[who].getMorale(13);
                break;
            case "Interesting Story":
                value = heroes[who].getMorale(14);
                break;
            case "Joyful Memory":
                value = heroes[who].getMorale(15);
                break;
            case "Myth":
                value = heroes[who].getMorale(16);
                break;
            case "Occult":
                value = heroes[who].getMorale(17);
                break;
            case "Reality Check":
                value = heroes[who].getMorale(18);
                break;
            case "Sad Memory":
                value = heroes[who].getMorale(19);
                break;
            case "Self-Indulgent":
                value = heroes[who].getMorale(20);
                break;
            case "Unique Comment":
                value = heroes[who].getMorale(21);
                break;
            default:
                break;
        }

        return value;
    }
}
