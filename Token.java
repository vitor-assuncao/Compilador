public class Token {
    TipoToken tipo;
    String valor;

    public Token(TipoToken tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
    }
    
    TipoToken getTipo(){
        return tipo;
    }
    
    String getValor(){
        return valor;
    }

    public String toString() {
        return "[" + tipo + ", " + valor + "]";
    }
}                                    

                                           