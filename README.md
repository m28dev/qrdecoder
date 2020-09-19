# QRコード読み取りアプリ
## デプロイ手順
```
$ mvn clean
$ mvn package shade:shade
$ sam package --output-template-file packaged.yaml --s3-bucket upload-my-bucket
$ sam deploy --template-file packaged.yaml --stack-name simple-qr-decoder --capabilities CAPABILITY_IAM --region ap-northeast-1
```
