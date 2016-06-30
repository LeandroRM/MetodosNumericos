/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MetodosIterativos;

import java.math.BigDecimal;

/**
 *
 * @author Leandro Ramos Marcelino <leandroramosmarcelino@hotmail.com>
 */
public class GaussSiedel {
    private final double[][] matrizAumentada;
    private final double[] valoresIniciais;
    private BigDecimal[] valoresFinais;
    private int iteracoes;

    public GaussSiedel(double[][] matrizAumentada, double[] valoresIniciais) {
        this.matrizAumentada = matrizAumentada;
        this.valoresIniciais = valoresIniciais;
        
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
    
    private final void run() {
        BigDecimal novoValor;
        BigDecimal[] proximosValores = new BigDecimal[valoresFinais.length];
        boolean stop = false;
        BigDecimal valorX;
        proximosValores = copyArray(valoresFinais);
            
        
        while (!stop) {
            iteracoes++;

            for (int i = 0; i < matrizAumentada.length; i++) { //LINHAS
                novoValor = new BigDecimal(matrizAumentada[i][matrizAumentada[i].length - 1]);
                
                for (int j = 0; j < matrizAumentada[i].length -1; j++) { //COLUNAS
                    //Os valores na diagonal da matriz serão utilizados posteriormente
                    if (j != i) {
                        if (j < i) {
                            valorX = proximosValores[j];
                        } else {
                            valorX = valoresFinais[j];
                        }
                        novoValor = novoValor.add(BigDecimal.valueOf(matrizAumentada[i][j]).multiply(BigDecimal.valueOf(-1)).multiply(valorX));
                    }
                }

                novoValor = novoValor.divide(BigDecimal.valueOf(matrizAumentada[i][i]));
                
                proximosValores[i] = novoValor;
            }

            stop = true;
            
            for (int i = 0; i < valoresFinais.length; i++) { //LINHAS
                if (diferenca(proximosValores[i], valoresFinais[i]).doubleValue() > 0.000001) {
                    stop = false;
                    break;
                }
            }
            
            valoresFinais = copyArray(proximosValores);
        }
    }
    
    private BigDecimal diferenca(BigDecimal n1, BigDecimal n2) {
        return (n1.subtract(n2).doubleValue() > 0) ? n1.subtract(n2) : n1.subtract(n2).negate();
    }
    
    private BigDecimal[] copyArray(BigDecimal[] original) {
        BigDecimal[] clone = new BigDecimal[original.length];
        
        for (int i = 0; i < original.length; i++) {
            clone[i] = original[i];
        }
        
        return clone;
    }
    
    public String resultado() {
        StringBuilder sb = new StringBuilder("Resultados com ");
        sb.append(iteracoes).append(" : ");
        
        int cont = 0;
        
        for (BigDecimal d : valoresFinais) {
            sb.append("\n").append("x").append(++cont).append(" = ").append(d);
        }
        
        return sb.toString();
    }

    private final BigDecimal[] converteParaBig(double[] original) {
        BigDecimal[] clone = new BigDecimal[original.length];
        
        for (int i = 0; i < original.length; i++) {
            clone[i] = new BigDecimal(original[i]);
        }
        
        return clone;
    }   
}
