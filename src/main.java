
import MetodosIterativos.Jacobi;

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
        double[][] matriz = new double[5][6];
        
        matriz[0][0] = 5;
        matriz[0][1] = 0;
        matriz[0][2] = 0;
        matriz[0][3] = -3;
        matriz[0][4] = -1;
        matriz[0][5] = 2;
        
        matriz[1][0] = -1;
        matriz[1][1] = 4;
        matriz[1][2] = 0;
        matriz[1][3] = 0;
        matriz[1][4] = -1;
        matriz[1][5] = 3;
        
        matriz[2][0] = 0;
        matriz[2][1] = 0;
        matriz[2][2] = 2;
        matriz[2][3] = -1;
        matriz[2][4] = 0;
        matriz[2][5] = -1;
        
        matriz[3][0] = -1;
        matriz[3][1] = 0;
        matriz[3][2] = 0;
        matriz[3][3] = 4;
        matriz[3][4] = -2;
        matriz[3][5] = 0;
        
        matriz[4][0] = 0;
        matriz[4][1] = 0;
        matriz[4][2] = 0;
        matriz[4][3] = -1;
        matriz[4][4] = 2;
        matriz[4][5] = -1;
        
        Jacobi jacobi = new Jacobi(matriz, new double[]{1,1,1,1,1});
        
        System.out.println(jacobi.resultado());
    }
    
}
