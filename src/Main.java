
import java.io.File;
import java.text.ParseException;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author feijoada
 */
public class Main {
   
    public static void main(String[] args) {
       ArvoreB arvorePrincipal;
       File arq = new File("C:\\Users\\feijoada\\Desktop\\arvore.dat");
       
       try{
        if(!arq.exists() || arq.length()==0){
        int valorM= Integer.parseInt(JOptionPane.showInputDialog("Digite o valor de M", null));
        arvorePrincipal = ArvoreB.getInstancia(valorM,arq);
        }else{
        arvorePrincipal = ArvoreB.getInstancia(0,arq);
        }
       
        Apresentacao apresentacao = new Apresentacao(arvorePrincipal);
        apresentacao.setVisible(true);
							
       }catch(NumberFormatException nfe){
           JOptionPane.showMessageDialog(null, "Valor de M digitado incorretamente, redigite");
          
       }
       //arvorePrincipal = ArvoreB.getInstancia(Integer.parseInt(tbOrdem.getText()),
	//						tbNomeArvore.getText());
    }
}
