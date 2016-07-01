
import MetodosIterativos.GaussSiedel;
import MetodosIterativos.Jacobi;
import java.math.BigDecimal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Leandro Ramos Marcelino <leandroramosmarcelino@hotmail.com>
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[][] matriz = new double[3][5];

        matriz[0][0] = 9;
        matriz[0][1] = 0;
        matriz[0][2] = 0;
        matriz[0][3] = -1;
        matriz[0][4] = 0;
        matriz[1][0] = 0;
        matriz[1][1] = 2;
        matriz[1][2] = -2;
        matriz[1][3] = -1;
        matriz[1][4] = 0;
        matriz[2][0] = 8;
        matriz[2][1] = 0;
        matriz[2][2] = -1;
        matriz[2][3] = 0;
        matriz[2][4] = 0;
        
        GaussSiedel metodo = new GaussSiedel(matriz, new double[]{1,1,1,1});
        
        System.out.println(metodo.resultado());
        System.out.println(metodo.balanceamentoQuimico());
    }
    
}
