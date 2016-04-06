package a3;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ProcesamientoImagen
{
    //Imagen actual que se ha cargado
    public BufferedImage imgoriginal, imageActual;
    String Name,Format;
    
    public double entropyR()
    {
        double P[]=new double[256];
        for(int i=0;i<256;i++)
            P[i]=0;
        for(int i = 0; i < imageActual.getWidth(); i++)
            for( int j = 0; j < imageActual.getHeight(); j++ )
            {
                Color x=new Color(imageActual.getRGB(i, j));
                int e=(int)x.getRed();
                P[e]=(++P[e])/(imageActual.getWidth()*imageActual.getHeight());
            }
        double H=0.0;
        for(int i=0;i<256;i++)
            if(P[i]!=0)
                H+=P[i]*(Math.log(1.0/P[i])/Math.log(2));
        return H;
    }
    public double entropyG()
    {
        double P[]=new double[256];
        for(int i=0;i<256;i++)
            P[i]=0;
        for(int i = 0; i < imageActual.getWidth(); i++)
            for( int j = 0; j < imageActual.getHeight(); j++ )
            {
                Color x=new Color(imageActual.getRGB(i, j));
                int e=(int)x.getGreen();
                P[e]=(++P[e])/(imageActual.getWidth()*imageActual.getHeight());
            }
        double H=0.0;
        for(int i=0;i<256;i++)
            if(P[i]!=0)
                H+=P[i]*(Math.log(1.0/P[i])/Math.log(2));
        return H;
    }
    public double entropyB()
    {
        double P[]=new double[256];
        for(int i=0;i<256;i++)
            P[i]=0;
        for(int i = 0; i < imageActual.getWidth(); i++)
            for( int j = 0; j < imageActual.getHeight(); j++ )
            {
                Color x=new Color(imageActual.getRGB(i, j));
                int e=(int)x.getBlue();
                P[e]=(++P[e])/(imageActual.getWidth()*imageActual.getHeight());
            }
        double H=0.0;
        for(int i=0;i<256;i++)
            if(P[i]!=0)
                H+=P[i]*(Math.log(1.0/P[i])/Math.log(2));
        return H;
    }
    
    public double CCV(int M)
    {
        double Ex,Ey,Dx,Dy,Co,sum;
        Point px[]=new Point[M];
        Point py[]=new Point[M];
        Random r=new Random();
        for(int i=0;i<M;i++)
        {
            int x=r.nextInt(imageActual.getWidth()), y=r.nextInt(imageActual.getHeight()-1);
            px[i]=new Point(x,y);
            py[i]=new Point(x,y+1);
        }
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color c=new Color(imageActual.getRGB(px[i].x, px[i].y));
            sum+=(int)((c.getRed()+c.getGreen()+c.getBlue())/3);
        }
        Ex=(1.0/M)*sum;
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color c=new Color(imageActual.getRGB(py[i].x, py[i].y));
            sum+=(int)((c.getRed()+c.getGreen()+c.getBlue())/3);
        }
        Ey=(1.0/M)*sum;
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color c=new Color(imageActual.getRGB(px[i].x, px[i].y));
            sum+=Math.pow(((c.getRed()+c.getGreen()+c.getBlue())/3)-Ex,2);
        }
        Dx=(1.0/M)*sum;
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color c=new Color(imageActual.getRGB(py[i].x, py[i].y));
            sum+=Math.pow(((c.getRed()+c.getGreen()+c.getBlue())/3)-Ey,2);
        }
        Dy=(1.0/M)*sum;
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color cy=new Color(imageActual.getRGB(py[i].x, py[i].y));
            Color cx=new Color(imageActual.getRGB(px[i].x, px[i].y));
            sum+=(((cx.getRed()+cx.getGreen()+cx.getBlue())/3)-Ex)*(((cy.getRed()+cy.getGreen()+cy.getBlue())/3)-Ey);
        }
        Co=(1.0/M)*sum;
        return Co/(Math.sqrt(Dx)*Math.sqrt(Dy));
    }
    public double CCH(int M)
    {
        double Ex,Ey,Dx,Dy,Co,sum;
        Point px[]=new Point[M];
        Point py[]=new Point[M];
        Random r=new Random();
        for(int i=0;i<M;i++)
        {
            int x=r.nextInt(imageActual.getWidth()-1), y=r.nextInt(imageActual.getHeight());
            px[i]=new Point(x,y);
            py[i]=new Point(x+1,y);
        }
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color c=new Color(imageActual.getRGB(px[i].x, px[i].y));
            sum+=(int)((c.getRed()+c.getGreen()+c.getBlue())/3);
        }
        Ex=(1.0/M)*sum;
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color c=new Color(imageActual.getRGB(py[i].x, py[i].y));
            sum+=(int)((c.getRed()+c.getGreen()+c.getBlue())/3);
        }
        Ey=(1.0/M)*sum;
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color c=new Color(imageActual.getRGB(px[i].x, px[i].y));
            sum+=Math.pow(((c.getRed()+c.getGreen()+c.getBlue())/3)-Ex,2);
        }
        Dx=(1.0/M)*sum;
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color c=new Color(imageActual.getRGB(py[i].x, py[i].y));
            sum+=Math.pow(((c.getRed()+c.getGreen()+c.getBlue())/3)-Ey,2);
        }
        Dy=(1.0/M)*sum;
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color cy=new Color(imageActual.getRGB(py[i].x, py[i].y));
            Color cx=new Color(imageActual.getRGB(px[i].x, px[i].y));
            sum+=(((cx.getRed()+cx.getGreen()+cx.getBlue())/3)-Ex)*(((cy.getRed()+cy.getGreen()+cy.getBlue())/3)-Ey);
        }
        Co=(1.0/M)*sum;
        return Co/(Math.sqrt(Dx)*Math.sqrt(Dy));
    }
    public double CCD(int M)
    {
        double Ex,Ey,Dx,Dy,Co,sum;
        Point px[]=new Point[M];
        Point py[]=new Point[M];
        Random r=new Random();
        for(int i=0;i<M;i++)
        {
            int x=r.nextInt(imageActual.getWidth()-1), y=r.nextInt(imageActual.getHeight()-1);
            px[i]=new Point(x,y);
            py[i]=new Point(x+1,y+1);
        }
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color c=new Color(imageActual.getRGB(px[i].x, px[i].y));
            sum+=(int)((c.getRed()+c.getGreen()+c.getBlue())/3);
        }
        Ex=(1.0/M)*sum;
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color c=new Color(imageActual.getRGB(py[i].x, py[i].y));
            sum+=(int)((c.getRed()+c.getGreen()+c.getBlue())/3);
        }
        Ey=(1.0/M)*sum;
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color c=new Color(imageActual.getRGB(px[i].x, px[i].y));
            sum+=Math.pow(((c.getRed()+c.getGreen()+c.getBlue())/3)-Ex,2);
        }
        Dx=(1.0/M)*sum;
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color c=new Color(imageActual.getRGB(py[i].x, py[i].y));
            sum+=Math.pow(((c.getRed()+c.getGreen()+c.getBlue())/3)-Ey,2);
        }
        Dy=(1.0/M)*sum;
        sum=0;
        for(int i=0;i<M;i++)
        {
            Color cy=new Color(imageActual.getRGB(py[i].x, py[i].y));
            Color cx=new Color(imageActual.getRGB(px[i].x, px[i].y));
            sum+=(((cx.getRed()+cx.getGreen()+cx.getBlue())/3)-Ex)*(((cy.getRed()+cy.getGreen()+cy.getBlue())/3)-Ey);
        }
        Co=(1.0/M)*sum;
        return Co/(Math.sqrt(Dx)*Math.sqrt(Dy));
    }
    
    
    public ProcesamientoImagen() 
    {
        abrirImagen();
    }
    public int getm()
    {
        return imageActual.getWidth();
    }
    public int getn()
    {
        return imageActual.getHeight();
    }
    public void abrirImagen()
    {
        //Creamos la variable que será devuelta (la creamos como null)
        BufferedImage bmp=null;
        //Creamos un nuevo cuadro de diálogo para seleccionar imagen
        JFileChooser selector=new JFileChooser();
        //Le damos un título
        selector.setDialogTitle("Seleccione una imagen");
        //Filtramos los tipos de archivos
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG & GIF & BMP", "jpg", "gif", "bmp");
        selector.setFileFilter(filtroImagen);
        //Abrimos el cuadro de diálog
        int flag=selector.showOpenDialog(null);
        //Comprobamos que pulse en aceptar
        if(flag==JFileChooser.APPROVE_OPTION)
            try 
            {
                //Devuelve el fichero seleccionado
                File imagenSeleccionada=selector.getSelectedFile();
                Name=imagenSeleccionada.getName();
                Format=Name.substring(Name.lastIndexOf(".")+1);
                //Asignamos a la variable bmp la imagen leida
                bmp = ImageIO.read(imagenSeleccionada);
                
            }catch (Exception e){final JPanel panel = new JPanel();JOptionPane.showMessageDialog(panel, "Could not open file", "Error", JOptionPane.ERROR_MESSAGE);}    
        
        //Asignamos la imagen cargada a la propiedad imageActual
        imageActual=bmp;
    }
    public Cell[][] cargarImagen()
    {
        Cell[][] C=new Cell[imageActual.getWidth()][imageActual.getHeight()];
        
        for( int i = 0; i < imageActual.getWidth(); i++ )
            for( int j = 0; j < imageActual.getHeight(); j++ )
                
                C[i][j]=new Cell(new Color(imageActual.getRGB(i, j)));
        
        return C;
    }
    public void sobreEscribirImagen(Cell[][] C,int m,int n) throws IOException
    {   
        File result=new File("./"+Name);
        
        for( int i = 0; i <m; i++ )
            for( int j = 0; j <n; j++ )
            {
                //Asignamos el nuevo valor al BufferedImage
                imageActual.setRGB(i, j,C[i][j].value.getRGB());
            }
        ImageIO.write(imageActual, Format, result);
        Grafica.graficarhistograma(imageActual, Name);
        
        JOptionPane.showMessageDialog(null, "La entropia en los rojos es: "+entropyR()+"\r\n"+"La entropia en los verdes es: "+entropyG()+"\r\n"+"La entropia en los azules es: "+entropyB());
        JOptionPane.showMessageDialog(null, "Coeficiente de corelacion vertical: "+CCV(3000)+"\r\n"+"Coeficiente de corelacion horizontal: "+CCH(3000)+"\r\n"+"Coeficiente de corelacion diagonal: "+CCD(3000));
    }
    public void sobreEscribirImagen(Cell[][] C,int m,int n,String name) throws IOException
    {        
        File result=new File("./"+name+"."+Format);
        for( int i = 0; i <m; i++ )
            for( int j = 0; j <n; j++ )
            {
                //Asignamos el nuevo valor al BufferedImage
                imageActual.setRGB(i, j,C[i][j].value.getRGB());
            }
        ImageIO.write(imageActual, Format, result);
        Grafica.graficarhistograma(imageActual, Name);
        JOptionPane.showMessageDialog(null, "La entropia en los rojos es: "+entropyR()+"\r\n"+"La entropia en los verdes es: "+entropyG()+"\r\n"+"La entropia en los azules es: "+entropyB());
        JOptionPane.showMessageDialog(null, "Coeficiente de corelacion vertical: "+CCV(3000)+"\r\n"+"Coeficiente de corelacion horizontal: "+CCH(3000)+"\r\n"+"Coeficiente de corelacion diagonal: "+CCD(3000));
    }
}