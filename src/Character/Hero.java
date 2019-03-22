package Character;

public class Hero {
    public String name;
    public String speech1;
    public String speech2;

    int[] morale= new int[22];

    public Hero(String name)
    {
        this.name = name;
    }

    public void setSpeech(String s1, String s2)
    {
        this.speech1 = s1;
        this.speech2 = s2;
    }

    public void setMorale(int[] array)
    {
        int i=0;
        for (i=0; i<22; i++)
        {
            morale[i] = array[i];
        }
    }

    public int getMorale(int i)
    {
        return morale[i];
    }
}
