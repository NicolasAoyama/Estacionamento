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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @Transactional
    public void cadastraMovimentacao(Movimentacao movimentacao){
        if(movimentacao.getVeiculo().getPlaca()==null || movimentacao.getVeiculo().getPlaca().isEmpty()){
            throw new RuntimeException("Verifique a placa e tente novamente");
        }
        if("".equals(movimentacao.getVeiculo().getAno())){
            throw new RuntimeException("Verifique o ano do seu veiculo e tente novamente");
        }
        if(movimentacao.getVeiculo().getMarca().getNomeMarca()==null || movimentacao.getVeiculo().getMarca().getNomeMarca().isEmpty()){
            throw new RuntimeException("Verifique o nome da marca do seu veiculo e tente novamente");
        }
        if("".equals(movimentacao.getCondutor().getNomeCondutor())){
            throw new RuntimeException("Verifique o nome do Condutor e tente novamente");
        }
        if("".equals(movimentacao.getCondutor().getCpf())){
            throw new RuntimeException("Vefique o CPF do condutor e tente novamente");
        }
        this.movimentacaoRepository.save(movimentacao);
    }

    @Transactional
    public void attMovimentacao(final Long id, Movimentacao movimentacao){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);
        if(movimentacaoBanco==null || !movimentacaoBanco.getId().equals(movimentacao.getId())){
            throw new RuntimeException("Registro nao encontrado, verifique e tente novamente");
        }
        if(movimentacao.getVeiculo().getPlaca()==null || movimentacao.getVeiculo().getPlaca().isEmpty()){
            throw new RuntimeException("Verifique a placa e tente novamente");
        }
        if("".equals(movimentacao.getVeiculo().getAno())){
            throw new RuntimeException("Verifique o ano do seu veiculo e tente novamente");
        }
        if(movimentacao.getVeiculo().getMarca().getNomeMarca()==null || movimentacao.getVeiculo().getMarca().getNomeMarca().isEmpty()){
            throw new RuntimeException("Verifique o nome da marca do seu veiculo e tente novamente");
        }
        if("".equals(movimentacao.getCondutor().getNomeCondutor())){
            throw new RuntimeException("Verifique o nome do Condutor e tente novamente");
        }
        if("".equals(movimentacao.getCondutor().getCpf())){
            throw new RuntimeException("Vefique o CPF do condutor e tente novamente");
        }
        this.movimentacaoRepository.save(movimentacao);
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



    @Transactional(rollbackOn = Exception.class)
    public String finalizarMovimentacao (@RequestParam("id") Long id, Movimentacao movimentacao){

        Configuracao objetoconfig = movimentacaoRepository.obterConfiguracao();
        if (id == null){
            throw new RuntimeException("ID nulo, verifique e tente novamente");
        } else if (!movimentacao.getId().equals(id)) {
            throw new RuntimeException("Os IDs nao estao conferindo, verifique e tente novamente");
        }else if(!movimentacaoRepository.idExistente(id)) {
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
        }else if (!veiculoRepository.idExistente(movimentacao.getVeiculo().getId())) {
            throw new RuntimeException("Veiculo invalido, verifique e tente novamente");
        }else if (!veiculoRepository.getById(movimentacao.getVeiculo().getId()).isAtivo()) {
            throw new RuntimeException("Veiculo invalido, verifique e tente novamente");
        }else if (veiculoRepository.veiculoExistente(movimentacao.getVeiculo().getId()) && movimentacao.getSaida() == null){
            throw new RuntimeException("O veiculo ainda esta estacionado");
        }  else {
//TEMPO DENTRO DO ESTACIONAMENTO

            int tempoMulta = calculaMulta(configuracaoRepository.getById(Long.valueOf(1)), movimentacao);
            int calculaTempo = calculaTempo(movimentacao);
            int calculatempoCondutor = calculaTempo(movimentacao);



            movimentacao.setTempoTotalHora(calculaTempo);
            movimentacao.setTempoTotalMinuto(calculaTempo * 60);
            movimentacao.setTempoMultaHora(tempoMulta / 60);
            movimentacao.setTempoMultaMinuto(tempoMulta);


            movimentacao.setValorHoraMulta(objetoconfig.getValorMinutoMulta());
            movimentacao.setValorHora(objetoconfig.getValorHora());


            movimentacao.setValorMulta(BigDecimal.valueOf((tempoMulta * objetoconfig.getValorMinutoMulta().intValue())));

            //(calculaTempo * objConfiguracao.getValorHora().intValue())

//    calculatempoCondutor =+ calculatempoCondutor;
//    movimentacao.getCondutor().setTempototal(calculatempoCondutor);
//
//   objCondutor.setTempototal(calculatempoCondutor);


//    BigDecimal valorHora = configuracaoRepository.getReferenceById(id).getValorHora();
//    BigDecimal valorMinutoMulta = configuracaoRepository.getReferenceById(id).getValorMinutoMulta();

//    //movimentacao.setValorTotal(BigDecimal.valueOf(movimentacao.getTempoTotalhora() * valorHora + movimentacao.getTempoMultaMinuto() * valorMinutoMulta));
//    movimentacao.setValorTotal(BigDecimal.valueOf(movimentacao.getTempoTotalhora() + valorHora.intValue()).add(BigDecimal.valueOf(movimentacao.getTempoMultaMinuto() + valorMinutoMulta.intValue())));


            //TEMPO DO CONDUTOR DO ESTACIONAMENTO

            //CONVERTE UM VALOR LONG EM INT
            int condutorExistente = Math.toIntExact(movimentacao.getCondutor().getId());

            //PESQUISA O NUMERO DO ID CONFORME FOI PASSADO ANTERIORMENTE
            Condutor condutorBanco = condutorRepository.getById((long) condutorExistente);

            //CALCULO DO TEMPO ELE PUXA O TEMPO TOTAL DO BANCO + CALCULO DO RETORNO DE CALCULA TEMPO CONDUTOR
            int tempoNovo = condutorBanco.getTempoTotal() + calculatempoCondutor;

            //CALCULO DO TEMPO ELE PUXA O TEMPO TOTAL DO BANCO + CALCULO DO RETORNO DE CALCULA TEMPO CONDUTOR
            int tempoNovoPago = condutorBanco.getTempoPago() + calculatempoCondutor;

            condutorBanco.setTempoTotal(tempoNovo);
            condutorBanco.setTempoPago(tempoNovoPago);
            movimentacao.getCondutor().setTempoTotal(condutorBanco.getTempoTotal());


            //TEMPO DE DESCONTO

            if(condutorBanco.getTempoDesconto() >= 5) {
                condutorBanco.setTempoDesconto(0);
                movimentacao.getCondutor().setTempoDesconto(condutorBanco.getTempoDesconto());
            }

            if (condutorBanco.getTempoPago() > 50) {
                //CALCULA O VALOR DO DESCONTO
                condutorBanco.setTempoDesconto((condutorBanco.getTempoPago() / 50 * 5) + condutorBanco.getTempoDesconto());

                //CALCULA O TANTO QUE SOBROU DO CONDUTOR PARA DEIXAR ARMAZENADO PARA AS PROXIMAS 50 HORAS PARA DESCONTO
                condutorBanco.setTempoPago(condutorBanco.getTempoPago() % 50);
            }


            Veiculo veiculoBanco = veiculoRepository.getById(id);


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

        return movimentacao.toString();
    }






    private int calculaMulta(final Configuracao configuracao, final Movimentacao movimentacao){


        LocalDateTime entrada = movimentacao.getEntrada();
        LocalDateTime saida = movimentacao.getSaida();
        LocalTime inicioExpediente = configuracao.getInicioExpediente();
        LocalTime fimExpediente = configuracao.getFimExpediente();
        int multa = 0;
        int AnoEntrada = entrada.getYear();
        int saidaAno = saida.getYear();
        int totalDias = 0;

        if(AnoEntrada != saidaAno){
            totalDias += saidaAno - AnoEntrada;
        } else{
            totalDias += saida.getDayOfYear() - entrada.getDayOfYear();
        }
        //if (entrada.toLocalTime().isBefore(inicioExpediente)){
        if (  inicioExpediente.isAfter(entrada.toLocalTime())){
            //multa += ((int) Duration.between(inicioExpediente,saida.toLocalTime()).getSeconds()/60);
            multa += ((int) Duration.between(entrada.toLocalTime(), inicioExpediente).toMinutes());
        }
//        if(saida.toLocalTime().isBefore(fimExpediente))
        if (fimExpediente.isBefore(saida.toLocalTime()))
        {
            //multa += ((int) Duration.between(fimExpediente,saida.toLocalTime()).getSeconds()) / 60 ;
            multa += ((int) Duration.between(fimExpediente, saida.toLocalTime()).toMinutes());

        }
        if (totalDias > 0){
            int diferenca = ((int) Duration.between(inicioExpediente, fimExpediente).toMinutes());//getSeconds()/60);
            multa +=   (totalDias * 24 * 60 ) - (diferenca);
            // multa =   (totalDias * 24 * 60 ); //- (diferenca * totalDias);
            //multa =    (diferenca * totalDias * 60);
            //multa = totalDias;
            //multa = diferenca;
        }



        return multa;
    }

    public int calculaTempo (final Movimentacao movimentacao){
        int tempo=0;
        LocalDateTime tempoEntrada = movimentacao.getEntrada();
        LocalDateTime tempoSaida = movimentacao.getSaida();

        tempo =  (int)  Duration.between(tempoEntrada,tempoSaida).getSeconds()/3600;


        return tempo;

    }


    public int calculaTempoComDesconto(Movimentacao movimentacao, Configuracao configuracao){

        LocalDateTime entrada = movimentacao.getEntrada();
        LocalDateTime saida = movimentacao.getSaida();
        LocalTime inicioExpediente = configuracao.getInicioExpediente();
        LocalTime fimExpediente = configuracao.getFimExpediente();
        int tempoDesconto = 0;
        int AnoEntrada = entrada.getYear();
        int saidaAno = saida.getYear();
        int totalDias = 0;

        if(AnoEntrada != saidaAno){
            totalDias += saidaAno - AnoEntrada;
        } else{
            totalDias += saida.getDayOfYear() - entrada.getDayOfYear();
        }
        if (  inicioExpediente.isAfter(entrada.toLocalTime())){
            tempoDesconto += ((int) Duration.between(entrada.toLocalTime(), inicioExpediente).toMinutes());
        }
        if (fimExpediente.isBefore(saida.toLocalTime()))
        {
            tempoDesconto += ((int) Duration.between(fimExpediente, saida.toLocalTime()).toMinutes());

        }
        if (totalDias > 0){
            int diferenca = ((int) Duration.between(inicioExpediente, fimExpediente).toMinutes());//getSeconds()/60);
            tempoDesconto +=   (totalDias * 24 * 60 ) - (diferenca + movimentacao.getCondutor().getTempoDesconto());

        }


        return tempoDesconto;
    }

}
