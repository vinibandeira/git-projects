public class GatewayPagamento {

    public void realizarCobranca(Pagamento pagamento) {

        boolean sucesso = pagamento.processar();

        if (sucesso) {
            pagamento.status = "APROVADO";
        } else {
            pagamento.status = "RECUSADO";
        }

        pagamento.imprimirRecibo();
    }
}