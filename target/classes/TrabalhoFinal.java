package software.trabalhofinal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class TrabalhoFinal extends JFrame implements Runnable{
    Thread t;
    boolean exe = true;
    int pontuacao = 0;
    int numeroDeVidas = 5;
    int larguraDaTela = 600;
    int alturaDaTela = 600;
    int x1 = 100,
            y1 = 0,
            h1 = 100,
            w1 = 100,
            x2 = 200,
            y2 = 500,
            h2 = 100,
            w2 = 100;
    ImageIcon [] imgNave0 = new ImageIcon[1];
    Image [] imagemNave0 = new Image[1];
    ImageIcon [] imgNave1 = new ImageIcon[1];
    Image [] imagemNave1 = new Image[1];
    ImageIcon [] imgfim = new ImageIcon[1];
    Image [] imagemFim = new Image[1];
    
    String localDaImagem = "C:\\Users\\famil\\OneDrive\\Documentos\\Trabalho final de poo";
    
    public TrabalhoFinal(){
        imgNave0[0] = new ImageIcon(localDaImagem+"\\imagem0.png");
        imagemNave0[0] = imgNave0[0].getImage();
        imgNave1[0] = new ImageIcon(localDaImagem+"\\imagem1.png");
        imagemNave1[0] = imgNave1[0].getImage();
        imgfim[0] = new ImageIcon(localDaImagem+"\\linhadechegada.png");
        imagemFim[0] = imgfim[0].getImage();
        
        t = new Thread(this);
        setSize(larguraDaTela, alturaDaTela);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        showNotify();
        
    }
    
    public void showNotify(){
        //iniciar Thread
        exe = true;
         t.start();
    }
    
    public void hideNotify(){
        exe = false;
        t.start();
        
    }
    
    
    public void paint(Graphics g){
        
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(imagemFim[0], 0, 500, 600, 150, this);
        g.drawImage(imagemNave0[0], x1, y1, w1, h1, this);
        g.drawImage(imagemNave1[0], x2, y2, w2, h2, this);
        g.setColor(Color.BLACK);
        g.drawString("Pontuação: " + String.valueOf(pontuacao), 10, 50);
        g.drawString("Vidas: " + String.valueOf(numeroDeVidas), 10, 75);
        
    }
    
    public static void main(String[] args) {
        TrabalhoFinal a = new TrabalhoFinal(); 
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        while(exe){
            try{
            Thread.sleep(100);
            } catch (Exception ex) {}
            repaint();
           y1+=10;
           y2-=10;
           if (((x1+w1)>x2)&&((y1+h1)>y2)&&((x2+w2)>x1)&&((y2+h2)>y1)) { 
                JOptionPane.showMessageDialog(this, "Bateu! vidas restantes: "+ (numeroDeVidas-1));
                numeroDeVidas--;
                y1 = 0;
                y2 = alturaDaTela;
                x1 = new Random().nextInt(larguraDaTela - w1);
                x2 = new Random().nextInt(larguraDaTela - w2);
           }
           if(y1+h1 > getHeight()){
               pontuacao += 1;
               y1 = 0;
                y2 = alturaDaTela;
                x1 = new Random().nextInt(larguraDaTela - w1);
                x2 = new Random().nextInt(larguraDaTela - w2);
           }
           if(pontuacao >= 10){
               JOptionPane.showMessageDialog(this, "Você ganhou!");
               exe = false;
           }
           if(numeroDeVidas <= 0){
               JOptionPane.showMessageDialog(this, "Você perdeu!");
               exe = false;
           }
        }
    }
}
