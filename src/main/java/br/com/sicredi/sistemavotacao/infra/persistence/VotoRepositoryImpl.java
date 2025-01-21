package br.com.sicredi.sistemavotacao.infra.persistence;

import br.com.sicredi.sistemavotacao.core.domain.Voto;
import br.com.sicredi.sistemavotacao.core.repository.VotoRepository;
import br.com.sicredi.sistemavotacao.infra.persistence.entities.VotoEntity;
import br.com.sicredi.sistemavotacao.infra.persistence.mappers.VotoMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.UUID;

@Repository
public class VotoRepositoryImpl implements VotoRepository {


    private final DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;
    private final DynamoDbAsyncTable<VotoEntity> table;
    private final VotoMapper mapper;

    public VotoRepositoryImpl(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient, VotoMapper mapper) {
        this.dynamoDbEnhancedAsyncClient = dynamoDbEnhancedAsyncClient;
        this.table = dynamoDbEnhancedAsyncClient.table("voto", TableSchema.fromBean(VotoEntity.class));
        this.mapper = mapper;
    }

    @Override
    public Mono<Voto> save(Voto voto) {
        return Mono.fromFuture(table.putItem(mapper.toEntity(voto)))
                .thenReturn(voto);
    }

    @Override
    public Mono<Boolean> existsByPautaIdAndAssociadoId(UUID pautaId, String associadoId) {
        var retorno = Mono.fromFuture(table.getItem(Key.builder()
                        .partitionValue(pautaId.toString())
                        .sortValue(associadoId)
                        .build())).block();

        return retorno != null ? Mono.just(true) : Mono.just(false);
    }

    @Override
    public Flux<Voto> findByPautaId(UUID pautaId) {
        return Flux.from(table.query(r -> r.queryConditional(
                        QueryConditional.keyEqualTo(Key.builder().partitionValue(pautaId.toString()).build()))))
                .flatMapIterable(Page::items)
                .map(mapper::toDomain);
    }
}
