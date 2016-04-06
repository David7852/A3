package a3;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class Grafica {
    private static final String SITIO_1 = "Red";
    private static final String SITIO_2 = "Green";
    private static final String SITIO_3 = "Blue";
    private static final String BARCHART_JPG = "chart.jpg";
    public static void graficarhistograma(BufferedImage imageActual,String title) {
        title="Histograma "+title;
        DefaultCategoryDataset datasetR = new DefaultCategoryDataset();
        DefaultCategoryDataset datasetG = new DefaultCategoryDataset();
        DefaultCategoryDataset datasetB = new DefaultCategoryDataset();
         try
         {
            int yr[]=new int[256];int yg[]=new int[256];int yb[]=new int[256];
            for( int i = 0; i < imageActual.getWidth(); i++ )
                for( int j = 0; j < imageActual.getHeight(); j++ )
                {
                    Color c=new Color(imageActual.getRGB(i, j));
                    yr[c.getRed()]++;
                    yg[c.getGreen()]++;
                    yb[c.getBlue()]++;
                }
            for(int i=0;i<256;i++)
            {
                datasetR.setValue(yr[i], SITIO_1, ""+yr[i]);
                datasetG.setValue(yg[i], SITIO_2, ""+yg[i]);
                datasetB.setValue(yb[i], SITIO_3, ""+yb[i]);
            }
         }
         catch(Exception e)
         {
         System.out.println("Error al leer");
         }
        JFreeChart chartR = null;
        chartR = createChart(datasetR,title);
        salvaGraficoEnFichero(chartR,title+"Red");
        ChartPanel panelR = new ChartPanel(chartR);
        JFrame ventanaR = new JFrame(title);
        ventanaR.getContentPane().add(panelR);
        ventanaR.pack();
        ventanaR.setVisible(true);
        ventanaR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JFreeChart chartG = null;
        chartG = createChart(datasetG,title);
        salvaGraficoEnFichero(chartG,title+"Green");
        ChartPanel panelG = new ChartPanel(chartG);
        JFrame ventanaG = new JFrame(title);
        ventanaG.getContentPane().add(panelG);
        ventanaG.pack();
        ventanaG.setVisible(true);
        ventanaG.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JFreeChart chartB = null;
        chartB = createChart(datasetB,title);
        salvaGraficoEnFichero(chartB,title+"Blue");
        ChartPanel panelB = new ChartPanel(chartB);
        
        JFrame ventanaB = new JFrame(title);
        ventanaB.getContentPane().add(panelB);
        ventanaB.pack();
        ventanaB.setVisible(true);
        ventanaB.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private static JFreeChart createChart(CategoryDataset dataset,String title) {
        JFreeChart chart;
        
            chart = ChartFactory.createLineChart("Histograma "+title, "Valores del pixel",
                    "frecuencia de aparicion", dataset, PlotOrientation.VERTICAL,true,true,true);
        return chart;
    }
    private static void salvaGraficoEnFichero(JFreeChart chart,String name) {
        try {
            String tmpDir = "./";
            ChartUtilities.saveChartAsJPEG(new File(tmpDir +name+  BARCHART_JPG), chart, 500, 300);
            System.out.println("Salvada imagen en " + tmpDir +name+  BARCHART_JPG);
        } catch (IOException e) {
            System.err.println("Error creando grafico.");
        }
    }
}