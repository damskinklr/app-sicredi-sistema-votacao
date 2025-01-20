package br.com.sicredi.sistemavotacao.infra.persistence;

import br.com.sicredi.sistemavotacao.core.domain.Associado;
import br.com.sicredi.sistemavotacao.core.repository.AssociadoRepository;
import br.com.sicredi.sistemavotacao.infra.persistence.entities.AssociadoEntity;
import br.com.sicredi.sistemavotacao.infra.persistence.mappers.AssociadoMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.UUID;

@Repository
public class AssociadoRepositoryImpl implements AssociadoRepository {

    private final DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;
    private final DynamoDbAsyncTable<AssociadoEntity> table;
    private final AssociadoMapper mapper;

    public AssociadoRepositoryImpl(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient, AssociadoMapper mapper) {
        this.dynamoDbEnhancedAsyncClient = dynamoDbEnhancedAsyncClient;
        this.table = dynamoDbEnhancedAsyncClient.table("associado", TableSchema.fromBean(AssociadoEntity.class));
        this.mapper = mapper;
    }

    @Override
    public Mono<Associado> save(Associado associado) {
        return Mono.fromFuture(table.putItem(mapper.toEntity(associado)))
                .thenReturn(associado);
    }

    @Override
    public Mono<Associado> findById(UUID id) {
        return Mono.fromFuture(table.getItem(r -> r.key(k -> k.partitionValue(id.toString()))))
                .map(mapper::toDomain);
    }
}
