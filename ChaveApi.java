public class ChaveApi {

    // 1. Estado Interno (Atributos privados)
    private String token;
    private String plano;
    private int limiteRequisicoes;
    private int requisicoesRealizadas;
    private boolean ativa;

    // 2. Regras de Inicialização (Construtor)
    public ChaveApi(String token, String plano, int limiteRequisicoes) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("O token não pode ser vazio.");
        }
        if (limiteRequisicoes <= 0) {
            throw new IllegalArgumentException("O limite de requisições deve ser maior que zero.");
        }
        
        this.token = token;
        this.plano = plano;
        this.limiteRequisicoes = limiteRequisicoes;
        
        // Estado inicial garantido
        this.requisicoesRealizadas = 0;
        this.ativa = true;
    }

    // 4. Comportamentos Esperados (Métodos de Negócio)
    
    public void registrarChamada() {
        if (!this.ativa) {
            throw new IllegalStateException("Acesso negado: Chave inativa.");
        }
        
        if (this.requisicoesRealizadas >= this.limiteRequisicoes) {
            throw new IllegalStateException("Acesso negado: Limite de requisições excedido.");
        }
        
        this.requisicoesRealizadas++;
    }

    public void fazerUpgrade(String novoPlano, int novoLimite) {
        if (novoLimite < this.limiteRequisicoes) {
            throw new IllegalArgumentException("Operação rejeitada: O novo limite não pode ser menor que o atual.");
        }
        
        this.plano = novoPlano;
        this.limiteRequisicoes = novoLimite;
    }

    public void resetarCiclo() {
        this.requisicoesRealizadas = 0;
    }

    public void bloquearChave() {
        this.ativa = false;
    }

    public void desbloquearChave() {
        this.ativa = true;
    }

    // Getters para consulta do estado
    public String getToken() { return token; }
    public String getPlano() { return plano; }
    public int getLimiteRequisicoes() { return limiteRequisicoes; }
    public int getRequisicoesRealizadas() { return requisicoesRealizadas; }
    public boolean isAtiva() { return ativa; }
}