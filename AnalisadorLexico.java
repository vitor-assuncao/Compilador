import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalisadorLexico {
    static ArrayList<Token> tokenizar(String entrada) {
        ArrayList<Token> tokens = new ArrayList<>();

        String regexInteiro = "\\binteiro\\b";
        String regexFlutuante = "\\bflutuante\\b";
        String regexReal = "\\breal\\b";
        String regexBooleano = "\\bbooleano\\b";
        String regexTipoCaractere = "\\bcaractere\\b";
        String regexIdentificador = "[a-zA-Z][a-zA-Z0-9_]*";
        String regexNumeroReal = "\\b\\d+\\.\\d+\\b";
        String regexNumeroInteiro = "\\b\\d+\\b";
        String regexOperadorAritmetico = "[+\\-*/%]";
        String regexOperadorRelacional = "==|!=|>=|<=|>|<";
        String regexOperadorLogico = "\\bE\\b|\\bOU\\b|\\bNAO\\b";
        String regexOperadorAtribuicao = "=";
        String regexAbreChave = "\\binicio\\b";
        String regexFechaChave = "\\bfim\\b";
        String regexAbreParenteses = "\\(";
        String regexFechaParenteses = "\\)";
        String regexPontoEVirgula = ";";
        String regexVerdadeiro = "\\bverdadeiro\\b";
        String regexFalso = "\\bfalso\\b";
        String regexFaca = "\\brepita\\b";
        String regexAteque = "\\bateque\\b";
        String regexEnquanto = "\\benquanto\\b";
        String regexPara = "\\bpara\\b";
        String regexRetorna = "\\bretorna\\b";
        String regexPrincipal = "\\bprincipal\\b";
        String regexFunction = "\\bfunction\\b";
        String regexInvocar = "\\binvocar\\b";
        String regexCaractere = "'.'";
        String regexCadeia = "\"[^\"]*\"";
        String regexImprima = "\\bimprima\\b";
        String regexVazio = "\\bvazio\\b";
        String regexSe = "\\bse\\b";
        String regexSenao = "\\bsenao\\b";
        String regexVirgula = ",";
        String regexEOF = "\\bEOF\\b";

        Pattern padraoInteiro = Pattern.compile(regexInteiro);
        Pattern padraoFlutuante = Pattern.compile(regexFlutuante);
        Pattern padraoReal = Pattern.compile(regexReal);
        Pattern padraoBooleano = Pattern.compile(regexBooleano);
        Pattern padraoIdentificador = Pattern.compile(regexIdentificador);
        Pattern padraoNumeroReal = Pattern.compile(regexNumeroReal);
        Pattern padraoNumeroInteiro = Pattern.compile(regexNumeroInteiro);
        Pattern padraoOperadorAritmetico = Pattern.compile(regexOperadorAritmetico);
        Pattern padraoOperadorRelacional = Pattern.compile(regexOperadorRelacional);
        Pattern padraoOperadorLogico = Pattern.compile(regexOperadorLogico);
        Pattern padraoOperadorAtribuicao = Pattern.compile(regexOperadorAtribuicao);
        Pattern padraoAbreChave = Pattern.compile(regexAbreChave);
        Pattern padraoFechaChave = Pattern.compile(regexFechaChave);
        Pattern padraoAbreParenteses = Pattern.compile(regexAbreParenteses);
        Pattern padraoFechaParenteses = Pattern.compile(regexFechaParenteses);
        Pattern padraoPontoEVirgula = Pattern.compile(regexPontoEVirgula);
        Pattern padraoVerdadeiro = Pattern.compile(regexVerdadeiro);
        Pattern padraoFalso = Pattern.compile(regexFalso);
        Pattern padraoFaca = Pattern.compile(regexFaca);
        Pattern padraoRetorna = Pattern.compile(regexRetorna);
        Pattern padraoPrincipal = Pattern.compile(regexPrincipal);
        Pattern padraoFunction = Pattern.compile(regexFunction);
        Pattern padraoCaractere = Pattern.compile(regexCaractere);
        Pattern padraoTipoCaractere = Pattern.compile(regexTipoCaractere);
        Pattern padraoCadeia = Pattern.compile(regexCadeia);
        Pattern padraoInvocar = Pattern.compile(regexInvocar);
        Pattern padraoVazio = Pattern.compile(regexVazio);
        Pattern padraoSe = Pattern.compile(regexSe);
        Pattern padraoSenao = Pattern.compile(regexSenao);
        Pattern padraoVirgula = Pattern.compile(regexVirgula);

        Pattern padraoImprima = Pattern.compile(regexImprima);
        Pattern padraoAteque = Pattern.compile(regexAteque);
        Pattern padraoEnquanto = Pattern.compile(regexEnquanto);
        Pattern padraoPara = Pattern.compile(regexPara);

        Matcher matcher = Pattern.compile(
                "(" + regexInteiro + "|" + regexFlutuante + "|" + regexReal + "|" + regexBooleano + "|" +
                regexIdentificador + "|" + regexNumeroReal + "|" + regexNumeroInteiro + "|" +
                regexOperadorAritmetico + "|" + regexOperadorRelacional + "|" + regexOperadorLogico + "|" +
                regexOperadorAtribuicao + "|" + regexAbreChave + "|" + regexFechaChave + "|" +
                regexAbreParenteses + "|" + regexFechaParenteses + "|" + regexPontoEVirgula + "|" +
                regexVerdadeiro + "|" + regexFalso + "|" + regexFaca + "|" + regexRetorna + "|" +
                regexPrincipal + "|" + regexCadeia + "|" + regexVazio + "|" + regexImprima + "|" +
                regexFunction + "|" + regexCaractere + "|" + regexTipoCaractere + "|" + regexInvocar + "|" +
                regexSe + "|" + regexSenao + "|" + regexVirgula + "|" + regexAteque + "|" + regexEnquanto + "|" + regexPara + ")"
            ).matcher(entrada);

        while (matcher.find()) {
            String correspondencia = matcher.group().trim();

            TipoToken tipo;
            if (padraoInteiro.matcher(correspondencia).matches()) {
                tipo = TipoToken.INTEIRO;
            } else if (padraoFlutuante.matcher(correspondencia).matches()) {
                tipo = TipoToken.FLUTUANTE;
            } else if (padraoReal.matcher(correspondencia).matches()) {
                tipo = TipoToken.REAL;
            } else if (padraoTipoCaractere.matcher(correspondencia).matches()) {
                tipo = TipoToken.CARACTERE;
            } else if (padraoBooleano.matcher(correspondencia).matches()) {
                tipo = TipoToken.BOOLEANO;
            } else if (padraoNumeroReal.matcher(correspondencia).matches()) {
                tipo = TipoToken.NUMERO_REAL;
            } else if (padraoNumeroInteiro.matcher(correspondencia).matches()) {
                tipo = TipoToken.NUMERO_INTEIRO;
            } else if (padraoVazio.matcher(correspondencia).matches()) {
                tipo = TipoToken.VAZIO;
            } else if (padraoSe.matcher(correspondencia).matches()) {
                tipo = TipoToken.SE;
            } else if (padraoSenao.matcher(correspondencia).matches()) {
                tipo = TipoToken.SENAO;
            } else if (padraoOperadorAritmetico.matcher(correspondencia).matches()) {
                switch (correspondencia) {
                    case "+":
                        tipo = TipoToken.ADICAO;
                        break;
                    case "-":
                        tipo = TipoToken.SUBTRACAO;
                        break;
                    case "*":
                        tipo = TipoToken.MULTIPLICACAO;
                        break;
                    case "/":
                        tipo = TipoToken.DIVISAO;
                        break;
                    case "%":
                        tipo = TipoToken.RESTO;
                        break;
                    default:
                        throw new IllegalArgumentException("Operador aritmético desconhecido: " + correspondencia);
                }
            } else if (padraoOperadorRelacional.matcher(correspondencia).matches()) {
                switch (correspondencia) {
                    case ">=":
                        tipo = TipoToken.MAIOR_IGUAL;
                        break;
                    case "<=":
                        tipo = TipoToken.MENOR_IGUAL;
                        break;
                    case "==":
                        tipo = TipoToken.IGUALDADE;
                        break;
                    case "!=":
                        tipo = TipoToken.DIFERENTE;
                        break;
                    case ">":
                        tipo = TipoToken.MAIOR;
                        break;
                    case "<":
                        tipo = TipoToken.MENOR;
                        break;
                    default:
                        throw new IllegalArgumentException("Operador relacional desconhecido: " + correspondencia);
                }
            } else if (padraoOperadorLogico.matcher(correspondencia).matches()) {
                switch (correspondencia) {
                    case "E":
                        tipo = TipoToken.E;
                        break;
                    case "OU":
                        tipo = TipoToken.OU;
                        break;
                    case "NAO":
                        tipo = TipoToken.NAO;
                        break;
                    default:
                        throw new IllegalArgumentException("Operador lógico desconhecido: " + correspondencia);
                }
            } else if (padraoOperadorAtribuicao.matcher(correspondencia).matches()) {
                tipo = TipoToken.ATRIBUICAO;
            } else if (padraoAbreChave.matcher(correspondencia).matches()) {
                tipo = TipoToken.ABRE_CHAVE;
            } else if (padraoFechaChave.matcher(correspondencia).matches()) {
                tipo = TipoToken.FECHA_CHAVE;
            } else if (padraoAbreParenteses.matcher(correspondencia).matches()) {
                tipo = TipoToken.ABRE_PARENTESES;
            } else if (padraoFechaParenteses.matcher(correspondencia).matches()) {
                tipo = TipoToken.FECHA_PARENTESES;
            } else if (padraoPontoEVirgula.matcher(correspondencia).matches()) {
                tipo = TipoToken.PONTO_E_VIRGULA;
            } else if (padraoVerdadeiro.matcher(correspondencia).matches()) {
                tipo = TipoToken.VERDADEIRO;
            } else if (padraoFalso.matcher(correspondencia).matches()) {
                tipo = TipoToken.FALSO;
            } else if (padraoFaca.matcher(correspondencia).matches()) {
                tipo = TipoToken.REPITA;
            } 
            else if (padraoAteque.matcher(correspondencia).matches()) {
                tipo = TipoToken.ATEQUE;
            } 
            else if (padraoEnquanto.matcher(correspondencia).matches()) {
                tipo = TipoToken.ENQUANTO;
            } 
            else if (padraoPara.matcher(correspondencia).matches()) {
                tipo = TipoToken.PARA;
            } else if (padraoPrincipal.matcher(correspondencia).matches()) {
                tipo = TipoToken.PRINCIPAL;
            } else if (padraoFunction.matcher(correspondencia).matches()) {
                tipo = TipoToken.FUNCTION;
            } else if (padraoInvocar.matcher(correspondencia).matches()) {
                tipo = TipoToken.INVOCAR;
            } else if (padraoRetorna.matcher(correspondencia).matches()) {
                tipo = TipoToken.RETORNA;
            } else if (padraoCaractere.matcher(correspondencia).matches()) {
                tipo = TipoToken.CHAR;
            } else if (padraoCadeia.matcher(correspondencia).matches()) {
                tipo = TipoToken.CADEIA;
            }else if (padraoImprima.matcher(correspondencia).matches()) {
                tipo = TipoToken.IMPRIMA;
            }else if (padraoIdentificador.matcher(correspondencia).matches()) {
                tipo = TipoToken.IDENTIFICADOR;
            } else if (padraoVirgula.matcher(correspondencia).matches()) {
                tipo = TipoToken.VIRGULA;
            } else {
                throw new IllegalArgumentException("Token inválido: " + correspondencia);
            }
            tokens.add(new Token(tipo, correspondencia));
        }

        tokens.add(new Token(TipoToken.EOF, "EOF"));
        return tokens;
    }
}
