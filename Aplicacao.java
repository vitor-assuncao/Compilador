import java.util.ArrayList;
import java.util.Scanner;

public class Aplicacao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nDigite o código a ser analisado:");
        String entrada = scanner.nextLine();

        ArrayList<Token> tokens = AnalisadorLexico.tokenizar(entrada);
        System.out.println("Tokens encontrados:");

        for (Token token : tokens) {
            System.out.println(token);
        }
        
        AnalisadorSintatico sint = new AnalisadorSintatico(tokens);
        sint.analisar();
        System.out.println("\nAnálise Sintática concluída com êxito!");

        scanner.close();
    }
}
