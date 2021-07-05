package br.com.datatraffic.vztclr.entity.canonico;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;
import lombok.Value;


@Data
@Value
@ToString(exclude="assinatura")
public class FluxoAssinado implements Serializable {

	private static final long serialVersionUID = 1L;

	private DadosRelevantes dados;

	private byte[] assinatura;

}
