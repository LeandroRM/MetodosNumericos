/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MetodosIterativos;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Leandro Ramos Marcelino <leandroramosmarcelino@hotmail.com>
 */
public class GaussSiedel extends Jacobi {

    public GaussSiedel(double[][] matrizAumentada, double[] valoresIniciais) {
        super(matrizAumentada, valoresIniciais);
    }

    @Override
    /**
     * Método responsável por encontrar valores para as variáveis
     * x1, x2, ..., xn
     */
    protected void run() {
        BigDecimal novoValor;
        BigDecimal[] proximosValores = copyArray(valoresFinais);
        boolean stop = false;
        BigDecimal valorX;
            
        
        while (!stop) {
            iteracoes++;

            for (int i = 0; i < matrizAumentada.length; i++) { //LINHAS - EQUAÇÕES
                //Recebe o valor referente ao resultado da equação, valor da matriz B
                novoValor = new BigDecimal(matrizAumentada[i][matrizAumentada[i].length - 1]);
                
                for (int j = 0; j < matrizAumentada[i].length -1; j++) { //COLUNAS - VARIÁVEIS + RESULTADO
                    /*  Soma dos outros coeficientes da equação 
                        multiplicados pelo valor de suas respectivas variáveis
                        com sinal invertido*/
                    if (j != i) {
                        //Caso o coeficiente já tenha recebido um valor k+1;
                        if (j < i) {
                            valorX = proximosValores[j];
                        } else {
                            valorX = valoresFinais[j];
                        }
                        
            novoValor = novoValor.add(BigDecimal.valueOf(matrizAumentada[i][j]).multiply(BigDecimal.valueOf(-1)).multiply(valorX));
                    }
                }

                //Passa o coeficiente da variável em evidência dividindo
                novoValor = novoValor.divide(BigDecimal.valueOf(matrizAumentada[i][i]), 100, RoundingMode.HALF_EVEN);
                
                proximosValores[i] = novoValor;
            }
            
            /*
                Em casos onde temos mais variáveis que equações, este for é responsável
                por repassar o valor das variáveis extras
            */
            for (int i = matrizAumentada.length; i < valoresFinais.length; i++) {
                proximosValores[i] = valoresFinais[i];
            }

            stop = true;
            
            /*
                erro = kn - k(n-1)
                se para todas as variáveis o erro for menor que 0.000001 então o resultado foi encontrado
            */
            for (int i = 0; i < valoresFinais.length; i++) {
                if (diferenca(proximosValores[i], valoresFinais[i]).doubleValue() > 0.000001) {
                    stop = false;
                    break;
                }
            }
            
            valoresFinais = copyArray(proximosValores);
        }
    }
}
