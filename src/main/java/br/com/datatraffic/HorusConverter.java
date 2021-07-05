package br.com.datatraffic;

import br.com.datatraffic.util.CryptoUtils;
import br.com.datatraffic.util.PojoUtils;
import br.com.datatraffic.vztclr.entity.canonico.DadosRelevantes;
import br.com.datatraffic.vztclr.entity.canonico.FluxoAssinado;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SerializationUtils;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERSequence;

import java.io.File;
import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collection;

public class HorusConverter {

    @SneakyThrows
    public static void main(String[] args) {

        final String CERTIFICADO;
        final String IMAGENS_PATH;

        if (args.length < 2) {
            System.out.println();
            System.out.println("Atenção: Argumentos inválidos!\nUtilize java -jar horus-converter-jar-with-dependencies.jar CERTIFICADO IMAGENS_PATH");
            System.out.println();
            System.exit(0);
        } else {
            CERTIFICADO = args[0];
            IMAGENS_PATH = args[1];

            // Verificador de Assinatura
            final DTFAssinatura signerCheck = new DTFAssinatura();
            final X509Certificate certificate = signerCheck.carregarCertificado(CERTIFICADO);

            // Carrega os arquivos de passagem
            Collection<File> files = FileUtils.listFiles(new File(IMAGENS_PATH), new String[]{"msg"}, true);

            System.out.println();
            System.out.println("Carregando " + IMAGENS_PATH);
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");

            for (File file : files) {

                // DADOS RELEVANTES
                FluxoAssinado fluxo = (FluxoAssinado) SerializationUtils.deserialize(FileUtils.readFileToByteArray(file));
                DadosRelevantes dados = fluxo.getDados();
                System.out.println(dados.getDataHoraPassagem() + " -> " + "Fx. " + dados.getFaixa() + " - Veloc. Aferida: " + dados.getVelocidadeAferida());

                // Extrai o arquivo de dados (binário do openssl)
                File filePack = new File(file.getPath() + ".package");
                byte[] pack = PojoUtils.convertObjectToByteArray(dados);
                System.out.println("Pacote de dados: " + filePack.getPath());
                System.out.println("  Hash (SHA-256): " + CryptoUtils.convertByteArrayToHexString(CryptoUtils.calcularHashSHA256(pack)));
                FileUtils.writeByteArrayToFile(filePack, pack);

                // Extrai o arquivo de assinatura (binário do openssl)
                File fileSign = new File(file.getPath() + ".signature");
                String signHex = CryptoUtils.convertByteArrayToHexString(fluxo.getAssinatura());
                BigInteger r = new BigInteger(signHex.substring(0, signHex.length() / 2), 16);
                BigInteger s = new BigInteger(signHex.substring(signHex.length() / 2), 16);
                ASN1EncodableVector v = new ASN1EncodableVector();
                v.add(new ASN1Integer(r));
                v.add(new ASN1Integer(s));
                byte[] signature = new DERSequence(v).getEncoded();
                FileUtils.writeByteArrayToFile(fileSign, signature);

                // Valida a assinatura
                System.out.println("Assinatura: " + fileSign.getPath());
                System.out.println("  Hex: " + signHex);
                System.out.println("  Base64: " + Base64.getEncoder().encodeToString(signature));
                byte[] sign = fluxo.getAssinatura();
                boolean resultado = signerCheck.verificarAssinatura(pack, sign, certificate);
                System.out.println("Status: " + (resultado ? "assinatura válida" : "assinatura inválida"));

                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");

            }
        }

    }

}
