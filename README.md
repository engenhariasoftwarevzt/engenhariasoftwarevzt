## Instruções para verificação de assinaturas

- Dentro do PC-DataVison, abrir o browser, obter o certificado:
```sh 
	GET https://192.168.0.1/api/2.0/certificado
	Usuário: admin
	Senha: Data@123
```
-  Exibir código fonte e copiar o conteúdo, certificado e salvar em algum diretório: 
```sh 
    exemplo: /opt/certificado.x509
```
- Executar software extrator dos pacotes e suas respectivas assinaturas, java=1.8
```sh 
java -jar horus-converter-jar-with-dependencies.jar CAMINHO_CERTIFICADO CAMINHO_DIRETORIO_IMAGENS 
	exemplo: java -jar horus-converter-jar-with-dependencies.jar /opt/certificado.x509 /imagens/data
```
 -	Dentro do CAMINHO_DERETORIO_IMAGENS será criado arquivo .package e .signature
```sh 
	.package arquivos que contem o pacote relevante
	.signatue assinatura padrão DER
```
- Verificação assinatura openssl.
```sh    
    Comando, dentro do pc datavision existe essa versão instalada:
    openssl version
	Resposta: OpenSSL 1.0.2g  1 Mar 2016
```
```sh    
	openssl ecparam -list_curves
    Deverá ter essa curva: brainpoolP256r1: RFC 5639 curve over a 256 bit prime field
```
```sh    
	//Exportar chave do certificado x509. X509 é um padrão de certificado de chave pública.
	openssl x509 -pubkey -noout -in certificate.x509 > pubkey.pem
```
```sh    
	//verificar a assinatura
	openssl dgst -sha256 -verify pubkey.pem -signature F_1625489117710.ms.signature F_1625489117710.msg.package 
	Resposta: Verified OK
```

