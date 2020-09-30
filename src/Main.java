import java.util.Arrays;
import javax.swing.* ;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Main extends JFrame implements ActionListener{
   
    private JTextArea area = new JTextArea(5, 10);
    private JTextArea area2 = new JTextArea(5, 10);

    private JButton abrir = new JButton("Abrir");
    private JButton guardar = new JButton("Guardar");
    private JButton ordenar = new JButton("Ordenar");
    private JButton contar = new JButton("Contar");
   
    public Main() {
        super("Ordenar palabras");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JScrollPane pane = new JScrollPane(area);
        JScrollPane pane2 = new JScrollPane(area2);
        area2.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(abrir);
        panel.add(guardar);
        panel.add(ordenar);
        panel.add(contar);
       
        abrir.addActionListener(this);
        guardar.addActionListener(this);
        ordenar.addActionListener(this);
        contar.addActionListener(this);
       
        getContentPane().add(panel, "North");
        getContentPane().add(pane);
        getContentPane().add(pane2, "South");
       
        setSize(400, 400);
        setVisible(true);
    }
   
    public static void main(String[] args) {
        new Main();
    }
   
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == abrir){
            JFileChooser ch = new JFileChooser();
            if(ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                File f = ch.getSelectedFile();
                if(f != null && f.exists()){
                    area.setText("");
                    area2.setText ("");
                    try{
                        RandomAccessFile f2 = new RandomAccessFile(f, "rw");
                        String str = "";
                        while((str = f2.readLine()) != null){
                            area.append(str+"\n");
                        }
                        f2.close();
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(this,
                                ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(this,
                            "Archivo invalido", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if(e.getSource() == guardar){
            if(area.getText().length() > 0){
                String str = "";
                do{
                    str = JOptionPane.showInputDialog (this,
                            "Nombre del Archivo",
                            "Guardando...",
                            JOptionPane.QUESTION_MESSAGE);
                }while(str == null || str.length() <= 0);
                try{
                    PrintWriter out = new PrintWriter(
                            new FileOutputStream(str), true);
                    String[] data = area.getText ().split("\n");
                    for(int i = 0; i < data.length; i++){
                        out.println(data[i]);
                    }
                    out.close();
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this,
                            ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);                  
                }
            }else{
                 JOptionPane.showMessageDialog(this,
                        "No hay texto", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource() == contar){
            if(area.getText().length() > 0){
                StringTokenizer token = new StringTokenizer(
                        area.getText(), " \n\t");
                int i = 0;
                while(token.hasMoreElements()){
                    token.nextToken();
                    i++;
                }
                JOptionPane.showMessageDialog (this,
                        "Total de palabras: "+i, "Total de palabras",
                        JOptionPane.INFORMATION_MESSAGE);
            }else{
                 JOptionPane.showMessageDialog (this,
                        "No hay texto", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource() == ordenar){
            if( area.getText().length() > 0){
                StringTokenizer token = new StringTokenizer(
                        area.getText(), " \n\t");
                Vector vec = new Vector();
                while( token.hasMoreElements()){
                    vec.add(token.nextToken());
                }
                Object[] data = vec.toArray();
                Arrays.sort(data);
                for(int i = 0; i < data.length; i++){
                    area2.append(data[i]+"\n");
                }
            }else{
                 JOptionPane.showMessageDialog(this,
                        "No hay texto", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}