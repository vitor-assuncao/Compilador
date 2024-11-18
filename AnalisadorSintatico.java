import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class AnalisadorSintatico {
    private ArrayList<Token> tokens;
    private int cursor;
    private Token tokenAtual;

    public AnalisadorSintatico(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.cursor = 0;
        this.tokenAtual = tokens.get(cursor);
    }

    private void proximoToken() {
        if (cursor < tokens.size()) {
            cursor++;
            tokenAtual = tokens.get(cursor);
        }
    }

    private boolean verificarTipoToken(TipoToken tipo) {
        return tokenAtual.getTipo() == tipo;
    }

    private void consumirToken(TipoToken tipo) {
        if (verificarTipoToken(tipo)) {
            proximoToken();
        } else {
            throw new RuntimeException("Erro de sintaxe: esperava " + tipo + " mas encontrou " + tokenAtual.getTipo());
        }
    }

    public void analisar() {
        programa();
        if (cursor != tokens.size() - 1) {
            throw new RuntimeException("Erro de sintaxe: tokens restantes inesperados");
        }
    }

    private void programa() {
        while (verificarTipoToken(TipoToken.INTEIRO) || verificarTipoToken(TipoToken.FLUTUANTE) ||
        verificarTipoToken(TipoToken.REAL) || verificarTipoToken(TipoToken.BOOLEANO) ||
        verificarTipoToken(TipoToken.CARACTERE) || verificarTipoToken(TipoToken.VAZIO)) {
            funcaoOuDeclaracaoGlobal();
        }
    }

    private void principal() {
        consumirToken(TipoToken.PRINCIPAL);
        consumirToken(TipoToken.ABRE_PARENTESES);
        parametros();
        consumirToken(TipoToken.FECHA_PARENTESES);
        if (verificarTipoToken(TipoToken.PONTO_E_VIRGULA)) {
            consumirToken(TipoToken.PONTO_E_VIRGULA);
        } else {
            consumirToken(TipoToken.ABRE_CHAVE);
            bloco();
            consumirToken(TipoToken.FECHA_CHAVE);
        }
    }

    private void funcaoOuDeclaracaoGlobal() {
        tipo();
        if (verificarTipoToken(TipoToken.FUNCTION)) {
            consumirToken(TipoToken.FUNCTION);
            if (verificarTipoToken(TipoToken.IDENTIFICADOR)) {
                funcao();
            } else if (verificarTipoToken(TipoToken.PRINCIPAL)) {
                principal();
            } else {
                throw new RuntimeException("Erro de sintaxe: identificador ou 'principal' esperado após 'function'");
            }
        } else {
            String identificador = tokenAtual.getValor();
            consumirToken(TipoToken.IDENTIFICADOR);
            if (verificarTipoToken(TipoToken.PONTO_E_VIRGULA)) {
                consumirToken(TipoToken.PONTO_E_VIRGULA);
            } else if (verificarTipoToken(TipoToken.ATRIBUICAO)) {
                consumirToken(TipoToken.ATRIBUICAO);
                expressao();
                consumirToken(TipoToken.PONTO_E_VIRGULA);
            } else {
                throw new RuntimeException("Erro de sintaxe: esperava ; ou = mas encontrou " + tokenAtual.getTipo());
            }
        }
    }

    private void funcao() {
        consumirToken(TipoToken.IDENTIFICADOR);
        consumirToken(TipoToken.ABRE_PARENTESES);
        parametros();
        consumirToken(TipoToken.FECHA_PARENTESES);
        if (verificarTipoToken(TipoToken.PONTO_E_VIRGULA)) {
            consumirToken(TipoToken.PONTO_E_VIRGULA);
        } else {
            consumirToken(TipoToken.ABRE_CHAVE);
            bloco();
            consumirToken(TipoToken.FECHA_CHAVE);
        }
    }

    private void parametros() {
        if (verificarTipoToken(TipoToken.INTEIRO) || verificarTipoToken(TipoToken.FLUTUANTE) ||
        verificarTipoToken(TipoToken.REAL) || verificarTipoToken(TipoToken.BOOLEANO) ||
        verificarTipoToken(TipoToken.CARACTERE) || verificarTipoToken(TipoToken.VAZIO)) {
            tipo();
            consumirToken(TipoToken.IDENTIFICADOR);
            while (verificarTipoToken(TipoToken.VIRGULA)) {
                consumirToken(TipoToken.VIRGULA);
                tipo();
                consumirToken(TipoToken.IDENTIFICADOR);
            }
        }
    }

    private void bloco() {
        while (!verificarTipoToken(TipoToken.FECHA_CHAVE) && !verificarTipoToken(TipoToken.EOF)) {
            declaracaoOuInstrucao();
        }
    }

    private void declaracaoOuInstrucao() {
        if (verificarTipoToken(TipoToken.INTEIRO) || verificarTipoToken(TipoToken.FLUTUANTE) ||
        verificarTipoToken(TipoToken.REAL) || verificarTipoToken(TipoToken.BOOLEANO) ||
        verificarTipoToken(TipoToken.CARACTERE) ||  verificarTipoToken(TipoToken.VAZIO)) {
            declaracao();
        } else {
            instrucao();
        }
    }

    private void declaracao() {
        tipo();
        String identificador = tokenAtual.getValor();
        consumirToken(TipoToken.IDENTIFICADOR);
        if (verificarTipoToken(TipoToken.ATRIBUICAO)) {
            consumirToken(TipoToken.ATRIBUICAO);
            if(verificarTipoToken(TipoToken.INVOCAR))
                instrucaoInvocar();
            else{
                expressao();
                consumirToken(TipoToken.PONTO_E_VIRGULA);
            }
        }else
            consumirToken(TipoToken.PONTO_E_VIRGULA);
    }

    private void declaracaoGlobal() {
        tipo();
        consumirToken(TipoToken.IDENTIFICADOR);
        consumirToken(TipoToken.PONTO_E_VIRGULA);
    }

    private void tipo() {
        if (verificarTipoToken(TipoToken.INTEIRO)) {
            consumirToken(TipoToken.INTEIRO);
        } else if (verificarTipoToken(TipoToken.FLUTUANTE)) {
            consumirToken(TipoToken.FLUTUANTE);
        } else if (verificarTipoToken(TipoToken.REAL)) {
            consumirToken(TipoToken.REAL);
        } else if (verificarTipoToken(TipoToken.BOOLEANO)) {
            consumirToken(TipoToken.BOOLEANO);
        } else if (verificarTipoToken(TipoToken.CARACTERE)) {
            consumirToken(TipoToken.CARACTERE);
        } else if (verificarTipoToken(TipoToken.VAZIO)) {
            consumirToken(TipoToken.VAZIO);
        } else {
            throw new RuntimeException("Erro de sintaxe: tipo esperado mas encontrou " + tokenAtual.getTipo());
        }
    }

    private void instrucao() {
        if (verificarTipoToken(TipoToken.SE)) {
            instrucaoSe();
        } else if (verificarTipoToken(TipoToken.REPITA)) {
            instrucaoRepita();
        } else if (verificarTipoToken(TipoToken.PARA)) {
            instrucaoPara();
        } else if (verificarTipoToken(TipoToken.ENQUANTO)) {
            instrucaoEnquanto();
        } else if (verificarTipoToken(TipoToken.IDENTIFICADOR)) {
            instrucaoAtribuicaoOuChamada();
        } else if (verificarTipoToken(TipoToken.IMPRIMA)) {
            instrucaoImprima();
        } else if (verificarTipoToken(TipoToken.RETORNA)) {
            instrucaoRetorna();
        } else if (verificarTipoToken(TipoToken.INVOCAR)) {
            instrucaoInvocar();
        } else {
            throw new RuntimeException("Erro de sintaxe: instrução inesperada " + tokenAtual.getTipo());
        }
    }

    private void instrucaoSe() {
        consumirToken(TipoToken.SE);
        consumirToken(TipoToken.ABRE_PARENTESES);
        expressao();
        consumirToken(TipoToken.FECHA_PARENTESES);
        consumirToken(TipoToken.ABRE_CHAVE);
        bloco();
        consumirToken(TipoToken.FECHA_CHAVE);
        if (verificarTipoToken(TipoToken.SENAO)) {
            consumirToken(TipoToken.SENAO);
            if(verificarTipoToken(TipoToken.SE)){
                instrucaoSe();
            }else{
            consumirToken(TipoToken.ABRE_CHAVE);
            bloco();
            consumirToken(TipoToken.FECHA_CHAVE);
            }
        }
    }

    private void instrucaoRepita() {
        consumirToken(TipoToken.REPITA);
        consumirToken(TipoToken.ABRE_CHAVE);
        bloco();
        consumirToken(TipoToken.FECHA_CHAVE);
        consumirToken(TipoToken.ATEQUE);
        consumirToken(TipoToken.ABRE_PARENTESES);
        expressao();
        consumirToken(TipoToken.FECHA_PARENTESES);
        consumirToken(TipoToken.PONTO_E_VIRGULA);
    }

    private void instrucaoPara() {
        consumirToken(TipoToken.PARA);
        consumirToken(TipoToken.ABRE_PARENTESES);
        if (!verificarTipoToken(TipoToken.PONTO_E_VIRGULA)) {
            declaracaoOuInstrucao();
        }
        consumirToken(TipoToken.PONTO_E_VIRGULA);
        if (!verificarTipoToken(TipoToken.PONTO_E_VIRGULA)) {
            expressao();
        }
        consumirToken(TipoToken.PONTO_E_VIRGULA);
        if (!verificarTipoToken(TipoToken.FECHA_PARENTESES)) {
            instrucao();
        }
        consumirToken(TipoToken.FECHA_PARENTESES);
        consumirToken(TipoToken.ABRE_CHAVE);
        bloco();
        consumirToken(TipoToken.FECHA_CHAVE);
    }

    private void instrucaoEnquanto() {
        consumirToken(TipoToken.ENQUANTO);
        consumirToken(TipoToken.ABRE_PARENTESES);
        expressao();
        consumirToken(TipoToken.FECHA_PARENTESES);
        consumirToken(TipoToken.ABRE_CHAVE);
        bloco();
        consumirToken(TipoToken.FECHA_CHAVE);
    }

    private void instrucaoAtribuicaoOuChamada() {
        String identificador = tokenAtual.getValor();
        consumirToken(TipoToken.IDENTIFICADOR);
        if (verificarTipoToken(TipoToken.ABRE_PARENTESES)) {
            consumirToken(TipoToken.ABRE_PARENTESES);
            argumentos();
            consumirToken(TipoToken.FECHA_PARENTESES);
            consumirToken(TipoToken.PONTO_E_VIRGULA);
        } else if (verificarTipoToken(TipoToken.ATRIBUICAO)) {
            consumirToken(TipoToken.ATRIBUICAO);
            expressao();
            consumirToken(TipoToken.PONTO_E_VIRGULA);
        } else {
            throw new RuntimeException("Erro de sintaxe: esperava ( ou = mas encontrou " + tokenAtual.getTipo());
        }
    }

    private void instrucaoImprima() {
        consumirToken(TipoToken.IMPRIMA);
        consumirToken(TipoToken.ABRE_PARENTESES);
        if (verificarTipoToken(TipoToken.CADEIA)) {
            consumirToken(TipoToken.CADEIA);
            while (verificarTipoToken(TipoToken.VIRGULA)) {
                consumirToken(TipoToken.VIRGULA);
                expressao();
            }
        } else {
            throw new RuntimeException("Erro de sintaxe: cadeia esperada na instrução imprima");
        }
        consumirToken(TipoToken.FECHA_PARENTESES);
        consumirToken(TipoToken.PONTO_E_VIRGULA);
    }

    private void instrucaoRetorna() {
        consumirToken(TipoToken.RETORNA);
        expressao();
        consumirToken(TipoToken.PONTO_E_VIRGULA);
    }

    private void instrucaoInvocar() {
        consumirToken(TipoToken.INVOCAR);
        consumirToken(TipoToken.IDENTIFICADOR);
        consumirToken(TipoToken.ABRE_PARENTESES);
        argumentos();
        consumirToken(TipoToken.FECHA_PARENTESES);
        consumirToken(TipoToken.PONTO_E_VIRGULA);
    }

    private void argumentos() {
        if (!verificarTipoToken(TipoToken.FECHA_PARENTESES)) {
            expressao();
            while (verificarTipoToken(TipoToken.VIRGULA)) {
                consumirToken(TipoToken.VIRGULA);
                expressao();
            }
        }
    }

    private void expressao() {
        expressaoRelacional();
        while (verificarTipoToken(TipoToken.E) || verificarTipoToken(TipoToken.OU)) {
            proximoToken();
            expressaoRelacional();
        }
    }

    private void expressaoRelacional() {
        expressaoAritmetica();
        while (verificarTipoToken(TipoToken.IGUALDADE) || verificarTipoToken(TipoToken.DIFERENTE) ||
        verificarTipoToken(TipoToken.MAIOR) || verificarTipoToken(TipoToken.MENOR) ||
        verificarTipoToken(TipoToken.MAIOR_IGUAL) || verificarTipoToken(TipoToken.MENOR_IGUAL)) {
            proximoToken();
            expressaoAritmetica();
        }
    }

    private void expressaoAritmetica() {
        termo();
        while (verificarTipoToken(TipoToken.ADICAO) || verificarTipoToken(TipoToken.SUBTRACAO)) {
            proximoToken();
            termo();
        }
    }

    private void termo() {
        fator();
        while(verificarTipoToken(TipoToken.MULTIPLICACAO) || verificarTipoToken(TipoToken.DIVISAO) ||
        verificarTipoToken(TipoToken.RESTO)) {
            proximoToken();
            fator();
        }
    }

    private void fator() {
        if (verificarTipoToken(TipoToken.NUMERO_INTEIRO)) {
            consumirToken(TipoToken.NUMERO_INTEIRO);
        } else if (verificarTipoToken(TipoToken.NUMERO_REAL)) {
            consumirToken(TipoToken.NUMERO_REAL);
        } else if (verificarTipoToken(TipoToken.IDENTIFICADOR)) {
            consumirToken(TipoToken.IDENTIFICADOR);
        } else if (verificarTipoToken(TipoToken.CADEIA)) {
            consumirToken(TipoToken.CADEIA);
        } else if (verificarTipoToken(TipoToken.CHAR)) {
            consumirToken(TipoToken.CHAR);
        } else if (verificarTipoToken(TipoToken.VERDADEIRO)) {
            consumirToken(TipoToken.VERDADEIRO);
        } else if (verificarTipoToken(TipoToken.FALSO)) {
            consumirToken(TipoToken.FALSO);
        } else if (verificarTipoToken(TipoToken.ABRE_PARENTESES)) {
            consumirToken(TipoToken.ABRE_PARENTESES);
            expressao(); 
            consumirToken(TipoToken.FECHA_PARENTESES);
        } else {
            throw new RuntimeException("Erro de sintaxe: termo inesperado " + tokenAtual.getTipo());
        }
    }
}
