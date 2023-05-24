package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.Entity.Configuracao;
import br.com.uniamerica.Estacionamento.Entity.Movimentacao;
import br.com.uniamerica.Estacionamento.Entity.Veiculo;
import br.com.uniamerica.Estacionamento.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;
    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private  ConfiguracaoService configuracaoService;
    public Optional<Movimentacao> procurarMovimentacao(Long id){
        if (!movimentacaoRepository.idExistente(id) ){
            throw new RuntimeException("Esse ID nao esta no banco de dados, verifique e tente novamente");
        }else {
            Optional<Movimentacao> movimentacao = this.movimentacaoRepository.findById(id);
            return movimentacao;
        }
    }
    public List<Movimentacao> listaMovimentacao(){

        List<Movimentacao> movimentacao = movimentacaoRepository.findAll();
        return movimentacao;
    }
    public List<Movimentacao> ativosMovimentacao(){
        List<Movimentacao> movimentacao = movimentacaoRepository.findByAtivoTrue();
        return movimentacao;
    }

    @Transactional(rollbackOn = Exception.class)
    public void cadastrar(final Movimentacao movimentacao){

        if (movimentacao.getCondutor() == null){
            throw new RuntimeException("Condutor Nulo");
        }else if(!veiculoRepository.ProcuraId(movimentacao.getVeiculo().getId())){
            throw new RuntimeException("Veiculo Não Existe No Banco de Dados");
        }else if (veiculoRepository.veiculoExistente(movimentacao.getVeiculo().getId()) && movimentacao.getSaida() == null){
            throw new RuntimeException("Veiculo já está estacionado.");
        } else if (!veiculoRepository.getById(movimentacao.getVeiculo().getId()).isAtivo()) {
            throw new RuntimeException("Veiculo inativo");
        }else if(!condutorRepository.idExistente(movimentacao.getCondutor().getId())){
            throw new RuntimeException("Condutor Não Existe No Banco de Dados");
        } else if (!condutorRepository.getById(movimentacao.getCondutor().getId()).isAtivo()) {
            throw new RuntimeException("Condutor inativo.");
        } else if (movimentacao.getEntrada() == null) {
            throw new RuntimeException("Data de Entrada Nula.");
        } else {
            movimentacaoRepository.save(movimentacao);
        }
    }

    public Movimentacao atualizarMovimentacao(Long id, Movimentacao movimentacaoAtualizado) {
        Movimentacao movimentacaoExistente = movimentacaoRepository.findById(id).orElse(null);
        if (movimentacaoExistente == null) {
            return null;
        } else {
            movimentacaoExistente.setCondutor(movimentacaoAtualizado.getCondutor());
            movimentacaoExistente.setEntrada(movimentacaoAtualizado.getEntrada());
            movimentacaoExistente.setSaida(movimentacaoAtualizado.getSaida());
            movimentacaoExistente.setVeiculo(movimentacaoAtualizado.getVeiculo());
            movimentacaoExistente.setTempoDesconto(movimentacaoAtualizado.getTempoDesconto());
            movimentacaoExistente.setTempoMultaHora(movimentacaoAtualizado.getTempoMultaHora());
            movimentacaoExistente.setValorDesconto(movimentacaoAtualizado.getValorDesconto());
            movimentacaoExistente.setValorHora2(movimentacaoAtualizado.getValorHora2());
            movimentacaoExistente.setValorTotal(movimentacaoAtualizado.getValorTotal());
            movimentacaoExistente.setValorMulta(movimentacaoAtualizado.getValorMulta());
            movimentacaoExistente.setValorHoraMulta(movimentacaoAtualizado.getValorHoraMulta());
            return movimentacaoRepository.save(movimentacaoExistente);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteMovimentacao( @RequestParam("id") final Long id) {
        Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);
        if(movimentacaoRepository.idExistente(movimentacao.getId())){
            movimentacao.setAtivo(false);
            movimentacaoRepository.save(movimentacao);
        }else {
            movimentacaoRepository.delete(movimentacao);
        }
    }
//aq embaca as coisas
    @Transactional(rollbackOn = Exception.class)
    public String finalizarMovimentacao (@RequestParam("id") Long id, Movimentacao movimentacao){
//Aqui eu crio o objetoconfig do tipo configuracao e atribuo as configuracoes do estacionamento pegando la no repositorio
        Configuracao objetoconfig = movimentacaoRepository.obterConfiguracao();
//A partir daqui eu comeco a fazer as checagens
        if (id == null){
            throw new RuntimeException("ID nulo, verifique e tente novamente");
        } else if(!movimentacaoRepository.idExistente(id)) {
            throw new RuntimeException("ID invalido, verifique e tente novamente");
        }else if(movimentacao.getSaida() == null){
            throw new RuntimeException("Data de saida nao informada, verifique e tente novamente");
        }else if (movimentacao.getSaida() != null && movimentacao.getEntrada().isAfter(movimentacao.getSaida())){
            throw new RuntimeException("Erro: A data de entrada esta apos a data de saida, verifique e tente novamente");
        }else if (movimentacao.getCondutor() == null){
            throw new RuntimeException("Condutor Nulo, verifique e tente novamente");
        } else if (!condutorRepository.getById(movimentacao.getCondutor().getId()).isAtivo()) {
            throw new RuntimeException("Este condutor esta inativo.");
        } else if(!condutorRepository.idExistente(movimentacao.getCondutor().getId())){
            throw new RuntimeException("Condutor invalido, verifique e tente novamente");
        } else if (movimentacao.getVeiculo() == null) {
            throw new RuntimeException("Veiculo Nulo, verifique e tente novamente");
        }else if (!veiculoRepository.ProcuraId(movimentacao.getVeiculo().getId())) {
            throw new RuntimeException("Veiculo invalido, verifique e tente novamente");
        }else if (!veiculoRepository.getById(movimentacao.getVeiculo().getId()).isAtivo()) {
            throw new RuntimeException("Veiculo invalido, verifique e tente novamente");
        }else if (veiculoRepository.veiculoExistente(movimentacao.getVeiculo().getId()) && movimentacao.getSaida() == null){
            throw new RuntimeException("O veiculo ainda esta estacionado");
        }  else {
//Comeco a fazer os calculos caso todas as checagens funcionem
//qria calcula a multa mas ta foda
            int tempoMulta = calculaMulta(configuracaoRepository.getById(Long.valueOf(1)), movimentacao);
//Aqui eu atribuo valor para o tempoMulta, chamando a funcao calculaMulta com o valor da multa como parametro
            int calculaTempo = calculaTempo(movimentacao);
//Aqui eu atribuo valor para o calculaTempo, chamando a funcao calculaTempo com os valores da movimentacao como parametro
            int calculatempoCondutor = calculaTempo(movimentacao);
//Aqui eu atribuo valor para o calculatempoCondutor, chamando a funcao calculaTempo com os valores da movimentacao como parametro
//A diferenca eh que aqui eu to calculando o tempo para o codutor

//Atribuindo os valores para as variaveis da movimentacao
            movimentacao.setTempoTotalHora(calculaTempo);
            movimentacao.setTempoTotalMinuto(calculaTempo * 60);
            movimentacao.setTempoMultaMinuto(tempoMulta / 60);
            movimentacao.setTempoMultaHora(tempoMulta);
            movimentacao.setValorHoraMulta(objetoconfig.getValorMinutoMulta());
            movimentacao.setValorHora2(objetoconfig.getValorHora());
            movimentacao.setValorMulta(BigDecimal.valueOf((tempoMulta * objetoconfig.getValorMinutoMulta().intValue())));

//Aqui eu atribuo o ID do meu condutor ao condutorExistente (em int)
            int condutorExistente = Math.toIntExact(movimentacao.getCondutor().getId());
//Aqui eu pego Condutor do repositório de condutores utilizando o ID do condutorExistente e atribuo para o condutorBanco
            Condutor condutorBanco = condutorRepository.getById((long) condutorExistente);
//Aqui eu calculo o tempoNovo somando o tempo total do condutor com o valor da calculatempoCondutor, assim eu vou somando os tempos
            int tempoNovo = condutorBanco.getTempoTotal() + calculatempoCondutor;
//Aqui eu calculo o tempoNovoPago somando o tempo já pago do condutorcom calculatempoCondutor, assim eu consigo o tempopago
            int tempoNovoPago = condutorBanco.getTempoPago() + calculatempoCondutor;
//Aqui apenas atribuo valores para as variaveis mostrar pro usuario final depois
            condutorBanco.setTempoTotal(tempoNovo);
            condutorBanco.setTempoPago(tempoNovoPago);
            movimentacao.getCondutor().setTempoTotal(condutorBanco.getTempoTotal());
//Calculando o tempo de desconto
//Aqui eu reseto o meu tempo de desconto, quando ele passa de 5 eu transformo ele em 0 novamente
            if(condutorBanco.getTempoDesconto() >= 5) {
                condutorBanco.setTempoDesconto(0);
                movimentacao.getCondutor().setTempoDesconto(condutorBanco.getTempoDesconto());
            }
//Aqui eu faco a verificaao se meu TempoPago é maior que 50 ou nao
            if (condutorBanco.getTempoPago() > 50) {
//Caso seja, eu faco o calculo para dar o desconto ao usuario, onde a cada 50 horas o usuario ganharia 5
                condutorBanco.setTempoDesconto((condutorBanco.getTempoPago() / 50 * 5) + condutorBanco.getTempoDesconto());
//Aqui seria o calculo para caso o usuario nao use tudo e deixe para a proxima (perguntar q negocio bizarro acontece qnd eu digito t0d0 o seu desconto)
                condutorBanco.setTempoPago(condutorBanco.getTempoPago() % 50);
            }
//Aqui eu obtenho o objeto Veiculo do repositório e atribuo a variável veiculoBanco
            Veiculo veiculoBanco = veiculoRepository.getById(id);
//Setando mais variaveis para o usuario
            movimentacao.setValorDesconto(BigDecimal.valueOf(condutorBanco.getTempoDesconto() * objetoconfig.getValorHora().intValue()));
            movimentacao.getCondutor().setTempoDesconto(condutorBanco.getTempoDesconto());
            movimentacao.getCondutor().setTempoPago(condutorBanco.getTempoPago());
            movimentacao.setTempoDesconto(condutorBanco.getTempoDesconto());
            movimentacao.getCondutor().setNomeCondutor(condutorBanco.getNomeCondutor());
            movimentacao.getVeiculo().setPlaca(veiculoBanco.getPlaca());
            movimentacao.getVeiculo().setModelo(veiculoBanco.getModelo());
            movimentacao.getVeiculo().setAno(veiculoBanco.getAno());
            movimentacao.getVeiculo().setTipo(veiculoBanco.getTipo());
            movimentacao.getVeiculo().setCor(veiculoBanco.getCor());
            movimentacao.setValorTotal(BigDecimal.valueOf(( calculaTempo - (tempoMulta/60)) * objetoconfig.getValorHora().intValue() + (tempoMulta * objetoconfig.getValorMinutoMulta().intValue()) - (movimentacao.getTempoDesconto() * objetoconfig.getValorHora().intValue())));
            condutorRepository.save(condutorBanco);
            movimentacaoRepository.save(movimentacao);
        }
//Aqui eu retorno para a minha toString feita na entity movimentacao
        return movimentacao.toString();
    }
//Inicion do calculo da multa
    private int calculaMulta(final Configuracao configuracao, final Movimentacao movimentacao){
//Salvo em variaveis os valores de entrada, saida, inicio e fim de expediente
        LocalDateTime entrada = movimentacao.getEntrada();
        LocalDateTime saida = movimentacao.getSaida();
        LocalTime inicioExpediente = configuracao.getInicioExpediente();
        LocalTime fimExpediente = configuracao.getFimExpediente();
//inicialio as variaveis para calculo
        int multa = 0;
        int AnoEntrada = entrada.getYear();
        int saidaAno = saida.getYear();
        int totalDias = 0;
//Calculo o total de dias
        if(AnoEntrada != saidaAno){
            totalDias += saidaAno - AnoEntrada;
        } else{
            totalDias += saida.getDayOfYear() - entrada.getDayOfYear();
        }
//Calculo a multa sendo a duracao entre a entrada e a saida em minnutos e retorno ela
        if (  inicioExpediente.isAfter(entrada.toLocalTime())){
            multa += ((int) Duration.between(entrada.toLocalTime(), inicioExpediente).toMinutes());
        }
        if (fimExpediente.isBefore(saida.toLocalTime()))
        {
            multa += ((int) Duration.between(fimExpediente, saida.toLocalTime()).toMinutes());
        }
        if (totalDias > 0){
            int diferenca = ((int) Duration.between(inicioExpediente, fimExpediente).toMinutes());
            multa +=   (totalDias * 24 * 60 ) - (diferenca);
        }
        return multa;
    }
    public int calculaTempo (final Movimentacao movimentacao){
//Aqui apenas calculo o tempo total sendo ele a diferenca entre entrada e saida, dividindo por 3600 para conseguir em horas
        int tempo=0;
        LocalDateTime tempoEntrada = movimentacao.getEntrada();
        LocalDateTime tempoSaida = movimentacao.getSaida();
        tempo =  (int)  Duration.between(tempoEntrada,tempoSaida).getSeconds()/3600;
        return tempo;
    }
}