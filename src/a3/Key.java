package a3;
import java.util.ArrayList;
import java.util.Random;
public class Key 
{
    String key;
    ArrayList<String> k;
    int size;
    public Key() 
    {
        k=new ArrayList<String>();
        this.randomKey();
    }
    public Key(String key)
    {
        k=new ArrayList<String>();
        this.key=key;
        for(int i=0;i<key.length();i++)
            k.add(""+this.key.charAt(i));
        size=key.length();
    }
    public Key(int[] Ks)
    {
        k=new ArrayList<String>();
        for(int i=0;i<Ks.length;i++)
            k.add(Integer.toHexString(Ks[i]));
        size=Ks.length;
    }
    public void randomKey()
    {
        Random r=new Random();
        do{
            size=r.nextInt(15)+1;
        }while(size<5);
        for(int i=0;i<size;i++)
            k.add(Integer.toHexString(r.nextInt(16)));
    }
    public boolean iskey(String t)
    {
        if(t.length()<5||t.length()>15)
            return false;
        for(int i=0;i<t.length();i++)
        {
            String s=""+t.charAt(i);
            if(!("1".equals(s)||"2".equals(s)||"3".equals(s)||"4".equals(s)||"5".equals(s)||"6".equals(s)||"7".equals(s)||"8".equals(s)||"9".equals(s)||"A".equals(s.toUpperCase())||"B".equals(s.toUpperCase())||"C".equals(s.toUpperCase())||"D".equals(s.toUpperCase())||"E".equals(s.toUpperCase())||"F".equals(s.toUpperCase())))
                return false;
        }
        return true;
    }
}
