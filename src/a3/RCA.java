package a3;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public final class RCA 
{
    Cell C[][];//C se llena con las celulas que obtiene del file manager o interfaz especificada.
    ProcesamientoImagen mag;
    int m,n;//estos valores son el tamaño de la imagen
    Function F;//arreglo con las 6 reglas a aplicar
    Key key;//llave para interar
    public RCA() throws IOException 
    {
        mag=new ProcesamientoImagen();
        m=mag.getm(); 
        n=mag.getn();
        C=mag.cargarImagen();
        do
        {
            String k=JOptionPane.showInputDialog("Ingrese una clave Hexadecimal de entre 5 a 15 digitos.");
            key=new Key(k);
            if(!key.iskey(key.key))
                JOptionPane.showMessageDialog(null, "Clave invalida, vuelva a intentar");
        }while(!key.iskey(key.key));
        int select=JOptionPane.showConfirmDialog(null, "Pulse si para codificar, no para decodificar");
        F=new Function();
        if(select==0)
            ejecutar(true);
        else
            if(select==1)
                ejecutar(false);
            else
                return;
        mag.sobreEscribirImagen(C, m, n,"COD");
    }
    public void ejecutar(boolean mode)//indica si se aplica cicloE para valores true o CiloD para valores false.
    {
        if(mode)
        for(int i=0;i<key.size;i++)
            cicloe(Integer.parseInt(key.k.get(i), 16));
        else
            for(int i=key.size-1;i>=0;i--)
                ciclod(Integer.parseInt(key.k.get(i), 16));
    }    
    public void cicloe(int X)
    {
        for(int D=0;D<=Math.round((Math.max(m, n)-4)/3);D++)
        {
            aplicaregla(dividirpar(D), new Rule(F.rules[0]));
            aplicaregla(dividirimpar(D), new Rule(F.rules[1]));
            for(int x=0;x<X;x++)
            {
                aplicaregla(dividirpar(D), new Rule(F.rules[2]));
                aplicaregla(dividirimpar(D), new Rule(F.rules[3]));
            }
            aplicaregla(dividirpar(D), new Rule(F.rules[4]));
            aplicaregla(dividirimpar(D), new Rule(F.rules[5]));
        }
    }
    public void ciclod(int X)
    {
        for(int D=Math.round((Math.max(m, n)-4)/3);D>=0/3;D--)
        {
            aplicaregla(dividirimpar(D), new Rule(F.rules[5]));
            aplicaregla(dividirpar(D), new Rule(F.rules[4]));
            for(int x=0;x<X;x++)
            {
                aplicaregla(dividirimpar(D), new Rule(F.rules[3]));
                aplicaregla(dividirpar(D), new Rule(147));
//              la regla 147 es inversa a la 57, de no aplicarla, habria que
//              aplicar la regla 57 3 veces tras la aplicacion original para ser revertida.
//              aplicaregla(dividirpar(D), new Rule(F.rules[2]));
//              aplicaregla(dividirpar(D), new Rule(F.rules[2]));
//              aplicaregla(dividirpar(D), new Rule(F.rules[2]));
            }
            aplicaregla(dividirimpar(D), new Rule(F.rules[1]));
            aplicaregla(dividirpar(D), new Rule(F.rules[0]));
        }
    }
    public ArrayList<Block> dividirpar(int D)
    {
        ArrayList<Block> listb=new ArrayList<>();
        for(int i=0;i+(2*(D+1))<=m;i+=2*(D+1))
            for(int j=0;j+(2*(D+1))<=n;j+=2*(D+1))
            {
                Block b=new Block(i, j);
                b.c00=C[i][j];
                b.c01=C[i][j+1+D];
                b.c10=C[i+1+D][j];
                b.c11=C[i+1+D][j+1+D];
                listb.add(b);
            }
        return listb;
    }
    public ArrayList<Block> dividirimpar(int D)
    {
        ArrayList<Block> listb=new ArrayList<>();
        for(int i=1;i+(2*(D+1))<=m;i+=2*(D+1))
            for(int j=1;j+(2*(D+1))<=n;j+=2*(D+1))
            {
                Block b=new Block(i, j);
                b.c00=C[i+D][j+D];
                b.c01=C[i+D][j+1+D+D];
                b.c10=C[i+1+D+D][j+D];
                b.c11=C[i+1+D+D][j+1+D+D];
                listb.add(b);
            }
        return listb;
    }
    public void aplicaregla(ArrayList<Block> listb,Rule R)
    {
        for(Block b:listb)
        {
            Cell[] aux=new Cell[4];
            aux[0]=new Cell();aux[1]=new Cell();aux[2]=new Cell();aux[3]=new Cell();
            aux[0].value=b.c00.value;
            aux[1].value=b.c01.value;
            aux[2].value=b.c10.value;
            aux[3].value=b.c11.value;
            
            b.c00.value=aux[R.Trans[0]].value;
            b.c01.value=aux[R.Trans[1]].value;
            b.c10.value=aux[R.Trans[2]].value;
            b.c11.value=aux[R.Trans[3]].value;
        }
    }
}
