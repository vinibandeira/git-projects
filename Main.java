public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA DE BILLING API ===");

        // 1. Instanciando uma nova chave
        System.out.println("\n-> Criando nova chave plano Basic (Limite: 3 requisições)");
        ChaveApi minhaChave = new ChaveApi("TOKEN-ABC-123", "Basic", 3);
        
        System.out.println("Status Inicial:");
        System.out.println("Token: " + minhaChave.getToken());
        System.out.println("Requisições realizadas: " + minhaChave.getRequisicoesRealizadas());
        System.out.println("Ativa? " + minhaChave.isAtiva());

        // 2. Testando chamadas dentro do limite
        System.out.println("\n-> Fazendo 3 chamadas na API...");
        minhaChave.registrarChamada(); // 1ª
        minhaChave.registrarChamada(); // 2ª
        minhaChave.registrarChamada(); // 3ª
        System.out.println("Chamadas registradas! Total agora: " + minhaChave.getRequisicoesRealizadas());

        // 3. Testando a proteção: Estouro de limite
        System.out.println("\n-> Tentando fazer a 4ª chamada (deve dar erro)...");
        try {
            minhaChave.registrarChamada();
        } catch (IllegalStateException e) {
            System.out.println("ERRO CAPTURADO: " + e.getMessage()); // Vai imprimir "Limite excedido"
        }

        // 4. Testando a proteção: Upgrade inválido
        System.out.println("\n-> Tentando downgrade de limite para 1 (deve dar erro)...");
        try {
            minhaChave.fazerUpgrade("Gratis", 1);
        } catch (IllegalArgumentException e) {
            System.out.println("ERRO CAPTURADO: " + e.getMessage()); // Vai imprimir "Não pode ser menor"
        }

        // 5. Testando Upgrade válido
        System.out.println("\n-> Fazendo upgrade para o plano Pro (Limite: 5)...");
        minhaChave.fazerUpgrade("Pro", 5);
        System.out.println("Novo limite: " + minhaChave.getLimiteRequisicoes());
        
        System.out.println("Tentando chamar a API novamente (agora deve passar)...");
        minhaChave.registrarChamada(); // 4ª (Agora passa porque o limite é 5)
        System.out.println("Chamada realizada! Total: " + minhaChave.getRequisicoesRealizadas());

        // 6. Testando bloqueio de chave
        System.out.println("\n-> Bloqueando a chave por suspeita de fraude...");
        minhaChave.bloquearChave();
        
        try {
            minhaChave.registrarChamada();
        } catch (IllegalStateException e) {
            System.out.println("ERRO CAPTURADO: " + e.getMessage()); // Vai imprimir "Chave inativa"
        }

        // 7. Testando virada do mês
        System.out.println("\n-> Desbloqueando a chave e resetando ciclo (Dia 1º do mês)...");
        minhaChave.desbloquearChave();
        minhaChave.resetarCiclo();
        System.out.println("Requisições após o reset: " + minhaChave.getRequisicoesRealizadas());
        
        minhaChave.registrarChamada(); // Deve funcionar normalmente
        System.out.println("Chamada realizada com sucesso após o reset!");
    }
}