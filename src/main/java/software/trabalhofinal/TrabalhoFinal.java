package software.trabalhofinal;


import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class TrabalhoFinal extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener{
    
    int x1Old = 0;
    int y1Old = 0;
    int x2Old = 0;
    int y2Old = 0;

    
    
    Thread t;
    boolean exe = true;
    int pontuacao = 0;
    int numeroDeVidas = 5;
    int larguraDaTela = 600;
    int alturaDaTela = 800; 
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
    ImageIcon [] backp = new ImageIcon[1];
    Image [] back = new Image[1];
    
    String localDaImagem = "C:\\Users\\famil\\OneDrive\\Documentos\\NetBeansProjects\\TrabalhoFinal\\src\\main\\java\\software\\trabalhofinal\\imagens";
    
    public TrabalhoFinal(){
        imgNave0[0] = new ImageIcon(localDaImagem+"\\imagem0.png");
        imagemNave0[0] = imgNave0[0].getImage();
        imgNave1[0] = new ImageIcon(localDaImagem+"\\imagem1.png");
        imagemNave1[0] = imgNave1[0].getImage();
        imgfim[0] = new ImageIcon(localDaImagem+"\\linhadechegada.png");
        imagemFim[0] = imgfim[0].getImage();
        backp[0] = new ImageIcon(localDaImagem+"\\back.jpg");
        back[0] = backp[0].getImage(); 
        t = new Thread(this);
       
        setSize(larguraDaTela, alturaDaTela);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        showNotify();
        addKeyListener(this);
        addMouseListener(this);
        
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
    
    public void pause(){
        exe = false;
        int resposta = JOptionPane.showOptionDialog(null, "Pause, deseja continuar ou sair?", null, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null); 
        if(resposta == JOptionPane.YES_OPTION){
            exe = true;
            t = new Thread(this); // Criar uma nova instância da thread
            t.start();                 // Iniciar a nova thread
        } else {
            System.exit(0);
        }
}
    
    
    public void paint(Graphics g){  

    BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = buffer.createGraphics();

    g2.setColor(Color.white);    
    g2.drawImage(back[0], 0, 0, 800, 700, this);
    g2.fillRect(0, 700, 600, 225);
    g2.drawImage(imagemFim[0], 0, 650, 600, 225, this);

    if (x1 != x1Old || y1 != y1Old) {
        g2.drawImage(imagemNave0[0], x1, y1, w1, h1, this);
    }

    if (x2 != x2Old || y2 != y2Old) {
        g2.drawImage(imagemNave1[0], x2, y2, w2, h2, this);
    }
    x1Old = x1;
    y1Old = y1;
    x2Old = x2;
    y2Old = y2;

    g2.setColor(Color.GREEN);
    g2.drawString("Pontuação: " + String.valueOf(pontuacao), 10, 50);
    
    g2.drawString("Vidas: " + String.valueOf(numeroDeVidas), 10, 75);

    //copia o buffer para a tela
    g.drawImage(buffer, 0, 0, null);
        
    }
    
    public static void main(String[] args) {
        TrabalhoFinal a = new TrabalhoFinal(); 
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
    @Override
    public void run() {
        int y11 = 10;
        int y22 = 10;

        while(exe){
            //buffer para não ficar piscando por causa da atualização do jogo
            repaint();
            //caso de algum erro inesperado não fechar o programa
            try{
            Thread.sleep(90);
            } catch (Exception ex) {}
            
            //movimentação dos personagens
            
            y1 += y11;
            y2 -= y22;
            //Condição para verificar se os objetos se colidiram
            if (((x1+w1)>x2)&&((y1+h1)>y2)&&((x2+w2)>x1)&&((y2+h2)>y1)) { 
                JOptionPane.showMessageDialog(this, "Bateu! vidas restantes: "+ (numeroDeVidas-1));
                numeroDeVidas--;
                y1 = 0;
                y2 = alturaDaTela;
                x1 = new Random().nextInt(larguraDaTela - w1);
                x2 = new Random().nextInt(larguraDaTela - w2);
            }
            //condição para verificar se o jogador chegou ao fim para pontuar
            if(y1+h1 > getHeight()){
                pontuacao += 1;
                if(y11 <= 24){
                y11 += 3;
                y22 += 3;
                }
                y1 = 0;
                y2 = alturaDaTela;
                x1 = new Random().nextInt(larguraDaTela - w1);
                x2 = new Random().nextInt(larguraDaTela - w2);
            }
            //condição para quando o jogador conseguir 10 vitorias ele ganhar o jogo
            if(pontuacao >= 10){
               exe = false;
               JOptionPane optionPane = new JOptionPane();
               optionPane.setMessage("Você ganhou! Deseja recomeçar?");
               JButton butao;
               butao = new JButton("sim");
               
               optionPane.setMessage(butao);
               
               
                Object[] options = {"Sim", "Não"};
                optionPane.setOptions(options);
               
               
               int resposta = optionPane.showOptionDialog(null, "Você ganhou! Deseja recomeçar?", null, JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null, null, null); 
               if(resposta == JOptionPane.YES_NO_OPTION){
                   numeroDeVidas = 5;
                   pontuacao = 0;
                   y11 = 10;
                   y22 = 10;
                   exe = true;
                   
               }else{
                   System.exit(0);
               }
     
            }
            //condição para quando o jogador perder todas as vidas ele perder o jogo
            if(numeroDeVidas <= 0){
               exe = false;
               

               JOptionPane optionPane = new JOptionPane(); 
               optionPane.setMessage("Você perdeu!");
               
               
               int resposta = optionPane.showOptionDialog(null, "Você ganhou! Deseja recomeçar?", null, JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null, null, null); 
               if(resposta == JOptionPane.YES_NO_OPTION){
                   numeroDeVidas = 5;
                   pontuacao = 0;
                   y11 = 10;
                   y22 = 10;
                   exe = true;
                   
               }else{
                   System.exit(0);
               }
            }
            //pause

        
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke){
        
    }
    
    @Override
    public void keyPressed(KeyEvent ke){

        if(ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyCode() == KeyEvent.VK_A){
            if(x1 < 10){
            
            }else{
              x1 -= 12;  
            }
        }
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyCode() == KeyEvent.VK_D){
            if(x1 < 495){
            x1 += 12;
            }else{
                
            }
        }
        
    }


    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseClicked(MouseEvent e) {
               pause();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //nada aqui
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    


}

