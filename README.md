# QRコード読み取りアプリ
## デプロイ手順
```
sam build
sam deploy --guided
```

## 動作確認
```
$ curl -X POST -H 'Content-type: image/png' --data-binary "@QR_510236.png" <QRdecodeEndpoint>/
```

## 動作デモ
https://m28dev.github.io/qrdecoder/
