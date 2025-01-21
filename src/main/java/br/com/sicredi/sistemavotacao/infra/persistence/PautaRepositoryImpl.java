package br.com.sicredi.sistemavotacao.infra.persistence;

import br.com.sicredi.sistemavotacao.core.domain.Pauta;
import br.com.sicredi.sistemavotacao.core.repository.PautaRepository;
import br.com.sicredi.sistemavotacao.infra.persistence.entities.PautaEntity;
import br.com.sicredi.sistemavotacao.infra.persistence.mappers.PautaMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.UUID;

@Repository
public class PautaRepositoryImpl implements PautaRepository {

    private final DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;
    private final DynamoDbAsyncTable<PautaEntity> table;
    private final PautaMapper mapper;

    public PautaRepositoryImpl(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient, PautaMapper mapper) {
        this.dynamoDbEnhancedAsyncClient = dynamoDbEnhancedAsyncClient;
        this.table = dynamoDbEnhancedAsyncClient.table("pauta", TableSchema.fromBean(PautaEntity.class));
        this.mapper = mapper;
    }

    @Override
    public Mono<Pauta> save(Pauta pauta) {
        return Mono.fromFuture(table.putItem(mapper.toEntity(pauta)))
                .thenReturn(pauta);
    }

    @Override
    public Mono<Pauta> findById(UUID id) {
        return Mono.fromFuture(table.getItem(r -> r.key(k -> k.partitionValue(id.toString()))))
                .map(mapper::toDomain);
    }
}
