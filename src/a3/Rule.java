package a3;
public class Rule 
{
    int Trans[];
    public Rule(int value) 
    {
        Trans=new int[4];
        String bin=Integer.toBinaryString(value);
        for(;bin.length()<8;)
            bin="0"+bin;       
        Trans[0]=Integer.parseInt(bin.substring(6,8), 2);
        Trans[1]=Integer.parseInt(bin.substring(4,6), 2);
        Trans[2]=Integer.parseInt(bin.substring(2,4), 2);
        Trans[3]=Integer.parseInt(bin.substring(0,2), 2);
    }    
}
