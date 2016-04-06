package a3;
public class Function 
{
    int[] rules;
    public Function() 
    {
        rules=new int[6];
        rules[0]=180;
        rules[1]=225;
        rules[2]=57;
        rules[3]=27;
        rules[4]=108;
        rules[5]=198;
    }
    public Function(int a,int b,int c,int d,int e,int f)
    {
        rules=new int[6];
        rules[0]=a;
        rules[1]=b;
        rules[2]=c;
        rules[3]=d;
        rules[4]=e;
        rules[5]=f;
    }
}
