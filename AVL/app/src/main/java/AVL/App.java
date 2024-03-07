package AVL;

import java.util.InputMismatchException;
import java.util.Scanner;

public class APP {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int escolha=0;
        AVLTree<Integer> arvore = new AVLTree<>();

        do{
            menu();
            escolha = tratamentoEntrada(escolha);

            switch (escolha){

                case 1:
                    try{
                        int valor;
                        System.out.print("Digite um valor que deseja adicionar: ");
                        valor = in.nextInt();
                        arvore.inserirAVL(valor);
                    }catch (InputMismatchException e){
                        System.out.println(e.getMessage());
                        System.out.println("Inserção não foi efetuada!");
                    }

                    break;

                case 2:
                    arvore.inOrdem();
                    break;

                case 3:
                    arvore.inNivel();
                    break;

                case 0:
                    System.out.println("Fim do programa!");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente!");
                    break;
            }
        }while (escolha!=0);
    }

    private static void menu(){
        System.out.println("=======================================================");
        System.out.println("           Árvore AVL de numeros inteiros");
        System.out.println("=======================================================");
        System.out.println("Menu");
        System.out.println("");
        System.out.println("1- Inserir na árvore");
        System.out.println("2- Passeio em ordem");
        System.out.println("3- Passeio por nivel");
        System.out.println("0- Sair do programa");
        System.out.print("Escolha uma opção: ");
    }

    private static int tratamentoEntrada(int escolha){
        Scanner in = new Scanner(System.in);
        try{
            escolha = in.nextInt();
        }catch (InputMismatchException e){
            System.out.println(e.getMessage());
        }
        return escolha;
    }
}
