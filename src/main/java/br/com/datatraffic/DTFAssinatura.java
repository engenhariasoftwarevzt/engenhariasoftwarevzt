package br.com.datatraffic;

import br.com.datatraffic.util.CryptoUtils;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class DTFAssinatura {

    private static final String PROVIDER_DEFUALT = "BC";

    public X509Certificate carregarCertificado(String certificado) throws CertificateException, NoSuchProviderException, FileNotFoundException {
        Security.addProvider(new BouncyCastleProvider());
        CertificateFactory factory = CertificateFactory.getInstance("X.509", PROVIDER_DEFUALT);
        return (X509Certificate) factory.generateCertificate(new FileInputStream(certificado));
    }

    /**
     * Verifica a assinatura.
     *
     * @param pack Pacote de dados
     * @param sign Assinatura
     * @param cert Certificado
     * @return Assinatura válida?
     */
    public boolean verificarAssinatura(byte[] pack, byte[] sign, X509Certificate cert) {
        if (sign == null) {
            return false;
        }
        try {
            String signHex = CryptoUtils.convertByteArrayToHexString(sign);
            byte[] hashFluxoRecebido = CryptoUtils.calcularHashSHA256(pack);
            ECPublicKey publicKey = (ECPublicKey) cert.getPublicKey();
            return validarAssinatura(signHex, hashFluxoRecebido, publicKey);
        } catch (Exception e) {
            System.out.println("Erro ao verificar a assinatura." + e.getMessage());
        }
        return false;
    }

    private boolean validarAssinatura(String signHex, byte[] hashFluxoRecebido, ECPublicKey publicKey) {
        ECParameterSpec ecSpec;
        ECDomainParameters domainParameters;
        ECDSASigner verify = new ECDSASigner();
        ecSpec = publicKey.getParameters();
        domainParameters = new ECDomainParameters(ecSpec.getCurve(), ecSpec.getG(), ecSpec.getN(), ecSpec.getH(), ecSpec.getSeed());
        verify.init(false, new ECPublicKeyParameters(publicKey.getQ(), domainParameters));
        BigInteger r = new BigInteger(signHex.substring(0, signHex.length() / 2), 16);
        BigInteger s = new BigInteger(signHex.substring(signHex.length() / 2), 16);
        return verify.verifySignature(hashFluxoRecebido, r, s);
    }

}
