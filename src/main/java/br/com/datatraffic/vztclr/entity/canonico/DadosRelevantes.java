package br.com.datatraffic.vztclr.entity.canonico;

import lombok.ToString;
import lombok.Value;

import java.io.Serializable;

@Value
@ToString(callSuper = true, exclude = {"zoom", "panoramica", "zoom1"})
public class DadosRelevantes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String numeroSerie;
    private String local;
    private String dataVerificacao;
    private byte[] zoom;
    private byte[] panoramica;
    private byte[] panoramica1;
    private byte[] zoom1;
    private Integer codigoInfracao;
    private Short velocidadeAferida;
    private Short velocidadeConsiderada;
    private Short velocidadePermitida;
    private Integer gap;
    private Integer taxaOcupacao;
    private Short idClassificacao;
    private Short faixa;
    private Integer direcao;
    private Short tamanhoVeiculo;
    private boolean displayOK;
    private String dataHoraPassagem;
    private Short distancia;

    private boolean velocidadeOK;
    private Byte reportDisplay;
    private Integer idResposta;
    private Integer blink;
    private boolean blinkOK;
    private Integer red;
    private boolean redOK;
    private Integer green;
    private boolean greenOK;
    private Integer unity;
    private boolean unitOK;
    private Integer ten;
    private boolean tenOK;
    private Integer hund;
    private boolean hundOK;

}