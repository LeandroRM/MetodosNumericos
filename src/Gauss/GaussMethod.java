/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gauss;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Leandro Ramos Marcelino <leandroramosmarcelino@hotmail.com>
 */
public class GaussMethod {

    private static int SIZE;

    public final static Scanner entry = new Scanner(System.in);

    public static void main(String[] args) throws Exception{

        System.out.println("Informe o tamanho da matriz:");
        SIZE = entry.nextInt();
        double[][] matrizInicial = new double[SIZE][SIZE + 1];
        final double[][] matrizFixa = matrizInicial;

        System.out.println("Preencha a matriz: ");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print("[" + (i + 1) + "][" + (j + 1) + "] = ");
                matrizInicial[i][j] = entry.nextDouble();
            }
            
            System.out.print("Resultado da linha " + (i + 1) + ": ");
            matrizInicial[i][SIZE] = entry.nextDouble();
        }

        matrizInicial = Escalonar(matrizInicial);
        
        System.out.println("Matriz escalonada");
        imprimir(matrizInicial);
        
//        boolean erro = false;
//        int linhaErro = -1;

        //Verifica se alguma linha não está zerada
        for (int i = 0; i < SIZE; i++) {
            //O importante é nenhuma variável da diagonal estar zerada
            if (matrizInicial[i][i] == 0) {
                throw new Exception("Não é possivel solucionar esta equação!");
//                erro = true;
//                linhaErro = i;
            }
        }
        
//        if (erro) {
//            if (matrizInicial[linhaErro][SIZE] == 0) {
//                System.out.println("O numero de variaveis é maior que o numero de equações portanto tem infinitas soluções;");
//            } else {
//                System.out.println("Equação incompativel");
//            }
//            System.exit(1);
//        }

        final double resultados[] = Retrosubstituicao(matrizInicial);

        for (int i = 0; i < SIZE; i++) {
            System.out.println("x" + (i + 1) + " = " + resultados[i]);
        }
        
        System.out.println("Teste final: ");
        double valorEsperado;
        double valor;
        for (int i = 0; i < SIZE; i++) {
            valorEsperado = matrizFixa[i][SIZE];
            valor = 0;
            
            for (int j = 0; j < SIZE; j++) {
                valor += matrizFixa[i][j] * resultados[j];
                System.out.println(valor);
            }
            
            if (valor != valorEsperado) {
                System.out.println("Falhou");
                return;
            }
        }
        
        System.out.println("Aprovado");
    }

    public static double[] Retrosubstituicao(double[][] matrizInicial) {
        int equacao = SIZE - 1;
        double resultados[] = new double[SIZE];

        for (int i = equacao; i >= 0; i--) {
            double resultado = matrizInicial[i][SIZE]; //Resultado da linha i
            double fator = 0; // fator que será dividido ex x1 = ??/1
            double conta = 0;// Conta das outras variaveis

            for (int j = SIZE - 1; j >= 0; j--) {
                if (equacao == j) {
                    fator = matrizInicial[i][j];
                } else {
                    conta += matrizInicial[i][j] * resultados[j];
                }
            }
        
            conta = (conta + resultado) / fator;
            resultados[equacao] = conta;
            equacao--;
        }

        return resultados;
    }

    public static double[][] Escalonar(double[][] matrizInicial) {
        imprimir(matrizInicial);

        //Garantir que a maior esteja em cima;
        int maiorIndex = 0;

        for (int i = 1; i < SIZE; i++) {
            if (matrizInicial[i][0] > matrizInicial[maiorIndex][0]) {
                maiorIndex = i;
            }
        }
        
        //se a maior linha não for a primeira
        if (maiorIndex != 0) {
            //troca a primeira linha com a linha da posicao encontrada;
            for (int i = 0; i <= SIZE; i++) {
                double dAux = matrizInicial[maiorIndex][i];
                matrizInicial[maiorIndex][i] = matrizInicial[0][i];
                matrizInicial[0][i] = dAux;
            }
        }

        imprimir(matrizInicial);
        boolean bParar = true;

        //Criterio de parada
        for (int i = 1; i < SIZE; i++) {
            for (int j = 0; j <= i - 1; j++) {
                if (matrizInicial[i][j] != 0) {
                    bParar = false;
                    break;
                }
            }
            
            if (!bParar) {
                break;
            }
        }
        
        if (bParar) {
            return matrizInicial;
        } else {
            //ESCALONAR MATRIZ 
            int linha = 0;
            int coluna = 0;
            bParar = false;

            //Encontrar PIVO
            int pivo = 1;
            
            for (int i = 0; i < SIZE; i++) {
                for (int j = pivo; j < SIZE; j++) {
                    if (matrizInicial[j][i] != 0) {
                        coluna = i;
                        linha = j;
                        
                        bParar = true;
                        
                        break;
                    }
                }
                
                if (bParar) {
                    break;
                } else {
                    pivo++;
                    System.out.println(pivo);
                }
            }

            //ver onde nao está 0 para zerar
            for (int i = 0; i < SIZE; i++) {
                for (int j = 1; j < SIZE; j++) {
                    if (matrizInicial[j][i] != 0) {
                        coluna = i;
                        linha = j;
                        
                        bParar = true;
                        break;
                    }

                }
                
                if (bParar) {
                    break;
                }
            }
            
            //Verificar divisor
            double valPivo = matrizInicial[pivo - 1][coluna];
            double valZerar = matrizInicial[linha][coluna];
            double valFator = valZerar / valPivo;

            System.out.println("Pivo: " + valPivo);

            double linhaPivoMultiplicada[] = new double[SIZE + 1];

            for (int i = 0; i <= SIZE; i++) {
                linhaPivoMultiplicada[i] = matrizInicial[pivo - 1][i] * valFator * -1;
            }

//            double linhaNova[] = new double[SIZE + 1];

//            for (int i = 0; i <= SIZE; i++) {
//                linhaNova[i] = linhaPivoMultiplicada[i] + matrizInicial[linha][i];
//            }

//            double matrizRetorno[][] = new double[SIZE][SIZE + 1];

            for (int i = 0; i <= SIZE; i++) {
//                matrizInicial[linha][i] = linhaNova[i];
                matrizInicial[linha][i] = linhaPivoMultiplicada[i] + matrizInicial[linha][i];
            }
//            for (int i = 0; i < SIZE; i++) {
//                for (int j = 0; j <= SIZE; j++) {
//                    if (i == linha) {
//                    } else {
//                        matrizRetorno[i][j] = matrizInicial[i][j];
//                    }
//                }

            return Escalonar(matrizInicial);
        }
    }

    public static void imprimir(double[][] matrizInicial) {
        System.out.println("Imprimindo matriz");

        for (int i = 0; i < SIZE; i++) {
            System.out.println(Arrays.toString(matrizInicial[i]));
        }
        
        System.out.println("");
    }
}
