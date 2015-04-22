Convert a DER file (.crt .cer .der) to PEM

openssl x509 -inform der -in certificate.cer -out certificate.pem

python pin.py certificate.pem
