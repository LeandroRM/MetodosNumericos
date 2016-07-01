/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MetodosIterativos;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author Leandro Ramos Marcelino <leandroramosmarcelino@hotmail.com>
 */
public class Jacobi {
    protected final double[][] matrizAumentada;
    protected BigDecimal[] valoresFinais;
    protected int iteracoes;

    public Jacobi(double[][] matrizAumentada, double[] valoresIniciais) {
        this.matrizAumentada = matrizAumentada;
        valoresFinais = converteParaBig(valoresIniciais);
        iteracoes = 0;
        
        for (int i = 0; i < this.matrizAumentada.length; i++) {
            if (this.matrizAumentada[i][i] == 0) {
                System.out.println("Os valores da diagonal da matriz devem ser diferentes de zero");
                return;
            }
        }
        
        run();
    }
    
    /**
     * Método responsável por encontrar valores para as variáveis
     * x1, x2, ..., xn
     */
    protected void run() {
        //Variável auxiliar para o cálculo dos valores k+1
        BigDecimal novoValor;
        
        //Vetor que guarda os valores k+1
        BigDecimal[] proximosValores = new BigDecimal[valoresFinais.length];
        
        //Variável que define quando a estrutura de repetição deve parar
        boolean stop = false;
        
        while (!stop) {
            iteracoes++;

            for (int i = 0; i < matrizAumentada.length; i++) { //LINHAS - EQUAÇÕES
                //Recebe o valor referente ao resultado da equação, valor da matriz B
                novoValor = new BigDecimal(matrizAumentada[i][matrizAumentada[i].length - 1]);
                
                for (int j = 0; j < matrizAumentada[i].length -1; j++) { //COLUNAS - VARIÁVEIS E RESULTADO
                    /*  Soma dos outros coeficientes da equação 
                        multiplicados pelo valor de suas respectivas variáveis
                        com sinal invertido*/
                    if (j != i) {
                        novoValor = novoValor.add(BigDecimal.valueOf(matrizAumentada[i][j]).multiply(BigDecimal.valueOf(-1)).multiply(valoresFinais[j]));
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
    
    protected final BigDecimal diferenca(BigDecimal n1, BigDecimal n2) {
        return (n1.subtract(n2).doubleValue() > 0) ? n1.subtract(n2) : n1.subtract(n2).negate();
    }
    
    protected final BigDecimal[] copyArray(BigDecimal[] original) {
        BigDecimal[] clone = new BigDecimal[original.length];
        
        for (int i = 0; i < original.length; i++) {
            clone[i] = original[i];
        }
        
        return clone;
    }
    
    public String resultado() {
        StringBuilder sb = new StringBuilder("Resultados com ");
        sb.append(iteracoes).append(" iterações : ");
        
        int cont = 0;
        
        for (BigDecimal d : valoresFinais) {
            sb.append("\n").append("x").append(++cont).append(" = ").append(d);
        }
        
        return sb.toString();
    }

    /**
     * Método responsável por ajustar o resultado, de forma que fiquem quimicamente
     * balanceados, ou seja, que todos os resultados sejam valores inteiros.
     * Os valores são encontrados na base da tentativa e erro. 
     * Todos os resultados são multiplicados por 2,3,4,5,6,7...,n
     * até que se encontre um múltiplo onde todos fiquem inteiros
    */
    public String balanceamentoQuimico() {
        StringBuilder sb = new StringBuilder("Resultados balanceados: ");
        
        BigDecimal[] resultadoInteiro = copyArray(valoresFinais);
        boolean stop = false;
        int multiplo = 2;
        
        while (!stop) {
            stop = true;
            
            for (BigDecimal num : resultadoInteiro) {
                if (!isInteger(num)) {
                    stop = false;
                    resultadoInteiro = multiplicaArray(valoresFinais, multiplo++);
                    break;
                }
            }
        }
        
        int cont = 0;
        
        for (BigDecimal d : resultadoInteiro) {
            sb.append("\n").append("x").append(++cont).append(" = ").append(d);
        }
        
        return sb.toString();
    }

    protected final BigDecimal[] converteParaBig(double[] original) {
        BigDecimal[] clone = new BigDecimal[original.length];
        
        for (int i = 0; i < original.length; i++) {
            clone[i] = new BigDecimal(original[i]);
        }
        
        return clone;
    }
    
    protected final BigDecimal[] multiplicaArray(BigDecimal[] array, int valor) {
        BigDecimal[] clone = new BigDecimal[array.length];
        
        for (int i = 0; i < array.length; i++) {
            clone[i] = array[i].multiply(BigDecimal.valueOf(valor)).setScale(5, RoundingMode.HALF_EVEN);
        }
        
        return clone;
    }
    
    protected final boolean isInteger(BigDecimal num) {
        BigDecimal auxInt = new BigDecimal(Integer.valueOf(num.toString().substring(0, num.toString().indexOf('.'))));
        return auxInt.compareTo(num) == 0;
    }
}
