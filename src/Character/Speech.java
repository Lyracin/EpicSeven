package Character;

public class Speech {
    public Hero hero;
    public String speech;
    public int morale;

    Speech(Hero s, String t, int m)
    {
        this.hero=s;
        this.speech=t;
        this.morale=m;
    }
}
