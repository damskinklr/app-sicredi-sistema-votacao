package br.com.sicredi.sistemavotacao.infra.persistence.entities;


import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Setter
@DynamoDbBean
public class PautaEntity {
    private String id;
    private String titulo;
    private String descricao;
    private boolean aberta;
    private String abertura;
    private String fechamento;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    @DynamoDbAttribute("titulo")
    public String getTitulo() {
        return titulo;
    }

    @DynamoDbAttribute("descricao")
    public String getDescricao() {
        return descricao;
    }

    @DynamoDbAttribute("abertura")
    public String getAbertura() {
        return abertura;
    }

    @DynamoDbAttribute("fechamento")
    public String getFechamento() {
        return fechamento;
    }

    @DynamoDbAttribute("aberta")
    public boolean isAberta() {
        return aberta;
    }
}
