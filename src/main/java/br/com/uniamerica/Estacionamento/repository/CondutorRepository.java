package br.com.uniamerica.Estacionamento.repository;
import br.com.uniamerica.Estacionamento.Entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
/*Aqui eu tenho minhas funcoes de verificacao, sao chamadas pelas services
Importante ter em mente que eu to fazendo um extends JpaRepository e algumas funcoes ja vem prontas por conta disso
por exemplo a findbyativotrue, assim eu nao tenho a necessidade de desenvolvver a funcao*/
public interface CondutorRepository extends JpaRepository<Condutor, Long> {
    /*Aqui eu tenho uma lista de condutores onde eu vou pegar apenas os condutores ativos
    Como dito acima, eu nao preciso desenvolver essa funcao pois esta no JpaRepository*/
    List<Condutor> findByAtivoTrue();
    /*Aqui eu faco uma verificacao em SQL procurando se existe algum id igual ao fornecido
    entao eu crio um boolean idExistente que sera true, caso exista o id, e false caso nao exista

     Todos as outras consultas SQL a baixo funcionam com a mesma logica*/
    @Query(value = "select exists (select * from condutores where id = :id)", nativeQuery = true)
    boolean idExistente(@Param("id") final Long id);

    @Query(value = "select exists (select * from condutores where nome = :nome)", nativeQuery = true)
    boolean nomeExistente(@Param("nome") final String nome);

    @Query(value = "select exists (select * from movimentacoes where condutor = :id)", nativeQuery = true)
    boolean condutorExistente(@Param("id") final Long id);
    @Query(value = "select exists (select * from condutores where telefone = :telefone)", nativeQuery = true)
    boolean telefoneExistente(@Param("telefone") final String telefone);

    @Query(value = "select exists (select * from condutores where cpf = :cpf)", nativeQuery = true)
    boolean cpfExistente(@Param("cpf") final String cpf);


}
